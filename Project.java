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
import java.util.HashMap;
import java.util.Iterator;

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
		skillsetPrototype = new SkillSet();
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
		HashMap<String,Skill> prototypeSkillsByName = new HashMap<String,Skill>();
		for(Skill s : skillsetPrototype.getSkills())
		{
			prototypeSkillsByName.put(s.getSkillName(), s);
		}
		for(Student s : students)
		{
			HashMap<String,Skill> studentSkillsByName = new HashMap<String,Skill>();
			for(Skill sk : s.getSkills())
			{
				studentSkillsByName.put(sk.getSkillName(), sk);
			}
			Iterator<String> it = prototypeSkillsByName.keySet().iterator();
			while (it.hasNext())
			{
				String key = it.next();
				Skill pk = prototypeSkillsByName.get(key);
				Skill sk = studentSkillsByName.get(key);
				if(sk == null)
				{
					sk = new Skill(key, pk.getWeight(), pk.getRating());
					studentSkillsByName.put(key, sk);
					s.addSkill(sk);
				}
				else
				{
					sk.setWeight(pk.getWeight());
				}
			}
		}
	}

	public void addStudent(Student st)
	{
		students.add(st);
	}

	public void addStudents(Vector<Student> st)
	{
		students.addAll(st);
	}

	public void removeAllStudents()
	{
		students.removeAllElements();
	}

	public int getNumStudents()
	{
		return students.size();
	}

	public Vector<Student> getStudents()
	{
		return students;
	}

	public Vector<Team> getTeams()
	{
//		Team[] arrTeam = new Team[teams.size()];
//		arrTeam = teams.toArray(arrTeam);
//		return arrTeam;
		return teams;
	}

	public void setTeams(Vector<Team> teams)
	{
		this.teams = teams;
	}

	public String getName()
	{
		return projectName;
	}

	public void setName(String value)
	{
		projectName = value;
	}
	
	public String[] getSkillNames()
	{
		return skillsetPrototype.getNames();
	}
	
	public void addPrototypeSkill(Skill s)
	{
		skillsetPrototype.add(s);
	}
	
	public void setMinTeamSize(int size)
	{
		minTeamSize = size;
	}

	public void setAssignmentMethod(AssignmentMethod method)
	{
		assignMethod = method;
	}
}

