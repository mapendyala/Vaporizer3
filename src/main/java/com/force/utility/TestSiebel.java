package com.force.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestSiebel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("hello");

		try
		              {
		                     Class.forName("oracle.jdbc.driver.OracleDriver");
		              }
		              catch(ClassNotFoundException e)
		              {
		                     System.out.println("Where is your Oracle JDBC Driver?");
		                     e.printStackTrace();
		                     return;
		              }
		              System.out.println("Oracle JDBC Driver Registered!");
		              Connection connection = null;
		              try
		              {
		                     connection = DriverManager.getConnection("jdbc:oracle:thin:@167.219.18.231:443/SBLDB", "snetuser1","snetuser1");
		              }
		              catch (SQLException e)
		              {
		                     System.out.println("Connection Failed! Check output console");
		                     e.printStackTrace();
		                     return;
		              }
		              try
		              {
		            	  
		              
		              if (connection != null)
		              {
		                     System.out.println("You made it, take control your database now!");
		                     String query="SELECT BUSCOMP.NAME, " +
		                     		" CASE 	WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Account' THEN 'S_ORG_EXT'" +
		                     		" WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Contact' THEN 'S_CONTACT' WHEN BASETABLE.NAME = 'S_PARTY'" +
		                     		" AND BUSCOMP.NAME = 'User' THEN 'S_USER'" +
		                     		" WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Employee' THEN 'S_CONTACT'" +
		                     		" WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Position' THEN 'S_POSTN' ELSE BASETABLE.NAME END" +
		                     		" FROM SIEBEL.S_BUSCOMP BUSCOMP INNER JOIN SIEBEL.S_REPOSITORY REP " +
		                     		" ON REP.ROW_ID = BUSCOMP.REPOSITORY_ID AND REP.NAME = 'Siebel Repository' " +
		                     		" INNER JOIN SIEBEL.S_TABLE BASETABLE ON BASETABLE.NAME = BUSCOMP.TABLE_NAME AND " +
		                     		" BASETABLE.REPOSITORY_ID = REP.ROW_ID " +
		                     		" WHERE BUSCOMP.NAME = 'Account'";
		                    
		                     List<Object> myList=new ArrayList<Object>();
		                     
		                     Statement st=connection.createStatement();
		                     
		               ResultSet mySet=st.executeQuery(query);
		                     
		                while(mySet.next())
		                {
		                	System.out.println("myset is"+mySet.getString(1));

 	System.out.println("myset second is"+mySet.getString(2));

		                }
		                     

		              }
		              else
		              {
		                     System.out.println("Failed to make connection!");
		              }
		              
		// TODO Auto-generated method stub

	}
	catch(SQLException e)
	{
		
	}
}
}
