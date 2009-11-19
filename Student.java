class Student
{
	private String firstName;
	private String lastName;
	private String utdEmail;
	private String phoneNumber;
	private double GPA;

	// Default constructor
	public Student() { }

	// Convenience constructor
	public Student(String firstName, String lastName, String utdEmail,
	               String phoneNumber, double GPA)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.utdEmail = utdEmail;
		this.phoneNumber = phoneNumber;
		this.GPA = GPA;
	}

	// Two students are equal only if all of their attributes are equal.
	public boolean equals(Object obj)
	{
		boolean isEqual = false;

		if (obj != null && obj instanceof Student) {
			Student student = (Student)obj;
			if (firstName.equals(student.getFirstName()
			        && lastName.equals(student.getLastName()
				    && utdEmail.equals(student.getUtdEmail()
					&& phoneNumber.equals(student.getPhoneNumber()
					&& GPA == obj.getGPA()) {
				isEqual = true;
			}
		}

		return true;
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
}

