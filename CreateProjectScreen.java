import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateProjectScreen extends Screen implements ActionListener
{
	public final static long serialVersionUID = 1L;

	private JTextField courseNumber;
	private JTextField projectName;
	private JButton createButton;
	private CreateSkillsPanel skillsPanel;
	private TFSFrame mainFrame;

	public CreateProjectScreen()
	{
		mainFrame = TFSFrame.getInstance();
		initComponents();
		BuildPanel();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == createButton) {
			// TODO: validate data

			Project proj = mainFrame.getCurrentProject();
			String courseNo = courseNumber.getText();
			String projName = projectName.getText();
			SkillSet skills = skillsPanel.getSkillSet();
			proj.setCourseNumber(courseNo);
			proj.setProjectName(projName);
			proj.setRequiredSkills(skills);

			// add skillset to each student
			for (Student s : proj.getStudents()) {
				s.setSkillSet(new SkillSet(skills));
			}

			mainFrame.setCurrentProject(proj);
			mainFrame.setStatus("Created project: " + projName);
			mainFrame.enableImportMenu();
			mainFrame.setScreen(new AssignTeamsScreen(proj));
		}
	}

	public String getScreenTitle()
	{
		return "Create Project";
	}

	public void reloadData(Project proj)
	{
		skillsPanel.setSkillSet(proj.getRequiredSkills());
	}

	private void initComponents()
	{
		courseNumber = new JTextField(20);
		projectName = new JTextField(20);
		createButton = new JButton("Create Project");
		skillsPanel = new CreateSkillsPanel();

		createButton.addActionListener(this);
	}

	private void BuildPanel()
	{
		JPanel row1 = GuiHelpers.row();
		row1.add(GuiHelpers.margin(5,0));
		row1.add(new JLabel("Course Number: "));
		courseNumber.setMaximumSize(courseNumber.getPreferredSize());
		row1.add(courseNumber);
		row1.add(Box.createHorizontalGlue());

		JPanel row2 = GuiHelpers.row();
		row2.add(GuiHelpers.margin(5,0));
		row2.add(new JLabel("Project Name: "));
		row2.add(projectName);
		projectName.setMaximumSize(projectName.getPreferredSize());
		row2.add(Box.createHorizontalGlue());
		row2.add(createButton);

		JPanel vertPanel = GuiHelpers.column();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		vertPanel.add(row1);
		vertPanel.add(row2);
		vertPanel.add(GuiHelpers.margin(0,20));
		vertPanel.add(skillsPanel);

		JPanel textFields = GuiHelpers.row();
		textFields.add(vertPanel);
		textFields.add(Box.createHorizontalGlue());

		add(textFields);
	}
}

class CreateSkillsPanel extends JPanel implements ActionListener
{
	public final static long serialVersionUID = 1L;

	JButton addSkill = new JButton("Add skill");
	JButton removeSkill = new JButton("Remove skills");
	CreateSkillsTableModel model = new CreateSkillsTableModel();
	JTable table = new JTable(model);

	CreateSkillsPanel()
	{
		addSkill.addActionListener(this);
		removeSkill.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(560, 200));

		JPanel buttons = GuiHelpers.row();
		buttons.add(addSkill);
		buttons.add(removeSkill);

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == addSkill) {
			Skill s = new Skill("New Skill", 1);
			model.addSkill(s);
		} else if (e.getSource() == removeSkill) {
			model.removeSelectedSkills(table.getSelectedRows());
		}
	}

	public SkillSet getSkillSet()
	{
		return model.getSkillSet();
	}

	public void setSkillSet(SkillSet skills)
	{
		model.setSkillSet(skills);
	}
}

