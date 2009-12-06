/*
class: TFSFrame

This is the main frame for the Team Forming System (TFS) user interface. It contains a
menu for project commands and navigation, a status bar for error messages and
notifications, and an area to view the various screens. Each screen is implemented as a
panel that can be shown within this panel and exchanged with any other panel.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

public class TFSFrame extends JFrame implements ActionListener
{
	public final static long serialVersionUID = 1L;

	private static TFSFrame instance = null;
	
	private static final String emailSuffix = "@utdallas.edu";

	private Project currentProject;
	private Screen currentScreen;

	private JScrollPane contentPane;
	private JTextField statusBar;

	private JMenuItem menuNew;
	private JMenuItem menuOpen;
	private JMenuItem menuSave;
	private JMenuItem menuSaveAs;
	private JMenuItem menuClose;
	private JMenuItem menuExit;

	private JMenuItem menuImportStudents;
	private JMenuItem menuImportRatings;
	
	private CreateProjectScreen cps;

	// Implementing singleton design pattern, use TFSFrame.getInstance()
	private TFSFrame()
	{
		BuildFrame();

		pack();
		setVisible(true);
	}

	public static TFSFrame getInstance()
	{
		if (instance == null) {
			instance = new TFSFrame();
		}

		return instance;
	}

	public void setStatus(String message)
	{
		statusBar.setText(message);
	}

	public void setScreen(Screen screen)
	{
		currentScreen = screen;
		contentPane.setViewportView(screen);
		setTitle(screen.getScreenTitle());
		pack();
		setVisible(true);
	}

	public void showError(String message)
	{
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public Project getCurrentProject()
	{
		if (currentProject == null) {
			currentProject = new Project();
		}
		return currentProject;
	}

	public void setCurrentProject(Project proj)
	{
		currentProject = proj;
		currentScreen.reloadData(proj);
	}

	private void BuildFrame()
	{
		BuildMenu();

		setLayout(new BorderLayout());

		contentPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		                              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.setPreferredSize(new Dimension(600, 480));
		add(contentPane, BorderLayout.CENTER);

		statusBar = new JTextField("...");
		statusBar.setEditable(false);
		add(statusBar, BorderLayout.SOUTH);
	}

	private void BuildMenu()
	{
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuNew = new JMenuItem("New Project");
		menuOpen = new JMenuItem("Open");
		menuSave = new JMenuItem("Save");
		menuSaveAs = new JMenuItem("Save As...");
		menuClose = new JMenuItem("Close");
		menuExit = new JMenuItem("Exit");

		JMenu importMenu = new JMenu("Import");
		menuImportStudents = new JMenuItem("Students");
		menuImportRatings = new JMenuItem("Ratings");
		
		importMenu.add(menuImportStudents);
		importMenu.add(menuImportRatings);

		fileMenu.add(menuNew);
		fileMenu.add(importMenu);
//		fileMenu.add(menuOpen);
//		fileMenu.add(menuSave);
//		fileMenu.add(menuSaveAs);
//		fileMenu.add(menuClose);
		fileMenu.addSeparator();
		fileMenu.add(menuExit);

		menuBar.add(fileMenu);

		setJMenuBar(menuBar);

		registerMenuListeners();
	}

	private void registerMenuListeners()
	{
		menuExit.addActionListener(this);
		menuImportStudents.addActionListener(this);
		menuImportRatings.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		JMenuItem source = (JMenuItem)e.getSource();
		if (source == menuExit) {
			System.exit(0);
		} else if (source == menuImportStudents) {
			setStatus("Importing students");
			importStudents();
		} else if (source == menuImportRatings) {
			setStatus("Import ratings");
			importRatings();
		}
	}

	private void importStudents() {
		File csvFile = getCSVFile();
		if (csvFile == null) {
			setStatus("Import students: no file selected");
			return;
		}

		CSVReader csv;
		try {
			csv = new CSVReader(csvFile);
		} catch (FileNotFoundException ex) {
			setStatus("Error: file not found: " + csvFile.getName());
			return;
		}

		Vector<Student> students = new Vector<Student>();

		if (csv.hasNextLine()) {
			csv.nextLine(); // skip header row
		}

		while (csv.hasNextLine()) {
			String[] fields = csv.nextLine();

			Student s = new Student();
			s.setLastName(fields[0]);
			s.setFirstName(fields[1]);

			if (fields[2].matches("[@]")) {
				s.setUtdEmail(fields[2]);
			} else {
				s.setUtdEmail(fields[2] + emailSuffix);
			}

			if (currentProject != null) {
				s.setSkillSet(currentProject.getRequiredSkills());
			}

			students.add(s);
		}
		csv.close();

		Project proj = getCurrentProject();
		proj.addStudents(students);
		currentScreen.reloadData(proj);
		setStatus("Imported " + students.size() + " students");
	}
	
	public void importRatings()
	{
		File csvFile = getCSVFile();
		if (csvFile == null) {
			setStatus("Import ratings: no file selected");
			return;
		}

		CSVReader csv;
		try {
			csv = new CSVReader(csvFile);
		} catch (FileNotFoundException ex) {
			setStatus("Error: file not found: " + csvFile.getName());
			return;
		}

		Project proj = getCurrentProject();
		/*if(proj==null)
		{
			System.out.println("WTF Mate?");
			System.exit(0);
		}*/
		Vector<String> skillNames = new Vector<String>();
		Vector<String> newSkillNames = new Vector<String>();
		if (csv.hasNextLine())
		{
			// Lets import the column specification and merge with root skills by name, default weight to 1
			String[] fields = csv.nextLine();
			
			for(String field : fields)
			{
				if (field == null || field.matches("^\\s$"))
				{
					setStatus("Error: The header info for " + csvFile.getName() + " is incomplete.");
					return;
				}
			}
			if (fields.length < 2)
			{
				setStatus("Error: There are no skill headers in " + csvFile.getName());
				return;
			}
			for(int index = 1; index < fields.length; index++)
			{
				skillNames.add(fields[index]);
				newSkillNames.add(fields[index]);
			}
			String[] currentNames = proj.getSkillNames();
			for(String name : currentNames)
			{
				if(newSkillNames.contains(name))
				{
					newSkillNames.remove(name);
				}
			}
		}
		HashMap<String, SkillSet> StuIDToSkillSets = new HashMap<String, SkillSet>();
		for(Student s: proj.getStudents())
		{
			System.out.println(s.getUtdEmail());
			StuIDToSkillSets.put(s.getUtdEmail(), s.getSkillSet());
		}

		Vector<String> nullStudents = new Vector<String>();
		int lineNum = 1;
		HashMap<SkillSet,Integer[]> commitHashMap = new HashMap<SkillSet, Integer[]>();
		while (csv.hasNextLine()) {
			lineNum++;
			String[] fields = csv.nextLine();
			if (!fields[0].matches("[@]"))
			{
				fields[0] += emailSuffix;
			}
			System.out.println(fields[0]);
			SkillSet skill = StuIDToSkillSets.get(fields[0]);
			if(skill == null)
			{
				nullStudents.add(fields[0]);
			}
			else
			{
				int fieldSizeDiff = skillNames.size() + 1 - fields.length;
				if(fieldSizeDiff != 0)
				{
					setStatus("Import Ratings err: Line " + lineNum + " is missing " + fieldSizeDiff + " field(s)!");
				}
				Integer[] ratings = new Integer[fields.length - 1];
				int currentIndex = 1;
				try {
					for(int index = 1; index < fields.length; index++)
					{
						currentIndex = index;
						ratings[index - 1] = Integer.parseInt(fields[index]);
					}
				}
				catch(Exception e)
				{
					setStatus("Import Ratings err: Line " + lineNum + " field " + currentIndex + " is not a number!");
					return;
				}
				commitHashMap.put(skill, ratings);
			}
		}
		csv.close();
		
		//Lets commit the changes loaded!
		Iterator<SkillSet> it = commitHashMap.keySet().iterator();
		while(it.hasNext())
		{
			SkillSet ss = it.next();
			Integer[] ratings = commitHashMap.get(ss);
			HashMap <String,Skill> skillsByName = new HashMap<String, Skill>();
			for(Skill sk : ss.getSkills())
			{
				skillsByName.put(sk.getSkillName(), sk);
			}
			for(int index=0; index<skillNames.size();index++)
			{
				String name = skillNames.get(index);
				Skill sk = skillsByName.get(name);
				int rating = ratings[index];
				if(sk == null)
				{
					ss.add(new Skill(name, 1, rating));
				}
				else
				{
					sk.setRating(rating);
				}
			}
		}
		
		for(String newName : newSkillNames)
		{
			Skill s = new Skill(newName, 1);
			proj.addPrototypeSkill(s);
		}
		currentScreen.reloadData(proj);
		//setStatus("Imported " + students.size() + " students");
		setStatus("Imported " + (lineNum - 1 - nullStudents.size()) + " student rating sets" + ((nullStudents.size()==0) ? (".") : (", " + nullStudents.size() + " students could not be matched!")));
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

	public static void main(String[] args)
	{
		TFSFrame mainFrame = TFSFrame.getInstance();
		mainFrame.setStatus("Welcome to the Team Forming System");
		mainFrame.setScreen(new CreateProjectScreen());
	}
}

