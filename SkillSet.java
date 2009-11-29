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

	public void add(Skill skill)
	{
		skills.add(skill);
	}

	public Skill[] getSkills()
	{
		Skill[] arrSkill = new Skill[skills.size()];
		arrSkill = skills.toArray(arrSkill);
		return arrSkill;
	}

	public Skill get(int index)
	{
		return skills.get(index);
	}

	public void set(int index, Skill skill)
	{
		skills.set(index, skill);
	}

	public void remove(Skill sk)
	{
		skills.remove(sk);
	}

	public int size()
	{
		return skills.size();
	}
}

