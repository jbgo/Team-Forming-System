class Student
{
	private String firstName;
	private String lastName;
	private String utdEmail;
	private String phoneNumber;
	private double GPA;
	private int teamNumber;
	private SkillSet skillSet;

	// Default constructor
	public Student() {
		this.skillSet = new SkillSet();
		}

	// Convenience constructor
	public Student(String firstName, String lastName, String utdEmail,
	               String phoneNumber, double GPA)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.utdEmail = utdEmail;
		this.phoneNumber = phoneNumber;
		this.GPA = GPA;
		this.skillSet = new SkillSet();
	}

	// Two students are equal only if all of their attributes are equal.
	public boolean equals(Object obj)
	{
		boolean isEqual = false;

		if (obj != null && obj instanceof Student) {
			Student student = (Student)obj;
			if (firstName.equals(student.getFirstName())
			        && lastName.equals(student.getLastName())
				    && utdEmail.equals(student.getUtdEmail())) {
				isEqual = true;
			}
		}

		return isEqual;
	}

	/*
	 * Accessor and mutator methods
	 */

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String value)
	{
		firstName = value;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String value)
	{
		lastName = value;
	}

	public String getUtdEmail()
	{
		return utdEmail;
	}

	public void setUtdEmail(String value)
	{
		utdEmail = value;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String value)
	{
		phoneNumber = value;
	}

	public double getGPA()
	{
		return GPA;
	}

	public void setGPA(double GPA)
	{
		this.GPA = GPA;
	}

	public int getTeamNumber()
	{
		return teamNumber;
	}

	public void setTeamNumber(int value)
	{
		teamNumber = value;
	}
	
	public void addSkill(Skill skill)
	{
		skillSet.add(skill);
	}
	
	public void setSkill(int index, Skill skill)
	{
		skillSet.set(index, skill);
	}
	
	public void removeSkill(Skill skill)
	{
		skillSet.remove(skill);
	}
	
	public Skill[] getSkills()
	{
		return skillSet.getSkills();
	}
	
	public Skill getSkill(int index)
	{
		return skillSet.get(index);
	}

	public SkillSet getSkillSet()
	{
		return skillSet;
	}

	public void setSkillSet(SkillSet ss)
	{
		skillSet = ss;
	}
	
	public int getRatingSum()
	{
		return skillSet.getRatingSum();
	}
	
	public int getWeightedRatingSum()
	{
		return skillSet.getWeightedRatingSum();
	}
	
	public int getRatingAverage()
	{
		return skillSet.getRatingAverage();
	}
	
	public int getWeightedRatingAverage()
	{
		return skillSet.getWeightedRatingAverage();
	}
	
	public int getNumSkills()
	{
		return skillSet.size();
	}
}

