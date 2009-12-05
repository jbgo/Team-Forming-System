import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditStudentScreen extends Screen implements ActionListener
{
	Student student;
	Screen parentScreen;

	JTextField lastNameField = new JTextField();
	JTextField firstNameField = new JTextField();
	JTextField emailField = new JTextField();
	JTextField phoneField = new JTextField();
	JTextField GPAField = new JTextField();

	SkillSetPanel skillsPanel;

	JButton updateButton;
	JButton cancelButton = new JButton("Cancel");

	public EditStudentScreen(Screen parent, Student st, boolean isNewStudent)
	{
		parentScreen = parent;
		student = st;
		initComponents(isNewStudent);
		buildPanel();

		if (!isNewStudent) {
			prePopulateFields();
		}
	}

	public String getScreenTitle()
	{
		return "Edit Student";
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		TFSFrame mainFrame = TFSFrame.getInstance();

		if (source == updateButton) {
			student.setFirstName(firstNameField.getText());
			student.setLastName(lastNameField.getText());
			student.setUtdEmail(emailField.getText());

			Project proj = mainFrame.getCurrentProject();
			proj.addStudent(student);
			parentScreen.reloadData(proj);
		} else if (source == cancelButton) {
		}

		mainFrame.setScreen(parentScreen);
	}

	private void initComponents(boolean isNewStudent)
	{
		if (isNewStudent) {
			updateButton = new JButton("Add");
		} else {
			updateButton = new JButton("Update");
		}

		updateButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	private void buildPanel()
	{
		JPanel tempColumn = GuiHelpers.column();
		tempColumn.add(new JLabel("First name: "));
		tempColumn.add(firstNameField);
		tempColumn.add(new JLabel("Last name: "));
		tempColumn.add(lastNameField);
		tempColumn.add(new JLabel("UTD email: "));
		tempColumn.add(emailField);
		tempColumn.add(new JLabel("Phone: "));
		tempColumn.add(phoneField);
		tempColumn.add(new JLabel("GPA: "));
		tempColumn.add(GPAField);
		tempColumn.add(updateButton);
		tempColumn.add(cancelButton);
		add(tempColumn);
	}

	private void prePopulateFields()
	{
	}
}

class SkillSetPanel extends JPanel
{
}

