package com.force.partner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.utility.ChildObjectBO;
import com.force.utility.SfdcObjectBO;
import com.force.utility.UtilityClass;
import com.google.gson.JsonArray;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.bind.XmlObject;
import com.sforce.ws.wsdl.Schema;

public class PartnerWSDL {

	PartnerConnection partnerConnection = null;

	public PartnerConnection getPartnerConnection() {
		return partnerConnection;
	}

	public void setPartnerConnection(PartnerConnection partnerConnection) {
		this.partnerConnection = partnerConnection;
	}

	String username = "subhchakraborty@deloitte.com.vaporizer";
	String password = "Sep@2013";
	String authEndPoint = "https://login.salesforce.com/services/Soap/u/24.0/";

	public boolean login() {
		boolean success = false;
		try {

			ConnectorConfig config = new ConnectorConfig();
			config.setUsername(username);
			config.setPassword(password);
			config.setAuthEndpoint(authEndPoint);
			config.setTraceFile("traceLogs.txt");
			config.setTraceMessage(true);
			config.setPrettyPrintXml(true);

			partnerConnection = new PartnerConnection(config);
			setPartnerConnection(partnerConnection);

			success = true;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		return success;
	}

	public JSONObject getConnectionData(String projectId) {

		JSONObject connData = new JSONObject();
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String soqlQuery = "SELECT Siebel_Username__c,Siebel_Password__c, Siebel_Token__c FROM Project__c  where id='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];
					String username = (String) contact
							.getField("Siebel_Username__c");
					String password = (String) contact
							.getField("Siebel_Password__c");
					String databaseUrl = (String) contact
							.getField("Siebel_Token__c");
					connData.put("username", username);
					connData.put("password", password);
					connData.put("databaseUrl", databaseUrl);

				}
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return connData;
	}

