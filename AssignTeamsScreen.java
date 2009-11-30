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
	private StudentsPanel studentPanel;

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
		studentPanel = new StudentsPanel();
	}

	public void buildPanel()
	{
		JPanel col = GuiHelpers.column();
		col.add(new JLabel("Assign Teams"));
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
	JButton addStudent = new JButton("Add student");
	JButton removeStudents = new JButton("Remove students");
	StudentsTableModel model = new StudentsTableModel();
	JTable table = new JTable(model);

	StudentsPanel()
	{
		addStudent.addActionListener(this);
		removeStudents.addActionListener(this);

		setColumnWidths(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(560, 200));

		JPanel buttons = GuiHelpers.row();
		buttons.add(addStudent);
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

