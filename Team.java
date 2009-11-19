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
}

