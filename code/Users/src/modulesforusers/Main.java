package modulesforusers;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Main {
	// You can change the variables here to match your database
	static final String DB_URL = "jdbc:mysql://localhost:3306/java_finalproject_chatapplication";
	static final String USER = "root";
	static final String PASS = "Duy24042002";
	
	static final Font fontBigBold = new Font("Times New Roman", Font.BOLD,30);
	static final Font fontBiggestBold = new Font("Times New Roman", Font.BOLD,40);
	static final Font fontSmallBold = new Font("Times New Roman", Font.BOLD,20);
	static final Font fontSmallestBold = new Font("Times New Roman", Font.BOLD,15);
	
	static final Font fontBig = new Font("Times New Roman", Font.PLAIN,30);
	static final Font fontBiggest = new Font("Times New Roman", Font.PLAIN,40);
	static final Font fontSmall = new Font("Times New Roman", Font.PLAIN,20);
	static final Font fontSmallest = new Font("Times New Roman", Font.PLAIN,15);
	
	static final Font fontBigItalic = new Font("Times New Roman", Font.ITALIC,30);
	static final Font fontBiggestItalic = new Font("Times New Roman", Font.ITALIC,40);
	static final Font fontSmallItalic = new Font("Times New Roman", Font.ITALIC,20);
	static final Font fontSmallestItalic = new Font("Times New Roman", Font.ITALIC,15);
	
	static final Font fontBigBoldItalic = new Font("Times New Roman", Font.BOLD | Font.ITALIC,30);
	static final Font fontBiggestBoldItalic = new Font("Times New Roman", Font.BOLD | Font.ITALIC,40);
	static final Font fontSmallBoldItalic = new Font("Times New Roman", Font.BOLD | Font.ITALIC,20);
	static final Font fontSmallestBoldItalic = new Font("Times New Roman", Font.BOLD | Font.ITALIC,15);
		
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);  
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginScreen(conn);
			}
		});
	}

}
