/*
class: Project

A project contains a list of students that are assigned to that project. After calling
the assignStudentsToTeams(int minTeamSize, AssignmentMethod method) method, the
students will be organized into a list of teams according to the given AssignmentMethod.
The size of each team will be no less than the minimumTeamSize and no greater than the
minTeamSize + 1. The assignStudentsToTeams() method may be called multiple times, and the
new team assignments will be completely independent from the old team assignments. The
assignStudentToTeam() may also be used in the case that the professor wants to assign a
particular student to a particular team; however, the professor must first call
assignStudentsToTeams() before he can make individual team assignments.
*/

import java.util.Vector;

class Project
{
	private String courseNumber;
	private String projectName;
	private SkillSet skillsetPrototype;

	private int minTeamSize = Settings.DefaultTeamSize;
	private AssignmentMethod assignMethod = Settings.DefaultAssignmentMethod;

	private Vector<Student> students = new Vector<Student>();
	private Vector<Team> teams = new Vector<Team>();

	Project()
	{
	}

	Project(String courseNumber, String projectName, SkillSet requiredSkills)
	{
		this.courseNumber = courseNumber;
		this.projectName = projectName;
		skillsetPrototype = requiredSkills;
	}

	public void setCourseNumber(String courseNumber)
	{
		this.courseNumber = courseNumber;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public SkillSet getRequiredSkills()
	{
		return new SkillSet(skillsetPrototype);
	}

	public void setRequiredSkills(SkillSet requiredSkills)
	{
		skillsetPrototype = requiredSkills;
	}

	public void addStudent(Student st)
	{
		students.add(st);
	}

	public void addStudents(Vector<Student> st)
	{
		students.addAll(st);
	}

	public void removeStudentFromProject(Student student)
	{
	}

	public void assignStudentsToTeams(int minTeamSize, AssignmentMethod method)
	{
	}

	public void assignStudentToTeam(Student student, int teamNumber)
	{
	}

	public void removeStudentFromTeam(Student student, int teamNumber)
	{
	}

	public int getNumStudents()
	{
		return students.size();
	}

	public Vector<Student> getStudents()
	{
		return students;
	}

	public Team[] getTeams()
	{
		Team[] arrTeam = new Team[teams.size()];
		arrTeam = teams.toArray(arrTeam);
		return arrTeam;
	}

	public String getName()
	{
		return projectName;
	}

	public void setName(String value)
	{
		projectName = value;
	}
}

