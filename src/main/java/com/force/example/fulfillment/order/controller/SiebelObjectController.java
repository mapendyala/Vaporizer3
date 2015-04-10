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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.force.example.fulfillment.order.model.MappingSFDC;
import com.force.partner.PartnerWSDL;
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
							+ "AND BUSCOMP.NAME = '"+siebelObjName+"'";
					
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
					
					rowNumJoinNameMap.put(rowNumber, joinNameUi);
					
					if(joinName == null){
						fieldNameList.add(joinNameUi);
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
										joinCndtnRowNmMap.put(rowNumber, joinCondition);
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
												
												if(joinName == null || joinName.trim().equals("")){
													joinCondition = joinCondition.replace(srcFldLst.get(i), /*buscompTable*/"T0" + "." + colName);
												}else{
													vTableCounter++;
													String destTableNameQry3 = "SELECT BCJOIN.DEST_TBL_NAME FROM SIEBEL.S_JOIN BCJOIN WHERE "
															+ "BCJOIN.BUSCOMP_ID = '"+buscompTable+"' AND BCJOIN.REPOSITORY_ID = '"+repositoryId+"' AND BCJOIN.NAME = '"+joinName+"'";
													
													ResultSet resultSetDestNameQry3 = connection.createStatement().executeQuery(destTableNameQry3);
													
													while(resultSetDestNameQry3.next()) {
														destTableName = resultSetDestNameQry3.getString(1);
													}												
													
													if(joinCondition != null && !joinCondition.trim().equals("")){
														joinCondition = joinCondition.replace(srcFldLst.get(i), "T" + vTableCounter+"_"+rowNumber + "." + colName);
													}
												}
												
											}
											joinCndtnRowNmMap.put(rowNumber, joinCondition);
											System.out.println("JOIN Condition : Else : If else loop  : "+ joinCondition);
										}	
									}
	
								}	
						}else{
							simlrRowNumber = dupJoinNm;
							joinCondition = joinCndtnRowNmMap.get(simlrRowNumber);
							joinCndtnRowNmMap.put(rowNumber, joinCondition);
							
						}
						fieldNameList.add(joinNameUi);
						
						if(dupJoinNm == null){
							colNameUI = "T1_"+rowNumber+"."+columnName;
						}else{
							colNameUI = colNmRowNmMap.get(simlrRowNumber).substring(0,colNmRowNmMap.get(simlrRowNumber).indexOf(".")) +"."+columnName;
						}
						
					}
					 
					fieldNameList.add(colNameUI);
					colNmRowNmMap.put(rowNumber, colNameUI);
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
		 
		 joinNmRowNumMap.put(joinNmKey, rowNum);
		 
		 return null;
	 }
	 /**rachita**/
	 public File getextractionData(HttpServletRequest request, String rowId, String baseTable, String siebelTableNameValue, String sfdcObject) {
			Map<String,Integer> joinNameMap =  SiebelObjectController.joinNmRowNumMap;
			Map<Integer,String> colNameMap =  SiebelObjectController.colNmRowNmMap;
			Map<Integer,String> joinCndtnMap =  SiebelObjectController.joinCndtnRowNmMap;
			Map<Integer,String> reltnShpMap =  SiebelObjectController.relationShpNmRowNmMap;
			Map<Integer,String> slFrcNm =  SiebelObjectController.salesFrcNmRowNmMap;
			Map<Integer,String> extrnlIdMap =  SiebelObjectController.externalIdRowNmMap;
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
			
			StringBuffer extractionQry2 = new StringBuffer("SELECT ")/*.append(aliasConditionQry).append(" FROM DUAL UNION ").append("SELECT ")*/.append(columnNamesQry).append(" FROM");
			
			
			/*********---------- End *************/
			Set<String> keys = joinNameMap.keySet();
	        for(String key: keys){
	            System.out.println(key);
	            String joinCondition = joinCndtnMap.get(joinNameMap.get(key));
	            
	            if(joinCondition != null && joinCondition != ""){
	            	joinCond = joinCond + " " + joinCondition;
	            }
	            
	        }
			
		//	extractionQry = extractionQry.append(colNms.substring(0, colNms.length())).append(" FROM ").append("SIEBEL.").append(baseTable).append(" T0").append(joinCond);
			
			extractionQry2 = extractionQry2.append(" SIEBEL.").append(baseTable).append(" T0").append(joinCond);
			System.out.println("EXtraction Query 2  :"  + extractionQry2.toString());
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
					mySet=st.executeQuery(extractionQry2.toString());
					
					FileWriter fileWriter = new FileWriter(file);
					for(int i=1; i<headers.size()+1 ; i++){
						System.out.println(">>>>>"+(String)headers.get(i));
						fileWriter.append(sfdcObject+"#"+(String)headers.get(i));
						fileWriter.append(COMMA_DELIMITER);
					}
					fileWriter.append(NEW_LINE_SEPARATOR);
					
					while(mySet.next()){
						for(int i=1 ; i < size+1 ; i++){
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



