class Skill
{
	private String skillName;
	private int weight = 1;
	private int rating = -1;

	public Skill()
	{
	}

	// copy constructor
	public Skill(Skill sk)
	{
		this.skillName = sk.getSkillName();
		this.weight = sk.getWeight();
		this.rating = sk.getRating();
	}

	public Skill(String name, int weight)
	{
		skillName = name;
		this.weight = weight;
		this.rating = rating;
	}

	public Skill(String name, int weight, int rating)
	{
		skillName = name;
		this.weight = weight;
		this.rating = rating;
	}

	public String getSkillName()
	{
		return skillName;
	}

	public void setSkillName(String value)
	{
		skillName = value;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int value)
	{
		if (value <= 0) {
			throw new RuntimeException("weight must be greater than 0");
		}
		weight = value;
	}
	
	public int getRating()
	{
		return rating;
	}
	
	public void setRating(int rating)
	{
		this.rating = rating;
	}
}

