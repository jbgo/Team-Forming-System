/*
class: TFSFrame

This is the main frame for the Team Forming System (TFS) user interface. It contains a
menu for project commands and navigation, a status bar for error messages and
notifications, and an area to view the various screens. Each screen is implemented as a
panel that can be shown within this panel and exchanged with any other panel.
*/

import javax.swing.*;
import java.awt.*;

public class TFSFrame extends JFrame
{
	private static TFSFrame instance = null;

	JScrollPane contentPane;
	JTextField statusBar;

	// Implementing singleton design pattern, use TFSFrame.getInstance()
	private TFSFrame()
	{
		BuildFrame();

		pack();
		setVisible(true);
	}

	public static TFSFrame getInstance()
	{
		if (instance == null) {
			instance = new TFSFrame();
		}

		return instance;
	}

	public void setStatus(String message)
	{
		statusBar.setText(message);
	}

	public void setScreen(Screen screen)
	{
		contentPane.setViewportView(screen);
		setTitle(screen.getScreenTitle());
		pack();
		setVisible(true);
	}

	private void BuildFrame()
	{
		BuildMenu();

		setLayout(new BorderLayout());

		contentPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		                              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.setPreferredSize(new Dimension(600, 480));
		add(contentPane, BorderLayout.CENTER);

		statusBar = new JTextField("...");
		statusBar.setEditable(false);
		add(statusBar, BorderLayout.SOUTH);
	}

	private void BuildMenu()
	{
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem menuNew = new JMenuItem("New Project");
		JMenuItem menuOpen = new JMenuItem("Open");
		JMenuItem menuSave = new JMenuItem("Save");
		JMenuItem menuSaveAs = new JMenuItem("Save As...");
		JMenuItem menuClose = new JMenuItem("Close");
		JMenuItem menuExit = new JMenuItem("Exit");

		fileMenu.add(menuNew);
//		fileMenu.add(menuOpen);
//		fileMenu.add(menuSave);
//		fileMenu.add(menuSaveAs);
//		fileMenu.add(menuClose);
		fileMenu.addSeparator();
		fileMenu.add(menuExit);

		menuBar.add(fileMenu);

		setJMenuBar(menuBar);
	}

	public static void main(String[] args)
	{
		TFSFrame mainFrame = TFSFrame.getInstance();
		mainFrame.setStatus("Welcome to the Team Forming System");
		mainFrame.setScreen(new CreateProjectPanel());
	}
}

