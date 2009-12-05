import java.util.Vector;

class Team
{
	private Vector<Student> members = new Vector<Student>();

	public void addStudent(Student student)
	{
		members.add(student);
	}

	public void removeStudent(Student student)
	{
	}
	
	public void setTeamNumber(int teamNumber)
	{
		for(Student s : members)
		{
			s.setTeamNumber(teamNumber);
		}
	}

	public int teamSize()
	{
		return members.size();
	}

	public Student[] getStudents()
	{
		Student[] students = new Student[teamSize()];
		students = members.toArray(students);
		return students;
	}
	
	public int getRatingSum()
	{
		int sum = 0;
		for(Student s : members)
		{
			sum += s.getRatingSum();
		}
		return sum;
	}
	
	public int getWeightedRatingSum()
	{
		int sum = 0;
		for(Student s : members)
		{
			sum += s.getWeightedRatingSum();
		}
		return sum;
	}
	
	public int getRatingAverageTotal()
	{
		int sum=0;
		for(Student s : members)
		{
			sum += s.getRatingAverage();
		}
		return sum;
	}
	
	public int getRatingAverage()
	{
		return getRatingAverageTotal()/members.size();
	}
	
	public int getWeightedRatingAverageTotal()
	{
		int sum=0;
		for(Student s : members)
		{
			sum += s.getWeightedRatingAverage();
		}
		return sum;
	}
	
	public int getWeightedRatingAverage()
	{
		return getWeightedRatingAverageTotal()/members.size();
	}
	
	public int getGPASum()
	{
		int sum = 0;
		for(Student s : members)
		{
			sum += s.getGPA();
		}
		return sum;
	}
	
	public int getWeightedRatingAverageTotal(Student s)
	{
		Skill[] skills = s.getSkills();
		int[] sums = new int[skills.length];
		for(Student m : members)
		{
			for(int index = 0; index < sums.length; index++)
			{
				if (index >= m.getNumSkills())
				{
					break;
				}
				int rating = m.getSkill(index).getRating();
				if (rating > 0)
				{
					sums[index] += rating;
				}
			}
		}
		int sum = 0;
		for(int index = 0; index < sums.length; index++)
		{
			sum += sums[index] * skills[index].getWeight();
		}
		
		return sum/sums.length;
	}
	
}

