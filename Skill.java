class Skill
{
	private String skillName;
	private int weight = 1;
	private int rating = -1; // -1 implies not rated

	public Skill()
	{
	}

	public Skill(String name, int weight)
	{
		skillName = name;
		this.weight = weight;
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

	// value must be in the range [1, 5]
	public void setRating(int value)
	{
		if (value < 1 || value > 5) {
			throw new RuntimeException("rating must be between 1 and 5 inclusive");
		}
		rating = value;
	}
}

