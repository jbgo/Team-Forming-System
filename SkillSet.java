/*
class: Skillset

The professor must create a SkillSet instance for a Project if he wishes to assign teams
based on skill level. The SkillSet he creates will consists of Skills with names but no
ratings. When creating Students instance, the SkillSet for the project will be cloned,
and the ratings will be specifically set for that Student.
*/

import java.util.Vector;

class SkillSet
{
	private Vector<Skill>skills = new Vector<Skill>();
	//private HashMap<Skill,Integer> skills = new HashMap<Skill,Integer>();

	public SkillSet() { }

	// copy constructor
	public SkillSet(SkillSet ss)
	{
		for (Skill sk : ss.getSkills()) {
			skills.add(new Skill(sk));
		}
	}

	public void add(Skill skill)
	{
		//skills.put(skill, rating);
		//if the skill already exists, then the key is over written
		skills.add(skill);
	}

	public Skill[] getSkills()
	{
		Skill[] arrSkills = new Skill[skills.size()];
		return skills.toArray(arrSkills);
	}

	public Skill get(int index)
	{
		return skills.get(index);
	}

	public void set(int index, Skill sk)
	{
		skills.set(index, sk);
	}
	
	public int getRatingSum()
	{
		int sum = 0;
		for (Skill sk : skills)
		{
			int x = sk.getRating();
			if (x > 0)
			{
				sum += x;
			}
		}
		return sum;
	}
	
	public int getWeightedRatingSum()
	{
		int sum = 0;
		for(Skill sk : skills)
		{
			int rating = sk.getRating();
			if (rating > 0)
			{
				sum += sk.getWeight() * rating;
			}
		}
		return sum;
	}
	
	public int getRatingAverage()
	{
		return getRatingSum()/skills.size();
	}

	public int getWeightedRatingAverage()
	{
		return getWeightedRatingSum()/skills.size();
	}

	public void remove(Skill sk)
	{
		skills.remove(sk);
	}
	
	public void remove(int index)
	{
		skills.remove(index);
	}

	public int size()
	{
		return skills.size();
	}
	
	public String[] getNames()
	{
		String[] names = new String[skills.size()];
		for(int index = 0; index < names.length; index++)
		{
			names[index] = skills.get(index).getSkillName();
		}
		return names;
	}
}

