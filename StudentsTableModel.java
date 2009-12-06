import javax.swing.table.*;
import java.util.Vector;

public class StudentsTableModel extends AbstractTableModel
{
	public final static long serialVersionUID = 1L;

	Vector<Student> students;
	String[] columnNames = {
		"Team #", "Last Name", "First Name", "UTD Email"
	};

	public StudentsTableModel(Vector<Student> students)
	{
		this.students = students;
	}

	public void addStudents(Vector<Student> studentList)
	{
		students.addAll(studentList);
		fireTableDataChanged();
	}

	public void setStudents(Vector<Student> studentList)
	{
		students = studentList;
		fireTableDataChanged();
	}

	public Student getStudentForRow(int row)
	{
		return students.get(row);
	}

	public void removeSelectedStudents(int[] rows)
	{
		Student[] st = new Student[rows.length];
		for (int i = 0; i < rows.length; ++i) {
			st[i] = students.get(rows[i]);
		}

		for (int i = 0; i < st.length; ++i) {
			students.remove(st[i]);
		}

		fireTableDataChanged();
	}

	// overrides for AbstractTableModel methods

	public int getRowCount()
	{
		return students.size();
	}

	public int getColumnCount()
	{
		return columnNames.length;
	}

	public Object getValueAt(int row, int column)
	{
		Student st = students.get(row);
		switch (column) {
			case 0:
				int teamNo = st.getTeamNumber();
				return (teamNo == 0) ? "-" : teamNo;
			case 1:
				return st.getLastName();
			case 2:
				return st.getFirstName();
			case 3:
				return st.getUtdEmail();
		}

		return null;
	}

	public String getColumnName(int col)
	{
		return columnNames[col];
	}
}

