import javax.swing.table.*;

public class CreateSkillsTableModel extends AbstractTableModel
{
	public final static long serialVersionUID = 1L;

	SkillSet skills;

	CreateSkillsTableModel ()
	{
		Skill s;
		skills = new SkillSet();
	}

	public void addSkill(Skill s)
	{
		skills.add(s);
		fireTableRowsInserted(skills.size()-1, skills.size());
	}

	public void removeSelectedSkills(int[] rows)
	{
		Skill[] sk = new Skill[rows.length];
		for (int i = 0; i < rows.length; ++i) {
			sk[i] = skills.get(rows[i]);
		}

		for (int i = 0; i < sk.length; ++i) {
			skills.remove(sk[i]);
		}

		fireTableDataChanged();
	}

	public SkillSet getSkillSet()
	{
		return skills;
	}

	// AbstractTableModel overrides

	public int getRowCount()
	{
		return skills.size();
	}

	public int getColumnCount()
	{
		return 2;
	}

	public Object getValueAt(int row, int column)
	{
		Skill s = skills.get(row);
		switch (column) {
			case 0:
				return s.getSkillName();
			case 1:
				return s.getWeight();
		}

		return null;
	}

	public String getColumnName(int column)
	{
		switch (column) {
			case 0:
				return "Skill Name";
			case 1:
				return "Weight";
			default:
				return "unknown";
		}
	}

	public boolean isCellEditable(int row, int col)
	{
		return true;
	}

	public void setValueAt(Object value, int row, int col) {
		Skill sk = skills.get(row);
		switch(col) {
			case 0:
				String name = (String)value;
				sk.setSkillName(name);
				break;
			case 1:
				String wstr = (String)value;
				int w = Integer.parseInt(wstr);
				sk.setWeight(w);
				break;
		}
		skills.set(row, sk);
        fireTableCellUpdated(row, col);
    }
}

