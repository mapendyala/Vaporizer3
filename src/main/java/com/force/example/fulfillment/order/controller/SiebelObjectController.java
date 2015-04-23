/**
 * 
 */
package com.force.example.fulfillment.order.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.force.example.fulfillment.order.model.MappingSFDC;
import com.force.partner.PartnerWSDL;
import com.force.partner.TargetPartner;
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
	public static Map<String,Integer> joinNmRowNumMap = new HashMap<String,Integer>();
	public static Map<Integer,String> rowNumJoinNameMap = new HashMap<Integer,String>();
	public static Map<Integer,String> colNmRowNmMap = new HashMap<Integer,String>();
	public static Map<Integer,String> joinCndtnRowNmMap = new HashMap<Integer,String>();
	public static List<MappingSFDC> sfdcFldRowNmList = new ArrayList<MappingSFDC>();
	public static Map<Integer,String> relationShpNmRowNmMap = new HashMap<Integer,String>();
	public static Map<Integer,String> externalIdRowNmMap = new HashMap<Integer,String>();
	public static Map<Integer,String> salesFrcNmRowNmMap = new HashMap<Integer,String>();
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	public static Map<String,List> lookUpRelationMap=new HashMap<String, List>();
	public static Map<String,List> juncRelationMap=new HashMap<String, List>();
	public static Map<String,Integer> fieldNamesMap = new HashMap();
	public static Map<Integer,String> joinCndtnRowNmMapFinal = new HashMap<Integer,String>();
	public static List<String> sblFieldNamesLst = new ArrayList<String>();
	public static String extractionQuery = null;
	
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
				/*String query="SELECT BUSCOMP.NAME, " +
						" CASE 	WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Account' THEN 'S_ORG_EXT'" +
						" WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Contact' THEN 'S_CONTACT' WHEN BASETABLE.NAME = 'S_PARTY'" +
						" AND BUSCOMP.NAME = 'User' THEN 'S_USER'" +
						" WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Employee' THEN 'S_CONTACT'" +
						" WHEN BASETABLE.NAME = 'S_PARTY' AND BUSCOMP.NAME = 'Position' THEN 'S_POSTN' ELSE BASETABLE.NAME END" +
						" FROM SIEBEL.S_BUSCOMP BUSCOMP INNER JOIN SIEBEL.S_REPOSITORY REP " +
						" ON REP.ROW_ID = BUSCOMP.REPOSITORY_ID AND REP.NAME = 'Siebel Repository' " +
						" INNER JOIN SIEBEL.S_TABLE BASETABLE ON BASETABLE.NAME = BUSCOMP.TABLE_NAME AND " +
						" BASETABLE.REPOSITORY_ID = REP.ROW_ID " +
						" WHERE BUSCOMP.NAME LIKE '"+userValue+"%'";*/
				
				String query="SELECT BUSCOMP.NAME, BASETABLE.NAME FROM SIEBEL.S_BUSCOMP BUSCOMP "
						+ "INNER JOIN SIEBEL.S_REPOSITORY REP ON REP.ROW_ID = BUSCOMP.REPOSITORY_ID AND REP.NAME = 'Siebel Repository' "
						+ "INNER JOIN SIEBEL.S_TABLE BASETABLE ON BASETABLE.NAME = BUSCOMP.TABLE_NAME AND BASETABLE.REPOSITORY_ID = REP.ROW_ID "
						+ "WHERE BUSCOMP.NAME LIKE '"+userValue+"%'";

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

		List<Object> myList=new ArrayList<Object>();
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
		if(null!=childTables){
			try
			{
				if (connection != null)
				{
					for (int i = 0; i < childTables.size(); i++) {
						String child=  childTables.get(i);
						String query1="SELECT ALLTAB.COLUMN_NAME FROM ALL_TAB_COLUMNS ALLTAB WHERE ALLTAB.TABLE_NAME ='"+child+"' "
								+"AND (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME = '"+child+"') - ALLTAB.NUM_NULLS > ("+tvalue+" * (SELECT NUM_ROWS FROM ALL_TABLES WHERE TABLE_NAME = '"+child+"') / 100)";

						Statement st=connection.createStatement();
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
			List<String> fieldNameList=new ArrayList<String>(); 
			System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
			makeConnection();
			try{
				if (connection != null){
					System.out.println("You made it, take control your database now!");
					
					String fieldNameQry = "SELECT BCFIELD.NAME FROM SIEBEL.S_FIELD BCFIELD INNER JOIN SIEBEL.S_BUSCOMP BUSCOMP ON "
							+ "BUSCOMP.ROW_ID = BCFIELD.BUSCOMP_ID AND BUSCOMP.REPOSITORY_ID = BCFIELD.REPOSITORY_ID WHERE BCFIELD.REPOSITORY_ID = "
							+ "(SELECT ROW_ID FROM SIEBEL.S_REPOSITORY WHERE NAME = 'Siebel Repository') AND BCFIELD.CALCULATED = 'N' AND BCFIELD.MULTI_VALUED = 'N' AND BCFIELD.INACTIVE_FLG = 'N' "
							+ "AND BUSCOMP.NAME = '"+siebelObjName+"' ORDER BY BCFIELD.NAME ASC";
					
					Statement st=connection.createStatement();
					ResultSet mySet=st.executeQuery(fieldNameQry);
					while(mySet.next()){
						fieldNameList.add(mySet.getString(1));
					}
				}
			}
			catch(SQLException e){
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
			return fieldNameList; 	 
		} 	 
	 
	 public String fetchSqlQryForBizSearchCompExp(HttpServletRequest request,String siebelObjName){
			String sqlQry = ""; 
			System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
			makeConnection();
			try{
				if (connection != null){
					System.out.println("You made it, take control your database now!");
					
					String bizSearchQry = "SELECT SRCHSPEC FROM SIEBEL.S_BUSCOMP WHERE NAME = '"+siebelObjName+"' "
							+ "AND REPOSITORY_ID = (SELECT ROW_ID FROM SIEBEL.S_REPOSITORY WHERE NAME = 'Siebel Repository')";
					
					Statement st=connection.createStatement();
					ResultSet mySet=st.executeQuery(bizSearchQry);
					while(mySet.next()){
						sqlQry = mySet.getString(1);
					}
				}
			}
			catch(SQLException e){
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
			return sqlQry;	 
		}
	 public List<String> fetchColumndAndFrgnKeyName(HttpServletRequest request,String siebelObjName, String sblFldValSlctd, String rowNum, Integer srchQryAlias){
			List<String> fieldNameList=new ArrayList<String>();
			System.out.println("SIEBL OBJ PARAM IS"+siebelObjName + " Selected field : " + siebelObjName + "Row Number " + rowNum);
			int rowNumber = Integer.parseInt(rowNum);
			makeConnection();
			try	{
				if (connection != null){
					System.out.println("You made it, take control your database now!");

					String siebel = "SIEBEL.";
					String destTableName = null,destCol = null,buscompTable = null
							,joinName = null,colName = null,busCompId = null,repositoryId = null, joinNameUi=null , columnName = null, foreignKeyName = "", outerJoinFlg = "", outerJoinName = "";
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
					
					String colNameUI = null;
					
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
					if(srchQryAlias == null){
					rowNumJoinNameMap.put(rowNumber, joinNameUi);
					}
					if(joinName == null){
						fieldNameList.add(/*joinNameUi*/"");
						colNameUI = "T0." + columnName;
					}
					
					else {
						
						
						Integer dupJoinNm = addJoinToMap(joinNameUi, rowNumber);
						Integer simlrRowNumber = null;
						
						//List<String> usedJoinNames = new ArrayList<String>();
						Map<String, String> usedJoinNames = new HashMap<String, String>();
						
						//usedJoinNames.add(joinName);
						
						if(dupJoinNm == null){
							
							while(joinName != null && !joinName.trim().equals("")){
								
								destTableName = "";						
		
								/*String destNmQry = "SELECT BCJOIN.DEST_TBL_NAME FROM SIEBEL.S_JOIN BCJOIN WHERE BCJOIN.BUSCOMP_ID = '"+busCompId+"' "
										+ "AND BCJOIN.REPOSITORY_ID = '"+repositoryId+"' AND BCJOIN.NAME = '"+joinName+"' ";*/
								// Considering Join Type :
								String destNmQry = "SELECT BCJOIN.DEST_TBL_NAME, BCJOIN.OUTER_JOIN FROM SIEBEL.S_JOIN BCJOIN WHERE BCJOIN.BUSCOMP_ID = '"+busCompId+"' " 
										+ "AND BCJOIN.REPOSITORY_ID = '"+repositoryId+"' AND BCJOIN.NAME = '"+joinName+"' ";
								ResultSet resltSetDestNmQry = connection.createStatement().executeQuery(destNmQry);
								while(resltSetDestNmQry.next()){
									destTableName = resltSetDestNmQry.getString(1);
									outerJoinFlg = resltSetDestNmQry.getString(2);
									foreignKeyName = destTableName;
								}
								// Determine type of Outer Join Type
								if(outerJoinFlg != ""){
									if(outerJoinFlg.equalsIgnoreCase("Y")){
										outerJoinName = "LEFT OUTER JOIN ";
									}else if(outerJoinFlg.equalsIgnoreCase("N")){
										outerJoinName = "INNER JOIN ";
									}
								}
								
								// Construction of Join Constraints : START
								List<String> destColNmsJCList = null, joinValsJCList= null;
								String joinCnstrntString = null;
								String joinConstrntQry = "SELECT JC.DEST_COL_NAME, JC.JOIN_VALUE FROM SIEBEL.S_JOIN_CONSTRNT JC INNER JOIN SIEBEL.S_JOIN SJ ON SJ.ROW_ID = JC.JOIN_ID"
										+ " AND SJ.REPOSITORY_ID = JC.REPOSITORY_ID AND SJ.BUSCOMP_ID = '"+busCompId+"' AND SJ.NAME = '"+joinName+"'"
										+ " WHERE JC.INACTIVE_FLG = 'N' AND JC.JOIN_VALUE NOT LIKE '%(%'";
								ResultSet resltSetjoinConstrntQry = connection.createStatement().executeQuery(joinConstrntQry);
								while(resltSetjoinConstrntQry.next()){
									if(destColNmsJCList == null ){
										destColNmsJCList = new ArrayList<String>();
										joinValsJCList = new ArrayList<String>();
									}
									destColNmsJCList.add(resltSetjoinConstrntQry.getString(1));
									joinValsJCList.add(resltSetjoinConstrntQry.getString(2));
								}
								if(destColNmsJCList!= null && destColNmsJCList.size() > 0){
									for(int destColsItr = 0; destColsItr < destColNmsJCList.size(); destColsItr++){
										if(joinCnstrntString == null){
											joinCnstrntString = destColNmsJCList.get(destColsItr) + " = '" + joinValsJCList.get(destColsItr) + "'"; 
										}else{
											joinCnstrntString = joinCnstrntString + " AND " + destColNmsJCList.get(destColsItr) + " = '" + joinValsJCList.get(destColsItr) + "'";
										}
									}
								}
								// Construction of Join Constraints : END
								
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
										if(destCol == null || destCol.trim().equals("")){
											String destTbl = "";
											if(siebelObjName.equals("Account")){
												destTbl = "S_ORG_EXT";
											}else if(siebelObjName.equals("Contact")){
												destTbl = "S_CONTACT";
											}
											String destColQry2 = "SELECT TBLCOL.NAME,TBLCOL.USR_KEY_SEQUENCE FROM SIEBEL.S_COLUMN TBLCOL INNER JOIN SIEBEL.S_TABLE "
													+ "TBL ON TBL.ROW_ID = TBLCOL.TBL_ID AND TBL.NAME = '"+destTableName+"' AND TBL.REPOSITORY_ID = '"+repositoryId+"' "
													+ "INNER JOIN SIEBEL.S_TABLE FKEYTBL ON FKEYTBL.ROW_ID = TBLCOL.FKEY_TBL_ID AND FKEYTBL.REPOSITORY_ID = '"+repositoryId+"' "
													+ "AND FKEYTBL.NAME = '"+destTbl+"' ";
											ResultSet resltSetDestColQry2 = connection.createStatement().executeQuery(destColQry2);
											while(resltSetDestColQry2.next()){
												String usrKeySeq = resltSetDestColQry2.getString(2);
												if(usrKeySeq != null && !usrKeySeq.equals("null")){
													destCol = resltSetDestColQry2.getString(1);
												}
											}
										}
										String joinType = /*"LEFT OUTER JOIN "*/"INNER JOIN ";
										if(joinCondition == null || joinCondition.trim().equals("")){
											joinCondition = joinType + siebel + destTableName + " T" + vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." +destCol +"="+ /*buscompTable*/"T0" + ".ROW_ID";
											if(joinCnstrntString != null ){
												joinCondition = joinCondition + joinCnstrntString+ " ";
											}
										}else{
											if(joinCnstrntString != null){
												joinCondition = joinType + siebel + destTableName + " T" + vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." +destCol +"="+ /*buscompTable*/"T0" + ".ROW_ID " + joinCnstrntString + " "+ joinCondition;
											}else{
												joinCondition = joinType + siebel + destTableName + " T" + vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." +destCol +"="+ /*buscompTable*/"T0" + ".ROW_ID " + joinCondition;
											}
											
										}
										if(srchQryAlias == null){
										joinCndtnRowNmMap.put(rowNumber, joinCondition);
										}
										joinName = "";
								}else {
									/* Take the below query's result set into a Property Set, as there could be multiple rows returned by this query */ 
									String srcFldQrys = "SELECT JOINSPEC.DEST_COL_NAME, JOINSPEC.SRC_FLD_NAME FROM SIEBEL.S_JOIN_SPEC "
											+ "JOINSPEC INNER JOIN SIEBEL.S_JOIN SJOIN ON SJOIN.ROW_ID = JOINSPEC.JOIN_ID AND SJOIN.NAME = '"+joinName+"' "
											+ "AND SJOIN.BUSCOMP_ID = '"+busCompId+"' AND SJOIN.REPOSITORY_ID = '"+repositoryId+"' WHERE JOINSPEC.INACTIVE_FLG = 'N'";
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
											String joinType = /*"LEFT OUTER JOIN "*/"INNER JOIN ";
											String destColQry = "SELECT TBLCOL.NAME FROM SIEBEL.S_COLUMN TBLCOL INNER JOIN SIEBEL.S_TABLE "
													+ "TBL ON TBL.ROW_ID = TBLCOL.TBL_ID AND TBL.NAME = '"+destTableName+"' AND TBL.REPOSITORY_ID = '"+repositoryId+"' "
													+ "INNER JOIN SIEBEL.S_TABLE FKEYTBL ON FKEYTBL.ROW_ID = TBLCOL.FKEY_TBL_ID AND FKEYTBL.REPOSITORY_ID = '"+repositoryId+"' "
													+ "AND FKEYTBL.NAME = '"+buscompTable+"' ";
											ResultSet resltSetDestColQry = connection.createStatement().executeQuery(destColQry);
											while(resltSetDestColQry.next()){
												destCol = resltSetDestColQry.getString(1);
											}
											
											if(joinCondition == null || joinCondition.trim().equals("")){
												joinCondition = joinType +siebel + destTableName + " T"+ vTableCounter+"_"+rowNumber +" ON T" + vTableCounter+"_"+rowNumber +  "." +destCol +"="+ buscompTable + ".ROW_ID";
												System.out.println("JOIN Condition : Else : If loop  : "+ joinCondition);
												if(joinCnstrntString != null){
													joinCondition = joinCondition + joinCnstrntString+ " ";
												}
											}
											
											joinName = "";
										}
										
										else if(srcFldLst != null && srcFldLst.size() > 0) {
											for(int i=0; i<srcFldLst.size() ; i++){
												String srcFld = srcFldLst.get(i);
												boolean isSrcEmplty = false;
												if(srcFld == null || !srcFld.trim().equals("null")){
													srcFld = "T0.ROW_ID";
													isSrcEmplty = true;
												}
												
												if(joinCondition == null || joinCondition.trim() == "") {
													joinCondition = outerJoinName + siebel + destTableName + " T" +vTableCounter+"_"+rowNumber +" ON T" + vTableCounter+"_"+rowNumber +  "." + destColLst.get(i) + " = " + srcFld; 
													if(joinCnstrntString != null){
														joinCondition = joinCondition + joinCnstrntString+ " ";
													}
												}else {
													if(joinCnstrntString != null){
														joinCondition = outerJoinName + siebel + destTableName + " T"+ vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." + destColLst.get(i) + " = " + srcFld + " " +joinCnstrntString+ " "+ joinCondition;
													}else{
														joinCondition = outerJoinName + siebel + destTableName + " T"+ vTableCounter+"_"+rowNumber+ " ON T" + vTableCounter+"_"+rowNumber +  "." + destColLst.get(i) + " = " + srcFld + " " + joinCondition; 
													}
												}
												joinName= null; colName = null;
												if(!isSrcEmplty){
													// Join name, Column names :
													String joinColNmQry2 = "SELECT BCFIELD.JOIN_NAME, BCFIELD.COL_NAME FROM SIEBEL.S_FIELD BCFIELD WHERE BCFIELD.NAME = '"+srcFld+"' "
															+ "AND BCFIELD.BUSCOMP_ID = '"+busCompId+"' AND BCFIELD.MULTI_VALUED = 'N' AND BCFIELD.CALCULATED = 'N' AND BCFIELD.REPOSITORY_ID = '"+repositoryId+"' ";
													ResultSet resltSetjoinColNmQry2 = connection.createStatement().executeQuery(joinColNmQry2);
													while(resltSetjoinColNmQry2.next()){
														joinName = resltSetjoinColNmQry2.getString(1);
														colName = resltSetjoinColNmQry2.getString(2);
													}
													
													if(joinName == null || joinName.trim().equals("")){
														joinCondition = joinCondition.replace(srcFld, /*buscompTable*/"T0" + "." + colName);
													}else{
														vTableCounter++;
														String destTableNameQry3 = "SELECT BCJOIN.DEST_TBL_NAME FROM SIEBEL.S_JOIN BCJOIN WHERE "
																+ "BCJOIN.BUSCOMP_ID = '"+buscompTable+"' AND BCJOIN.REPOSITORY_ID = '"+repositoryId+"' AND BCJOIN.NAME = '"+joinName+"'";
														
														ResultSet resultSetDestNameQry3 = connection.createStatement().executeQuery(destTableNameQry3);
														
														while(resultSetDestNameQry3.next()) {
															destTableName = resultSetDestNameQry3.getString(1);
														}												
														
														if(joinCondition != null && !joinCondition.trim().equals("")){
															joinCondition = joinCondition.replace(srcFld, "T" + vTableCounter+"_"+rowNumber + "." + colName);
														}
													}
												}
											}
											if(srchQryAlias == null){
											joinCndtnRowNmMap.put(rowNumber, joinCondition);
											}
											System.out.println("JOIN Condition : Else : If else loop  : "+ joinCondition);
										}	
									}
	
								}	
						}else{
							simlrRowNumber = dupJoinNm;
							if(srchQryAlias == null){
							joinCondition = joinCndtnRowNmMap.get(simlrRowNumber);
							joinCndtnRowNmMap.put(simlrRowNumber, joinCondition);
							}
						}
						fieldNameList.add(joinNameUi);
						
						if(dupJoinNm == null){
							colNameUI = "T1_"+rowNumber+"."+columnName;
						}else{
							colNameUI = colNmRowNmMap.get(simlrRowNumber).substring(0,colNmRowNmMap.get(simlrRowNumber).indexOf(".")) +"."+columnName;
						}
						
					}
					 
					fieldNameList.add(colNameUI);
					if(srchQryAlias == null){
					colNmRowNmMap.put(rowNumber, colNameUI);
					}
					fieldNameList.add(joinCondition);
					fieldNameList.add(foreignKeyName);
					fieldNamesMap.put(sblFldValSlctd,rowNumber);
				}
				
			}
			catch(SQLException e){
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
			return fieldNameList; 	 
		} 
	 
	 private static Integer addJoinToMap(String joinNmKey, int rowNum){
		 if(joinNmRowNumMap.containsKey(joinNmKey)){
			 
			 Integer removedJoinNm = joinNmRowNumMap.get(joinNmKey);
			 String prevJoinName = rowNumJoinNameMap.get(removedJoinNm); 
			 
			 if((prevJoinName != null && joinNmKey != null && !(prevJoinName.equals(joinNmKey))) || (prevJoinName !=null && joinNmKey == null) || (prevJoinName == null && joinNmKey != null)){
				 
				 joinNmRowNumMap.remove(joinNmKey);
				 
				 Boolean bFound = false;
				 
				 for (Map.Entry<Integer, String> e : rowNumJoinNameMap.entrySet()) {
					    Integer key = e.getKey();
					    String value = e.getValue();
					    
					    if (value != null && joinNmKey != null && value.equals(joinNmKey)) {
					    	bFound = true;
					    	joinNmRowNumMap.put(joinNmKey, key);
					    	return joinNmRowNumMap.get(joinNmKey);
					    }
					}
				 
				 if(!bFound) {
					 joinNmRowNumMap.put(joinNmKey, rowNum);
					 return null;
				 }
				 
			 }
			 
			 else {
			 
				 return joinNmRowNumMap.get(joinNmKey);
				 
			 }
		 }
		 
		 else if (joinNmRowNumMap.containsValue(rowNum)) {
			 
			 String vKeyToRemove = getKeyByValue (joinNmRowNumMap, rowNum);
			 
			 joinNmRowNumMap.remove(vKeyToRemove);
			 
		 }

		joinNmRowNumMap.put(joinNmKey, rowNum);
		return null;

	 }
	 public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		    for (Entry<T, E> entry : map.entrySet()) {
		        if (value.equals(entry.getValue())) {
		            return entry.getKey();
		        }
		    }
		    return null;
	 }
	 /**rachita**/
	 public String getextractionQuery(HttpServletRequest request, String rowId, String baseTable, String siebelTableNameValue, String sfdcObject) {
			Map<String,Integer> joinNameMap =  SiebelObjectController.joinNmRowNumMap;
			Map<Integer,String> colNameMap =  SiebelObjectController.colNmRowNmMap;
			Map<Integer,String> joinCndtnMap =  SiebelObjectController.joinCndtnRowNmMap;
			Map<Integer,String> reltnShpMap =  SiebelObjectController.relationShpNmRowNmMap;
			Map<Integer,String> slFrcNm =  SiebelObjectController.salesFrcNmRowNmMap;
			Map<Integer,String> extrnlIdMap =  SiebelObjectController.externalIdRowNmMap;
			Map<String,Integer> sblFieldNamesMap = SiebelObjectController.fieldNamesMap;
			String salesForceObjName = sfdcObject;
			
			
			if(joinNameMap.isEmpty()){
				PartnerWSDL prtnrWSDL = new PartnerWSDL(request.getSession(),true);
				prtnrWSDL.login();
				prtnrWSDL.getSavedMappingSingleValueDBData(rowId,null,sfdcObject);
				joinNameMap =  SiebelObjectController.joinNmRowNumMap;
				colNameMap =  SiebelObjectController.colNmRowNmMap;
				joinCndtnMap =  SiebelObjectController.joinCndtnRowNmMap;
				reltnShpMap =  SiebelObjectController.relationShpNmRowNmMap;
				slFrcNm =  SiebelObjectController.salesFrcNmRowNmMap;
				extrnlIdMap =  SiebelObjectController.externalIdRowNmMap; 
				sblFieldNamesMap = SiebelObjectController.fieldNamesMap;
			}
			Map<Integer,String> headers = new HashMap<Integer,String>();
			int size = colNameMap.size();
			StringBuffer extractionQry =  new StringBuffer("SELECT ");
			String colNms = null;
			String joinCond = "";
		/*	Iterator colNmItrtr = colNameMap.entrySet().iterator();
			while (colNmItrtr.hasNext()) {
				String asCondition = null;

				Map.Entry pair = (Map.Entry)colNmItrtr.next();
		        String colmVal = (String)  pair.getValue();
		        if(colNms == null){
		        	colNms = colmVal;
		        }else{
		        	colNms =  colNms + "," + colmVal;
		        }
		        Integer rowNumKey = (Integer)pair.getKey();
		        if((reltnShpMap.containsKey(rowNumKey) && reltnShpMap.get(rowNumKey) != null && !reltnShpMap.get(rowNumKey).equals("")) &&
		        		(extrnlIdMap.containsKey(rowNumKey) && extrnlIdMap.get(rowNumKey) != null && !extrnlIdMap.get(rowNumKey).equals(""))){
		        	asCondition = " AS \"" ;
		        	asCondition = asCondition + salesForceObjName + "#" + reltnShpMap.get(rowNumKey) + "." + extrnlIdMap.get(rowNumKey);
		        	asCondition = asCondition +"\"";
		        	colNms = colNms + asCondition;
		        	String mapVal = asCondition.replace("\"", "").replace("AS", "");
		        	headers.put(rowNumKey, mapVal);
		        }else if(slFrcNm.containsKey(rowNumKey) && slFrcNm.get(rowNumKey) != null && !slFrcNm.get(rowNumKey).equals("")){
		        	asCondition = " AS \"" ;
		        	asCondition = asCondition + salesForceObjName + "#" + slFrcNm.get(rowNumKey);
		        	asCondition = asCondition + "\"";
		        	colNms = colNms + asCondition;
		        	String mapVal = asCondition.replace("\"", "").replace("AS", "");
		        	headers.put(rowNumKey, mapVal);
		        }else{
		        	return "Sales Force Fields are not selected for row : " + rowNumKey;
		        }
		    }*/
			
			
	/***** ----- UNION Query -----Start  **********/
			
			String columnNamesQry = null;
			String aliasConditionQry = null;
			if(!(colNameMap != null && colNameMap.size() > 0)){
				return null;
			}
			
			Iterator colNmItrtr1 = colNameMap.entrySet().iterator();
			while (colNmItrtr1.hasNext()) {
				String asCondition = null;

				Map.Entry pair = (Map.Entry)colNmItrtr1.next();
		        String colmVal = (String)  pair.getValue();
		        
   
		        if(columnNamesQry == null){
		        	columnNamesQry = colmVal;
		        }else{
		        	columnNamesQry =  columnNamesQry + "," + colmVal;
		        }
		        
		        
		        
		        //shifted to a new method
			       /* Integer rowNumKey = (Integer)pair.getKey();
			        if((reltnShpMap.containsKey(rowNumKey) && reltnShpMap.get(rowNumKey) != null && !reltnShpMap.get(rowNumKey).equals("")) &&
			        		(extrnlIdMap.containsKey(rowNumKey) && extrnlIdMap.get(rowNumKey) != null && !extrnlIdMap.get(rowNumKey).equals(""))){
			        	if(aliasConditionQry == null){
			        		aliasConditionQry = "\'";
			        		aliasConditionQry = aliasConditionQry + salesForceObjName + "#" + reltnShpMap.get(rowNumKey) + "." + extrnlIdMap.get(rowNumKey);
			        		aliasConditionQry = aliasConditionQry + "\'";
			        	}else{
			        		aliasConditionQry = aliasConditionQry + ",";
			        		aliasConditionQry = aliasConditionQry + "\'";
			        		aliasConditionQry = aliasConditionQry + salesForceObjName + "#" + reltnShpMap.get(rowNumKey) + "." + extrnlIdMap.get(rowNumKey);
			        		aliasConditionQry = aliasConditionQry + "\'";
			        	}
			        	
			        	String mapVal = aliasConditionQry.replace("\'", "");
			        	headers.put(rowNumKey, salesForceObjName + "#" + reltnShpMap.get(rowNumKey) + "." + extrnlIdMap.get(rowNumKey));
			        }else if(slFrcNm.containsKey(rowNumKey) && slFrcNm.get(rowNumKey) != null && !slFrcNm.get(rowNumKey).equals("")){
			        	if(aliasConditionQry == null){
			        		aliasConditionQry = "\'";
			        		aliasConditionQry = aliasConditionQry + salesForceObjName + "#" + slFrcNm.get(rowNumKey);
			        		aliasConditionQry = aliasConditionQry + "\'";
			        	}else{
			        		aliasConditionQry = aliasConditionQry + ",";
			        		aliasConditionQry = aliasConditionQry + "\'";
			        		aliasConditionQry = aliasConditionQry + salesForceObjName + "#" + slFrcNm.get(rowNumKey);
			        		aliasConditionQry = aliasConditionQry + "\'";
			        	}
			        	String mapVal = aliasConditionQry.replace("\"", "");
			        	headers.put(rowNumKey, salesForceObjName + "#" + slFrcNm.get(rowNumKey));
			        }else{
			        	return "Sales Force Fields are not selected for row : " + rowNumKey;
			        } */
		    }
			
			StringBuffer extractionQry2 = new StringBuffer("SELECT ")/*.append(aliasConditionQry).append(" FROM DUAL UNION ").append("SELECT ")*/.append(columnNamesQry).append(" FROM");
			
			
			/*********---------- End *************/
			
			
			Set<String> keys = joinNameMap.keySet();
			//Set<Integer> keys = joinCndtnMap.keySet();
			for(String key: keys){
	            System.out.println(key);
	            String joinCondition = joinCndtnMap.get(joinNameMap.get(key));
	            
	            if(joinCondition != null && joinCondition != ""){
	            	joinCond = joinCond + " " + joinCondition;
	            }
	            
	        }
			
		//	extractionQry = extractionQry.append(colNms.substring(0, colNms.length())).append(" FROM ").append("SIEBEL.").append(baseTable).append(" T0").append(joinCond);
			
			extractionQry2 = extractionQry2.append(" SIEBEL.").append(baseTable).append(" T0").append(joinCond);
			// Construction of WHERE Query :
			String bizSrchQry = fetchSqlQryForBizSearchCompExp(request, siebelTableNameValue);
			if(bizSrchQry != null){
				List opnBrcBrckt = new ArrayList();
				List clsBrcBrckt = new ArrayList();
				
				for(int i=0; i<bizSrchQry.length(); i++){
					if(bizSrchQry.charAt(i)=='['){
						opnBrcBrckt.add(i);
					}else if(bizSrchQry.charAt(i)==']'){
						clsBrcBrckt.add(i);
					}
				}
				
				//List of Field Names from the Business Specification Search Query
				List<String> fieldNamesList = new ArrayList<String>();
				List<String> cnvrtdColnmList = new ArrayList<String>();
				List<String> cnvtrdJoinCndLst = new ArrayList<String>();
				// Holding join names 
				List<String> cnvrtdJoinNmLst = new ArrayList<String>();
				for(int i=0;i<opnBrcBrckt.size(); i++){
					int opnBrckIndx = (Integer) opnBrcBrckt.get(i);
					int clsBrckIndx = (Integer) clsBrcBrckt.get(i);
					fieldNamesList.add(bizSrchQry.substring(opnBrckIndx+1, clsBrckIndx));
				}
				
				Integer aliasNumber = null;
				//Field Names to be changed to Column Names.
				for(String fieldName : fieldNamesList){
					// If the field is already present in the mapping screen Then retrieve the corresponding column name.
					if(sblFieldNamesMap.containsValue(fieldName)){
						Integer exstngFldRowNum = sblFieldNamesMap.get(fieldName);
						String exstngColNm = colNameMap.get(exstngFldRowNum);
						cnvrtdColnmList.add(exstngColNm);
					}// If the field is not selected then generate the join name, join condition.
					else{
						Integer exstngCnt = sblFieldNamesMap.size();
						if(aliasNumber != null){
							aliasNumber = aliasNumber + 1;
						}else{
							aliasNumber = exstngCnt + 1;
						}
						List<String> reqdColJnNmCndList = fetchColumndAndFrgnKeyName(request, siebelTableNameValue, fieldName, aliasNumber.toString(), aliasNumber);
						if(reqdColJnNmCndList.get(0) != null && reqdColJnNmCndList.get(0) != ""){
							if(cnvrtdJoinNmLst.contains(reqdColJnNmCndList.get(0))){
								int index = cnvrtdJoinNmLst.indexOf(reqdColJnNmCndList.get(0));
								String colName = cnvrtdColnmList.get(index);
								String colNameAlias = colName.substring(0,colName.indexOf("."));
								String currentColname = reqdColJnNmCndList.get(1); 
								String currentColNameWithPrevAlias = colNameAlias.concat(currentColname.substring(currentColname.indexOf(".")));
								cnvrtdColnmList.add(currentColNameWithPrevAlias);
								
							}else{
								cnvrtdJoinNmLst.add(reqdColJnNmCndList.get(0));
								cnvrtdColnmList.add(reqdColJnNmCndList.get(1));
								// If the join name is not present then the join condition will be built else, its not required to save the same join condition again
								if(reqdColJnNmCndList.get(2) != null && reqdColJnNmCndList.get(2) != ""){
									cnvtrdJoinCndLst.add(" "+ reqdColJnNmCndList.get(2));
								}
							}
						}// If field does not have a join name then append T0 before column name.
						else{
							cnvrtdColnmList.add(reqdColJnNmCndList.get(1));
						}
					}
				}
				
				// replace all fields with column names
				String convrtdFldColNmQry = bizSrchQry;
				for(int i=0; i<cnvrtdColnmList.size(); i++ ){
					convrtdFldColNmQry = convrtdFldColNmQry.replace(fieldNamesList.get(i), cnvrtdColnmList.get(i));
				}
				// remove all [ ] () in the query
				String[] replaceAllStr = {"[","]"};
				for(String str : replaceAllStr){
					convrtdFldColNmQry = convrtdFldColNmQry.replace(str, "");
				}
				
				// append join conditions if any field has a join name.
				if(cnvtrdJoinCndLst != null && cnvtrdJoinCndLst.size() > 0){
					for(String joinCnd : cnvtrdJoinCndLst){
						extractionQry2.append(joinCnd);
					}
				}
				extractionQry2.append(" WHERE " + convrtdFldColNmQry);
		}
			System.out.println("EXtraction Query 2  :"  + extractionQry2.toString());
			extractionQuery = extractionQry2.toString();
		    return extractionQry2.toString() ;
	    }

	 public Map<Integer,String> getHeadersForCSV(HttpServletRequest request, String rowId, String sfdcObject, boolean hitDB){
			Map<Integer,String> colNameMap =  SiebelObjectController.colNmRowNmMap ;
			Map<Integer,String> reltnShpMap =  SiebelObjectController.relationShpNmRowNmMap ;
			Map<Integer,String> slFrcNm =  SiebelObjectController.salesFrcNmRowNmMap ;
			Map<Integer,String> extrnlIdMap =  SiebelObjectController.externalIdRowNmMap ;
			String salesForceObjName = sfdcObject;
			
			
			if(colNameMap.isEmpty() || hitDB){
				PartnerWSDL prtnrWSDL = new PartnerWSDL(request.getSession(),true);
				prtnrWSDL.login();
				prtnrWSDL.getSavedMappingSingleValueDBData(rowId,null,sfdcObject);
				colNameMap =  SiebelObjectController.colNmRowNmMap;
				reltnShpMap =  SiebelObjectController.relationShpNmRowNmMap;
				slFrcNm =  SiebelObjectController.salesFrcNmRowNmMap;
				extrnlIdMap =  SiebelObjectController.externalIdRowNmMap; 
			}
		 
		 Map<Integer,String> headers = new HashMap<Integer,String>();
		 int size = colNameMap.size();
		 String columnNamesQry = null;
			String aliasConditionQry = null;
			if(!(colNameMap != null && colNameMap.size() > 0)){
				return null;
			}
				
			Iterator colNmItrtr1 = colNameMap.entrySet().iterator();
			while (colNmItrtr1.hasNext()) {
				String asCondition = null;

				Map.Entry pair = (Map.Entry)colNmItrtr1.next();
		        String colmVal = (String)  pair.getValue();
		        if(columnNamesQry == null){
		        	columnNamesQry = colmVal;
		        }else{
		        	columnNamesQry =  columnNamesQry + "," + colmVal;
		        }
		        
		        Integer rowNumKey = (Integer)pair.getKey();
		        if((reltnShpMap.containsKey(rowNumKey) && reltnShpMap.get(rowNumKey) != null && !reltnShpMap.get(rowNumKey).equals("")) &&
		        		(extrnlIdMap.containsKey(rowNumKey) && extrnlIdMap.get(rowNumKey) != null && !extrnlIdMap.get(rowNumKey).equals(""))){
		        	if(aliasConditionQry == null){
		        		aliasConditionQry = "\'";
		        		aliasConditionQry = aliasConditionQry + salesForceObjName /*+ "#"*/ + reltnShpMap.get(rowNumKey) + "." + extrnlIdMap.get(rowNumKey);
		        		aliasConditionQry = aliasConditionQry + "\'";
		        	}else{
		        		aliasConditionQry = aliasConditionQry + ",";
		        		aliasConditionQry = aliasConditionQry + "\'";
		        		aliasConditionQry = aliasConditionQry + salesForceObjName /*+ "#"*/ + reltnShpMap.get(rowNumKey) + "." + extrnlIdMap.get(rowNumKey);
		        		aliasConditionQry = aliasConditionQry + "\'";
		        	}
		        	
		        	String mapVal = aliasConditionQry.replace("\'", "");
		        	headers.put(rowNumKey, salesForceObjName /*+ "#"*/ + reltnShpMap.get(rowNumKey) + "." + extrnlIdMap.get(rowNumKey));
		        }else if(slFrcNm.containsKey(rowNumKey) && slFrcNm.get(rowNumKey) != null && !slFrcNm.get(rowNumKey).equals("")){
		        	if(aliasConditionQry == null){
		        		aliasConditionQry = "\'";
		        		aliasConditionQry = aliasConditionQry + salesForceObjName /*+ "#"*/ + slFrcNm.get(rowNumKey);
		        		aliasConditionQry = aliasConditionQry + "\'";
		        	}else{
		        		aliasConditionQry = aliasConditionQry + ",";
		        		aliasConditionQry = aliasConditionQry + "\'";
		        		aliasConditionQry = aliasConditionQry + salesForceObjName /*+ "#"*/ + slFrcNm.get(rowNumKey);
		        		aliasConditionQry = aliasConditionQry + "\'";
		        	}
		        	String mapVal = aliasConditionQry.replace("\"", "");
		        	headers.put(rowNumKey, salesForceObjName /*+ "#"*/ + slFrcNm.get(rowNumKey));
		        }else{
		        	/*return "Sales Force Fields are not selected for row : " + rowNumKey;*/
		        }
		    }
			return headers;
	 }

	 public File getextractionData(HttpServletRequest request, String rowId, String baseTable, String siebelTableNameValue, String sfdcObject) {
		 TargetPartner tg= new TargetPartner(request.getSession());
		 String extractionQry = tg.getSavedExtractionQry(rowId);
		
		 Map<Integer,String> headers  = null;
		 if(extractionQry == null && extractionQry.trim().length() == 0 ){
			 extractionQry = getextractionQuery(request, rowId, baseTable, siebelTableNameValue, sfdcObject);
			 headers = getHeadersForCSV(request, rowId, sfdcObject, false);
		 }else{
			 headers = getHeadersForCSV(request, rowId, sfdcObject, true);
		 }
			File file = null;
			if(extractionQry != null){
				String sFileName = "tryAndTest";
				file = new File(sFileName + ".csv");
				createFile(file);
			}
			makeConnection();
			ResultSet mySet = null;
			ResultSetMetaData rsmd = null;
			try{
				if (connection != null){
					Statement st=connection.createStatement();
					System.out.println("extraction query is"+extractionQry);
					mySet=st.executeQuery(extractionQry.toString());
					
					rsmd = mySet.getMetaData();
							
					int numOfCols = rsmd.getColumnCount();
					
					FileWriter fileWriter = new FileWriter(file);
					// Fix for the last header miss in csv.
					for(int i=1; i<numOfCols+1 ; i++){
						System.out.println(">>>>>"+(String)headers.get(i));
						fileWriter.append(sfdcObject+"#"+(String)headers.get(i));
						fileWriter.append(COMMA_DELIMITER);
					}
					fileWriter.append(NEW_LINE_SEPARATOR);
					
					while(mySet.next()){
						for(int i=1 ; i < SiebelObjectController.colNmRowNmMap.size()+1 ; i++){
							String value = mySet.getString(i);
							System.out.println(value);
							if(value == null ){
								fileWriter.append("");
							}else{
								fileWriter.append(value);
							}
							fileWriter.append(COMMA_DELIMITER);
						}
						fileWriter.append(NEW_LINE_SEPARATOR);
					}
					
					fileWriter.flush();
					fileWriter.close();
				}
			}
			catch(SQLException e){
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        System.out.println("============ Writing to csv file is complete==================");

		    return file ;
	 }
	 
		public void createFile(File file) {
			try {
				if (file.createNewFile()) {
					System.out.println("File is created!");
					System.out.println(file.getAbsolutePath());
				} else {
					System.out.println("File already exists.Deleting Existing file");
					file.delete();
					if (file.createNewFile()) {
						System.out.println("File is created again!");
						System.out.println(file.getAbsolutePath());
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 public List<String> fetchFieldNameListForMultiVal(HttpServletRequest request,String siebelObjName){
				System.out.println("in child contoller method");
				List<String> fieldNameList=new ArrayList<String>(); 
				System.out.println("SIEBL OBJ PARAM IS"+siebelObjName);
				makeConnection();
				try{
					if (connection != null){
						System.out.println("You made it, take control your database now!");
						
						/*String fieldNameQry = "SELECT BCFIELD.NAME FROM FS77..S_FIELD  BCFIELD INNER JOIN FS77..S_BUSCOMP  BUSCOMP ON "
								+ "BUSCOMP.ROW_ID = BCFIELD.BUSCOMP_ID AND BUSCOMP.REPOSITORY_ID = BCFIELD.REPOSITORY_ID WHERE BCFIELD.REPOSITORY_ID = "
								+ "(SELECT ROW_ID FROM FS77..S_REPOSITORY WHERE NAME = 'Siebel Repository') AND BCFIELD.CALCULATED = 'N' AND BCFIELD.MULTI_VALUED = 'Y' "
								+ "AND BUSCOMP.NAME = '"+siebelObjName+"'";*/
						
						String fieldNameQry = "SELECT BCFIELD.NAME FROM SIEBEL.S_FIELD BCFIELD INNER JOIN SIEBEL.S_BUSCOMP BUSCOMP ON "
								+ "BUSCOMP.ROW_ID = BCFIELD.BUSCOMP_ID AND BUSCOMP.REPOSITORY_ID = BCFIELD.REPOSITORY_ID WHERE BCFIELD.REPOSITORY_ID = "
								+ "(SELECT ROW_ID FROM SIEBEL.S_REPOSITORY WHERE NAME = 'Siebel Repository') AND BCFIELD.CALCULATED = 'N' AND BCFIELD.MULTI_VALUED = 'Y' "
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
		 
		 public List<String> fetchColumndAndFrgnKeyNameForMultiVal(HttpServletRequest request,String siebelObjName, String sblFldValSlctd, String rowNum){
			 
			 List<String> colValues=new ArrayList<String>();
			 makeConnection();
			 try{
				 
				 if(connection!=null){
					 
					 String colVal =" SELECT MVL.DEST_BC_NAME, DESTBUSCOMP.TABLE_NAME, MVL.SRC_FLD_NAME, BCFIELD.DEST_FLD_NAME, RLINK.DST_FLD_NAME, RLINK.INTER_TBL_NAME, RLINK.IPARENT_COL_NAME, RLINK.ICHILD_COL_NAME "
					 + "FROM SIEBEL.S_FIELD BCFIELD "
					 +"INNER JOIN SIEBEL.S_BUSCOMP BUSCOMP ON BUSCOMP.ROW_ID = BCFIELD.BUSCOMP_ID AND BUSCOMP.REPOSITORY_ID = BCFIELD.REPOSITORY_ID "
					 +"INNER JOIN SIEBEL.S_MVLINK MVL ON MVL.NAME = BCFIELD.MVLINK_NAME AND MVL.BUSCOMP_ID = BCFIELD.BUSCOMP_ID AND MVL.REPOSITORY_ID = BCFIELD.REPOSITORY_ID "
					 +"INNER JOIN SIEBEL.S_BUSCOMP DESTBUSCOMP ON DESTBUSCOMP.NAME = MVL.DEST_BC_NAME and DESTBUSCOMP.REPOSITORY_ID = BCFIELD.REPOSITORY_ID "
					 +"INNER JOIN SIEBEL.S_LINK RLINK ON RLINK.NAME = MVL.DEST_LINK_NAME AND RLINK.REPOSITORY_ID = BCFIELD.REPOSITORY_ID "
					 +"WHERE BCFIELD.REPOSITORY_ID = (SELECT ROW_ID FROM SIEBEL.S_REPOSITORY WHERE NAME = 'Siebel Repository') "
					 +"AND BCFIELD.CALCULATED = 'N' "
					 +"AND BCFIELD.MULTI_VALUED = 'Y' "
					 +"AND BUSCOMP.NAME = '"+siebelObjName +"' "
					 +"AND BCFIELD.NAME = '"+sblFldValSlctd+"'";
					 Statement st=connection.createStatement();
					 ResultSet mySet=st.executeQuery(colVal);
					while(mySet.next()){
						colValues=new ArrayList<String>();
						if(mySet.getString(6)!=null && mySet.getString(6).trim().length()>0){
							colValues.add("M:M");
						}else{
							colValues.add("1:M");
						}
						colValues.add(mySet.getString(1));
						colValues.add(mySet.getString(2));
						//colValues.add(mySet.getString(3));
						colValues.add(mySet.getString(4));
						//colValues.add(mySet.getString(5));
						colValues.add(mySet.getString(6));
						colValues.add(mySet.getString(7));
						colValues.add(mySet.getString(8));
					
						
						}

					
					 
				 }
				 
			 }catch(Exception e){
				 e.printStackTrace();
				 
			 }
			 
			 return colValues;
			 
		 }
		 

}



