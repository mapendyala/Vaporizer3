package com.force.partner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.force.api.ApiSession;
import com.force.api.DescribeGlobal;
import com.force.api.DescribeSObject;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.example.fulfillment.order.controller.SiebelObjectController;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.example.fulfillment.order.model.MappingSFDC;
import com.force.example.fulfillment.order.model.PreMapData;
import com.force.example.fulfillment.order.model.Transformation;
import com.force.utility.SfdcObjectBO;
import com.force.utility.UtilityClass;
import com.sforce.soap.partner.DescribeSObjectResult;

@SuppressWarnings("rawtypes")
public class TargetPartner {

	HttpSession session;

	public TargetPartner(HttpSession sess) {
		session = sess;
	}

	public TargetPartner() {
		// TODO Auto-generated constructor stub
	}

	public ForceApi getForceApi() {
		JSONObject authParams = (JSONObject) session.getAttribute("authParams");
		ApiSession s = new ApiSession();
		s.setAccessToken(authParams.getString("oAuthToken"));
		s.setApiEndpoint(authParams.getString("instanceUrl"));
		return new ForceApi(s);
	}

	public String getProjectName(String projectId) {

		String projectName = null;
		try {
			// partnerConnection.setQueryOptions(250);
			if (projectId == null)
				projectId = "a0PG000000CIFgnMAH";

			// SOQL query to use
			String soqlQuery = " Select Name, Parent_Project__c, Type__c from Project__c where id= '"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();

				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					projectName = (String) records.get(i).get("Name");

				}
				if (qr.isDone()) {
					done = true;
				} else {
					// qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.projectName "
				+ projectName);
		return projectName;

	}

	/*
	 * public static void main(String args[]){ TargetPartner tp= new
	 * TargetPartner(); tp.getProjectName("a0PG000000B5e2yMAB");
	 * 
	 * }
	 */

