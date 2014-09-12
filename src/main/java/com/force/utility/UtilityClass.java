package com.force.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.json.JSONObject;

public class UtilityClass {
	
	
	
	public Connection getConnection(JSONObject connectionData) throws SQLException {
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();

		}
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					connectionData.getString("databaseUrl"),
					connectionData.getString("username"),
					connectionData.getString("password"));

		} catch (SQLException e) {
			
			System.out.println("Connection Failed! Check output console");
			throw new SQLException();
		}
		return connection;
	}

	
	public static  Connection getSiebelConnection() throws SQLException
	{
	
		
		Connection connection = null;
		try
        {
               Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException e)
        {
               System.out.println("Where is your Oracle JDBC Driver?");
               e.printStackTrace();
               return connection;
        }
		System.out.println("Oracle JDBC Driver Registered!");
        
        try
        {
               connection = DriverManager.getConnection("jdbc:oracle:thin:@167.219.18.231:443/SBLDB", "snetuser1","snetuser1");
        }
        catch (SQLException e)
        {
               System.out.println("Connection Failed! Check output console");
               e.printStackTrace();
               return connection;
        }
    	return connection;
	}
}
