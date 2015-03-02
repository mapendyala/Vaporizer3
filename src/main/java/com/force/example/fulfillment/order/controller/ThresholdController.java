/**
 * 
 */
package com.force.example.fulfillment.order.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.force.partner.PartnerWSDL;
import com.force.utility.SiebelObjectBO;
import com.force.utility.UtilityClass;

/**
 * @author mapendyala
 *
 */

@Controller
@RequestMapping(value = "/set")
public class ThresholdController {

	/**
	 * 
	 */
	public ThresholdController() {
		// TODO Auto-generated constructor stub
	}
	
	private static Connection connection = null;
	 @RequestMapping(value="/Threshold", method=RequestMethod.POST)
	 
@ResponseBody public List<SiebelObjectBO> fetchSiebelObjects(HttpServletRequest request)
{
	 List<SiebelObjectBO> objList=new ArrayList<SiebelObjectBO>();  
	 HttpSession session = request.getSession(true);
	 
	 try{
		 PartnerWSDL partnerWSDL= new PartnerWSDL(session);
		 String projectId = (String) session.getAttribute("projectId");
		 System.out.println("project id+"+projectId);
		 if(projectId==null){
			 projectId="a0PG0000008NkHDMA0";
		 }
		 System.out.println("project id+"+projectId);
		 partnerWSDL.login();
		 JSONObject connectionData=partnerWSDL.getConnectionData(projectId);
		  UtilityClass utilityClass= new UtilityClass();
		  connection= utilityClass.getConnection(connectionData);
	 } catch (Exception e)
	    {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
        
  }
	try
	{
	 if (connection != null)
     {
            System.out.println("You made it, take control your database now!");
            String primName = request.getParameter("primBaseName");
            System.out.println("Prime Base Name:" +primName);
            Integer threshold = Integer.parseInt(request.getParameter("threshold"));
            System.out.println("Threshold Value:" +threshold);
            String query = "SELECT ALLTAB.COLUMN_NAME FROM ALL_TAB_COLUMNS ALLTAB WHERE ALLTAB.TABLE_NAME ='"+primName+ 
                    "' AND (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME ='"+primName+"') - ALLTAB.NUM_NULLS >" +
                    "("+threshold+" * (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME ='"+primName+"') / 100)";
           
            
            Statement st=connection.createStatement();
            
      ResultSet mySet=st.executeQuery(query);
      
      
       while(mySet.next())
       {
       


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
}
