import javax.swing.*;

public abstract class Screen extends JPanel
{
	public abstract String getScreenTitle();
	public void reloadData(Project proj) {  };
}

