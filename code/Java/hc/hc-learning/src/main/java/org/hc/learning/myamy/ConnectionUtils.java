package org.hc.learning.myamy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection connection = null;
	public Connection getConnection() throws SQLException{
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
		return connection;
	}
	
}
