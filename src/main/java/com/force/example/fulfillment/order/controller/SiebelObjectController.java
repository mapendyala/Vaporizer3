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

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.force.utility.ChildObjectBO;
import com.force.utility.SiebelObjectBO;

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
	private static Map<String,String> joinNmCndtnMap = new HashMap<String,String>();
	
	@RequestMapping(value="/SiebelObject", method=RequestMethod.POST)

	@ResponseBody public List<SiebelObjectBO> fetchSiebelObjects(HttpServletRequest request)
	{
		List<SiebelObjectBO> objList=new ArrayList<SiebelObjectBO>(); 
		String userValue = request.getParameter("objectName");
		System.out.println("uservalues in bean is"+userValue);
		makeConnection();
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

				if(objList!=null && objList.size()!=0)
				{
					SiebelObjectBO	myObj=objList.get(0);
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

	public List<Object> fetchColumns(HttpServletRequest request,String siebelObjName,String thresholdValue,List<String>childTables){

		System.out.println("in child contoller method");
		List<Object> myList=new ArrayList<Object>();
		/* String userValue = request.getParameter("baseObjectName");
				 System.out.println("child uservalues in bean is"+userValue);*/
		System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
		String userValue=siebelObjName;
		int tvalue=0;
		if((null!=thresholdValue)&&(""!=thresholdValue)){
			tvalue = Integer.parseInt(thresholdValue);
		}
		makeConnection();
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
					myList.add(mySet.getString(1));

				}

			}
		}
		catch(SQLException e)
		{
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}

		//for child tables



		if(null!=childTables){
			try
			{
				if (connection != null)
				{
					System.out.println("Inside child");
					for (int i = 0; i < childTables.size(); i++) {
						String child=  childTables.get(i);
						String query1="SELECT ALLTAB.COLUMN_NAME FROM ALL_TAB_COLUMNS ALLTAB WHERE ALLTAB.TABLE_NAME ='"+child+"' "
								+"AND (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME = '"+child+"') - ALLTAB.NUM_NULLS > ("+tvalue+" * (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME = '"+child+"') / 100)";

						Statement st=connection.createStatement();
						System.out.println("child query is"+query1);
						ResultSet mySet1=st.executeQuery(query1);

						while(mySet1.next())
						{
							myList.add(mySet1.getString(1));

						}

					}}
			}
			catch(SQLException e)
			{
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
		}	 return myList; 
	}	
	@RequestMapping(value="/siebelChildObject", method=RequestMethod.POST)

	@ResponseBody public List<ChildObjectBO> fetchChildObjects(HttpServletRequest request,String siebelObjName)
	{
		System.out.println("in child contoller method");
		List<ChildObjectBO> childObjList=new ArrayList<ChildObjectBO>(); 
		/* String userValue = request.getParameter("baseObjectName");
			 System.out.println("child uservalues in bean is"+userValue);*/
		System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
		String userValue=siebelObjName;
		makeConnection();
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
	
	public void makeConnection(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e){
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
		}
		System.out.println("Oracle JDBC Driver Registered!");
		try{
			connection = DriverManager.getConnection("jdbc:oracle:thin:@167.219.18.231:443/SBLDB", "snetuser1","snetuser1");
		}
		catch (SQLException e){
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
	
	 public List<String> fetchFieldNameList(HttpServletRequest request,String siebelObjName){
			System.out.println("in child contoller method");
			List<String> fieldNameList=new ArrayList<String>(); 
			System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
			makeConnection();
			try{
				if (connection != null){
					System.out.println("You made it, take control your database now!");
					
					String fieldNameQry = "SELECT BCFIELD.NAME FROM SIEBEL.S_FIELD BCFIELD INNER JOIN SIEBEL.S_BUSCOMP BUSCOMP ON "
							+ "BUSCOMP.ROW_ID = BCFIELD.BUSCOMP_ID AND BUSCOMP.REPOSITORY_ID = BCFIELD.REPOSITORY_ID WHERE BCFIELD.REPOSITORY_ID = "
							+ "(SELECT ROW_ID FROM SIEBEL.S_REPOSITORY WHERE NAME = 'Siebel Repository') AND BCFIELD.CALCULATED = 'N' AND BCFIELD.MULTI_VALUED = 'N' "
							+ "AND BUSCOMP.NAME = '"+siebelObjName+"'";
					
					Statement st=connection.createStatement();
					System.out.println("fieldNameQry is"+fieldNameQry);
					ResultSet mySet=st.executeQuery(fieldNameQry);
					while(mySet.next()){
						fieldNameList.add(mySet.getString(1));
					}
					System.out.println("list size is"+fieldNameList.size());
				}
			}
			catch(SQLException e){
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
			return fieldNameList; 	 
		} 	 
	
	 public List<String> fetchColumndAndFrgnKeyName(HttpServletRequest request,String siebelObjName, String sblFldValSlctd, String rowNum){
			List<String> fieldNameList=new ArrayList<String>(); 
			System.out.println("SIEBL OBJ PARAM IS"+siebelObjName + " Selected field : " + siebelObjName + "Row Number " + rowNum);
			int rowNumber = Integer.parseInt(rowNum);
			makeConnection();
			try	{
				if (connection != null){
					System.out.println("You made it, take control your database now!");

					String siebel = "SIEBEL.";
					String destTableName = null,destCol = null,buscompTable = null
							,joinName = null,colName = null,busCompId = null,repositoryId = null, joinNameUi=null , columnName = null, foreignKeyName = "";
					String joinCondition = "";
					int vTableCounter = 1;
					
					String compQry = "SELECT ROW_ID, TABLE_NAME FROM SIEBEL.S_BUSCOMP WHERE NAME = '"+siebelObjName+"'";
					ResultSet resltSetCompQry = connection.createStatement().executeQuery(compQry);
					while(resltSetCompQry.next()){
						 busCompId = resltSetCompQry.getString(1);
						 buscompTable = resltSetCompQry.getString(2);
					}
					
					String rpstryQry = "SELECT ROW_ID FROM SIEBEL.S_REPOSITORY WHERE NAME = 'Siebel Repository'";
					ResultSet resltSetRpstryQry = connection.createStatement().executeQuery(rpstryQry);
					while(resltSetRpstryQry.next()){
						repositoryId = resltSetRpstryQry.getString(1);
					}
					
					// Join name, Column names :
					String joinColNmQry = "SELECT BCFIELD.JOIN_NAME, BCFIELD.COL_NAME FROM SIEBEL.S_FIELD BCFIELD WHERE BCFIELD.NAME = '"+sblFldValSlctd+"' "
							+ "AND BCFIELD.BUSCOMP_ID = '"+busCompId+"' AND BCFIELD.MULTI_VALUED = 'N' AND BCFIELD.CALCULATED = 'N' AND BCFIELD.REPOSITORY_ID = '"+repositoryId+"' ";
					ResultSet resltSetJoinColNmQry = connection.createStatement().executeQuery(joinColNmQry);
					while(resltSetJoinColNmQry.next()){
						joinName = resltSetJoinColNmQry.getString(1);
						colName = resltSetJoinColNmQry.getString(2);
						joinNameUi = joinName;
						columnName = colName;
					}
					
					
					while(joinName != null && !joinName.trim().equals("")){
						
						destTableName = "";						

						String destNmQry = "SELECT BCJOIN.DEST_TBL_NAME FROM SIEBEL.S_JOIN BCJOIN WHERE BCJOIN.BUSCOMP_ID = '"+busCompId+"' "
								+ "AND BCJOIN.REPOSITORY_ID = '"+repositoryId+"' AND BCJOIN.NAME = '"+joinName+"' ";
						ResultSet resltSetDestNmQry = connection.createStatement().executeQuery(destNmQry);
						while(resltSetDestNmQry.next()){
							destTableName = resltSetDestNmQry.getString(1);
							foreignKeyName = destTableName;
						}

						if(destTableName == null || destTableName.trim() == ""){
								destTableName = joinName;
								foreignKeyName = joinName;
								colName = "ROW_ID";
								String destColQry = "SELECT TBLCOL.NAME FROM SIEBEL.S_COLUMN TBLCOL INNER JOIN SIEBEL.S_TABLE "
										+ "TBL ON TBL.ROW_ID = TBLCOL.TBL_ID AND TBL.NAME = '"+destTableName+"' AND TBL.REPOSITORY_ID = '"+repositoryId+"' "
										+ "INNER JOIN SIEBEL.S_TABLE FKEYTBL ON FKEYTBL.ROW_ID = TBLCOL.FKEY_TBL_ID AND FKEYTBL.REPOSITORY_ID = '"+repositoryId+"' "
										+ "AND FKEYTBL.NAME = '"+buscompTable+"' ";
								ResultSet resltSetDestColQry = connection.createStatement().executeQuery(destColQry);
								while(resltSetDestColQry.next()){
									destCol = resltSetDestColQry.getString(1);
								}
								
								if(joinCondition == null || joinCondition.trim().equals("")){
									joinCondition = "LEFT OUTER JOIN "+ siebel + destTableName + " T" + vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." +destCol +"="+ /*buscompTable*/"T0" + ".ROW_ID";
									System.out.println("JOIN Condition : IF loop  : "+ joinCondition);
								}else{
									joinCondition = "LEFT OUTER JOIN "+ siebel + destTableName + " T" + vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." +destCol +"="+ /*buscompTable*/"T0" + ".ROW_ID " + joinCondition;
								}
								
								joinName = "";
						}else {
							/* Take the below query's result set into a Property Set, as there could be multiple rows returned by this query */ 
							String srcFldQrys = "SELECT JOINSPEC.DEST_COL_NAME, JOINSPEC.SRC_FLD_NAME FROM SIEBEL.S_JOIN_SPEC "
									+ "JOINSPEC INNER JOIN SIEBEL.S_JOIN SJOIN ON SJOIN.ROW_ID = JOINSPEC.JOIN_ID AND SJOIN.NAME = '"+joinName+"' "
									+ "AND SJOIN.BUSCOMP_ID = '"+busCompId+"' AND SJOIN.REPOSITORY_ID = '"+repositoryId+"'";
							System.out.println("srcFldQrys" + srcFldQrys);
							ResultSet resltSetSrcFldQrys = connection.createStatement().executeQuery(srcFldQrys);
							List<String> destColLst = null;
							List<String> srcFldLst = null;
							int destColCntr = 0;
							while(resltSetSrcFldQrys.next()){
								if(destColLst == null){
									destColLst = new ArrayList<String>();
									srcFldLst = new ArrayList<String>();
								}
								destColLst.add(destColCntr,resltSetSrcFldQrys.getString(1));
								srcFldLst.add(destColCntr,resltSetSrcFldQrys.getString(2));
							}
							
								if(destTableName == null || destTableName.trim() == ""){
									destTableName = joinName;
									colName = "ROW_ID";
									String destColQry = "SELECT TBLCOL.NAME FROM SIEBEL.S_COLUMN TBLCOL INNER JOIN SIEBEL.S_TABLE "
											+ "TBL ON TBL.ROW_ID = TBLCOL.TBL_ID AND TBL.NAME = '"+destTableName+"' AND TBL.REPOSITORY_ID = '"+repositoryId+"' "
											+ "INNER JOIN SIEBEL.S_TABLE FKEYTBL ON FKEYTBL.ROW_ID = TBLCOL.FKEY_TBL_ID AND FKEYTBL.REPOSITORY_ID = '"+repositoryId+"' "
											+ "AND FKEYTBL.NAME = '"+buscompTable+"' ";
									ResultSet resltSetDestColQry = connection.createStatement().executeQuery(destColQry);
									while(resltSetDestColQry.next()){
										destCol = resltSetDestColQry.getString(1);
									}
									
									if(joinCondition == null || joinCondition.trim().equals("")){
										joinCondition = "LEFT OUTER JOIN " +siebel + destTableName + " T"+ vTableCounter+"_"+rowNumber +" ON T" + vTableCounter+"_"+rowNumber +  "." +destCol +"="+ buscompTable + ".ROW_ID";
										System.out.println("JOIN Condition : Else : If loop  : "+ joinCondition);
									}
									
									joinName = "";
								}
								
								else if(srcFldLst != null && srcFldLst.size() > 0) {
									for(int i=0; i<srcFldLst.size() ; i++){
										
										if(joinCondition == null || joinCondition.trim() == "") {
											joinCondition = "LEFT OUTER JOIN "+ siebel + destTableName + " T" +vTableCounter+"_"+rowNumber +" ON T" + vTableCounter+"_"+rowNumber +  "." + destColLst.get(i) + " = " + srcFldLst.get(i); 
										}else {
											joinCondition = "LEFT OUTER JOIN "+ siebel + destTableName + " T"+ vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." + destColLst.get(i) + " = " + srcFldLst.get(i) + " " + joinCondition; 
										}
										
										// Join name, Column names :
										String joinColNmQry2 = "SELECT BCFIELD.JOIN_NAME, BCFIELD.COL_NAME FROM SIEBEL.S_FIELD BCFIELD WHERE BCFIELD.NAME = '"+srcFldLst.get(i)+"' "
												+ "AND BCFIELD.BUSCOMP_ID = '"+busCompId+"' AND BCFIELD.MULTI_VALUED = 'N' AND BCFIELD.CALCULATED = 'N' AND BCFIELD.REPOSITORY_ID = '"+repositoryId+"' ";
										ResultSet resltSetjoinColNmQry2 = connection.createStatement().executeQuery(joinColNmQry2);
										while(resltSetjoinColNmQry2.next()){
											joinName = resltSetjoinColNmQry2.getString(1);
											colName = resltSetjoinColNmQry2.getString(2);
										}
										
										if(joinName != null && joinName.trim().equals("")){
											joinCondition = joinCondition.replace(srcFldLst.get(i), /*buscompTable*/"T0" + "." + colName);
										}else{
											vTableCounter++;
											destTableName = "SELECT BCJOIN.DEST_TBL_NAME FROM SIEBEL.S_JOIN BCJOIN WHERE "
													+ "BCJOIN.BUSCOMP_ID = '"+buscompTable+"' AND BCJOIN.REPOSITORY_ID = '"+repositoryId+"' AND BCJOIN.NAME = '"+joinName+"'";
											if(joinCondition != null && !joinCondition.trim().equals("")){
												joinCondition = joinCondition.replace(srcFldLst.get(i), "T" + vTableCounter+"_"+rowNumber + "." + colName);
											}
										}
										
									}
									System.out.println("JOIN Condition : Else : If else loop  : "+ joinCondition);
								}	
							}
						}	
					fieldNameList.add(joinNameUi);
					fieldNameList.add("T1_"+rowNum+"."+columnName);
					fieldNameList.add(joinCondition);
					fieldNameList.add(foreignKeyName);
				}
				
			}
			catch(SQLException e){
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
			return fieldNameList; 	 
		} 
	 
	 private static String addJoinToMap(String joinNmKey, String joinCndtnVal){
		 if(joinNmCndtnMap.containsKey(joinNmKey)){
			 return joinNmCndtnMap.get(joinNmKey);
		 }
		 joinNmCndtnMap.put(joinNmKey, joinCndtnVal);
		 return joinCndtnVal;
	 }

  }

