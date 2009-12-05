/*
class: TFSFrame

This is the main frame for the Team Forming System (TFS) user interface. It contains a
menu for project commands and navigation, a status bar for error messages and
notifications, and an area to view the various screens. Each screen is implemented as a
panel that can be shown within this panel and exchanged with any other panel.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileNotFoundException;

public class TFSFrame extends JFrame implements ActionListener
{
	private static TFSFrame instance = null;

	private Project currentProject;
	private Screen currentScreen;

	private JScrollPane contentPane;
	private JTextField statusBar;

	private JMenuItem menuNew;
	private JMenuItem menuOpen;
	private JMenuItem menuSave;
	private JMenuItem menuSaveAs;
	private JMenuItem menuClose;
	private JMenuItem menuExit;

	private JMenuItem menuImportStudents;
	private JMenuItem menuImportRatings;

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
		currentScreen = screen;
		contentPane.setViewportView(screen);
		setTitle(screen.getScreenTitle());
		pack();
		setVisible(true);
	}

	public Project getCurrentProject()
	{
		if (currentProject == null) {
			currentProject = new Project();
		}
		return currentProject;
	}

	public void setCurrentProject(Project proj)
	{
		currentProject = proj;
		currentScreen.reloadData(proj);
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
		menuNew = new JMenuItem("New Project");
		menuOpen = new JMenuItem("Open");
		menuSave = new JMenuItem("Save");
		menuSaveAs = new JMenuItem("Save As...");
		menuClose = new JMenuItem("Close");
		menuExit = new JMenuItem("Exit");

		JMenu importMenu = new JMenu("Import");
		menuImportStudents = new JMenuItem("Students");
		menuImportRatings = new JMenuItem("Ratings");
		
		importMenu.add(menuImportStudents);
		importMenu.add(menuImportRatings);

		fileMenu.add(menuNew);
		fileMenu.add(importMenu);
//		fileMenu.add(menuOpen);
//		fileMenu.add(menuSave);
//		fileMenu.add(menuSaveAs);
//		fileMenu.add(menuClose);
		fileMenu.addSeparator();
		fileMenu.add(menuExit);

		menuBar.add(fileMenu);

		setJMenuBar(menuBar);

		registerMenuListeners();
	}

	private void registerMenuListeners()
	{
		menuExit.addActionListener(this);
		menuImportStudents.addActionListener(this);
		menuImportRatings.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		JMenuItem source = (JMenuItem)e.getSource();
		if (source == menuExit) {
			System.exit(0);
		} else if (source == menuImportStudents) {
			setStatus("Importing students");
			importStudents();
		} else if (source == menuImportRatings) {
			setStatus("Import ratings");
		}
	}

	private void importStudents() {
		File csvFile = getCSVFile();
		if (csvFile == null) {
			setStatus("Import students: no file selected");
			return;
		}

		CSVReader csv;
		try {
			csv = new CSVReader(csvFile);
		} catch (FileNotFoundException ex) {
			setStatus("Error: file not found: " + csvFile.getName());
			return;
		}

		Vector<Student> students = new Vector<Student>();
		while (csv.hasNextLine()) {
			String[] fields = csv.nextLine();
			Student s = new Student();
			s.setLastName(fields[0]);
			s.setFirstName(fields[1]);
			s.setUtdEmail(fields[2] + "@utdallas.edu");
			students.add(s);
		}
		csv.close();

		Project proj = getCurrentProject();
		proj.addStudents(students);
		currentScreen.reloadData(proj);
		setStatus("Imported " + students.size() + " students");
	}

	private File getCSVFile() {
		JFileChooser chooser = new JFileChooser();

		// only show CSV files
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}

				if (f.getName().endsWith(".csv")) {
					return true;
				} else {
					return false;
				}
			}

			public String getDescription() {
				return "CSV files";
			}
		});

		int status = chooser.showOpenDialog(this);
		if (status == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		return null;
	}

	public static void main(String[] args)
	{
		TFSFrame mainFrame = TFSFrame.getInstance();
		mainFrame.setStatus("Welcome to the Team Forming System");
		mainFrame.setScreen(new CreateProjectScreen());
	}
}

