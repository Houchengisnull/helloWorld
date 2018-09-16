package org.hc.learning.myamy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

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
	
	@Test
	public void test(){
		while (true) {
			try {
				Thread.sleep(100);
				try {
					System.out.println(connection.isClosed());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