	/**
	 * 
	 * @author piymishra
	 * @param projectId
	 * @return projectName
	 */
	public String getProjectName(String projectId) {

		String projectName = null;
		try {
			partnerConnection.setQueryOptions(250);
			if (projectId == null)
				projectId = "a0PG000000B23yKMAR";

			// SOQL query to use
			String soqlQuery = " Select Name, Parent_Project__c, Type__c from Project__c where id= '"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();

				// Process the query results
				for (int i = 0; i < records.length; i++) {
					projectName = (String) records[i].getField("Name");

				}
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return projectName;

	}

	public JSONObject getTargetOrgDetails(String projectId) {
		JSONObject connData = new JSONObject();

		try {
			partnerConnection.setQueryOptions(250);
			if (projectId == null)
				projectId = "a0PG000000B23yKMAR";

			// SOQL query to use
			String soqlQuery = " Select Salesforce_Password__c, Salesforce_Token__c, Salesforce_Username__c from Project__c where id= '"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();

				// Process the query results
				for (int i = 0; i < records.length; i++) {
					String password = (String) records[i]
							.getField("Salesforce_Password__c");
					String token = (String) records[i]
							.getField("Salesforce_Token__c");
					String username = (String) records[i]
							.getField("Salesforce_Username__c");
					connData.put("password", password);
					if (token == null) {
						token = "";
					}
					connData.put("token", token);
					connData.put("username", username);
				}

				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return connData;

	}

	/**
	 * @author piymishra
	 * @param projectId
	 * @param seibelBaseTable
	 * @return SFDCObjectName
	 */
	public String getSFDCObjectName(String projectId, String seibelBaseTable) {

		String SFDCObjectName = "No data";
		try {
			login();
			partnerConnection.setQueryOptions(250);

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
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SFDCObjectName = (String) records[i]
							.getField("Table_Name__c");
				}
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");

		return SFDCObjectName;
	}

	/**
	 * 
	 * @param file
	 * @param fileName
	 * @param content
	 * @param projId
	 * @param dataFileUrl
	 * @return
	 */
	public String getFile(File file, String fileName, String content,
			String projId, String dataFileUrl) {
		HttpURLConnection connection = null;
		try {
			URL url;
			login();
			String sessionId = partnerConnection.getSessionHeader()
					.getSessionId();
			String targetURL = "";
			if (dataFileUrl == null)
				targetURL = "https://na11.salesforce.com/services/apexrest/uploadFile/"
						+ projId;
			else
				targetURL = "https://na11.salesforce.com/services/apexrest/uploadFile/"
						+ projId;
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", content);
			// "application/vnd.ms-excel");
			connection.setRequestProperty("filename",
					(dataFileUrl != null) ? fileName.split("\\.")[0] + "_"
							+ dataFileUrl + "\\.csv" : fileName);
			connection.setRequestProperty("Authorization", "Bearer "
					+ sessionId);
			// connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			BufferedReader rdr = new BufferedReader(new FileReader(file));

			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			// String urlParameters = "s=hii";
			String doc2 = "";
			byte[] headerBytes1 = (rdr.readLine() + "\n").getBytes("UTF-8");
			doc2 = new String(headerBytes1, "UTF-8");
			wr.writeBytes(doc2);
			while ((doc2 = rdr.readLine()) != null) {
				// byte[] headerBytes=null;

				// headerBytes = (rdr.readLine()).getBytes("UTF-8");
				// doc2 = new String(headerBytes, "UTF-8");

				wr.writeBytes(doc2 + "\n");

			}

			wr.flush();
			wr.close();
			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return (response.toString().replaceAll("\"", "") != null ? response
					.toString().replaceAll("\"", "") : response.toString());

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	// Amrita:Getting field Mapping

	public List<MappingModel> getFieldMapping(JSONObject tableData,
			List<Object> myChildList) {
		List<MappingModel> mappingData = new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		ArrayList<String> fieldId = new ArrayList<String>();
		try {
			partnerConnection.setQueryOptions(250);
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
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					MappingModel mapping = new MappingModel();

					SObject contact = records[i];
					String siebelTableColumn = (String) contact
							.getField("Source_Field__c");
					String sfdcTableColumn = (String) contact
							.getField("Field_Target__c");

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
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
			String s = sfdcTablename + "_PreDefined_Mapping";

			partnerConnection.setQueryOptions(250);
			String soqlQuery1 = " Select  Object_API_Name__c,Id, Project__r.Name, Table_Name__c, Type__c from Table__c where  Project__r.Name='"
					+ s + "'";

			QueryResult qr1 = partnerConnection.query(soqlQuery1);
			boolean done1 = false;

			int mappingSeq1 = loopCount;
			while (!done1) {
				SObject[] records1 = qr1.getRecords();
				for (int i = 0; i < records1.length; i++) {

					if (((String) records1[i].getField("Type__c"))
							.equals("Siebel Child")) {
						fieldId.add((String) records1[i].getField("Id"));
					}

				}
				if (qr1.isDone()) {
					done1 = true;
				} else {
					qr1 = partnerConnection.queryMore(qr.getQueryLocator());
				}
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
				System.out.println("amritaaaaaaaa" + soqlQuery2);
				QueryResult qr2 = partnerConnection.query(soqlQuery2);
				boolean done2 = false;

				// int loopCount = 0;
				// Loop through the batches of returned results
				while (!done2) {

					SObject[] records2 = qr2.getRecords();
					// Process the query results
					for (int i = 0; i < records2.length; i++) {

						
						System.out.println("inside for loop ---------   " + i);
						MappingModel mapping = new MappingModel();
						SObject contact = records2[i];
						String siebelTableColumn = (String) contact
								.getField("Source_Field__c");
						String sfdcTableColumn = (String) contact
								.getField("Field_Target__c");
						// String fieldMapping= (String)
						// contact.getField("Source_Base_Table__r.Object_API_Name__c");
						String fieldMapping1 = (String) contact
								.getField("Table_API_Name__c");

						System.out
								.println("siebelTableColumn value are_"
										+ siebelTableColumn
										+ "-----sfdcTableColumn"
										+ sfdcTableColumn
										+ "-----------Source_Base_Table__r.Object_API_Name__c "
										+ fieldMapping1);
						if(siebelTableName.equalsIgnoreCase(fieldMapping1)){
							mapping.setSfdcFieldTable(sfdcTableColumn);
							mapping.setSfdcObjectName(sfdcTablename);
							mapping.setSiebleBaseTable(fieldMapping1);
							mapping.setSiebleBaseTableColumn(siebelTableColumn);
	
							mapping.setMappingSeq(mappingSeq1);
							mapping.setForeignFieldMapping(fieldMapping1);
							System.out.println("mappingsss " + siebelTableColumn
									+ " " + sfdcTableColumn);
							mappingSeq1++;
							mappingData.add(mapping);
						}
					}
					if (qr2.isDone()) {
						done2 = true;
					} else {
						qr2 = partnerConnection
								.queryMore(qr2.getQueryLocator());
					}

				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return mappingData;
		// return connData;
		// Project__c p = [Select Id, Name, Parent_Project__c, Type__c from
		// Project__c where Parent_Project__c='a0PG000000AtiE5' and
		// Name='Account_PreDefined_Mapping'];
	}

	public String getsubprojects(String siebelTableName) {
		String subprojectId = null;
		String parentProjectId = "a0PG000000AtiE5";
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String tabelName = siebelTableName + "_PreDefined_Mapping";
			String soqlQuery = "Select Id, Name, Parent_Project__c, Type__c from Project__c where Parent_Project__c='"
					+ parentProjectId + "' and Name='" + tabelName + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];

					subprojectId = (String) contact.getField("Id");

					System.out.println("subprojectId " + subprojectId);
				}
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return subprojectId;
		// return connData;
		// Project__c p = [Select Id, Name, Parent_Project__c, Type__c from
		// Project__c where Parent_Project__c='a0PG000000AtiE5' and
		// Name='Account_PreDefined_Mapping'];
	}

	// Amrita:Save Mapping Data Into Db

	public void saveMappingDataIntoDB(List<MappingModel> data,
			HttpServletRequest request, String attribute)
			throws ConnectionException {

		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);

		// String [] dataArray = data.toArray(new String[data.size()]);
		login();
		List<SObject> lstContactUpdate= new ArrayList<SObject>();
		List<SObject> lstContactInsert= new ArrayList<SObject>();
		SObject[] contactUpdate = new SObject[data.size()];
		SObject[] contactInsert = new SObject[data.size()];
		// data.get(0).getMigrate();
		for (Iterator<MappingModel> iterator = data.iterator(); iterator
				.hasNext();) {
			MappingModel mappingModel = (MappingModel) iterator.next();
			
			if( mappingModel.getId()==null || mappingModel.getId().equalsIgnoreCase("")){
				SObject contact = new SObject();
				contact.setType("Field_Mapping_Data_Migration__c");
				contact.setField("Table_Name__c",
						mappingModel.getForeignFieldMapping());
				contact.setField("Field_Target__c",
						mappingModel.getSfdcFieldTable());
				contact.setField("Source_Field__c",
						mappingModel.getSiebleBaseTableColumn());
				// contact.setField("Project__c",
				// ((String)session.getAttribute("projectId")));
				contact.setField("Mapping_Staging_Table__c",
						mappingModel.getMappingSfdcId());
	
				lstContactInsert.add(contact);
				// Add this sObject to an array
			}else{
				SObject contact = new SObject();
				contact.setType("Field_Mapping_Data_Migration__c");
				contact.setField("Id",
						mappingModel.getId());
				
				contact.setField("Table_Name__c",
						mappingModel.getForeignFieldMapping());
				contact.setField("Field_Target__c",
						mappingModel.getSfdcFieldTable());
				contact.setField("Source_Field__c",
						mappingModel.getSiebleBaseTableColumn());
				// contact.setField("Project__c",
				// ((String)session.getAttribute("projectId")));
				contact.setField("Mapping_Staging_Table__c",
						mappingModel.getMappingSfdcId());
	
				lstContactUpdate.add(contact);
	
			}
		}
		
		if(lstContactInsert.size()>0){
			contactInsert=lstContactInsert.toArray(new SObject[lstContactInsert.size()]);
			SaveResult[] saveResults = getPartnerConnection().create(contactInsert);
			for (int j = 0; j < saveResults.length; j++) {
				System.out.println(saveResults[j].isSuccess());
				// System.out.println(saveResults[j].getErrors()[j].getMessage());
			}
		}
		if(lstContactUpdate.size()>0){
			contactUpdate=lstContactUpdate.toArray(new SObject[lstContactUpdate.size()]);
			SaveResult[] saveResults = getPartnerConnection().update(contactUpdate);
			for (int j = 0; j < saveResults.length; j++) {
				System.out.println(saveResults[j].isSuccess());
				// System.out.println(saveResults[j].getErrors()[j].getMessage());
			}
		}
		/*SaveResult[] saveResults = getPartnerConnection().create(contacts);
		for (int j = 0; j < saveResults.length; j++) {
			System.out.println(saveResults[j].isSuccess());
			// System.out.println(saveResults[j].getErrors()[j].getMessage());
		}*/
	}

	public JSONObject getRelatedSiebelTable(String subprojectId) {
		JSONObject tableData = new JSONObject();
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery = "Select Id, Object_API_Name__c, Table_Name__c, Type__c from Table__c where Project__c ='"
					+ subprojectId
					+ "'and Parent_Table__c = null and Type__c ='Siebel'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];
					String id = (String) contact.getField("Id");
					String siebelTableName = (String) contact
							.getField("Object_API_Name__c");
					String sfdcTableName = (String) contact
							.getField("Table_Name__c");
					String type = (String) contact.getField("Type__c");
					tableData.put("id", id);
					tableData.put("siebelTableName", siebelTableName);
					tableData.put("sfdcTableName", sfdcTableName);

					System.out.println("table data " + id + " "
							+ siebelTableName + " " + sfdcTableName);
				}
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return tableData;
		// return connData;
		// Project__c p = [Select Id, Name, Parent_Project__c, Type__c from
		// Project__c where Parent_Project__c='a0PG000000AtiE5' and
		// Name='Account_PreDefined_Mapping'];
	}

	public void saveDataDB(List<MainPage> data, HttpServletRequest request,
			String projId) throws ConnectionException {
		HttpSession session = request.getSession(true);

		login();
		SObject[] contacts = new SObject[data.size()];
		int counter = 0;
		for (Iterator<MainPage> iterator = data.iterator(); iterator.hasNext();) {
			MainPage mainPage = (MainPage) iterator.next();
			if (mainPage.getSfdcId().equals("")) {
				SObject contact = new SObject();
				contact.setType("Mapping_Staging_Table__c");
				contact.setField("Migrate__c",
						Boolean.parseBoolean(mainPage.getMigrate()));

				contact.setField("Sequence__c", mainPage.getSequence());
				contact.setField("Prim_Base_Table__c",
						mainPage.getPrimBaseTable());
				contact.setField("Project__c",
						((String) session.getAttribute("projectId")));
				contact.setField("SFDC_Object__c", mainPage.getSfdcObject());
				contact.setField("Siebel_Object__c", mainPage.getSiebelObject());
				contact.setField("Threshold__c", mainPage.getThreshold());
				contacts[counter] = contact;
				counter++;
				// Add this sObject to an array
				SaveResult[] saveResults = getPartnerConnection().create(
						contacts);
				/*for (int j = 0; j < saveResults.length; j++) {
					//System.out.println(saveResults[j].isSuccess());
					// System.out.println(results[i].getErrors()[i].getMessage());
				}*/
			} else {
				String sqlQuery = "Select Id from Mapping_Staging_Table__c where Id ='"
						+ mainPage.getSfdcId() + "'";
				QueryResult qr = partnerConnection.query(sqlQuery);
				SObject[] records = qr.getRecords();
				for (int i = 0; i < records.length; i++) {
					SObject updateContact = new SObject();
					updateContact.setType("Mapping_Staging_Table__c");
					updateContact.setId(mainPage.getSfdcId());
					updateContact.setField("Migrate__c",
							Boolean.parseBoolean(mainPage.getMigrate()));
					updateContact.setField("Prim_Base_Table__c",
							mainPage.getPrimBaseTable());
					updateContact.setField("Project__c",
							((String) session.getAttribute("projectId")));
					updateContact.setField("SFDC_Object__c",
							mainPage.getSfdcObject());
					updateContact.setField("Siebel_Object__c",
							mainPage.getSiebelObject());
					updateContact.setField("Threshold__c",
							mainPage.getThreshold());
					updateContact.setField("Sequence__c",
							mainPage.getSequence());

					SaveResult[] saveResults = partnerConnection
							.update(new SObject[] { updateContact });
					/*for (int j = 0; j < saveResults.length; j++) {
						//System.out.println(saveResults[j].isSuccess());
						// System.out.println(results[i].getErrors()[i].getMessage());
					}*/

				}

			}

		}
	}

	public List<MainPage> getSavedDBData(String projectId, List<MainPage> data) {
		try {
			// SOQL query to use
			String soqlQuery = "Select Id, Migrate__c, Sequence__c, Prim_Base_Table__c, Project__c, SFDC_Object__c, Siebel_Object__c, Threshold__c from Mapping_Staging_Table__c where Project__c ='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			data.clear();

			while (!done) {
				System.out.println("done");
				SObject[] records = qr.getRecords();
				// Process the query results
				System.out.println("length" + records.length);
				for (int i = 0; i < records.length; i++) {
					MainPage mainPage = new MainPage();
					SObject contact = records[i];
					String id = (String) contact.getField("Id");
					mainPage.setSfdcId(id);
					String siebelTableName = (String) contact
							.getField("Siebel_Object__c");
					mainPage.setSiebelObject(siebelTableName);
					String sfdcTableName = (String) contact
							.getField("SFDC_Object__c");
					mainPage.setSfdcObject(sfdcTableName);
					String migrate = (String) contact.getField("Migrate__c");
					mainPage.setMigrate(migrate);
					String seq = (String) contact.getField("Sequence__c");
					mainPage.setSequence(seq);
					String primBaseTable = (String) contact
							.getField("Prim_Base_Table__c");
					mainPage.setPrimBaseTable(primBaseTable);
					String projId = (String) contact.getField("Project__c");
					mainPage.setProjId(projId);
					String threshold = (String) contact
							.getField("Threshold__c");
					mainPage.setThreshold(threshold);
					data.add(mainPage);
				}
				Collections.sort(data, MainPage.SequenceComparator);
			//	System.out.println("dataaaa  " + data);
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}

		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return data;
	}

	// Amrita
	public String getMappingId(String projectId,
			List<MappingModel> mappingData, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		MappingModel mappingModel = new MappingModel();
		String id = "";
		try {
			partnerConnection.setQueryOptions(250);
			String soqlQuery = "Select ID,  Project__c, Siebel_Object__c, SFDC_Object__c, Prim_Base_Table__c from Mapping_Staging_Table__c where Project__c ='"
					+ projectId
					+ "' and Siebel_Object__c='"
					+ sfdcTablename
					+ "' ";
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
			mappingData.clear();

			while (!done) {
				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {

					SObject contact = records[i];
					id = (String) contact.getField("Id");
					// mappingModel.setMappingSfdcId(id);
					// mappingData.add(mappingModel);
					// mappingData.add(mappingModel);
				}

				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}

		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}

		return id;
	}

	public List<MappingModel> getSavedMappingDBData(String projectId,
			List<MappingModel> mappingData, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		MappingModel mappingModel = new MappingModel();
		String id = "0";
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery = "Select ID,  Project__c, Siebel_Object__c, SFDC_Object__c, Prim_Base_Table__c from Mapping_Staging_Table__c where Project__c ='"
					+ projectId
					+ "' and Siebel_Object__c='"
					+ sfdcTablename
					+ "' ";
			// 'a0PG000000Atg1UMAR'
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			mappingData.clear();

			while (!done) {
				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {

					SObject contact = records[i];

					id = (String) contact.getField("Id");

				}

				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}

		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery1 = "Select Id, Field_Target__c, Source_Field__c, Table_Name__c from Field_Mapping_Data_Migration__c where Mapping_Staging_Table__c ='"
					+ id + "'";
			// Make the query call and get the query results
			QueryResult qr1 = partnerConnection.query(soqlQuery1);
			boolean done1 = false;

			int loopCount1 = 0;
			// Loop through the batches of returned results
			// mappingData.clear();

			while (!done1) {
				SObject[] records1 = qr1.getRecords();
				// Process the query results
				for (int i = 0; i < records1.length; i++) {
					MappingModel mappingModel1 = new MappingModel();
					SObject contact = records1[i];

					String siebelTableColumn = (String) contact
							.getField("Source_Field__c");
					String sfdcTableColumn = (String) contact
							.getField("Field_Target__c");
					String fieldMapping1 = (String) contact
							.getField("Table_Name__c");
					// Project__c, Siebel_Object__c, SFDC_Object__c,
					// Prim_Base_Table__c
					mappingModel1.setMappingSfdcId(id);
					mappingModel1.setId( (String)contact.getField("Id"));
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
				} else {
					qr1 = partnerConnection.queryMore(qr1.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return mappingData;
	}

	/**
	 * @author piymishra
	 * @param query
	 * @param ProjectId
	 */

	@SuppressWarnings("unchecked")
	public String ExtractDataFromSiebel(String query,Map sfdcMapping,Map siebelNames, String ProjectId) {

        File file = null;
        File mappingFile = null;
        Connection connection = null;
        String mappingFileURL="";
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
                  List<Object> myList = new ArrayList<Object>();
                  Statement st = connection.createStatement();
                  ResultSet mySet = st.executeQuery(query);
                  String sFileName = "tryAndTest";
                  if (mySet.next()) {

                        file = new File(sFileName + ".csv");
                        mappingFile = new File(sFileName + ".sdl");
                        createFile(file);
                        createFile(mappingFile);
                        ResultSetMetaData rsmd = mySet.getMetaData();
                        createCSV(mySet,sfdcMapping, siebelNames,rsmd, file, mappingFile);
                  }

                  while (mySet.next()) {
                	 /* System.out.println("=================== in myset");
                        ResultSetMetaData rsmd = mySet.getMetaData();
                        createCSV(mySet,sfdcMapping, siebelNames,rsmd, file, mappingFile);
*/

                  }

             } else {
                  System.out.println("Failed to make connection!");
             }


             if (ProjectId == null)
                  ProjectId = "a0PG000000Atg1U";
          mappingFileURL=  new PartnerWSDL().getFile(file, "19SepDemoFile.csv", "application/vnd.ms-excel", ProjectId, null);
          String SDlFileURl= new PartnerWSDL().getFile(mappingFile, "195SepDemoMappingFile.sdl", "application/vnd.ms-excel", ProjectId, mappingFileURL);
          System.out.println("filr path : " +mappingFileURL+":::::::"+SDlFileURl);
          com.force.example.fulfillment.DataLoaderController dt=new com.force.example.fulfillment.DataLoaderController();
          dt.dataUploadController(mappingFileURL,"subhchakraborty@deloitte.com.vaporizer","Sep@2013","Account");

        } catch (Exception e) {
            // System.out.println(e);
             
             e.printStackTrace();
        }
        // return file;

        return mappingFileURL;

  }


	/**
	 * @author piymishra
	 * @param file
	 */

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

	/**
	 * @author piymishra
	 * @param mySet
	 * @param siebelNames 
	 * @param sfdcMapping 
	 * @param rsmd
	 * @param file
	 * @param mappingFile
	 * @return
	 */
	public static File createCSV(ResultSet mySet, Map<String,String> sfdcMapping, Map<String,String> siebelNames, ResultSetMetaData rsmd,

            File file, File mappingFile) {
       try {

            FileWriter writer = new FileWriter(file);
            FileWriter writerMapping = new FileWriter(mappingFile);
            for (int i = 0; i <= sfdcMapping.size(); i++) {
            	if(sfdcMapping.get("sfdcFieldKey"+i)!=null){
                 writerMapping.append(siebelNames.get("siebelFieldKey"+i)+"="+sfdcMapping.get("sfdcFieldKey"+i));
                 System.out.println(siebelNames.get("siebelFieldKey"+i)+"="+sfdcMapping.get("sfdcFieldKey"+i));  
                 writer.append((CharSequence) siebelNames.get("siebelFieldKey"+i));
                 if (i >= 0 && i != sfdcMapping.size()) {
                       writerMapping.append("\n");
                       writer.append(",");
                 }
                 if (i == sfdcMapping.size()) {
                       writerMapping.append('\n');
                       writer.append('\n');
                 }
            }
            	else{
            		sfdcMapping.remove("sfdcFieldKey"+i);
            		siebelNames.remove("siebelFieldKey"+i);
            	}
            		
         }
            
            
           for (int i = 1; i <= rsmd.getColumnCount(); i++) {
               
                System.out.println(rsmd.getColumnName(i)+" ==== "+mySet.getString(i));
            }
           while(mySet.next())
           {
        	   for (int i = 1; i <= sfdcMapping.size(); i++) {

                   int type = rsmd.getColumnType(i);
                   System.out.println(mySet.getString(i));
                   System.out.println(type + "Types -"+ (type == Types.DATE) + Types.DOUBLE);
                   if (type == Types.VARCHAR || type == Types.CHAR) {
                         writer.append(mySet.getString(i));
                   } else if(type == Types.TIMESTAMP){
                  	 writer.append(mySet.getTimestamp(i).toString());
                   }else if(type == Types.DATE){
                  	 writer.append(mySet.getDate(i).toString());
                   }else{
                         writer.append( mySet.getString(i));
                  	
                   }
                   if (i >= 1 && i != sfdcMapping.size()) {
                         writer.append(",");
                   }

                   if (i ==sfdcMapping.size())
                         writer.append('\n');

              } 
           }
            
            writerMapping.flush();
            writerMapping.close();

            writer.flush();
            writer.close();
            System.out.println("============file creation done==================");
            return file;
       } catch (Exception e) {
            e.printStackTrace();
       }
       return file;

 }


	/**
	 * @author piymishra
	 * @return
	 */

	public DescribeGlobalSObjectResult[] getNames() {
		DescribeGlobalSObjectResult[] sobjectResults = null;
		try {
			// Make the describeGlobal() call
			DescribeGlobalResult describeGlobalResult = partnerConnection
					.describeGlobal();

			// Get the sObjects from the describe global result
			sobjectResults = describeGlobalResult.getSobjects();

			// Write the name of each sObject to the console
			for (int i = 0; i < sobjectResults.length; i++) {
				System.out.println(sobjectResults[i].getName());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return sobjectResults;
	}

	
	/*
	 * public void saveChildDataDB(List<ChildObjectBO> data, HttpServletRequest
	 * request) throws ConnectionException{ HttpSession session =
	 * request.getSession(true);
	 * 
	 * //String [] dataArray = data.toArray(new String[data.size()]); login();
	 * SObject[] contacts = new SObject[data.size()]; int counter=0;
	 * //data.get(0).getMigrate();
	 * System.out.println("in WSDL save child method"); int i=1;
	 * for(ChildObjectBO childObj : data) {
	 * System.out.println("in saving for loop"+i); SObject contact = new
	 * SObject(); contact.setType("Child_Base__c");
	 * contact.setField("Primary_Table__c", childObj.getBaseObjName());
	 * contact.setField("Project__c",
	 * ((String)session.getAttribute("projectId")));
	 * contact.setField("Child_Table__c", childObj.getChildObjName());
	 * contact.setField("Join_Condition__c", childObj.getJoinCondition());
	 * 
	 * contacts[counter] = contact; counter++; i++; } SaveResult[] saveResults =
	 * getPartnerConnection().create(contacts);
	 * System.out.println("save results length is"+saveResults.length); for(int
	 * j=0;j<saveResults.length;j++){
	 * System.out.println(saveResults[j].isSuccess());
	 * //System.out.println(results[i].getErrors()[i].getMessage()); } }
	 */

	// *author Nidhi

	public void saveChildDataDB(List<ChildObjectBO> data,
			HttpServletRequest request) throws ConnectionException {
		HttpSession session = request.getSession(true);

		// String [] dataArray = data.toArray(new String[data.size()]);
		login();
		SObject[] contacts = new SObject[data.size()];
		int counter = 0;
		// data.get(0).getMigrate();
		System.out.println("in WSDL save child method");
		int i = 1;
		for (ChildObjectBO childObj : data) {
			/*if (childObj.isCheckFlag()) {
				System.out.println("in saving for loop" + i);
				SObject contact = new SObject();
				contact.setType("Child_Base__c");
				contact.setField("Primary_Table__c", childObj.getBaseObjName());
				contact.setField("Project__c",
						((String) session.getAttribute("projectId")));
				contact.setField("Child_Table__c", childObj.getChildObjName());
				contact.setField("Join_Condition__c",
						childObj.getJoinCondition());
				contact.setField("Saved__c ", childObj.isCheckFlag());

				// System.out.println("child Saved value is"+
				// contact.getField("Child_Table__c"));
				contacts[counter] = contact;
				counter++;
			} else {
				System.out.println("in not save false block" + i);
			}
			i++;*/
			
			
				System.out.println("in saving for loop" + i);
				SObject contact = new SObject();
				contact.setType("Child_Base__c");
				contact.setField("Primary_Table__c", childObj.getBaseObjName());
				contact.setField("Project__c",
						((String) session.getAttribute("projectId")));
				contact.setField("Child_Table__c", childObj.getChildObjName());
				contact.setField("Join_Condition__c",
						childObj.getJoinCondition());
				contact.setField("Saved__c ", childObj.isCheckFlag());

				// System.out.println("child Saved value is"+
				// contact.getField("Child_Table__c"));
				contacts[counter] = contact;
				counter++;
			

		} //end of for loop
//here I save it
		if(contacts.length >= 199)
		{
			System.out.println("more dan 199 TRANSACTION");
			  List<SObject>  tmpList=new ArrayList();
	          for(int k=0;k<contacts.length;k++){
	        	 System.out.println("in contact block");
	             tmpList.add(contacts[k]);
	               		if(k==199){	
	               			System.out.println("in save block DML");
			SaveResult[] saveResults = getPartnerConnection().create(tmpList.toArray(new SObject[tmpList.size()]));
			
			
	        tmpList=new ArrayList();

	               		}
	} //end of for

		} //end of IF
		else
		{
			System.out.println("Less Transaction block");
			SaveResult[] saveResults = getPartnerConnection().create(contacts);
			for (int j = 0; j < saveResults.length; j++) {
				System.out.println(saveResults[j].isSuccess());
				// System.out.println(saveResults[j].getErrors()[j].getMessage());
			}
		}
	}

	public List<ChildObjectBO> getSavedChildDBData(String projectId,
			String primTable) {
		List<ChildObjectBO> childData = new ArrayList<ChildObjectBO>();
		try {
			System.out.println("in getSave Child Data proj is" + projectId
					+ "and prim is" + primTable);
			partnerConnection.setQueryOptions(250);

			// String subprojectId="a0PG000000AtiEAMAZ";
			// String soqlQuery =
			// "Select Id, Migrate__c, Sequence__c, Prim_Base_Table__c, Project__c, SFDC_Object__c, Siebel_Object__c, Threshold__c from Mapping_Staging_Table__c where Project__c ='"+projectId+"'";

			String soqlQuery = "Select Id, Saved__c , Primary_Table__c, Child_Table__c, Join_Condition__c, Project__c"
					+ " from Child_Base__c where Project__c ='"
					+ projectId
					+ "' and Primary_Table__c = '" + primTable + "' ";

			// Make the query call and get the query results
			QueryResult qryResult = partnerConnection.query(soqlQuery);
			boolean done = false;
			int loopCount = 0;
			// Loop through the batches of returned results
			childData.clear();
			int counter = 1;
			while (!done) {
				SObject[] records = qryResult.getRecords();
				System.out.println("records len from DB is" + records.length);

				// Process the query results
				for (int i = 0; i < records.length; i++) {

					ChildObjectBO childPageObj = new ChildObjectBO();
					SObject contact = records[i];
					/*
					 * String id = (String) contact .getField("Id");
					 * mainPage.setSfdcId(id);
					 */

					boolean checkFlag = Boolean.parseBoolean((String) contact
							.getField("Saved__c"));
					childPageObj.setCheckFlag(checkFlag);

					String siebelBaseTable = (String) contact
							.getField("Primary_Table__c");
					childPageObj.setBaseObjName(siebelBaseTable);

					String siebelChildTable = (String) contact
							.getField("Child_Table__c");
					childPageObj.setChildObjName(siebelChildTable);

					String joinCondtn = (String) contact
							.getField("Join_Condition__c");
					childPageObj.setJoinCondition(joinCondtn);

					childPageObj.setSeqNum(counter);

					/*
					 * String projId = (String) contact.getField("Project__c");
					 * mainPage.setProjId(projId);
					 */

					childData.add(childPageObj);
					counter++;
				}
				/*
				 * Collections.sort(childData, new Comparator<ChildObjectBO>(){
				 * 
				 * @Override public int compare(MainPage mainPage1, MainPage
				 * mainPage2) {
				 * 
				 * return
				 * mainPage1.getSequence().compareTo(mainPage2.getSequence()); }
				 * });
				 */

				if (qryResult.isDone()) {
					done = true;
				} else {
					qryResult = partnerConnection.queryMore(qryResult
							.getQueryLocator());
				}

			} // end of while

		} catch (ConnectionException ce) {
			System.out.println("in catch block");
			ce.printStackTrace();
		}
		/*System.out.println("\n Child Saved Data Query execution completed.");*/
		return childData;
	}
	public  String getextractionData(String projectId, String sfdcId, String subProjectId) 
    {
        
        String selectTables = "";
        String fromTables = "";
        String joinTables ="";
        String query = null;
        String mainTable = null;
        HashMap<String, String> siebelFieldMap = new HashMap<String, String>();
        HashMap<String, String> sfdcFieldMap = new HashMap<String, String>();
        int childTableCounter = 1;
        String sTab="";
        
               
                String soqlQuery2 = "Select Field_Target__c, Source_Field__c, Table_Name__c from Field_Mapping_Data_Migration__c  where Mapping_Staging_Table__c ='"+sfdcId+"'";
                System.out.println(">>>>"+sfdcId);
                try{
                    QueryResult qr = partnerConnection.query(soqlQuery2);
                    boolean done = false;
                    while(!done){
                        SObject[] records = qr.getRecords();
                        System.out.println(records.length);
                        //if there is no user defined data
                        if(records.length == 0){
                        	System.out.println("Predefined data>>>>>>>>>>>>>>");
                            String soqlQuery1 = "Select id, Object_API_Name__c, Project__c,Join_Condition__c, Table_Name__c, Type__c from Table__c where Project__c= '"+subProjectId+"'";
                            QueryResult qr1 = partnerConnection.query(soqlQuery1);
                            records = qr1.getRecords();
                            for (int i = 0; i < records.length; i++) {
                                SObject contact = records[i];
                                
                                String type = (String) contact.getField("Type__c");
                                
                                if(type.equals("Siebel")) {
                                     mainTable = (String) contact.getField("Object_API_Name__c");   
                                    fromTables = fromTables+" "+"SIEBEL."+mainTable+" "+"mainTableAlias";   
                                    String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
                                            + "Source_Base_Table__c = '"+(String) contact.getField("Id")+"'";
                                    QueryResult result = partnerConnection.query(mappingQuery);
                                    SObject[] mappingRecords = result.getRecords();
                                    for(int j=0;j<mappingRecords.length;j++){
                                        SObject mappingRecord = mappingRecords[j];
                                        String salesForceField = (String) mappingRecord.getField("Field_Target__c");
                                        String siebelField = (String) mappingRecord.getField("Source_Field__c");
                                        XmlObject sourceTable = (XmlObject) mappingRecord.getField("Source_Base_Table__r");
                                        String siebelBaseTable = (String) sourceTable.getChild("Object_API_Name__c").getValue();
                                        siebelFieldMap.put("siebelFieldKey"+i, siebelField);
                                        sfdcFieldMap.put("sfdcFieldKey"+i, salesForceField);
                                        //selectTables = selectTables+" "+ siebelBaseTable+"."+siebelField +" "+"as"+ " "+"\""+ siebelField+"="+salesForceField+"\""+",";                           
                                        selectTables = selectTables+" "+ "mainTableAlias"+"."+siebelField+",";
                                    }
                                        
                                    
                                    }else{
                                        if(type.equals("Siebel Child")){
                                            String childTable = (String) contact.getField("Table_Name__c");
                                            String joinCondition = (String) contact.getField("Join_Condition__c");
                                            if(joinCondition.contains(childTable)){
                                                joinCondition = joinCondition.replace(childTable, "childTableAlias"+childTableCounter);
                                            }
                                            if(joinCondition.contains(mainTable)){
                                                joinCondition = joinCondition.replace(mainTable, "mainTableAlias");
                                            }
                                            joinTables = joinTables+" "+"LEFT OUTER JOIN "+"SIEBEL."+childTable+" "+"childTableAlias"+childTableCounter+" on "+joinCondition;
                                            String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
                                                    + "Source_Base_Table__c = '"+(String) contact.getField("Id")+"'";
                                            QueryResult result = partnerConnection.query(mappingQuery);
                                            SObject[] mappingRecords = result.getRecords();
                                            for(int j=0;j<mappingRecords.length;j++){
                                                SObject mappingRecord = mappingRecords[j];
                                                
                                                String salesForceField = (String) mappingRecord.getField("Field_Target__c");
                                                String siebelField = (String) mappingRecord.getField("Source_Field__c");
                                                XmlObject sourceTable = (XmlObject) mappingRecord.getField("Source_Base_Table__r");
                                                String siebelBaseTable = (String) sourceTable.getChild("Object_API_Name__c").getValue();
                                                siebelFieldMap.put("siebelFieldKey"+i, siebelField);
                                                sfdcFieldMap.put("sfdcFieldKey"+i, salesForceField);
                                                //selectTables = selectTables+" "+ siebelBaseTable+"."+siebelField +" "+"as"+ " "+"\""+ siebelField+"="+salesForceField+"\""+",";                                   
                                                selectTables = selectTables+" "+ "childTableAlias"+childTableCounter+"."+siebelField+",";
                                            }
                                            childTableCounter++;
                                            
                                        }
                                    }
                                
                                }
                        }
                        //if user defined data is present
                        else{
                        
                        QueryResult qr2 = partnerConnection.query(soqlQuery2);
                        SObject[] records2 = qr2.getRecords();
                        
                        String soqlQuery3 = "Select Id, Child_Table__c, Join_Condition__c,Primary_Table__c from Child_Base__c where Project__c='"+subProjectId+"'";
                        QueryResult qr3 = partnerConnection.query(soqlQuery3);
                        SObject[] records3 = qr3.getRecords();
                        List<SObject> records4 =new ArrayList();
                        SObject childBase = null;
                        for (int i = 0; i < records2.length; i++) {
                        	if((String) records2[i].getField("Field_Target__c")!=null){
                        	if((((String) records2[i].getField("Field_Target__c")).equals("CREATEDBYID"))||(((String) records2[i].getField("Field_Target__c")).equals("CREATEDDATE"))){
                      		  System.out.println("sfdc continue"+(String) records2[i].getField("Field_Target__c")); 
                        	}
                      		  
                      	
                        	else{
                        		records4.add(records2[i]);
                        		
                        	}
                        	}
                        	
                        }
                        records2=records4.toArray(new SObject[records4.size()]);
                        for (int i = 0; i < records2.length; i++) {
                            SObject contact = records2[i];
                            if(contact.getField("Table_Name__c")==null){
                            	System.out.println("continue");
                            	continue;
                            	
                            	
                            	
                            }
                            
                            boolean bFlag=false;
                            for (int op=0; op<records3.length;op++){
                            	//if((String)records3[op].getField("Table_Name__c")!=null)
                            	
                                if(records3[op].getField("Child_Table__c")==contact.getField("Table_Name__c")){
                                    bFlag=true;
                                    childBase = records3[op];
                                    //id = records3[j].getField("Id");
                                }
                            }
                            //its a main table
                            if(bFlag==false) {
                            	// System.out.println("sfdce"+(((String) contact.getField("Field_Target__c")).equals("CREATEDBYID"))); 
                            	
                                mainTable = (String) contact.getField("Table_Name__c");   
                                sTab="SIEBEL."+mainTable+" "+"mainTableAlias";
                                fromTables = fromTables+" "+"SIEBEL."+mainTable+" "+"mainTableAlias";   
                                //String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
                                //  + "Source_Base_Table__c = '"+(String) contact.getField("Id")+"'";
                                //  QueryResult result = partnerConnection.query(mappingQuery);
                                //SObject[] mappingRecords = result.getRecords();

                                //SObject mappingRecord = mappingRecords[i];
                                String salesForceField = (String) contact.getField("Field_Target__c");
                                String siebelField = (String) contact.getField("Source_Field__c");
                                //XmlObject sourceTable = (XmlObject) mappingRecord.getField("Source_Base_Table__r");
                                //String siebelBaseTable = (String) sourceTable.getChild("Object_API_Name__c").getValue();
                                siebelFieldMap.put("siebelFieldKey"+i, siebelField);
                                System.out.println("Sibel "+siebelField);
                                System.out.println("sfdc "+salesForceField);
                                sfdcFieldMap.put("sfdcFieldKey"+i, salesForceField);
                                //selectTables = selectTables+" "+ siebelBaseTable+"."+siebelField +" "+"as"+ " "+"\""+ siebelField+"="+salesForceField+"\""+",";                           
                                selectTables = selectTables+" "+ "mainTableAlias"+"."+siebelField+",";


                            }
                            
                            else   //its a child table        
                                {
                                //for (int k=0;k<records3.length;k++){
                                //need to change name of contact1
                                
                                String childTable = (String) childBase.getField("Table_Name__c");
                                String joinCondition = (String) childBase.getField("Join_Condition__c");
                                if(joinCondition.contains(childTable)){
                                    joinCondition = joinCondition.replace(childTable, "childTableAlias"+childTableCounter);
                                }
                                if(joinCondition.contains(mainTable)){
                                    joinCondition = joinCondition.replace(mainTable, "mainTableAlias");
                                }
                                joinTables = joinTables+" "+"LEFT OUTER JOIN "+"SIEBEL."+childTable+" "+"childTableAlias"+childTableCounter+" on "+joinCondition;
                                //String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
                                //  + "Source_Base_Table__c = '"+(String) contact.getField("Id")+"'";
                                //QueryResult result = partnerConnection.query(mappingQuery);
                                //SObject[] mappingRecords = result.getRecords();
                                //for(int j=0;j<mappingRecords.length;j++){
                                //SObject mappingRecord = mappingRecords[j];

                                String salesForceField = (String) contact.getField("Field_Target__c");
                                String siebelField = (String) contact.getField("Source_Field__c");
                                XmlObject sourceTable = (XmlObject) contact.getField("Source_Base_Table__r");
                                String siebelBaseTable = (String) sourceTable.getChild("Object_API_Name__c").getValue();
                                siebelFieldMap.put("siebelFieldKey"+i, siebelField);
                                sfdcFieldMap.put("sfdcFieldKey"+i, salesForceField);
                                //selectTables = selectTables+" "+ siebelBaseTable+"."+siebelField +" "+"as"+ " "+"\""+ siebelField+"="+salesForceField+"\""+",";                                   
                                selectTables = selectTables+" "+ "childTableAlias"+childTableCounter+"."+siebelField+",";
                                //}
                                childTableCounter++;

                            }
                        }
                            
                            }
                    
                                                        
                       
                        
                  
                    
                    //String mappingFileUR= ExtractDataFromSiebel(query, sfdcFieldMap, siebelFieldMap, projectId);
                    if (qr.isDone()) {
                        done = true;
                    } else {
                        qr = partnerConnection.queryMore(qr.getQueryLocator());
                    }  
                }
                    } 
                     
                 // }
                 catch (ConnectionException ce) {
                    ce.printStackTrace();
                } 
                System.out.println("########"+selectTables);
                if(selectTables!=""){
                    selectTables = selectTables.substring(0, selectTables.length()-1);
                    System.out.println("########"+selectTables);
                    System.out.println("########"+fromTables);
                     query = "Select"+selectTables+" "+"FROM"+" "+sTab+" "+joinTables+" ";
                        
                        System.out.println(query);
                }
                System.out.println("\nQuery execution completed.");
                //query="Select mainTableAlias.CREATED_BY, mainTableAlias.CREATED, mainTableAlias.DESC_TEXT, mainTableAlias.MAIN_FAX_PH_NUM, mainTableAlias.NAME, mainTableAlias.MAIN_PH_NUM FROM SIEBEL.S_ORG_EXT mainTableAlias";
                String mappingFileURL=ExtractDataFromSiebel(query, sfdcFieldMap, siebelFieldMap, projectId);
                return mappingFileURL;
        }
	/**
	 * @author piymishra
	 * @param ObjectName
	 * @return
	 */
	public List<SfdcObjectBO> getSFDCOjectListforPopup(String ObjectName) {
		List<SfdcObjectBO> objList = new ArrayList<SfdcObjectBO>();

		try {
			login();
			DescribeGlobalSObjectResult[] sobjectResults = getNames();
			for (int i = 0; i < sobjectResults.length; i++) {
				SfdcObjectBO Sob = new SfdcObjectBO();
				if (sobjectResults[i].getName().contains(ObjectName)) {
					Sob.setObjName(sobjectResults[i].getName());
					objList.add(Sob);
				}

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return objList;
	}

	// Amrita:Fetch selected siebel child
	public List<String> getSavedChild(String projectId, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		ArrayList<String> child = new ArrayList<String>();
		try {

			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery = "Select   Child_Table__c  from Child_Base__c where Project__c='"
					+ projectId
					+ "' and Primary_Table__c='"
					+ siebelTableName
					+ "' and Saved__c=true";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];

					child.add((String) contact.getField("Child_Table__c"));
				}

				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return child;
	}

	void commentedCode() {
		/*
		 * public List<String > addObjectToTable(List<SiebelObject>
		 * listOfObjects, String projectId) { List<String > result = null; try {
		 * SObject[] tableObjects = new SObject[listOfObjects.size()]; int
		 * counter = 0; // iterate over the list of objects to get siebelObject
		 * for (Iterator<SiebelObject> iterator = listOfObjects.iterator();
		 * iterator .hasNext();) { SiebelObject siebelObject = iterator.next();
		 * // Create a new sObject of type Contact // and fill out its fields.
		 * System.out.println("we are ereeeeeeeeeeeeeeeeeeee"); SObject
		 * tableObject = new SObject(); tableObject.setType("Table__c");
		 * tableObject.setField("Project__c", projectId);
		 * tableObject.setField("Name", siebelObject.getName());
		 * tableObject.setField("Entity__c", siebelObject.getEntityType());
		 * tableObject.setField("Type__c", siebelObject.getType());
		 * tableObject.setField("Table_Name__c", siebelObject.getTableName());
		 * tableObject .setField("Siebel_ID__c", siebelObject.getSiebelId());
		 * tableObject.setField("Table_Comments__c",
		 * siebelObject.getDescription()); tableObject.setField("Repository__c",
		 * siebelObject.getRepositoryName());
		 * 
		 * tableObjects[counter] = tableObject; counter++; } result =
		 * createSalesForceObject( tableObjects);
		 * 
		 * } catch (ConnectionException ce) { ce.printStackTrace(); } return
		 * result; }
		 * 
		 * private String createSalesForceObject(String result, SObject[]
		 * tableObjects, List<SiebelField> listOfFields, String projectId)
		 * throws ConnectionException { // Make a create call and pass it the
		 * array of sObjects SaveResult[] results =
		 * partnerConnection.create(tableObjects);
		 * 
		 * System.out.println("results:::::arr" + results.length); for (int i =
		 * 0; i < results.length; i++) {
		 * 
		 * System.out.println("resulting array which is to be inserted" +
		 * results.length); }
		 * 
		 * for (int l = 0; l < listOfFields.size(); l++) {
		 * 
		 * System.out.println("Siebel field pick type" +
		 * listOfFields.get(l).getPickType()); }
		 * 
		 * // Iterate through the results list // and write the ID of the new
		 * sObject // or the errors if the object creation failed. // In this
		 * case, we only have one result // since we created one contact. for
		 * (int j = 0; j < results.length; j++) {
		 * System.out.println("getting values " + results[j].isSuccess() + "  "
		 * + listOfFields.get(j).getSiebelPicklistType().equals("Y") + "   " +
		 * listOfFields.get(j).getName() + "pick type::" +
		 * listOfFields.get(j).getPickType()); if (results[j].isSuccess() &&
		 * listOfFields.get(j).getSiebelPicklistType().equals("Y")) { // here
		 * logic should be for inserting picklist values,if parent // table is
		 * successfully inserted System.out.println("pick type:::values::" +
		 * listOfFields.get(j).getPickType());
		 * 
		 * result = results[j].getId();// parent table ID System.out
		 * .println("\nA Field was added was created with an ID of: " + result);
		 * List<SiebelListOfValues> pickListValue = AddObjectFieldController
		 * .getPickListOfValues(projectId, listOfFields.get(j) .getPickType());
		 * insertIntoPickListTable(pickListValue, result);
		 * 
		 * } else { // There were errors during the create call, // go through
		 * the errors array and write // them to the console for (int i = 0; i <
		 * results[j].getErrors().length; i++) { Error err =
		 * results[j].getErrors()[i];
		 * System.out.println("Errors were found on item " + j);
		 * System.out.println("Error code: " + err.getStatusCode().toString());
		 * System.out.println("Error message: " + err.getMessage()); } } }
		 * return result; }
		 * 
		 * public List<String> createSalesForceObject(SObject[] tableObjects)
		 * throws ConnectionException { // Make a create call and pass it the
		 * array of sObjects SaveResult[] results =
		 * partnerConnection.create(tableObjects); // Iterate through the
		 * results list // and write the ID of the new sObject // or the errors
		 * if the object creation failed. // In this case, we only have one
		 * result // since we created one contact. List<String> idList= new
		 * LinkedList<String>(); String result; for (int j = 0; j <
		 * results.length; j++) { if (results[j].isSuccess()) { // here logic
		 * should be for picklist values
		 * 
		 * result = results[j].getId(); idList.add(result); System.out
		 * .println("\nA table object was created with an ID of: " + result);
		 * 
		 * 
		 * for(){
		 * 
		 * tableObjects[0]. }
		 * 
		 * } else { // There were errors during the create call, // go through
		 * the errors array and write // them to the console for (int i = 0; i <
		 * results[j].getErrors().length; i++) { Error err =
		 * results[j].getErrors()[i];
		 * System.out.println("Errors were found on item " + j);
		 * System.out.println("Error code: " + err.getStatusCode().toString());
		 * System.out.println("Error message: " + err.getMessage()); } } }
		 * return idList; }
		 * 
		 * public String addFieldsToTable(List<SiebelField> listOfFields, String
		 * projectId, String siebelId, String tableId) { String result=null; try
		 * { SObject[] tableObjects = new SObject[listOfFields.size()]; int
		 * counter = 0; // iterate over the list of objects to get siebelObject
		 * for (Iterator<SiebelField> iterator = listOfFields.iterator();
		 * iterator .hasNext();) { SiebelField siebelField = iterator.next();
		 * 
		 * System.out.println("siebel pick type:::" +
		 * siebelField.getPickType());
		 * 
		 * // Create a new sObject of type Contact // and fill out its fields.
		 * System.out.println("we are ereeeeeeeeeeeeeeeeeeee"); SObject
		 * tableObject = new SObject(); tableObject.setType("Field__c");
		 * tableObject.setField("Name", siebelField.getName());
		 * tableObject.setField("Field_Type__c", siebelField.getType());
		 * tableObject.setField("Repository__c",
		 * siebelField.getRepositoryName());
		 * tableObject.setField("Column_Name__c", siebelField.getColumnName());
		 * 
		 * tableObject.setField("Table__c", tableId);
		 * 
		 * tableObjects[counter] = tableObject; counter++; } result =
		 * createSalesForceObject(result, tableObjects, listOfFields,
		 * projectId);
		 * 
		 * } catch (ConnectionException ce) { ce.printStackTrace(); } return
		 * result; }
		 * 
		 * public List<String > insertIntoPickListTable(List<SiebelListOfValues>
		 * pickList, String tableId) { List<String > result = null; try {
		 * SObject[] tableObjects = new SObject[pickList.size()]; int counter =
		 * 0; for (int i = 0; i < pickList.size(); i++) {
		 * 
		 * // table id for each picklist SiebelListOfValues listValue =
		 * pickList.get(i);
		 * System.out.println("inserting into picklist --------" + tableId +
		 * "     " + listValue.getValue()); SObject tableObject = new SObject();
		 * tableObject.setType("Picklist__c");// //
		 * tableObject.setField("field", listValue.getId().toString());
		 * tableObject.setField("Value__c", listValue.getValue());// Integer
		 * tableObject.setField("Selected__c", listValue.getSelected());//
		 * Boolean; tableObject.setField("Field__c", tableId);// Boolean;
		 * tableObjects[counter] = tableObject; counter++;
		 * 
		 * }
		 * 
		 * result = createSalesForceObject(tableObjects);
		 * 
		 * } catch (ConnectionException ce) { ce.printStackTrace(); } return
		 * result; } // method to get the table ids and the corr siebelids
		 * 
		 * public Map<String,String> getSiebleIdForTableId(List<String>
		 * tableIds) { Map<String,String> mapofSiebelTableIds= new
		 * HashMap<String,String>(); try { // Set query batch size
		 * 
		 * partnerConnection.setQueryOptions(250); String ids=new String(); //
		 * SOQL query to use for (Iterator iterator = tableIds.iterator();
		 * iterator.hasNext();) { String tableId = (String) iterator.next();
		 * if(iterator.hasNext()) ids+=ids+"'"+tableId+"',"; else
		 * ids+=ids+"'"+tableId+"'"; } System.out.println(ids); String soqlQuery
		 * = "SELECT Id,Siebel_ID__c FROM Table__C where id in ( "+ids+" )"; //
		 * Make the query call and get the query results QueryResult qr =
		 * partnerConnection.query(soqlQuery); boolean done = false; int
		 * loopCount = 0; // Loop through the batches of returned results while
		 * (!done) { System.out.println("Records in results set " + loopCount++
		 * + " - "); SObject[] records = qr.getRecords(); // Process the query
		 * results for (int i = 0; i < records.length; i++) { SObject table =
		 * records[i]; Object id = table.getField("Id"); Object siebelId =
		 * table.getField("Siebel_ID__c");
		 * System.out.println("7777777777 "+id+"  "+siebelId+" 77777777777777");
		 * mapofSiebelTableIds.put(id.toString(), siebelId.toString());
		 * 
		 * 
		 * } if (qr.isDone()) { done = true; } else { qr =
		 * partnerConnection.queryMore(qr.getQueryLocator()); } } } catch
		 * (ConnectionException ce) { ce.printStackTrace(); }
		 * System.out.println("\nQuery execution completed."); return
		 * mapofSiebelTableIds; }
		 * 
		 * 
		 * 
		 * 
		 * public String createSample() { String result = null; try { // Create
		 * a new sObject of type Contact // and fill out its fields. SObject
		 * contact = new SObject(); contact.setType("Merchandise__c");
		 * contact.setField("Name", "Mobilessss"); contact.setField("Price__c",
		 * "100.00"); contact.setField("Quantity__c", "5");
		 * 
		 * SObject[] contacts = new SObject[1]; contacts[0] = contact; result =
		 * createSalesForceObject(result, contacts); } catch
		 * (ConnectionException ce) { ce.printStackTrace(); } return result; }
		 * public void getNames() { try { // Make the describeGlobal() call
		 * DescribeGlobalResult describeGlobalResult = partnerConnection
		 * .describeGlobal();
		 * 
		 * // Get the sObjects from the describe global result
		 * DescribeGlobalSObjectResult[] sobjectResults = describeGlobalResult
		 * .getSobjects();
		 * 
		 * // Write the name of each sObject to the console for (int i = 0; i <
		 * sobjectResults.length; i++) {
		 * System.out.println(sobjectResults[i].getName()); } } catch
		 * (ConnectionException ce) { ce.printStackTrace(); } }
		 * 
		 * public void describeObjects() { try { DescribeSObjectResult[]
		 * describeSObjectResults = partnerConnection .describeSObjects(new
		 * String[] { "Merchandise__c" });
		 * 
		 * // Iterate through the list of describe sObject results for (int i =
		 * 0; i < describeSObjectResults.length; i++) { DescribeSObjectResult
		 * desObj = describeSObjectResults[i]; // Get the name of the sObject
		 * String objectName = desObj.getName();
		 * System.out.println("sObject name: " + objectName);
		 * 
		 * // For each described sObject, get the fields Field[] fields =
		 * desObj.getFields();
		 * 
		 * // Get some other properties // if (desObj.getActivateable()) //
		 * System.out.println("\tActivateable");
		 * 
		 * // Iterate through the fields to get properties for each field for
		 * (int j = 0; j < fields.length; j++) { Field field = fields[j];
		 * System.out.println("\tField: " + field.getName());
		 * System.out.println("\t\tLabel: " + field.getLabel());
		 * 
		 * if (field.isCustom())
		 * System.out.println("\t\tThis is a custom field.");
		 * 
		 * System.out.println("\t\tType: " + field.getType());
		 * 
		 * if (field.getLength() > 0) System.out.println("\t\tLength: " +
		 * field.getLength()); if (field.getPrecision() > 0)
		 * System.out.println("\t\tPrecision: " + field.getPrecision());
		 * 
		 * 
		 * // Determine whether this is a picklist field
		 * 
		 * if (field.getType() == FieldType.picklist) { // Determine whether
		 * there are picklist values PicklistEntry[] picklistValues =
		 * field.getPicklistValues(); if (picklistValues != null &&
		 * picklistValues[0] != null) {
		 * System.out.println("\t\tPicklist values = "); for (int k = 0; k <
		 * picklistValues.length; k++) { System.out.println("\t\t\tItem: " +
		 * picklistValues[k].getLabel()); } } }
		 * 
		 * 
		 * // Determine whether this is a reference field
		 * 
		 * if (field.getType() == FieldType.reference) { // Determine whether
		 * this field refers to another object String[] referenceTos =
		 * field.getReferenceTo(); if (referenceTos != null && referenceTos[0]
		 * != null) { System
		 * .out.println("\t\tField references the following objects:" ); for
		 * (int k = 0; k < referenceTos.length; k++) {
		 * System.out.println("\t\t\t" + referenceTos[k]); } } }
		 * 
		 * 
		 * } } } catch (ConnectionException ce) { ce.printStackTrace(); } }
		 * 
		 * public void updateSample() { try { // Create an sObject of type
		 * contact SObject updateContact = new SObject();
		 * updateContact.setType("Contact"); // Set the ID of the contact to
		 * update updateContact.setId("003i000000bi3DJAAY"); // Set the Phone
		 * field with a new value updateContact.setField("Phone",
		 * "rrrrrrrrrrrrr"); // updateContact.setField("Phone",
		 * "(415) 555-1212"); // Create another contact that will cause an error
		 * // because it has an invalid ID.
		 * 
		 * SaveResult[] saveResults = partnerConnection .update(new SObject[] {
		 * updateContact }); // Iterate through the results and write the ID of
		 * // the updated contacts to the console, in this case one contact. //
		 * If the result is not successful, write the errors // to the console.
		 * In this case, one item failed to update. for (int j = 0; j <
		 * saveResults.length; j++) { System.out.println("\nItem: " + j); if
		 * (saveResults[j].isSuccess()) {
		 * System.out.println("Contact with an ID of " + saveResults[j].getId()
		 * + " was updated."); } else { // There were errors during the update
		 * call, // go through the errors array and write // them to the
		 * console. for (int i = 0; i < saveResults[j].getErrors().length; i++)
		 * { Error err = saveResults[j].getErrors()[i];
		 * System.out.println("Errors were found on item " + j);
		 * System.out.println("Error code: " + err.getStatusCode().toString());
		 * System.out .println("Error message: " + err.getMessage()); } } } }
		 * catch (ConnectionException ce) { ce.printStackTrace(); } }
		 * 
		 * public void querySample() { try { // Set query batch size
		 * partnerConnection.setQueryOptions(250); // SOQL query to use String
		 * soqlQuery = "SELECT Id,FirstName, LastName FROM Contact"; // Make the
		 * query call and get the query results QueryResult qr =
		 * partnerConnection.query(soqlQuery); boolean done = false; int
		 * loopCount = 0; // Loop through the batches of returned results while
		 * (!done) { System.out.println("Records in results set " + loopCount++
		 * + " - "); SObject[] records = qr.getRecords(); // Process the query
		 * results for (int i = 0; i < records.length; i++) { SObject contact =
		 * records[i]; Object id = contact.getField("Id"); Object firstName =
		 * contact.getField("FirstName"); Object lastName =
		 * contact.getField("LastName");
		 * System.out.println("conatcttttttttttt idddddddd " + id);
		 * 
		 * } if (qr.isDone()) { done = true; } else { qr =
		 * partnerConnection.queryMore(qr.getQueryLocator()); } } } catch
		 * (ConnectionException ce) { ce.printStackTrace(); }
		 * System.out.println("\nQuery execution completed."); }
		 * 
		 * // Code for inserting into Code table
		 * 
		 * public List<String> insertScriptIntoSalesForce(
		 * List<SalesForceScriptObject> sfdcScript) { List<String> result =
		 * null; try { // Create a new sObject of type Contact // and fill out
		 * its fields. SObject contact_test = new SObject();
		 * 
		 * System.out.println("inserting into salesforce record");
		 * 
		 * SObject[] tableObjects = new SObject[sfdcScript.size()]; int counter
		 * = 0; for (int i = 0; i < sfdcScript.size(); i++) {
		 * 
		 * // table id for each picklist SalesForceScriptObject listValue =
		 * sfdcScript.get(i);
		 * 
		 * SObject tableObject = new SObject();
		 * tableObject.setType("Business_Component__c");//
		 * tableObject.setField("Name", listValue.getName());
		 * tableObject.setField("Body__c", listValue.getBody());
		 * tableObject.setField("Project__c", listValue.getProjectId());
		 * tableObject.setField("Source__c", listValue.getSource());
		 * tableObject.setField("Table__c", listValue.getTableId());
		 * 
		 * 
		 * tableObjects[counter] = tableObject; counter++;
		 * 
		 * 
		 * Deployed__c Draft__c Editable__c Object_Name__c Primary_Version__c
		 * Project_Run__c Script__c Script_1__c Sforce_Object__c Siebel_Name__c
		 * Source_Code__c Sub_Type__c Table__c Type__c
		 * 
		 * }
		 * 
		 * result = createSalesForceObject( tableObjects);
		 * 
		 * } catch (ConnectionException ce) { ce.printStackTrace(); } return
		 * result; }
		 * 
		 * public static List<SalesForceScriptObject> getSiebelScript( String
		 * projectId, String objectName) throws SQLException {
		 * 
		 * List<SalesForceScriptObject> scriptObjectList = null; try {
		 * 
		 * PartnerWSDL partnerWSDL = new PartnerWSDL(); partnerWSDL.login();
		 * 
		 * JSONObject connectionData = partnerWSDL
		 * .getConnectionData(projectId); Connection connection = new
		 * UtilityClass() .getConnection(connectionData);
		 * System.out.println("Connecting to database...");
		 * 
		 * String query = "";
		 * 
		 * query +=
		 * "SELECT a.NAME,a.SCRIPT FROM SIEBEL.S_BUSCOMP_SCRIPT a, SIEBEL.S_BUSCOMP b where a.BUSCOMP_ID = b.ROW_ID "
		 * ; query += "AND "; query += "  b.NAME = '" + objectName + "' ";
		 * 
		 * System.out.println("Running query " + query); Statement statement =
		 * connection.createStatement( ResultSet.TYPE_FORWARD_ONLY,
		 * ResultSet.CONCUR_READ_ONLY); ResultSet recordSet =
		 * statement.executeQuery(query);
		 * 
		 * scriptObjectList = new ArrayList<SalesForceScriptObject>();
		 * 
		 * while (recordSet.next()) { // SiebelField field = new SiebelField();
		 * SalesForceScriptObject scriptObject = new SalesForceScriptObject();
		 * scriptObject.setName(recordSet.getString(1));
		 * scriptObject.setbo(recordSet.getString(2));
		 * 
		 * // Script_Name__c scriptObjectList.add(scriptObject); } } catch
		 * (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } return scriptObjectList; }
		 * 
		 * // added for injecting mock object for testcases
		 * 
		 * public PartnerConnection getPartnerConnection() { return
		 * partnerConnection; }
		 * 
		 * public void setPartnerConnection(PartnerConnection partnerConnection)
		 * { this.partnerConnection = partnerConnection; }
		 * 
		 * public static void main(String args[]) { PartnerWSDL partnerWSDL =
		 * new PartnerWSDL();
		 * 
		 * try { partnerWSDL.getSiebelScript("a0R90000007BBFa", "Account"); }
		 * catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * partnerWSDL.login(); List<String> str= new LinkedList<String>();
		 * str.add("7897897"); str.add("99999999"); str.add("9999777779");
		 * partnerWSDL.getSiebleIdForTableId(str); String str=
		 * "https://c.ap1.visual.force.com/apex/projectdetailpage2?pageID=2#$projectId=a0R90000007BBFaEAO#$ObjectID=j_id0:j_id10:j_id11:j_id12:block:j_id26:0:objects_field_edit"
		 * ; str=str.replaceAll("#\\$", "%23%24"); System.out.println(str);
		 * 
		 * }
		 */

		/*
		 * Properties configProp = new Properties();
		 * 
		 * InputStream in = this.getClass().getResourceAsStream(
		 * "/resources/config.properties");
		 * 
		 * try {
		 * 
		 * configProp.load(in);
		 * 
		 * } catch (IOException e) {
		 * 
		 * // TODO Auto-generated catch block
		 * 
		 * e.printStackTrace();
		 * 
		 * }
		 */
		/*
		 * String username = configProp.getProperty("salesforce_username");
		 * 
		 * String password = configProp.getProperty("salesforce_password");
		 * 
		 * String authEndPoint = configProp
		 * .getProperty("salesforce_authEndPoint");
		 */
	}

}
