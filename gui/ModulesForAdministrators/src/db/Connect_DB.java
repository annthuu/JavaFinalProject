package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Vector;

public class Connect_DB {
	private static Connect_DB instance = null;
	private Connection conn = null;
	
	// Database URL
	static final String DB_URL = "jdbc:mysql://localhost/java_finalproject_chatapplication";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "123456";
	
	private Connect_DB() {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(conn != null) 
				conn.close();
		} catch (SQLException se) {
			// TODO: handle exception
			se.printStackTrace();
		}
	}
	
	public static Connect_DB getInstance() {
        if(instance == null){
            instance = new Connect_DB();
        }
        return instance;
	}
	
	public Vector<Vector<Object>> getAllUser(String filter, String order) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "select * from users order by " + filter + " " + order;

		ResultSet rs = stmt.executeQuery(sql);
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) { 
			Vector<Object> row = new Vector<Object>();
			row.add(rs.getString("UserName"));
			row.add(rs.getString("FullName"));
			row.add(rs.getString("Address"));
			row.add(rs.getDate("DOB"));
			row.add(rs.getString("Sex"));
			row.add(rs.getString("Email"));
			row.add(rs.getTimestamp("CreateTime"));
			
			data.add(row);
		}

		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	} 

	public Vector<Vector<Object>> searchUser(String keyword, String criteria, String filter, String order) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "select * from users "
				+ "where " + criteria + " like '%" + keyword + "%' "
				+ "order by " + filter + " " + order;

		ResultSet rs = stmt.executeQuery(sql);
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) { 
			Vector<Object> row = new Vector<Object>();
			row.add(rs.getString("UserName"));
			row.add(rs.getString("FullName"));
			row.add(rs.getString("Address"));
			row.add(rs.getDate("DOB"));
			row.add(rs.getString("Sex"));
			row.add(rs.getString("Email"));
			row.add(rs.getTimestamp("CreateTime"));
			
			data.add(row);
		}

		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public void addUser(String username, String password, String fullname, String addr, 
			String dob, String sex, String email, String dateCreated) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = MessageFormat.format("insert into users values "
				+ "(uuid(), \"{0}\", \"{1}\", \"{2}\", \"{3}\", \"{4}\", \"{5}\", \"{6}\", \"{7}\", false, \"\")", 
				username, fullname, addr, dob, sex, email, password, dateCreated);

		stmt.executeUpdate(sql);
		
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateUser(String username, Vector<String> col, Vector<String> val) throws SQLException {
		int n = col.size();
		Statement stmt = conn.createStatement();
		String sql = "update users set ";
		for (int i = 0; i < n; i++) {
			sql = sql + col.get(i) + "=\"" + val.get(i) + "\"";
			if (i != n - 1)
				sql += ",";
		}
		sql += " where UserName=\"" + username + "\"";
		
		stmt.executeUpdate(sql);
		
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteUser(String username) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "delete from users where UserName=\"" + username + "\"";
		
		stmt.executeUpdate(sql);
		
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void banUser(String username) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "update users set LockAccount=true where UserName=\"" + username + "\"";
		
		stmt.executeUpdate(sql);
		
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
