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

	abstract Vector<Team> assignTeams(Vector<Student> students, int minTeamSize);
}

class RandomAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize)
	{
		return null;
	}
}

class SimilarSkillsAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize)
	{
		return null;
	}
}

class SkillRangeAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize)
	{
		return null;
	}
}

class AverageGPAAssigner extends TeamAssigner
{
	Vector<Team> assignTeams(Vector<Student> students, int minTeamSize)
	{
		return null;
	}
}

