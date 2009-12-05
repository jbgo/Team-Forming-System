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
import java.util.Comparator;

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
			case SKILLS_RANGE:
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
		if (numTeams == 1)
		{
			Vector<Team> dumbTeams = new Vector<Team>(numTeams);
			Team t = new Team();
			for(Student s : students)
			{
				t.addStudent(s);
			}
			dumbTeams.add(t);
			return dumbTeams;
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
		if (numTeams == 1)
		{
			Vector<Team> dumbTeams = new Vector<Team>(numTeams);
			Team t = new Team();
			for(Student s : students)
			{
				t.addStudent(s);
			}
			dumbTeams.add(t);
			return dumbTeams;
		}
		ArrayList<Student> studentList = new ArrayList<Student>();
		for(Student student : students)
		{
			studentList.add(student);
		}
		Collections.sort(studentList, new StudentBySimilarSkill());
		Vector<Team> teams = new Vector<Team>(numTeams);
		for (int teamIndex=0; teamIndex<numTeams; teamIndex++)
		{
			teams.add(new Team());
		}
		int studentCounter = 0;
		for (Student student: studentList)
		{
			int largestDist = 0;
			Team teamToAddTo = null;
			int validTeamSize = studentCounter/numTeams;
			for(Team t : teams)
			{
				if (t.teamSize() == validTeamSize)
				{
					int tDist = t.getWeightedRatingAverageTotal(student);
					if (tDist > largestDist)
					{
						largestDist = tDist;
						teamToAddTo = t;
					}
				}
			}
			teamToAddTo.addStudent(student);
			studentCounter++;
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
		if (numTeams == 1)
		{
			Vector<Team> dumbTeams = new Vector<Team>(numTeams);
			Team t = new Team();
			for(Student s : students)
			{
				t.addStudent(s);
			}
			dumbTeams.add(t);
			return dumbTeams;
		}
		ArrayList<Student> studentList = new ArrayList<Student>(students.size());
		for(Student student : students)
		{
			studentList.add(student);
		}
		Collections.sort(studentList, new StudentBySkillRange());
		ArrayList<Team> teams = new ArrayList<Team>(numTeams);
		for (int teamIndex=0; teamIndex<numTeams; teamIndex++)
		{
			teams.add(new Team());
		}
		Vector<Student> top = new Vector<Student>(numTeams);
		int lastVal = studentList.size() - 1;
		Comparator<Team> comp = new TeamByRatingSumI();
		for (int index = 0; index <= lastVal; index++)
		{
			top.add(studentList.get(index));
			
			if (top.size() == numTeams || index == lastVal)
			{
				int teamIndex = 0;
				for(Student s : top)
				{
					teams.get(teamIndex).addStudent(s);
					teamIndex++;
				}
				top.clear();
				if (index != lastVal)
				{
					Collections.sort(teams, comp);
				}
			}
		}
		
		return new Vector<Team>(teams);
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
		if (numTeams == 1)
		{
			Vector<Team> dumbTeams = new Vector<Team>(numTeams);
			Team t = new Team();
			for(Student s : students)
			{
				t.addStudent(s);
			}
			dumbTeams.add(t);
			return dumbTeams;
		}

		ArrayList<Student> studentList = new ArrayList<Student>(students.size());
		
		for(Student student : students)
		{
			studentList.add(student);
		}
		Collections.sort(studentList, new StudentByGPA());
		
		ArrayList<Team> teams = new ArrayList<Team>(numTeams);
		for (int teamIndex=0; teamIndex<numTeams; teamIndex++)
		{
			teams.add(new Team());
		}
		Vector<Student> top = new Vector<Student>(numTeams);
		int lastVal = studentList.size() - 1;
		Comparator<Team> comp = new TeamByGPASumI();
		for (int index = 0; index <= lastVal; index++)
		{
			top.add(studentList.get(index));
			
			if (top.size() == numTeams || index == lastVal)
			{
				int teamIndex = 0;
				for(Student s : top)
				{
					teams.get(teamIndex).addStudent(s);
					teamIndex++;
				}
				top.clear();
				if (index != lastVal)
				{
					Collections.sort(teams, comp);
				}
			}
		}
		
		return new Vector<Team>(teams);
	}
}

class StudentByGPA implements Comparator<Student>
{
	public int compare(Student a, Student b)
	{
		int compValue = 0;
		double gpaA = a.getGPA();
		double gpaB = b.getGPA();
		if (gpaA != gpaB)
		{
			compValue = (gpaA > gpaB) ? -1 : 1;
		}
		return compValue;
 	}
} 

class StudentBySkillRange implements Comparator<Student>
{
	public int compare(Student a, Student b)
	{
		int compValue = 0;
		double skillTotalA = a.getRatingSum();
		double skillTotalB = b.getRatingSum();
		if (skillTotalA != skillTotalB)
		{
			compValue = (skillTotalA > skillTotalB) ? -1 : 1;
		}
		return compValue;
 	}
}

class StudentBySimilarSkill implements Comparator<Student>
{
	public int compare(Student a, Student b)
	{
		int compValue = 0;
		int aveA = a.getWeightedRatingAverage();
		int aveB = b.getWeightedRatingAverage();
		if (aveA != aveB)
		{
			compValue = (aveA > aveB) ? -1 : 1;
		}
		return compValue;
	}
}

class TeamByRatingSumI implements Comparator<Team>
{
	public int compare(Team a, Team b)
	{
		int compValue = 0;
		int sumA = a.getWeightedRatingSum();
		int sumB = b.getWeightedRatingSum();
		if (sumA != sumB)
		{
			compValue = (sumA < sumB) ? -1 : 1;
		}
		return compValue;
	}
}

class TeamByGPASumI implements Comparator<Team>
{
	public int compare(Team a, Team b)
	{
		int compValue = 0;
		int sumA = a.getGPASum();
		int sumB = b.getGPASum();
		if (sumA != sumB)
		{
			compValue = (sumA < sumB) ? -1 : 1;
		}
		return compValue;
	}
}

