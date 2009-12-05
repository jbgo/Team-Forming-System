import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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
		importButton = new JButton("Import Students");
		importButton.addActionListener(this);
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
		col.add(importButton);
		col.add(studentPanel);
		add(col);
	}

	public String getScreenTitle()
	{
		return "Assign Students to Teams";
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == importButton) {
			importStudents();
			updateTeamSize();
		}
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

	private void importStudents() {
		TFSFrame mainFrame = TFSFrame.getInstance();
		File csvFile = getCSVFile();
		if (csvFile == null) {
			mainFrame.setStatus("Import students: no file selected");
			return;
		}

		CSVReader csv;
		try {
			csv = new CSVReader(csvFile);
		} catch (FileNotFoundException ex) {
			mainFrame.setStatus("Error: file not found: " + csvFile.getName());
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

		studentPanel.addStudents(students);
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

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == addStudent) {
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

