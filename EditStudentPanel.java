import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class EditStudentPanel extends Panel implements ActionListener
{
	JTextField txtFirst = new JTextField(25);
	JTextField txtLast = new JTextField(25);
	JTextField txtUTDEmail = new JTextField(25);
	JTextField txtPhone = new JTextField(25);

	JButton btnSave = new JButton("Save");
	JLabel lblError = new JLabel("");

	EditStudentPanel()
	{
		buildPanel();
	}

	void buildPanel()
	{
		setLayout(new GridLayout(13, 1));

		btnSave.addActionListener(this);
		
		add(new JLabel(""));
		add(new JLabel("First name: "));
		add(txtFirst);
		
		add(new JLabel("Last name: "));
		add(txtLast);

		add(new JLabel("UTD Email: "));
		add(txtUTDEmail);

		add(new JLabel("Phone: "));
		add(txtPhone);

		add(new JLabel(""));
		add(btnSave);
		add(lblError);
	}

	private void clearFields()
	{
		txtFirst.setText("");
		txtLast.setText("");
		txtUTDEmail.setText("");
		txtPhone.setText("");
	}

	public boolean validateFields()
	{
		return true;
	}

	// Inserts a student record into the database.
	public void actionPerformed(ActionEvent e)
	{
		if (!validateFields()) {
			System.out.println("field validation failed");
			return;
		}

		Connection conn = getConnection();
		if (conn == null) {
			System.out.println("could not create connection");
			return;
		}

		String sql = "insert into students (first_name, last_name, email, phone) "
			       + "values (?, ?, ?, ?)";
		int rowCount = 0;

		try {
			PreparedStatement psInsert = conn.prepareStatement(sql);
			psInsert.setString(1, txtFirst.getText());
			psInsert.setString(2, txtLast.getText());
			psInsert.setString(3, txtUTDEmail.getText());
			psInsert.setString(4, txtPhone.getText());
			rowCount = psInsert.executeUpdate();
		} catch (SQLException se) {
			printSQLException(se);
		}

		if (rowCount < 1) {
			System.out.println("SQL insert failed");
		} else {
			System.out.println("Inserted " + rowCount + " row(s)");
		}
		
		closeConnection(conn);
		clearFields();
	}

	private Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("Loaded the appropriate driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("\nUnable to load the JDBC driver");
            System.err.println("Please check your CLASSPATH.");
            cnfe.printStackTrace(System.err);
        } catch (InstantiationException ie) {
            System.err.println(
                        "\nUnable to instantiate the JDBC driver");
            ie.printStackTrace(System.err);
        } catch (IllegalAccessException iae) {
            System.err.println(
                        "\nNot allowed to access the JDBC driver");
            iae.printStackTrace(System.err);
        }

		Connection conn = null;
		Properties props = new Properties();

		try {
			conn = DriverManager.getConnection("jdbc:derby:teams_db;", props);
		} catch (SQLException se) {
			printSQLException(se);
		}

		return conn;
    }
	
	private void closeConnection(Connection conn)
	{
		try {
			conn.close();
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException se) {
			if (se.getErrorCode() == 50000 && "XJ015".equals(se.getSQLState())) {
				// Ignore this exception. It means that Derby shutdown normally.
			} else {
				printSQLException(se);
			}
		}  
	}

	// This method was copied from the Derby sample program SimpleApp.java packged with the
	// main Derby distribution.
	public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.add(new EditStudentPanel());
		f.pack();
		f.setVisible(true);
	}
}

