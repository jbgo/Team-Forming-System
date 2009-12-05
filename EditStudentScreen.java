import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class EditStudentScreen extends Screen implements ActionListener
{
	public final static long serialVersionUID = 1L;

	Student student;
	Screen parentScreen;
	TFSFrame mainFrame;

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
		mainFrame = TFSFrame.getInstance();
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

		if (source == updateButton && saveStudentData()) {
			Project proj = mainFrame.getCurrentProject();
			proj.addStudent(student);
			parentScreen.reloadData(proj);
			mainFrame.setScreen(parentScreen);
		} else if (source == cancelButton) {
			mainFrame.setScreen(parentScreen);
		}
	}

	private boolean saveStudentData()
	{
		String errorMessages = "";
		boolean isValid = true;

		String firstName = firstNameField.getText().trim();
		if (firstName.isEmpty()) {
			errorMessages += "Enter a first name.\n";
			isValid = false;
		}

		String lastName = lastNameField.getText().trim();
		if (lastName.isEmpty()) {
			errorMessages += "Enter a last name.\n";
			isValid = false;
		}

		String email = emailField.getText().trim().toLowerCase();
		if (!Pattern.matches("^[a-z]{3}\\d{6}@utdallas\\.edu", email)) {
			errorMessages += "Use a valid UTD email address (e.g. abc123000@utdallas.edu)\n";
			isValid = false;
		}

		String phone = phoneField.getText().trim();
		if (!Pattern.matches("^\\d{10,}$", phone)) {
			errorMessages += "Phone numbers may only contain numbers and must be at least 10 digits long.\n";
			isValid = false;
		}

		String txt = GPAField.getText().trim();
		boolean isGpaValid = true;
		double gpa = 0.0;
		try {
			gpa = Double.parseDouble(txt);
			if (gpa < 0.0 || gpa > 4.0) {
				isGpaValid = false;
			}
		} catch (NumberFormatException ex) {
			isGpaValid = false;
		} finally {
			if (!isGpaValid) {
				errorMessages += "A GPA must be between 0.0 and 4.0.\n";
				isValid = false;
			}
		}

		// TODO: get skillset

		if (!isValid) {
			mainFrame.showError(errorMessages);
		} else {
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setUtdEmail(email);
			student.setPhoneNumber(phone);
			student.setGPA(gpa);
		}

		return isValid;
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
	public final static long serialVersionUID = 1L;
}

