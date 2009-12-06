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
	private JComboBox viewTeamsCombo;
	private StudentsPanel studentPanel;
	private TFSFrame mainFrame;
	private int minTeamSize = 2;
	private int selectedTeamNumber = 0;

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

		viewTeamsCombo = new JComboBox();
		initViewTeamsCombo();

		assignButton = new JButton("Assign Teams");
		assignButton.addActionListener(this);
	}

	public void buildPanel()
	{
		JPanel col = GuiHelpers.column();

		JPanel row = GuiHelpers.row();
		row.add(new JLabel("Team size: "));
		teamSizeCombo.setMaximumSize(teamSizeCombo.getPreferredSize());
		row.add(teamSizeCombo);
		row.add(Box.createHorizontalGlue());
		col.add(row);

		row = GuiHelpers.row();
		row.add(new JLabel("Assignment method: "));
		assignMethodCombo.setMaximumSize(assignMethodCombo.getPreferredSize());
		row.add(assignMethodCombo);
		row.add(Box.createHorizontalGlue());
		col.add(row);

		row = GuiHelpers.row();
		row.add(assignButton);
		row.add(Box.createHorizontalGlue());
		col.add(row);

		row = GuiHelpers.row();
		row.add(Box.createHorizontalGlue());
		row.add(new JLabel("View team: "));
		viewTeamsCombo.setMaximumSize(viewTeamsCombo.getPreferredSize());
		row.add(viewTeamsCombo);
		col.add(row);

		col.add(GuiHelpers.margin(0,20));
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
		updateViewTeamsCombo();
		studentPanel.reloadData();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == assignButton) {
			doTeamAssignments();
		} else if (source == viewTeamsCombo) {
			showSelectedTeam();
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

		minTeamSize = (Integer)teamSizeCombo.getSelectedItem();
		project.setMinTeamSize(minTeamSize);

		assigner = TeamAssigner.getInstance(method);
		Vector<Team> newTeams = null;
		try {
			newTeams = assigner.assignTeams(project.getStudents(), minTeamSize);
		} catch (Exception ex) {
			mainFrame.showError(ex.getMessage());
			return;
		}

		project.setTeams(newTeams);

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

		if (minTeamSize >= low && minTeamSize <= high) {
			teamSizeCombo.setSelectedIndex(minTeamSize - low);
		}
	}

	private void initAssignMethodCombo()
	{
		for (int i = 0; i < assignMethods.length; i++) {
			assignMethodCombo.addItem(assignMethods[i]);
		}
	}

	private void initViewTeamsCombo()
	{
		viewTeamsCombo.addActionListener(this);
		updateViewTeamsCombo();
	}

	private void updateViewTeamsCombo()
	{
		viewTeamsCombo.removeAllItems();
		viewTeamsCombo.addItem("All Teams");

		Vector<Team> teams = project.getTeams();
		for (int i = 1; i <= teams.size(); i++) {
			viewTeamsCombo.addItem("Team " + i);
		}

		if (selectedTeamNumber >= 0 && selectedTeamNumber < teams.size()) {
			viewTeamsCombo.setSelectedIndex(selectedTeamNumber);
		}
	}

	private void showSelectedTeam()
	{
		int teamNo = viewTeamsCombo.getSelectedIndex();
		Vector<Team> teams = project.getTeams();

		selectedTeamNumber = teamNo;

		if (teams.size() < 2) {
			// mainFrame.showError("No teams to choose from");
			return;
		}
		
		if (teamNo == 0) {
			project.removeAllStudents();
			for (Team t : teams) {
				for (Student s : t.getStudents()) {
					project.addStudent(s);
				}
			}
			mainFrame.setStatus("Showing all students");
		}

		Vector<Student> students = project.getStudents();
		Team team = null;
		for (Team t : teams) {
			if (t.getStudents()[0].getTeamNumber() == teamNo) {
				team = t;
				break;
			}
		}

		if (team == null) { // show all teams
			// mainFrame.showError("Could not find team #" + teamNo);
		} else {
			project.removeAllStudents();
			for (Student s : team.getStudents()) {
				project.addStudent(s);
			}
			mainFrame.setStatus("Showing students in team #" + teamNo);
		}
		studentPanel.reloadData();
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
			if (index < 0) {
				mainFrame.setStatus("You must select a row to edit first");
				return;
			}
			Student s = model.getStudentForRow(index);
			mainFrame.setScreen(new EditStudentScreen(parentScreen, s, false));
		} else if (source == removeStudents) {
			int[] selRows = table.getSelectedRows();
			if (selRows.length == 0) {
				mainFrame.setStatus("You must select a row to remove first");
				return;
			}
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