	public JSONObject getMiddleWareData(String projectId) {

		JSONObject connData = new JSONObject();
		try {
			// partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String soqlQuery = "SELECT MiddleSalesforce_Username__c,MiddleSalesforce_Password__c, MiddleSalesforce_Token__c FROM Project__c  where id='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					Map contact = records.get(i);
					String username = (String) contact
							.get("MiddleSalesforce_Username__c");
					String password = (String) contact
							.get("MiddleSalesforce_Password__c");
					String databaseUrl = (String) contact
							.get("MiddleSalesforce_Token__c");
					connData.put("username", username);
					connData.put("password", password);
					connData.put("databaseUrl", "PcqSImIrDe0koQf8jqP7amlVy");//change to databaseUrl --subrat

				}
				if (qr.isDone()) {
					done = true;
				}/*
				 * else { qr =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return connData;
	}

	public JSONObject getConnectionData(String projectId) {

		JSONObject connData = new JSONObject();
		try {
			// partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String soqlQuery = "SELECT Siebel_Username__c,Siebel_Password__c, Siebel_Token__c FROM Project__c  where id='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					Map contact = records.get(i);
					String username = (String) contact
							.get("Siebel_Username__c");
					String password = (String) contact
							.get("Siebel_Password__c");
					String databaseUrl = (String) contact
							.get("Siebel_Token__c");
					connData.put("username", username);
					connData.put("password", password);
					connData.put("databaseUrl", databaseUrl);

				}
				if (qr.isDone()) {
					done = true;
				} /*
				 * else { qr =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return connData;
	}

	public List<MainPage> getSavedDBData(String projectId, List<MainPage> data) {
		try {
			// SOQL query to use
			System.out.println(">>>>>" + projectId);
			String soqlQuery = "Select Id, Migrate__c, Sequence__c, Prim_Base_Table__c, Project__c, SFDC_Object__c, Siebel_Object__c, Threshold__c from Mapping_Staging_Table__c where Siebel_Object__c != null and Project__c ='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			// QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			data.clear();

			while (!done) {

				List<Map> records = qr.getRecords();
				System.out.println("lengthhhhh" + records.size());

				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					MainPage mainPage = new MainPage();
					Map contact = records.get(i);
					String id = (String) contact.get("Id");
					mainPage.setSfdcId(id);
					String siebelTableName = (String) contact
							.get("Siebel_Object__c");
					mainPage.setSiebelObject(siebelTableName);
					String sfdcTableName = (String) contact
							.get("SFDC_Object__c");
					mainPage.setSfdcObject(sfdcTableName);
					Boolean migrate = (Boolean) contact.get("Migrate__c");
					mainPage.setMigrate(migrate);
					String seq = (String) contact.get("Sequence__c");
					mainPage.setSequence(seq);
					String primBaseTable = (String) contact
							.get("Prim_Base_Table__c");
					mainPage.setPrimBaseTable(primBaseTable);
					String projId = (String) contact.get("Project__c");
					mainPage.setProjId(projId);
					String threshold = (String) contact.get("Threshold__c");
					mainPage.setThreshold(threshold);
					data.add(mainPage);
				}
				Collections.sort(data, MainPage.SequenceComparator);
				// System.out.println("dataaaa  " + data);
				if (qr.isDone()) {
					done = true;
				} else {
					// qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return data;
	}
	
	public String getSavedExtractionQry(String sfdcId) {
		String extractionQry = "";
		if(sfdcId != null ){
			try {
				// SOQL query to use
				System.out.println(">>>>>"+sfdcId);
				String soqlQuery = "Select Extraction_Query__c from Mapping_Staging_Table__c where Id ='" + sfdcId + "'";
				// Make the query call and get the query results
				 QueryResult<Map> qr = getForceApi().query(soqlQuery);
				//QueryResult qr = partnerConnection.query(soqlQuery);
				boolean done = false;

				while (!done) {
					
					List<Map> records = qr.getRecords();
					// Process the query results
					for (int i = 0; i < records.size(); i++) {
						Map contact = records.get(i);
						extractionQry = (String)contact.get("Extraction_Query__c");
					}
					if (qr.isDone()) {
						done = true;
					} else {
						//qr = partnerConnection.queryMore(qr.getQueryLocator());
					}

				}

			} catch (Exception ce) {
				//ce.printStackTrace();
				System.out.println("Newly added object or no mappings for this entity.");
			}
		}
		System.out.println("\nQuery execution completed.");
		return extractionQry;
	}
	
	
	
	public JSONObject getTargetOrgDetails(String projectId) {
		JSONObject connData = new JSONObject();

		try {
			// partnerConnection.setQueryOptions(250);
			if (projectId == null)
				projectId = "a0Qj0000002gAmlEAE";

			// SOQL query to use
			String soqlQuery = " Select Salesforce_Password__c, Salesforce_Token__c, Salesforce_Username__c from Project__c where id= '"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();

				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					String password = (String) records.get(i).get(
							"Salesforce_Password__c");
					String token = (String) records.get(i).get(
							"Salesforce_Token__c");
					String username = (String) records.get(i).get(
							"Salesforce_Username__c");
					connData.put("password", password);
					if (token == null) {
						token = "";
					}
					connData.put("token", "PcqSImIrDe0koQf8jqP7amlVy"); //change to token later
					connData.put("username", username);
				}

				if (qr.isDone()) {
					done = true;
				} else {
					// qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return connData;

	}

	public String getSFDCObjectName(String projectId, String seibelBaseTable) {

		String SFDCObjectName = "No data";
		try {

			// String projectId="a0PG000000AtiE5";
			String s = seibelBaseTable + "_PreDefined_Mapping";
			// SOQL query to use
			String soqlQuery = " Select id, Object_API_Name__c, Project__r.Name, Project__r.Parent_Project__c, Table_Name__c, Type__c from Table__c where "

					+ "  Project__r.Name='"
					+ s
					+ "' and Parent_Table__c = null and Type__c ='Salesforce'";
			// String soqlQuery
			// ="Select id, Object_API_Name__c, Table_Name__c, Type__c from Table__c where Project__c ='"+projectId+"' and Parent_Table__c = null and Type__c ='Salesforce' and Object_API_Name__c='"+seibelBaseTable+"'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;
			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					SFDCObjectName = (String) records.get(i).get(
							"Table_Name__c");
				}
				if (qr.isDone()) {
					done = true;
				} else {
					// qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");

		return SFDCObjectName;
	}

	public List<SfdcObjectBO> getSFDCOjectListforPopup(String ObjectName) {
		List<SfdcObjectBO> objList = new ArrayList<SfdcObjectBO>();

		try {

			List<DescribeSObject> sobjectResults = getNames();
			for (int i = 0; i < sobjectResults.size(); i++) {
				SfdcObjectBO Sob = new SfdcObjectBO();
				if (sobjectResults.get(i).getName().contains(ObjectName)) {
					Sob.setObjName(sobjectResults.get(i).getName());
					objList.add(Sob);
				}

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return objList;
	}

	public List<DescribeSObject> getNames() {
		List<DescribeSObject> sobjectResults = null;
		try {
			// Make the describeGlobal() call
			DescribeGlobal describeGlobalResult = getForceApi()
					.describeGlobal();

			// Get the sObjects from the describe global result
			sobjectResults = describeGlobalResult.getSObjects();

			// Write the name of each sObject to the console
			for (int i = 0; i < sobjectResults.size(); i++) {
				System.out.println(sobjectResults.get(i).getName());
			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return sobjectResults;
	}

	/*
	 * public String getsubprojects(String siebelTableName) { String
	 * subprojectId = null; String parentProjectId = "a0PG000000AtiE5"; try {
	 * //partnerConnection.setQueryOptions(250); // SOQL query to use String
	 * tabelName = siebelTableName + "_PreDefined_Mapping"; String soqlQuery =
	 * "Select Id, Name, Parent_Project__c, Type__c from Project__c where Parent_Project__c='"
	 * + parentProjectId + "' and Name='" + tabelName + "'"; // Make the query
	 * call and get the query results QueryResult<Map> qr =
	 * getForceApi().query(soqlQuery); boolean done = false;
	 * 
	 * // Loop through the batches of returned results while (!done) {
	 * 
	 * List<Map> records = qr.getRecords(); // Process the query results for
	 * (int i = 0; i < records.size(); i++) { Map contact = records.get(i);
	 * 
	 * subprojectId = (String) contact.get("Id");
	 * 
	 * System.out.println("subprojectId " + subprojectId); } if (qr.isDone()) {
	 * done = true; } else { //qr =
	 * partnerConnection.queryMore(qr.getQueryLocator()); }
	 * 
	 * } } catch (Exception ce) { ce.printStackTrace(); }
	 * System.out.println("\nQuery execution completed."); return subprojectId;
	 * // return connData; // Project__c p = [Select Id, Name,
	 * Parent_Project__c, Type__c from // Project__c where
	 * Parent_Project__c='a0PG000000AtiE5' and //
	 * Name='Account_PreDefined_Mapping']; }
	 */
	/*
	 * public JSONObject getRelatedSiebelTable(String subprojectId) { JSONObject
	 * tableData = new JSONObject(); try {
	 * //partnerConnection.setQueryOptions(250); // SOQL query to use // String
	 * subprojectId="a0PG000000AtiEAMAZ"; String soqlQuery =
	 * "Select Id, Object_API_Name__c, Table_Name__c, Type__c from Table__c where Project__c ='"
	 * + subprojectId + "'and Parent_Table__c = null and Type__c ='Siebel'"; //
	 * Make the query call and get the query results QueryResult<Map> qr =
	 * getForceApi().query(soqlQuery); boolean done = false;
	 * 
	 * // Loop through the batches of returned results while (!done) {
	 * 
	 * List<Map> records = qr.getRecords(); // Process the query results for
	 * (int i = 0; i < records.size(); i++) { Map contact = records.get(i);
	 * String id = (String) contact.get("Id"); String siebelTableName = (String)
	 * contact .get("Object_API_Name__c"); String sfdcTableName = (String)
	 * contact .get("Table_Name__c"); tableData.put("id", id);
	 * tableData.put("siebelTableName", siebelTableName);
	 * tableData.put("sfdcTableName", sfdcTableName);
	 * 
	 * System.out.println("table data " + id + " " + siebelTableName + " " +
	 * sfdcTableName); } if (qr.isDone()) { done = true; } else { //qr =
	 * partnerConnection.queryMore(qr.getQueryLocator()); }
	 * 
	 * } } catch (Exception ce) { ce.printStackTrace(); }
	 * System.out.println("\nQuery execution completed."); return tableData; //
	 * return connData; // Project__c p = [Select Id, Name, Parent_Project__c,
	 * Type__c from // Project__c where Parent_Project__c='a0PG000000AtiE5' and
	 * // Name='Account_PreDefined_Mapping']; }
	 */

	public File getextractionData(String projectId, String sfdcId,
			String subProjectId) {

		String selectTables = "";
		String fromTables = "";
		String joinTables = "";
		String query = null;
		String mainTable = null;
		int h = 0;
		HashMap<String, String> siebelFieldMap = new HashMap<String, String>();
		HashMap<String, String> sfdcFieldMap = new HashMap<String, String>();
		int childTableCounter = 1;
		String sTab = "";

		String soqlQuery2 = "Select Field_Target__c, Source_Field__c, Table_Name__c from Field_Mapping_Data_Migration__c  where Mapping_Staging_Table__c ='"
				+ sfdcId + "'";
		System.out.println(">>>>" + sfdcId);
		try {
			QueryResult<Map> qr = getForceApi().query(soqlQuery2);
			QueryResult<Map> qr1 = null;
			boolean done = false;
			while (!done) {
				List<Map> records = qr.getRecords();
				System.out.println(records.size());
				// if there is no user defined data
				if ((records.size() == 0) || (sfdcId == "")) {
					System.out.println("Predefined data>>>>>>>>>>>>>>");
					String soqlQuery1 = "Select id, Object_API_Name__c, Project__c,Join_Condition__c, Table_Name__c, Type__c from Table__c where Project__c= '"
							+ subProjectId + "'";
					qr1 = getForceApi().query(soqlQuery1);
					records = qr1.getRecords();
					List<Map> records4 = new ArrayList();

					for (int i = 0; i < records.size(); i++) {
						Map contact = records.get(i);

						String type = (String) contact.get("Type__c");
						h++;
						int mainCount = 0;
						System.out.println("........." + type);
						if ((type != null) && (type.equals("Siebel"))) {
							sTab = "SIEBEL." + mainTable + " "
									+ "mainTableAlias";
							mainTable = (String) contact
									.get("Object_API_Name__c");
							fromTables = fromTables + " " + "SIEBEL."
									+ mainTable + " " + "mainTableAlias";
							String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
									+ "Source_Base_Table__c = '"
									+ (String) contact.get("Id") + "'";
							QueryResult<Map> result = getForceApi().query(
									mappingQuery);
							List<Map> mappingRecords = result.getRecords();

							for (int p = 0; p < mappingRecords.size(); p++) {
								if ((String) mappingRecords.get(p).get(
										"Field_Target__c") != null) {
									if ((((String) mappingRecords.get(p).get(
											"Field_Target__c"))
											.equals("CREATEDBYID"))
											|| (((String) mappingRecords.get(p)
													.get("Field_Target__c"))
													.equals("CREATEDDATE"))
											|| (((String) mappingRecords.get(p)
													.get("Field_Target__c"))
													.equals("SHIPPINGLONGITUDE"))
											|| (((String) mappingRecords.get(p)
													.get("Field_Target__c"))
													.equals("SHIPPINGLATITUDE"))
											|| (((String) mappingRecords.get(p)
													.get("Field_Target__c"))
													.equals("BILLINGLONGITUDE"))
											|| (((String) mappingRecords.get(p)
													.get("Field_Target__c"))
													.equals("BILLINGLATITUDE"))) {
										System.out
												.println("sfdc continue"
														+ (String) mappingRecords
																.get(p)
																.get("Field_Target__c"));
									}

									else {
										records4.add(mappingRecords.get(p));

									}
								}

							}
							// mappingRecords=records4.toArray(new
							// SObject[records4.size()]);
							for (int j = 0; j < mappingRecords.size(); j++) {
								Map mappingRecord = mappingRecords.get(j);
								String salesForceField = (String) mappingRecord
										.get("Field_Target__c");
								String siebelField = (String) mappingRecord
										.get("Source_Field__c");
								siebelFieldMap.put("siebelFieldKey" + j,
										siebelField);
								sfdcFieldMap.put("sfdcFieldKey" + j,
										salesForceField);
								System.out.println(j + "Sibel " + siebelField);
								System.out.println(j + "sfdc "
										+ salesForceField);
								// selectTables = selectTables+" "+
								// siebelBaseTable+"."+siebelField +" "+"as"+
								// " "+"\""+
								// siebelField+"="+salesForceField+"\""+",";
								selectTables = selectTables + " "
										+ "mainTableAlias" + "." + siebelField
										+ ",";
							}

							mainCount = siebelFieldMap.size();
						} else {
							if ((type != null) && (type.equals("Siebel Child"))) {
								String childTable = (String) contact
										.get("Table_Name__c");
								String joinCondition = (String) contact
										.get("Join_Condition__c");
								if (joinCondition.contains(childTable)) {
									joinCondition = joinCondition.replace(
											childTable, "childTableAlias"
													+ childTableCounter);
								}
								if (joinCondition.contains(mainTable)) {
									joinCondition = joinCondition.replace(
											mainTable, "mainTableAlias");
								}
								joinTables = joinTables + " "
										+ "LEFT OUTER JOIN " + "SIEBEL."
										+ childTable + " " + "childTableAlias"
										+ childTableCounter + " on "
										+ joinCondition;
								String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
										+ "Source_Base_Table__c = '"
										+ (String) contact.get("Id") + "'";
								QueryResult<Map> result = getForceApi().query(
										mappingQuery);

								List<Map> mappingRecords = result.getRecords();
								records4 = new ArrayList();
								for (int p = 0; p < mappingRecords.size(); p++) {
									if ((String) mappingRecords.get(p).get(
											"Field_Target__c") != null) {
										if ((((String) mappingRecords.get(p)
												.get("Field_Target__c"))
												.equals("CREATEDBYID"))
												|| (((String) mappingRecords
														.get(p)
														.get("Field_Target__c"))
														.equals("CREATEDDATE"))
												|| (((String) mappingRecords
														.get(p)
														.get("Field_Target__c"))
														.equals("SHIPPINGLONGITUDE"))
												|| (((String) mappingRecords
														.get(p)
														.get("Field_Target__c"))
														.equals("SHIPPINGLATITUDE"))
												|| (((String) mappingRecords
														.get(p)
														.get("Field_Target__c"))
														.equals("BILLINGLONGITUDE"))
												|| (((String) mappingRecords
														.get(p)
														.get("Field_Target__c"))
														.equals("BILLINGLATITUDE"))) {
											System.out
													.println("sfdc continue"
															+ (String) mappingRecords
																	.get(p)
																	.get("Field_Target__c"));
										}

										else {
											records4.add(mappingRecords.get(p));

										}
									}

								}
								for (int j = 0; j < mappingRecords.size(); j++) {
									Map mappingRecord = mappingRecords.get(j);
									String siebelField = (String) mappingRecord
											.get("Source_Field__c");
									selectTables = selectTables + " "
											+ "childTableAlias"
											+ childTableCounter + "."
											+ siebelField + ",";
								}
								childTableCounter++;
							}
						}
					}
				}
				// if user defined data is present
				else {

					QueryResult<Map> qr2 = getForceApi().query(soqlQuery2);
					List<Map> records2 = qr2.getRecords();

					List<Map> records4 = new ArrayList();
					Map childBase = null;
					for (int i = 0; i < records2.size(); i++) {
						if ((String) records2.get(i).get("Field_Target__c") != null) {
							if ((((String) records2.get(i).get(
									"Field_Target__c")).equals("CREATEDBYID"))
									|| (((String) records2.get(i).get(
											"Field_Target__c"))
											.equals("CREATEDDATE"))) {
								System.out.println("sfdc continue"
										+ (String) records2.get(i).get(
												"Field_Target__c"));
							}

							else {
								records4.add(records2.get(i));

							}
						}

					}
					// records2=records4.toArray(new SObject[records4.size()]);
					for (int i = 0; i < records2.size(); i++) {
						Map contact = records2.get(i);
						if (contact.get("Table_Name__c") == null) {
							System.out.println("continue");
							continue;

						}

						boolean bFlag = false;

						// its a main table
						if (bFlag == false) {
							// System.out.println("sfdce"+(((String)
							// contact.getField("Field_Target__c")).equals("CREATEDBYID")));

							mainTable = (String) contact.get("Table_Name__c");
							sTab = "SIEBEL." + mainTable + " "
									+ "mainTableAlias";
							fromTables = fromTables + " " + "SIEBEL."
									+ mainTable + " " + "mainTableAlias";
							// String mappingQuery =
							// "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
							// + "Source_Base_Table__c = '"+(String)
							// contact.getField("Id")+"'";
							// QueryResult result =
							// partnerConnection.query(mappingQuery);
							// SObject[] mappingRecords = result.getRecords();

							// SObject mappingRecord = mappingRecords[i];
							String salesForceField = (String) contact
									.get("Field_Target__c");
							String siebelField = (String) contact
									.get("Source_Field__c");
							// XmlObject sourceTable = (XmlObject)
							// mappingRecord.getField("Source_Base_Table__r");
							// String siebelBaseTable = (String)
							// sourceTable.getChild("Object_API_Name__c").getValue();
							siebelFieldMap.put("siebelFieldKey" + i,
									siebelField);
							System.out.println("Sibel " + siebelField);
							System.out.println("sfdc " + salesForceField);
							sfdcFieldMap.put("sfdcFieldKey" + i,
									salesForceField);
							// selectTables = selectTables+" "+
							// siebelBaseTable+"."+siebelField +" "+"as"+
							// " "+"\""+
							// siebelField+"="+salesForceField+"\""+",";
							selectTables = selectTables + " "
									+ "mainTableAlias" + "." + siebelField
									+ ",";

						}

						else // its a child table
						{
							// for (int k=0;k<records3.length;k++){
							// need to change name of contact1

							String childTable = (String) childBase
									.get("Table_Name__c");
							String joinCondition = (String) childBase
									.get("Join_Condition__c");
							if (joinCondition.contains(childTable)) {
								joinCondition = joinCondition.replace(
										childTable, "childTableAlias"
												+ childTableCounter);
							}
							if (joinCondition.contains(mainTable)) {
								joinCondition = joinCondition.replace(
										mainTable, "mainTableAlias");
							}
							joinTables = joinTables + " " + "LEFT OUTER JOIN "
									+ "SIEBEL." + childTable + " "
									+ "childTableAlias" + childTableCounter
									+ " on " + joinCondition;
							// String mappingQuery =
							// "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
							// + "Source_Base_Table__c = '"+(String)
							// contact.getField("Id")+"'";
							// QueryResult result =
							// partnerConnection.query(mappingQuery);
							// SObject[] mappingRecords = result.getRecords();
							// for(int j=0;j<mappingRecords.length;j++){
							// SObject mappingRecord = mappingRecords[j];

							String salesForceField = (String) contact
									.get("Field_Target__c");
							String siebelField = (String) contact
									.get("Source_Field__c");
							siebelFieldMap.put("siebelFieldKey" + i,
									siebelField);
							sfdcFieldMap.put("sfdcFieldKey" + i,
									salesForceField);
							// selectTables = selectTables+" "+
							// siebelBaseTable+"."+siebelField +" "+"as"+
							// " "+"\""+
							// siebelField+"="+salesForceField+"\""+",";
							selectTables = selectTables + " "
									+ "childTableAlias" + childTableCounter
									+ "." + siebelField + ",";
							// }
							childTableCounter++;

						}
					}

				}

				if (qr1 != null) {
					// String mappingFileUR= ExtractDataFromSiebel(query,
					// sfdcFieldMap, siebelFieldMap, projectId);
					if (qr1.isDone()) {
						done = true;
					} else {
						// qr1 = getForceApi().queryMore(qr1.getQueryLocator());
					}
				}

				// String mappingFileUR= ExtractDataFromSiebel(query,
				// sfdcFieldMap, siebelFieldMap, projectId);
				/*
				 * if (qr.isDone()) { done = true; } else { qr =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */
			}
		}

		// }
		catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("########" + selectTables);
		if ((selectTables != "") && (h == 0)) {
			selectTables = selectTables.substring(0, selectTables.length() - 1);
			System.out.println("########" + selectTables);
			System.out.println("########" + fromTables);
			query = "Select" + selectTables + " " + "FROM" + " " + sTab + " "
					+ joinTables + " ";

			System.out.println(query);
		}
		if ((selectTables != "") && (h > 0)) {
			selectTables = selectTables.substring(0, selectTables.length() - 1);
			query = "Select" + selectTables + " " + "FROM" + " " + fromTables
					+ " " + joinTables + " ";

			System.out.println(query);

		}
		System.out.println("\nQuery execution completed.");
		// query="Select mainTableAlias.CREATED_BY, mainTableAlias.CREATED, mainTableAlias.DESC_TEXT, mainTableAlias.MAIN_FAX_PH_NUM, mainTableAlias.NAME, mainTableAlias.MAIN_PH_NUM FROM SIEBEL.S_ORG_EXT mainTableAlias";
		File mappingFileURL = ExtractDataFromSiebel(query, sfdcFieldMap,
				siebelFieldMap, projectId);
		return mappingFileURL;
	}

	public File ExtractDataFromSiebel(String query, Map sfdcMapping,
			Map siebelNames, String ProjectId) {

		File file = null;
		File mappingFile = null;
		Connection connection = null;
		String mappingFileURL = "";
		;
		try {
			connection = UtilityClass.getSiebelConnection();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		try {

			if (connection != null) {

				if (query == null) {
					query = "SELECT ACC.NAME AS \"Name = Name\", ACC.LOC AS \"Location = BillingCity\", ADDR.ADDR AS \"Address1 = BillingStreet\""
							+ " FROM SIEBEL.S_ORG_EXT ACC"
							+ " INNER JOIN SIEBEL.S_ADDR_PER ADDR ON ADDR.ROW_ID = ACC.PR_ADDR_ID";
				}
				System.out.println(query);
				Statement st = connection.createStatement();
				ResultSet mySet = st.executeQuery(query);
				// String sFileName = "tryAndTest";
				String sFileName = new Date() + "_"
						+ Calendar.getInstance().getTime();
				sFileName = sFileName.trim();
				System.out.println(sFileName
						+ "==================================");
				if (mySet.next()) {

					file = new File(sFileName + ".csv");
					mappingFile = new File(sFileName + ".sdl");
					createFile(file);
					createFile(mappingFile);
					ResultSetMetaData rsmd = mySet.getMetaData();
					createCSV(mySet, sfdcMapping, siebelNames, rsmd, file,
							mappingFile);
				}

				while (mySet.next()) {
					/*
					 * System.out.println("=================== in myset");
					 * ResultSetMetaData rsmd = mySet.getMetaData();
					 * createCSV(mySet,sfdcMapping, siebelNames,rsmd, file,
					 * mappingFile);
					 */

				}

			} else {
				System.out.println("Failed to make connection!");
			}

			if (ProjectId == null)
				ProjectId = "a0PG000000Atg1U";
			mappingFileURL = new PartnerWSDL(session, true).getFile(file,
					"19SepDemoFile.csv", "application/vnd.ms-excel", ProjectId,
					null);
			String SDlFileURl = new PartnerWSDL(session, true).getFile(
					mappingFile, "195SepDemoMappingFile.sdl",
					"application/vnd.ms-excel", ProjectId, mappingFileURL);
			System.out.println("filr path : " + mappingFileURL + ":::::::"
					+ SDlFileURl);

		} catch (Exception e) {
			// System.out.println(e);

			e.printStackTrace();
		}
		// return file;

		return file;

	}

	public void createFile(File file) {
		try {
			if (file.createNewFile()) {
				System.out.println("File is created!");
				System.out.println(file.getAbsolutePath());
			} else {
				System.out
						.println("File already exists.Deleting Existing file");
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

	public static File createCSV(ResultSet mySet,
			Map<String, String> sfdcMapping, Map<String, String> siebelNames,
			ResultSetMetaData rsmd,

			File file, File mappingFile) {
		try {

			FileWriter writer = new FileWriter(file);
			FileWriter writerMapping = new FileWriter(mappingFile);
			for (int i = 0; i <= sfdcMapping.size(); i++) {
				if (sfdcMapping.get("sfdcFieldKey" + i) != null) {
					writerMapping.append(siebelNames.get("siebelFieldKey" + i)
							+ "=" + sfdcMapping.get("sfdcFieldKey" + i));
					System.out.println(siebelNames.get("siebelFieldKey" + i)
							+ "=" + sfdcMapping.get("sfdcFieldKey" + i));
					writer.append((CharSequence) siebelNames
							.get("siebelFieldKey" + i));
					if (i >= 0 && i != sfdcMapping.size()) {
						writerMapping.append("\n");
						writer.append(",");
					}
					if (i == sfdcMapping.size()) {
						writerMapping.append('\n');
						writer.append('\n');
					}
				} else {
					sfdcMapping.remove("sfdcFieldKey" + i);
					siebelNames.remove("siebelFieldKey" + i);
				}

			}

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {

				System.out.println(rsmd.getColumnName(i) + " ==== "
						+ mySet.getString(i));
			}
			while (mySet.next()) {
				for (int i = 1; i <= sfdcMapping.size(); i++) {

					int type = rsmd.getColumnType(i);
					System.out.println(mySet.getString(i));
					System.out.println(type + "Types -" + (type == Types.DATE)
							+ Types.DOUBLE);
					if (type == Types.VARCHAR || type == Types.CHAR) {
						writer.append(mySet.getString(i));
					} else if (type == Types.TIMESTAMP) {
						writer.append(mySet.getTimestamp(i).toString());
					} else if (type == Types.DATE) {
						writer.append(mySet.getDate(i).toString());
					} else {
						writer.append(mySet.getString(i));

					}
					if (i >= 1 && i != sfdcMapping.size()) {
						writer.append(",");
					}

					if (i == sfdcMapping.size())
						writer.append('\n');

				}
			}

			writerMapping.flush();
			writerMapping.close();

			writer.flush();
			writer.close();
			System.out
					.println("============file creation done==================");
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;

	}

	public String getMappingId(String projectId,
			List<MappingModel> mappingData, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String sfdcTablename = tableData.getString("sfdcTableName");
		String id = "";
		try {
			// partnerConnection.setQueryOptions(250);
			String soqlQuery = "Select ID,  Project__c, Siebel_Object__c, SFDC_Object__c, Prim_Base_Table__c from Mapping_Staging_Table__c where Project__c ='"
					+ projectId
					+ "' and Siebel_Object__c='"
					+ sfdcTablename
					+ "' ";
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;
			mappingData.clear();

			while (!done) {
				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {

					Map contact = records.get(i);
					id = (String) contact.get("Id");
				}

				if (qr.isDone()) {
					done = true;
				}/*
				 * else { qr =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}

		return id;
	}

	public List<MappingModel> getSavedMappingDBData(String projectId,
			List<MappingModel> mappingData, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		String id = "0";
		try {
			// partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery = "Select ID,  Project__c, Siebel_Object__c, SFDC_Object__c, Prim_Base_Table__c from Mapping_Staging_Table__c where Project__c ='"
					+ projectId
					+ "' and Siebel_Object__c='"
					+ sfdcTablename
					+ "' ";
			// 'a0PG000000Atg1UMAR'
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			mappingData.clear();

			while (!done) {
				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {

					Map contact = records.get(i);

					id = (String) contact.get("Id");

				}

				if (qr.isDone()) {
					done = true;
				}/*
				 * else { qr =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}
		try {
			// partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery1 = "Select Id, Field_Target__c, Source_Field__c, Table_Name__c from Field_Mapping_Data_Migration__c where Mapping_Staging_Table__c ='"
					+ id + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr1 = getForceApi().query(soqlQuery1);
			boolean done1 = false;

			// Loop through the batches of returned results
			// mappingData.clear();

			while (!done1) {
				List<Map> records1 = qr1.getRecords();
				// Process the query results
				for (int i = 0; i < records1.size(); i++) {
					MappingModel mappingModel1 = new MappingModel();
					Map contact = records1.get(i);

					String siebelTableColumn = (String) contact
							.get("Source_Field__c");
					String sfdcTableColumn = (String) contact
							.get("Field_Target__c");
					String fieldMapping1 = (String) contact
							.get("Table_Name__c");
					// Project__c, Siebel_Object__c, SFDC_Object__c,
					// Prim_Base_Table__c
					mappingModel1.setMappingSfdcId(id);
					mappingModel1.setId((String) contact.get("Id"));
					mappingModel1.setSfdcFieldTable(sfdcTableColumn);
					mappingModel1.setSfdcObjectName(sfdcTablename);
					mappingModel1.setSiebleBaseTable(siebelTableName);
					mappingModel1.setSiebleBaseTableColumn(siebelTableColumn);
					mappingModel1.setMappingSeq(i);
					// mappingModel.setMappingSeq(mappingSeq1);
					mappingModel1.setForeignFieldMapping(fieldMapping1);
					mappingData.add(mappingModel1);
					// mappingData.add(mappingModel);
				}

				if (qr1.isDone()) {
					done1 = true;
				}/*
				 * else { qr1 =
				 * partnerConnection.queryMore(qr1.getQueryLocator()); }
				 */

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return mappingData;
	}

	public List<MappingModel> getFieldMapping(JSONObject tableData,
			List<Object> myChildList) {
		List<MappingModel> mappingData = new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		ArrayList<String> fieldId = new ArrayList<String>();
		try {
			// partnerConnection.setQueryOptions(250);
			int mappingSeq = 0;
			// SOQL query to use
			// String sourceBaseTableId="a0QG000000BGG0VMAX";
			StringBuilder sb = new StringBuilder(
					"Select Source_Field__c, Field_Target__c, Source_Base_Table__c from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"
							+ tableData.getString("id")
							+ "'and Source_Field__c  in (");
			// Make the query call and get the query results
			boolean added1 = false;
			for (Object s1 : myChildList) {
				if (added1) {
					sb.append(",");
				}
				sb.append("'");
				sb.append(s1);
				sb.append("'");
				added1 = true;
			}
			sb.append(")");
			String soqlQuery = sb.toString();
			// QueryResult qr2 = partnerConnection.query(soqlQuery2);
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					MappingModel mapping = new MappingModel();

					Map contact = records.get(i);
					String siebelTableColumn = (String) contact
							.get("Source_Field__c");
					String sfdcTableColumn = (String) contact
							.get("Field_Target__c");

					mapping.setMappingSeq(mappingSeq);
					mapping.setSfdcFieldTable(sfdcTableColumn);
					mapping.setSfdcObjectName(sfdcTablename);
					mapping.setSiebleBaseTable(siebelTableName);
					mapping.setSiebleBaseTableColumn(siebelTableColumn);
					mapping.setForeignFieldMapping(siebelTableName);
					mappingSeq++;
					loopCount++;
					mappingData.add(mapping);

				}
				if (qr.isDone()) {
					done = true;
				} /*
				 * else { qr =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */

			}
			String s = sfdcTablename + "_PreDefined_Mapping";

			// partnerConnection.setQueryOptions(250);
			String soqlQuery1 = " Select  Object_API_Name__c,Id, Project__r.Name, Table_Name__c, Type__c from Table__c where  Project__r.Name='"
					+ s + "'";

			QueryResult<Map> qr1 = getForceApi().query(soqlQuery1);
			boolean done1 = false;

			int mappingSeq1 = loopCount;
			while (!done1) {
				List<Map> records1 = qr1.getRecords();
				for (int i = 0; i < records1.size(); i++) {

					if (((String) records1.get(i).get("Type__c"))
							.equals("Siebel Child")) {
						fieldId.add((String) records1.get(i).get("Id"));
					}

				}
				if (qr1.isDone()) {
					done1 = true;
				} /*
				 * else { qr1 =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */
			}
			for (int j = 0; j < fieldId.size(); j++) {
				String id = fieldId.get(j);
				StringBuilder sb1 = new StringBuilder(
						"Select Source_Field__c, Field_Target__c, Source_Base_Table__c,Table_API_Name__c  from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"
								+ id + "' and Source_Field__c  in (");
				// Make the query call and get the query results
				boolean added = false;
				for (Object s2 : myChildList) {
					if (added) {
						sb1.append(",");
					}
					sb1.append("'");
					sb1.append(s2);
					sb1.append("'");
					added = true;
				}
				sb1.append(")");
				String soqlQuery2 = sb1.toString();
				System.out.println("Mapping" + soqlQuery2);
				QueryResult<Map> qr2 = getForceApi().query(soqlQuery2);
				boolean done2 = false;

				// int loopCount = 0;
				// Loop through the batches of returned results
				while (!done2) {

					List<Map> records2 = qr2.getRecords();
					// Process the query results
					for (int i = 0; i < records2.size(); i++) {

						System.out.println("inside for loop ---------   " + i);
						MappingModel mapping = new MappingModel();
						Map contact = records2.get(i);
						String siebelTableColumn = (String) contact
								.get("Source_Field__c");
						String sfdcTableColumn = (String) contact
								.get("Field_Target__c");
						// String fieldMapping= (String)
						// contact.getField("Source_Base_Table__r.Object_API_Name__c");
						String fieldMapping1 = (String) contact
								.get("Table_API_Name__c");

						System.out
								.println("siebelTableColumn value are_"
										+ siebelTableColumn
										+ "-----sfdcTableColumn"
										+ sfdcTableColumn
										+ "-----------Source_Base_Table__r.Object_API_Name__c "
										+ fieldMapping1);
						if (siebelTableName.equalsIgnoreCase(fieldMapping1)) {
							mapping.setSfdcFieldTable(sfdcTableColumn);
							mapping.setSfdcObjectName(sfdcTablename);
							mapping.setSiebleBaseTable(fieldMapping1);
							mapping.setSiebleBaseTableColumn(siebelTableColumn);

							mapping.setMappingSeq(mappingSeq1);
							mapping.setForeignFieldMapping(fieldMapping1);
							System.out
									.println("mappingsss " + siebelTableColumn
											+ " " + sfdcTableColumn);
							mappingSeq1++;
							mappingData.add(mapping);
						}
					}
					if (qr2.isDone()) {
						done2 = true;
					}/*
					 * else { qr2 = partnerConnection
					 * .queryMore(qr2.getQueryLocator()); }
					 */

				}
			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return mappingData;
		// return connData;
		// Project__c p = [Select Id, Name, Parent_Project__c, Type__c from
		// Project__c where Parent_Project__c='a0PG000000AtiE5' and
		// Name='Account_PreDefined_Mapping'];
	}

	public ArrayList<String> getFieldTarget(JSONObject tableData) {

		String sfdcTablename = tableData.getString("sfdcTableName");
		String SFDTargetName = "No data";
		ArrayList<String> field = new ArrayList<String>();
		ArrayList<String> fieldId = new ArrayList<String>();
		String s = sfdcTablename + "_PreDefined_Mapping";

		try {
			// partnerConnection.setQueryOptions(250);

			// SOQL query to use
			// String sourceBaseTableId="a0QG000000BGG0VMAX";
			String soqlQuery = " Select  Object_API_Name__c,Id, Project__r.Name, Table_Name__c, Type__c from Table__c where  Project__r.Name='"
					+ s + "'";
			// String soqlQuery =
			// "Select Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__r.Table_Name__c ='"+sfdcTablename+"'";

			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;
			// Loop through the batches of returned results
			while (!done) {
				List<Map> records1 = qr.getRecords();
				for (int i = 0; i < records1.size(); i++) {

					if (((String) records1.get(i).get("Type__c"))
							.equals("Siebel Child")) {
						fieldId.add((String) records1.get(i).get("Id"));
					}

				}
				if (qr.isDone()) {
					done = true;
				}/*
				 * else { qr =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */
			}
			// String soqlQuery1 =
			// "Select Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__r.Table_Name__c ='"+sfdcTablename+"'";
			String soqlQuery1 = "Select  Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"
					+ tableData.getString("id") + "'";
			QueryResult<Map> qr1 = getForceApi().query(soqlQuery1);
			boolean done1 = false;
			// int loopCount = 0;
			while (!done1) {

				List<Map> records1 = qr1.getRecords();
				// Process the query results
				for (int i = 0; i < records1.size(); i++) {
					SFDTargetName = (String) records1.get(i).get(
							"Field_Target__c");
					field.add(SFDTargetName);
				}
				if (qr1.isDone()) {
					done1 = true;
				}/*
				 * else { qr1 =
				 * partnerConnection.queryMore(qr.getQueryLocator()); }
				 */

			}
			for (int j = 0; j < fieldId.size(); j++) {
				String id = fieldId.get(j);
				String soqlQuery2 = "Select  Field_Target__c  from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"
						+ id + "'";
				// Make the query call and get the query results
				QueryResult<Map> qr2 = getForceApi().query(soqlQuery2);
				boolean done2 = false;

				// int loopCount = 0;
				// Loop through the batches of returned results
				while (!done2) {

					List<Map> records2 = qr2.getRecords();
					// Process the query results
					for (int i = 0; i < records2.size(); i++) {
						SFDTargetName = (String) records2.get(i).get(
								"Field_Target__c");
						field.add(SFDTargetName);
					}
					if (qr2.isDone()) {
						done2 = true;
					}/*
					 * else { qr2 = partnerConnection
					 * .queryMore(qr2.getQueryLocator()); }
					 */

				}
			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");

		return field;

	}

	public List<SfdcObjectBO> getJuncOjectListforPopup(String ObjectName,
			String selectedSFDCChildObj) {
		List<SfdcObjectBO> objList = new ArrayList<SfdcObjectBO>();

		try {
			PartnerWSDL prtnrWSDL1 = new PartnerWSDL(session, false);
			prtnrWSDL1.login();
			List<DescribeSObjectResult> sobjectResults = prtnrWSDL1
					.getJuncNames(selectedSFDCChildObj);
			if (sobjectResults != null) {
				for (int i = 0; i < sobjectResults.size(); i++) {
					SfdcObjectBO Sob = new SfdcObjectBO();
					if (sobjectResults.get(i).getName().contains(ObjectName)) {
						Sob.setObjName(sobjectResults.get(i).getName());
						objList.add(Sob);
					}
				}

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return objList;
	}

	public List<PreMapData> getPredefinedMapData(String sObjectName)
			throws Exception {

		// SOQL query to use
		System.out.println(">>>>>" + sObjectName);
		String soqlQuery = "Select Name,Siebel_Field_Name__c,SFDC_Field_Name__c,SFDC_Object_Name__c,Mapping_Type__c FROM Single_Valued_Screen__c where Mapping_Type__c ='PreDefined' and SFDC_Object_Name__c  = '"
				+ sObjectName + "'";
		// Make the query call and get the query results
		QueryResult<Map> qr = getForceApi().query(soqlQuery);
		List<PreMapData> field = new ArrayList<PreMapData>();
		List<String> siebelFieldLst = SiebelObjectController.sblFieldNamesLst;

		boolean done = false;

		while (!done) {

			List<Map> records = qr.getRecords();
			System.out.println("records size::" + records.size());

			// Process the query results
			for (int i = 0; i < records.size(); i++) {
				Map contact = records.get(i);
				PreMapData preMapData = new PreMapData();
				String name = (String) contact.get("Name");
				String siebelFldName = (String) contact
						.get("Siebel_Field_Name__c");
				String sfdcFldName = (String) contact.get("SFDC_Field_Name__c");
				String sfdcObjName = (String) contact
						.get("SFDC_Object_Name__c");
				String mapTypeName = (String) contact.get("Mapping_Type__c");

				// System.out.println("name:: "+name+" siebelFldName:: "+siebelFldName+" sfdcObjName:: "+sfdcObjName);
				if(siebelFieldLst.contains(siebelFldName)){
					preMapData.setName(name);
					preMapData.setSiebelFldName(siebelFldName);
					preMapData.setSfdcFldName(sfdcFldName);
					preMapData.setSfdcObjName(sfdcObjName);
					preMapData.setMapTypeName(mapTypeName);
					field.add(preMapData);
				}
			}
			// Collections.sort(data, MainPage.SequenceComparator);
			// System.out.println("dataaaa  " + data);
			if (qr.isDone()) {
				done = true;
			} else {
				// qr = partnerConnection.queryMore(qr.getQueryLocator());
			}

		}
		return field;
	}

}
