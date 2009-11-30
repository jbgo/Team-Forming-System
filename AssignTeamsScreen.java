import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AssignTeamsScreen extends Screen implements ActionListener
{
	Project project;

	AssignTeamsScreen(Project proj)
	{
		project = proj;

		initComponents();
		buildPanel();
	}

	public void initComponents()
	{
	}

	public void buildPanel()
	{
		add(new JLabel("Assign Teams"));
	}

	public String getScreenTitle()
	{
		return "Assign Students to Teams";
	}

	public void actionPerformed(ActionEvent e)
	{
	}
}

