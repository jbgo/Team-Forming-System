import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

public class AssignTeamsScreen extends Screen implements ActionListener
{
	private Project project;
	private JButton importButton;
	private JComboBox teamSizeCombo;
	private JComboBox assignMethodCombo;
	private StudentsPanel studentPanel;

	private String[] assignMethods = {
		"Random", "Similar skills", "Range of skills", "Average GPA"
	};

	AssignTeamsScreen(Project proj)
	{
		project = proj;

		initComponents();
		buildPanel();
	}

	public void initComponents()
	{
		studentPanel = new StudentsPanel(project.getStudents());

		teamSizeCombo = new JComboBox();
		updateTeamSize();
		assignMethodCombo = new JComboBox();
		initAssignMethodCombo();
	}

	public void buildPanel()
	{
		JPanel col = GuiHelpers.column();
		col.add(new JLabel("Team size"));
		col.add(teamSizeCombo);
		col.add(new JLabel("Assignment method"));
		col.add(assignMethodCombo);
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
	}

	private void updateTeamSize()
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
	JButton addStudent = new JButton("Add");
	JButton editStudent = new JButton("Edit");
	JButton removeStudents = new JButton("Remove");
	StudentsTableModel model;
	JTable table;

	StudentsPanel(Vector<Student> students)
	{
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

	public void reloadData()
	{
		model.fireTableDataChanged();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == addStudent) {
		} else if (source == editStudent) {
		} else if (source == removeStudents) {
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

