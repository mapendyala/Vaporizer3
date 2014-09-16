/**
 * 
 */
package com.force.example.fulfillment.order.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.force.example.fulfillment.order.model.Order;
import com.force.partner.PartnerWSDL;
import com.force.utility.ChildObjectBO;
import com.force.utility.SiebelObjectBO;
import com.force.utility.UtilityClass;

/**
 * @author 
 *
 */

@Controller
@RequestMapping(value = "/get")
public class SiebelObjectController {

	/**
	 * 
	 */
	private static Connection connection = null;
	 	 @RequestMapping(value="/SiebelObject", method=RequestMethod.POST)
	 	 
	 @ResponseBody public List<SiebelObjectBO> fetchSiebelObjects(HttpServletRequest request)
	{
		 List<SiebelObjectBO> objList=new ArrayList<SiebelObjectBO>(); 
		 HttpSession session = request.getSession(true);
		 String userValue = request.getParameter("objectName");
		 System.out.println("uservalues in bean is"+userValue);
		 try
         {
                Class.forName("oracle.jdbc.driver.OracleDriver");
         }
         catch(ClassNotFoundException e)
         {
                System.out.println("Where is your Oracle JDBC Driver?");
                e.printStackTrace();
               
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
               
         }
		 
         
        /* try{
    		 PartnerWSDL partnerWSDL= new PartnerWSDL();
    		 String projectId = (String) session.getAttribute("projectName");
    		 JSONObject connectionData=partnerWSDL.getConnectionData(projectId);
    		  UtilityClass utilityClass= new UtilityClass();
    		  connection= utilityClass.getConnection(connectionData);
    	 } catch (Exception e)
    	    {
             System.out.println("Connection Failed! Check output console");
             e.printStackTrace();
            
      }*/
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
                 		" WHERE BUSCOMP.NAME LIKE '"+userValue+"%'";
                
               // System.out.println("qry is"+query);
               
                 Statement st=connection.createStatement();
                 
           ResultSet mySet=st.executeQuery(query);
           
           
            while(mySet.next())
            {
            	//System.out.println("myset is"+mySet.getString(1));
	//System.out.println("myset second is"+mySet.getString(2));
SiebelObjectBO siObj=new SiebelObjectBO();
siObj.setObjName(mySet.getString(1));
siObj.setPrimName(mySet.getString(2));
objList.add(siObj);
            }
           
            System.out.println("list size is"+objList.size());
            if(objList!=null && objList.size()!=0)
            {
            SiebelObjectBO	myObj=objList.get(0);
           System.out.println("list objname is"+ myObj.getObjName());
           System.out.println("list prim is"+myObj.getPrimName());
            }
		
	}
}
	
	catch(SQLException e)
	{
		 System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
	}
		 return objList;
	}
	 	//Code by Amrita 

		 public List<Object> fetchColumns(HttpServletRequest request,String siebelObjName,String thresholdValue){

				System.out.println("in child contoller method");
				 List<Object> myList=new ArrayList<Object>();
				 HttpSession session = request.getSession(true);
				 System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
				 String userValue=siebelObjName;
				 int tvalue=0;
				 if((null!=thresholdValue)&&(""!=thresholdValue)){
					  tvalue = Integer.parseInt(thresholdValue);
				 }
				
				 try
		         {
		                Class.forName("oracle.jdbc.driver.OracleDriver");
		         }
		         catch(ClassNotFoundException e)
		         {
		                System.out.println("Where is your Oracle JDBC Driver?");
		                e.printStackTrace();
		               
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
		               
		         }
				 

		        try
				{
				 if (connection != null)
		          {
		                 System.out.println("You made it, take control your database now!");
		           
		                 String query="SELECT ALLTAB.COLUMN_NAME FROM ALL_TAB_COLUMNS ALLTAB WHERE ALLTAB.TABLE_NAME ='"+userValue+"' "
		    	                 +"AND (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME = '"+userValue+"') - ALLTAB.NUM_NULLS > ("+tvalue+" * (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME = '"+userValue+"') / 100)";
		                
		                Statement st=connection.createStatement();
		                     System.out.println("child query is"+query);
		               ResultSet mySet=st.executeQuery(query);

		                 while(mySet.next())
			            {
			            	System.out.println("myset is"+mySet.getString(1));
			            	
			            	myList.add(mySet.getString(1));
				
			            }
		         
			}
		}
			catch(SQLException e)
			{
				 System.out.println("Connection Failed! Check output console");
		         e.printStackTrace();
			}
		         return myList; 	 
		 	 }	 
	 	 
 @RequestMapping(value="/siebelChildObject", method=RequestMethod.POST)
	 	 
		 @ResponseBody public List<ChildObjectBO> fetchChildObjects(HttpServletRequest request,String siebelObjName)
		{
			System.out.println("in child contoller method");
	 		 List<ChildObjectBO> childObjList=new ArrayList<ChildObjectBO>(); 
			 HttpSession session = request.getSession(true);
			/* String userValue = request.getParameter("baseObjectName");
			 System.out.println("child uservalues in bean is"+userValue);*/
			 System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
			 String userValue=siebelObjName;
			 try
	         {
	                Class.forName("oracle.jdbc.driver.OracleDriver");
	         }
	         catch(ClassNotFoundException e)
	         {
	                System.out.println("Where is your Oracle JDBC Driver?");
	                e.printStackTrace();
	               
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
	               
	         }
			 
	         
	        /* try{
	    		 PartnerWSDL partnerWSDL= new PartnerWSDL();
	    		 String projectId = (String) session.getAttribute("projectName");
	    		 JSONObject connectionData=partnerWSDL.getConnectionData(projectId);
	    		  UtilityClass utilityClass= new UtilityClass();
	    		  connection= utilityClass.getConnection(connectionData);
	    	 } catch (Exception e)
	    	    {
	             System.out.println("Connection Failed! Check output console");
	             e.printStackTrace();
	            
	      }*/
			
	         
	        try
			{
			 if (connection != null)
	          {
	                 System.out.println("You made it, take control your database now!");
	           
	         
	                
	            String query="SELECT CHILDTABLE.NAME, CHILDTABLE.NAME || '.' || CHILDTABLECOLUMN.NAME || ' = ' || BASETABLE.NAME || '.ROW_ID' FROM " +
	                     		" SIEBEL.S_COLUMN CHILDTABLECOLUMN INNER JOIN SIEBEL.S_TABLE CHILDTABLE ON CHILDTABLE.ROW_ID = CHILDTABLECOLUMN.TBL_ID AND " +
	                     		" CHILDTABLE.REPOSITORY_ID = CHILDTABLECOLUMN.REPOSITORY_ID INNER JOIN SIEBEL.S_TABLE BASETABLE ON " +
	                     		" BASETABLE.ROW_ID = CHILDTABLECOLUMN.FKEY_TBL_ID AND BASETABLE.REPOSITORY_ID = CHILDTABLECOLUMN.REPOSITORY_ID AND BASETABLE.NAME ='"+userValue+"'" +
	                     		" INNER JOIN SIEBEL.S_REPOSITORY REP ON REP.ROW_ID = CHILDTABLECOLUMN.REPOSITORY_ID " ;			                 
	                     List<Object> myList=new ArrayList<Object>();
	                     
	                     Statement st=connection.createStatement();
	                     System.out.println("child query is"+query);
	               ResultSet mySet=st.executeQuery(query);
	               /* while(mySet.next())
	                {
	                	System.out.println("CONTROLLER NEW myset is"+mySet.getString(1));

	                	System.out.println("CONTROLLER myset second is"+mySet.getString(2));
	                	
	                	//System.out.println("MEW myset second is"+mySet.getString(3));
	                }*/
	                
	               int i=0;
	                 while(mySet.next())
		            {
		            	//System.out.println("myset is"+mySet.getString(1));
			//System.out.println("myset second is"+mySet.getString(2));
	                i++;
		ChildObjectBO chObj=new ChildObjectBO();
		chObj.setSeqNum(i);
		chObj.setBaseObjName(userValue);
		chObj.setChildObjName(mySet.getString(1));
		chObj.setJoinCondition(mySet.getString(2));
		childObjList.add(chObj);
		            }
	           
	            System.out.println("list size is"+childObjList.size());
	            if(childObjList!=null && childObjList.size()!=0)
	            {
	            ChildObjectBO	myObj=childObjList.get(0);
	           System.out.println("childObjList  objname is"+ myObj.getChildObjName());
	           System.out.println("childObjList list prim is"+myObj.getJoinCondition());
	            }
			
		}
	}
		catch(SQLException e)
		{
			 System.out.println("Connection Failed! Check output console");
	         e.printStackTrace();
		}
	         return childObjList; 	 
} 	 
 
}
	
