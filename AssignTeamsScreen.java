import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

public class AssignTeamsScreen extends Screen implements ActionListener
{
	public final static long serialVersionUID = 1L;
	private Project project;
	private JButton assignButton;
	private JComboBox teamSizeCombo;
	private JComboBox assignMethodCombo;
	private StudentsPanel studentPanel;
	private TFSFrame mainFrame;

	private String[] assignMethods = {
		"Random", "Similar skills", "Range of skills", "Average GPA"
	};

	AssignTeamsScreen(Project proj)
	{
		project = proj;
		mainFrame = TFSFrame.getInstance();

		initComponents();
		buildPanel();
	}

	public void initComponents()
	{
		studentPanel = new StudentsPanel(this, project.getStudents());

		teamSizeCombo = new JComboBox();
		updateTeamSize();

		assignMethodCombo = new JComboBox();
		initAssignMethodCombo();

		assignButton = new JButton("Assign Teams");
		assignButton.addActionListener(this);
	}

	public void buildPanel()
	{
		JPanel col = GuiHelpers.column();
		col.add(new JLabel("Team size"));
		col.add(teamSizeCombo);
		col.add(new JLabel("Assignment method"));
		col.add(assignMethodCombo);
		col.add(assignButton);
		col.add(studentPanel);
		add(col);
	}

	public String getScreenTitle()
	{
		return "Assign Students to Teams";
	}

	public void reloadData(Project proj)
	{
		project = proj;
		updateTeamSize();
		studentPanel.reloadData();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == assignButton) {
			doTeamAssignments();
		}
	}

	public void doTeamAssignments()
	{
		AssignmentMethod method;
		TeamAssigner assigner;

		if (project.getNumStudents() < 4) {
			mainFrame.showError("A project must have at least 4 students to assign teams.");
			return;
		}

		AssignmentMethod[] methodTypes = {
			AssignmentMethod.RANDOM, AssignmentMethod.SKILLS_SIMILAR,
			AssignmentMethod.SKILLS_RANGE, AssignmentMethod.AVERAGE_GPA
		};

		int methodIndex = assignMethodCombo.getSelectedIndex();
		method = methodTypes[methodIndex];
		project.setAssignmentMethod(method);

		int minTeamSize = (Integer)teamSizeCombo.getSelectedItem();
		project.setMinTeamSize(minTeamSize);

		assigner = TeamAssigner.getInstance(method);
		Vector<Team> newTeams = null;
		try {
			newTeams = assigner.assignTeams(project.getStudents(), minTeamSize);
		} catch (Exception ex) {
			mainFrame.showError(ex.getMessage());
			return;
		}

		project.removeAllStudents();
		for (int i = 0; i < newTeams.size(); i++) {
			Team team = newTeams.get(i);
			team.setTeamNumber(i + 1);
			for (Student st : team.getStudents()) {
				project.addStudent(st);
			}
		}

		mainFrame.setStatus(newTeams.size() + " teams assigned according to the " 
				+ assignMethods[methodIndex] + " algorithm");

		reloadData(project);
	}

	public void updateTeamSize()
	{
		teamSizeCombo.removeAllItems();

		int numStudents = project.getNumStudents();
		if (numStudents < 4) {
			return;
		}

		int high = numStudents / 2;
		int low = numStudents / high;
		for (int i = low; i <= high; i++) {
			teamSizeCombo.addItem(i);
		}
	}

	private void initAssignMethodCombo()
	{
		for (int i = 0; i < assignMethods.length; i++) {
			assignMethodCombo.addItem(assignMethods[i]);
		}
	}
}

class StudentsPanel extends JPanel implements ActionListener
{
	public final static long serialVersionUID = 1L;

	AssignTeamsScreen parentScreen;
	JButton addStudent = new JButton("Add");
	JButton editStudent = new JButton("Edit");
	JButton removeStudents = new JButton("Remove");
	StudentsTableModel model;
	JTable table;

	StudentsPanel(AssignTeamsScreen parent, Vector<Student> students)
	{
		parentScreen = parent;
		model = new StudentsTableModel(students);
		table = new JTable(model);

		addStudent.addActionListener(this);
		editStudent.addActionListener(this);
		removeStudents.addActionListener(this);

		setColumnWidths(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(560, 200));

		JPanel buttons = GuiHelpers.row();
		buttons.add(addStudent);
		buttons.add(editStudent);
		buttons.add(removeStudents);

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}

	public void addStudents(Vector<Student> students)
	{
		model.addStudents(students);
	}

	public void setStudents(Vector<Student> students)
	{
		model.setStudents(students);
	}

	public void reloadData()
	{
		model.fireTableDataChanged();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		TFSFrame mainFrame = TFSFrame.getInstance();
		if (source == addStudent) {
			Student s = new Student();
			s.setSkillSet(mainFrame.getCurrentProject().getRequiredSkills());
			mainFrame.setScreen(new EditStudentScreen(parentScreen, s, true));
			parentScreen.updateTeamSize();
		} else if (source == editStudent) {
			int index = table.getSelectedRow();
			Student s = model.getStudentForRow(index);
			mainFrame.setScreen(new EditStudentScreen(parentScreen, s, false));
		} else if (source == removeStudents) {
			model.removeSelectedStudents(table.getSelectedRows());
			parentScreen.updateTeamSize();
		}
	}

	private void setColumnWidths(JTable table)
	{
		TableColumn col;
		for (int i = 0; i < model.getColumnCount(); ++i) {
			col = table.getColumnModel().getColumn(i);
			if (i == 0) {
				col.setPreferredWidth(50);
			} else {
				col.setPreferredWidth(150);
			}
		}
	}
}

