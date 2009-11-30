/*
class: TeamAssigner

TeamAssigner is an abstract class that encapsulates the various team assignment
algorithms using the Strategy design pattern. To get a concrete assigner class,
call the static method TeamAssigner.getInstance(AssignmentMethod) with one of
the AssignmentMethods defined in AssignmentMethod.java. Once you have a TeamAssigner
instance, you simply call instance.assignTeams(Vector<Student>, int) with a list of
students and the number of students per team, and the method will return a list of
teams containing those students.
*/

import java.util.Vector;
import java.util.Collections;
import java.util.ArrayList;

public abstract class TeamAssigner
{
	public static TeamAssigner getInstance(AssignmentMethod method)
	{
		TeamAssigner assigner = null;

		switch (method) {
			case RANDOM:
				assigner = new RandomAssigner();
				break;
			case SKILLS_SIMILAR:
				assigner = new SimilarSkillsAssigner();
				break;
			case SKILLS_RANDOM:
				assigner = new SkillRangeAssigner();
				break;
			case AVERAGE_GPA:
				assigner = new AverageGPAAssigner();
				break;
		}

		return assigner;
	}

	abstract Vector<Team> assignTeams(Vector<Student> students, int minTeamSize) throws Exception;
}

class RandomAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize) throws Exception
	{
		int numTeams = students.size()/minTeamSize;
		if (numTeams == 0)
		{
			throw new Exception("There are not enought students to construct even one team...");
		}
		ArrayList<Student> studentList = new ArrayList<Student>();
		for(Student student : students)
		{
			studentList.add(student);
		}
		Collections.shuffle(studentList);
		Vector<Team> teams = new Vector<Team>(numTeams);
		for (int teamIndex=0; teamIndex<numTeams; teamIndex++)
		{
			teams.add(new Team());
		}
		int teamIndex = 0;
		for (Student student: studentList)
		{
			teams.get(teamIndex).addStudent(student);
			teamIndex++;
			if (teamIndex==numTeams) {
				teamIndex=0;
			}
		}
		studentList.clear();
		studentList=null;
		
		return teams;
	}
}

class SimilarSkillsAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize) throws Exception
	{
		int numTeams = students.size()/minTeamSize;
		if (numTeams == 0)
		{
			throw new Exception("There are not enought students to construct even one team...");
		}
		ArrayList<Student> studentList = new ArrayList<Student>();
		for(Student student : students)
		{
			studentList.add(student);
		}
		Collections.shuffle(studentList);
		Vector<Team> teams = new Vector<Team>(numTeams);
		for (int teamIndex=0; teamIndex<numTeams; teamIndex++)
		{
			teams.add(new Team());
		}
		int teamIndex = 0;
		for (Student student: studentList)
		{
			teams.get(teamIndex).addStudent(student);
			teamIndex++;
			if (teamIndex==numTeams) {
				teamIndex=0;
			}
		}
		studentList.clear();
		studentList=null;
		
		return teams;
	}
}

class SkillRangeAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize) throws Exception
	{
		int numTeams = students.size()/minTeamSize;
		if (numTeams == 0)
		{
			throw new Exception("There are not enought students to construct even one team...");
		}
		ArrayList<Student> studentList = new ArrayList<Student>();
		for(Student student : students)
		{
			studentList.add(student);
		}
		Collections.shuffle(studentList);
		Vector<Team> teams = new Vector<Team>(numTeams);
		for (int teamIndex=0; teamIndex<numTeams; teamIndex++)
		{
			teams.add(new Team());
		}
		int teamIndex = 0;
		for (Student student: studentList)
		{
			teams.get(teamIndex).addStudent(student);
			teamIndex++;
			if (teamIndex==numTeams) {
				teamIndex=0;
			}
		}
		studentList.clear();
		studentList=null;
		
		return teams;
	}
}

class AverageGPAAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize) throws Exception
	{
		int numTeams = students.size()/minTeamSize;
		if (numTeams == 0)
		{
			throw new Exception("There are not enought students to construct even one team...");
		}
		ArrayList<Student> studentList = new ArrayList<Student>();
		for(Student student : students)
		{
			studentList.add(student);
		}
		Collections.shuffle(studentList);
		Vector<Team> teams = new Vector<Team>(numTeams);
		for (int teamIndex=0; teamIndex<numTeams; teamIndex++)
		{
			teams.add(new Team());
		}
		int teamIndex = 0;
		for (Student student: studentList)
		{
			teams.get(teamIndex).addStudent(student);
			teamIndex++;
			if (teamIndex==numTeams) {
				teamIndex=0;
			}
		}
		studentList.clear();
		studentList=null;
		
		return teams;
	}
}

