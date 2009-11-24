import javax.swing.*;

public class CreateProjectPanel extends Screen
{
	private JTextField courseName;
	private JTextField projectName;
	private JButton createButton;

	public CreateProjectPanel()
	{
		initComponents();
		BuildPanel();
	}

	public String getScreenTitle()
	{
		return "Create Project";
	}

	private void initComponents()
	{
		courseName = new JTextField(20);
		projectName = new JTextField(20);
		createButton = new JButton("Create Project");
	}

	private void BuildPanel()
	{
		JPanel row1 = GuiHelpers.row();
		row1.add(GuiHelpers.margin(5,0));
		row1.add(new JLabel("Course Name: "));
		courseName.setMaximumSize(courseName.getPreferredSize());
		row1.add(courseName);

		JPanel row2 = GuiHelpers.row();
		row2.add(GuiHelpers.margin(5,0));
		row2.add(new JLabel("Project Name: "));
		row2.add(projectName);
		projectName.setMaximumSize(projectName.getPreferredSize());

		JPanel row3 = GuiHelpers.row();
		row3.add(GuiHelpers.margin(85,0));
		row3.add(createButton);

		JPanel vertPanel = GuiHelpers.column();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		vertPanel.add(row1);
		vertPanel.add(row2);
		vertPanel.add(row3);
		vertPanel.add(Box.createVerticalGlue());

		JPanel textFields = GuiHelpers.row();
		textFields.add(vertPanel);
		textFields.add(Box.createHorizontalGlue());

		add(textFields);
	}
}

