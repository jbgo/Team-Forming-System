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
	private int minTeamSize = Settings.DefaultTeamSize;
	private AssignmentMethod = Settings.DefaultAssignmentMethod;

	private Vector<Student> students = new Vector<Student>();
	private Vector<Team> teams = new Vector<Team>();

	public void addStudentToProject(Student student)
	{
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

	public Student[] getStudents()
	{
		return students.toArray();
	}

	public Team[] getTeams()
	{
		return teams.toArray();
	}
}

