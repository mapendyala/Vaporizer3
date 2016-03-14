
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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.force.api.DescribeGlobal;
import com.force.api.DescribeSObject;
import com.force.example.fulfillment.order.controller.SiebelObjectController;
import com.force.example.fulfillment.order.model.LovTransformation;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.Mapping;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.example.fulfillment.order.model.MappingSFDC;
import com.force.example.fulfillment.order.model.MultiValMappingModel;
import com.force.example.fulfillment.order.model.Transformation;
import com.force.utility.SfdcObjectBO;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class PartnerWSDL {

	PartnerConnection partnerConnection = null;
String username;
String password;
	public PartnerConnection getPartnerConnection() {
		return partnerConnection;
	}

	public void setPartnerConnection(PartnerConnection partnerConnection) {
		this.partnerConnection = partnerConnection;
	}

	
	public PartnerWSDL(HttpSession session,boolean fromMiddleWare){
		if(fromMiddleWare){
			JSONObject middleWareConn=(JSONObject)session.getAttribute("middleWareConn");
			username=middleWareConn.getString("username");
			password=middleWareConn.getString("password")+middleWareConn.getString("databaseUrl");
		}else {
			JSONObject middleWareConn=(JSONObject)session.getAttribute("targetOrgConn");
			username=middleWareConn.getString("username");
			password=middleWareConn.getString("password")+middleWareConn.getString("token");
		}
		
		
	}
	String authEndPoint = "https://login.salesforce.com/services/Soap/u/33.0/";

	public boolean login() {
		boolean success = false;
		try {
			ConnectorConfig config = new ConnectorConfig();
			System.out.println("username passoerd"+username+"   "+password);
			config.setUsername(username);
			config.setPassword(password);
			config.setAuthEndpoint(authEndPoint);
			config.setTraceFile("traceLogs.txt");
			config.setTraceMessage(true);
			config.setPrettyPrintXml(true);
			partnerConnection = new PartnerConnection(config);
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
System.out.println("hereeeeeeeee");
			// SOQL query to use
			String soqlQuery = " Select Name, Parent_Project__c, Type__c from Project__c where id= '"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
System.out.println("qr "+qr);
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
System.out.println("records "+records);
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

	// Amrita:Getting field value for dropdown
//Select Source_Field__c, Field_Target__c, Source_Base_Table__c from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"+ tableData.getString("id")
	
	public ArrayList<String> getFieldTarget(JSONObject tableData){
		
		String sfdcTablename = tableData.getString("sfdcTableName");
		String SFDTargetName = "No data";
		ArrayList<String> field=new ArrayList<String>();
		ArrayList<String> fieldId = new ArrayList<String>();
		String s = sfdcTablename + "_PreDefined_Mapping";

		
		
		try {
			partnerConnection.setQueryOptions(250);
			
			// SOQL query to use
			// String sourceBaseTableId="a0QG000000BGG0VMAX";
			String soqlQuery = " Select  Object_API_Name__c,Id, Project__r.Name, Table_Name__c, Type__c from Table__c where  Project__r.Name='"
					+ s + "'";
			//String soqlQuery = "Select Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__r.Table_Name__c ='"+sfdcTablename+"'";
		
			
				QueryResult qr = partnerConnection.query(soqlQuery);
				boolean done = false;
				// Loop through the batches of returned results
				while (!done) {
					SObject[] records1 = qr.getRecords();
					for (int i = 0; i < records1.length; i++) {

						if (((String) records1[i].getField("Type__c"))
								.equals("Siebel Child")) {
							fieldId.add((String) records1[i].getField("Id"));
						}

					}
					if (qr.isDone()) {
						done = true;
					} else {
						qr = partnerConnection.queryMore(qr.getQueryLocator());
					}
				}
				//String soqlQuery1 = "Select Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__r.Table_Name__c ='"+sfdcTablename+"'";
				String soqlQuery1 = "Select  Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"+ tableData.getString("id")+"'";
				QueryResult qr1 = partnerConnection.query(soqlQuery1);
				boolean done1 = false;
				//int loopCount = 0;
			while (!done1) {

					SObject[] records1 = qr1.getRecords();
					// Process the query results
					for (int i = 0; i < records1.length; i++) {
						SFDTargetName = (String) records1[i]
								.getField("Field_Target__c");
						field.add(SFDTargetName);
					}
					if (qr1.isDone()) {
						done1 = true;
					} else {
						qr1 = partnerConnection.queryMore(qr.getQueryLocator());
					}

				}
			for (int j = 0; j < fieldId.size(); j++) {
				String id = fieldId.get(j);
				String soqlQuery2 ="Select  Field_Target__c  from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"
								+ id + "'";
				// Make the query call and get the query results
				QueryResult qr2 = partnerConnection.query(soqlQuery2);
				boolean done2 = false;

				// int loopCount = 0;
				// Loop through the batches of returned results
				while (!done2) {

					SObject[] records2 = qr2.getRecords();
					// Process the query results
					for (int i = 0; i < records2.length; i++) {
						SFDTargetName = (String) records2[i]
							.getField("Field_Target__c");
					field.add(SFDTargetName);}
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

			return field;
		
		}
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
				System.out.println("Mapping" + soqlQuery2);
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

	public void saveMappingSingleValuedDataIntoDB(List<MappingModel> data)
			throws ConnectionException {
		List<SObject> lstContactUpdate= new ArrayList<SObject>();
		List<SObject> lstContactInsert= new ArrayList<SObject>();
		SObject[] contactUpdate = new SObject[data.size()];
		SObject[] contactInsert = new SObject[data.size()];
		for (Iterator<MappingModel> iterator = data.iterator(); iterator
				.hasNext();) {
			MappingModel mappingModel = (MappingModel) iterator.next();
			
			if( mappingModel.getId()==null || mappingModel.getId().equalsIgnoreCase("")){
				SObject contact = new SObject();
				contact.setType("Single_Valued_Screen__c");
				contact.setField("Foreign_Key_Table__c",
						mappingModel.getFrgnKeyrow());
				contact.setField("Mapping_Type__c","UserDefined");
				contact.setField("Join_Name__c",
						mappingModel.getJoinNamerow());
				contact.setField("Mapping_Staging_Table__c",
						mappingModel.getSfdcRowId());
				contact.setField("SFDC_Field_Name__c",
						mappingModel.getSlfrcdropdown());
			/*	contact.setField("Siebel_Field_Description__c",
						mappingModel.getSbldscription());*/
				contact.setField("Siebel_Field_Name__c",
						mappingModel.getSblFieldNmdropdown());
				contact.setField("Column_Name__c",
						mappingModel.getClmnNmrow());
				contact.setField("Lov_Mapping__c", "View");
				contact.setField("Select__c", 
						mappingModel.getCheckFlag());
				contact.setField("LookUpField__c", 
						mappingModel.getLookUpFlag());
				contact.setField("Siebel_Table_Name__c",
						mappingModel.getSiebelEntity());
				contact.setField("Join_Condition__c",
						mappingModel.getJoinCondition());
				contact.setField("LookUpObject__c",
						mappingModel.getLookUpObject());
				contact.setField("Lookup_Relationship_Name__c",
						mappingModel.getLookUpRelationShipName());
				contact.setField("Lookup_External_Id_Field__c",
						mappingModel.getLookUpExternalId());
				
				lstContactInsert.add(contact);
				// Add this sObject to an array
			}else{
				SObject contact = new SObject();
				
				contact.setType("Single_Valued_Screen__c");
				contact.setField("Foreign_Key_Table__c",
						mappingModel.getFrgnKeyrow());
				contact.setField("Mapping_Type__c","UserDefined");
				contact.setField("Mapping_Staging_Table__c",
						mappingModel.getSfdcRowId());
				contact.setField("Join_Name__c",
						mappingModel.getJoinNamerow());
				contact.setField("Id",
						mappingModel.getId());  
				/*contact.setField("SFDC_Field_Description__c",
						mappingModel.getSlsfrcdscription());*/
				contact.setField("SFDC_Field_Name__c",
						mappingModel.getSlfrcdropdown());
				/*contact.setField("Siebel_Field_Description__c",
						mappingModel.getSbldscription());*/
				contact.setField("Siebel_Field_Name__c",
						mappingModel.getSblFieldNmdropdown());
				contact.setField("Column_Name__c",
						mappingModel.getClmnNmrow());
				contact.setField("Lov_Mapping__c", "View");
				contact.setField("Select__c", 
						mappingModel.getCheckFlag());
				contact.setField("LookUpField__c", 
						mappingModel.getLookUpFlag());
				contact.setField("Siebel_Table_Name__c",
						mappingModel.getSiebelEntity());
				contact.setField("Join_Condition__c",
						mappingModel.getJoinCondition());
				contact.setField("LookUpObject__c",
						mappingModel.getLookUpObject());
				contact.setField("Lookup_Relationship_Name__c",
						mappingModel.getLookUpRelationShipName());
				contact.setField("Lookup_External_Id_Field__c",
						mappingModel.getLookUpExternalId());
				//subrat changes 
				contact.setField("Transformation_Expression__c", mappingModel.getTransformText());
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
		
		String projectId=projId;
		if(request!=null){
			HttpSession session = request.getSession(true);
			projectId=(String) session.getAttribute("projectId");
		}
		

		login();
		List<SObject> lstCreate= new ArrayList<SObject>();
		List<SObject> lstUpdate= new ArrayList<SObject>();
		
		//int createCounter = 0;
		//int updateCounter = 0;
		for (Iterator<MainPage> iterator = data.iterator(); iterator.hasNext();) {
			MainPage mainPage = (MainPage) iterator.next();
			if (mainPage.getSfdcId().equals("")) {
				SObject contact = new SObject();
				contact.setType("Mapping_Staging_Table__c");
				contact.setField("Migrate__c",
						mainPage.getMigrate());

				contact.setField("Sequence__c", mainPage.getSequence());
				contact.setField("Prim_Base_Table__c",
						mainPage.getPrimBaseTable());
				contact.setField("Project__c",
						projectId);
				contact.setField("SFDC_Object__c", mainPage.getSfdcObject());
				contact.setField("Siebel_Object__c", mainPage.getSiebelObject());
				contact.setField("Threshold__c", mainPage.getThreshold());
				lstCreate.add(contact);
				
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
							mainPage.getMigrate());
					updateContact.setField("Prim_Base_Table__c",
							mainPage.getPrimBaseTable());
					updateContact.setField("Project__c",
							projectId);
					updateContact.setField("SFDC_Object__c",
							mainPage.getSfdcObject());
					updateContact.setField("Siebel_Object__c",
							mainPage.getSiebelObject());
					updateContact.setField("Threshold__c",
							mainPage.getThreshold());
					updateContact.setField("Sequence__c",
							mainPage.getSequence());
					lstUpdate.add(updateContact);
				}
			}
		}
		if(lstCreate != null && lstCreate.size()>0){
			SObject[] createContacts = lstCreate.toArray(new SObject[lstCreate.size()]);
			SaveResult[] saveCreateResults = getPartnerConnection().create(
					createContacts);
			for (int j = 0; j < saveCreateResults.length; j++) {
				System.out.println(saveCreateResults[j].isSuccess());
				System.out.println(saveCreateResults[j].getErrors());
			}
		}
		if(lstUpdate != null && lstUpdate.size()>0){
			SObject[] updateContacts = lstUpdate.toArray(new SObject[lstUpdate.size()]);
			SaveResult[] saveUpdateResults = partnerConnection
					.update(updateContacts);
			for (int j = 0; j < saveUpdateResults.length; j++) {
				System.out.println(saveUpdateResults[j].isSuccess());
				System.out.println(saveUpdateResults[j].getErrors());
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
					Boolean migrate = (Boolean) contact.getField("Migrate__c");
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
	
	
	
	public Map<String,String> getIdForSeq(String projectId) {
		Map<String,String> mapOfIdSeq= new HashMap<String, String>();
		try {
			System.out.println("projectId:: "+projectId);
			// SOQL query to use
			String soqlQuery = "Select Id, Sequence__c from Mapping_Staging_Table__c where Project__c ='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			while (!done) {
				System.out.println("done");
				SObject[] records = qr.getRecords();
				// Process the query results
				System.out.println("length" + records.length);
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];
					String id = (String) contact.getField("Id");
					String seq = (String) contact.getField("Sequence__c");
					mapOfIdSeq.put(seq,id);
				}
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
		return mapOfIdSeq;
	}

	// Amrita
	public String getMappingId(String projectId,
			List<MappingModel> mappingData, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String sfdcTablename = tableData.getString("sfdcTableName");
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
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
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

	public String checkFieldsSelctedFrEntity(String rowId){
		String recrdsExist = null;		
		try {
			partnerConnection.setQueryOptions(250);
			String soqlQuery1 = "Select Id,  Foreign_Key_Table__c, SFDC_Field_Description__c, SFDC_Field_Name__c, Siebel_Field_Description__c, Siebel_Field_Name__c,"
				+ "Column_Name__c,Lov_Mapping__c,Select__c,Join_Condition__C,Join_Name__c,LookUpField__c,LookUpObject__c,Lookup_External_Id_Field__c,Lookup_Relationship_Name__c from Single_Valued_Screen__c where  Mapping_Staging_Table__c='"+ rowId + "' and Mapping_Type__c = 'UserDefined'";
				// Make the query call and get the query results
				QueryResult qr1 = partnerConnection.query(soqlQuery1);
					SObject[] records1 = qr1.getRecords();
					if(records1 != null && records1.length > 0){
						recrdsExist = "present";
						return recrdsExist;
					}else{
						return null;
					}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return recrdsExist;		
	}
	
	public List<MappingModel> getSavedMappingSingleValueDBData(String rowId,
			List<MappingModel> mappingData,String sfdcObjectName) {
		
		List<MappingModel> refinedList = new ArrayList<MappingModel>();
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery1 = "Select Id,  Foreign_Key_Table__c, SFDC_Field_Description__c, SFDC_Field_Name__c, Siebel_Field_Description__c, Siebel_Field_Name__c,"
					+ "Column_Name__c,Lov_Mapping__c,Select__c,Join_Condition__C,Join_Name__c,LookUpField__c,LookUpObject__c,Lookup_External_Id_Field__c,Transformation_Expression__c,Lookup_Relationship_Name__c from Single_Valued_Screen__c where  Mapping_Staging_Table__c='"+ rowId + "' and Mapping_Type__c = 'UserDefined'";
			// Make the query call and get the query results
			QueryResult qr1 = partnerConnection.query(soqlQuery1);
			boolean done1 = false;
			
			if(mappingData != null){
				mappingData.clear();
			}
				SiebelObjectController.joinNmRowNumMap = new HashMap<String, Integer>();
				SiebelObjectController.colNmRowNmMap = new HashMap<Integer, String>();
				SiebelObjectController.joinCndtnRowNmMap = new HashMap<Integer, String>();
				SiebelObjectController.relationShpNmRowNmMap = new HashMap<Integer, String>();
				SiebelObjectController.salesFrcNmRowNmMap = new HashMap<Integer, String>();
				SiebelObjectController.externalIdRowNmMap = new HashMap<Integer, String>();
				SiebelObjectController.rowNumJoinNameMap = new HashMap<Integer, String>();
				SiebelObjectController.fieldNamesMap = new HashMap<String,Integer>();
			 
			while (!done1) {
				SObject[] records1 = qr1.getRecords();
				if(mappingData == null){
					mappingData = new ArrayList<MappingModel>();
				}
				List<MappingSFDC> externlFldLst = getExternalIdList(sfdcObjectName);
				// Process the query results
				for (int i = 0; i < records1.length; i++) {
					int j = i+1;
					MappingModel mappingModel1 = new MappingModel();
					SObject contact = records1[i];
					
					String joinName = (String)contact.getField("Join_Name__c");
					String colName = (String)contact.getField("Column_Name__c");
					String joinCond = (String)contact.getField("Join_Condition__c");
					String reltnShpNme = (String)contact.getField("Lookup_Relationship_Name__c");
					String extrnlId = (String)contact.getField("Lookup_External_Id_Field__c");
					String slsfrcDrpDwnSlctd = (String)contact.getField("SFDC_Field_Name__c");
					String sblFieldNmdropdown = (String)contact.getField("Siebel_Field_Name__c"); 
					//subrat changes
					String transformationString=(String)contact.getField("Transformation_Expression__c");
					
					Set<String> lstExternalIds= new LinkedHashSet<String>();
					lstExternalIds.add(extrnlId);
					if(externlFldLst!=null && externlFldLst.size()>0)
						for(MappingSFDC externalFld: externlFldLst ){
							lstExternalIds.add(externalFld.getLabel());
						}
					mappingModel1.setLstExternalIds(lstExternalIds);
					mappingModel1.setCheckFlag(Boolean.parseBoolean((String)contact.getField("Select__c")));
					mappingModel1.setLookUpFlag(Boolean.parseBoolean((String)contact.getField("LookUpField__c")));
					mappingModel1.setSblFieldNmdropdown((String)contact.getField("Siebel_Field_Name__c"));
					/*mappingModel1.setSbldscription((String)contact.getField("Siebel_Field_Description__c"));*/
					mappingModel1.setJoinNamerow(joinName);
					mappingModel1.setFrgnKeyrow((String)contact.getField("Foreign_Key_Table__c"));
					mappingModel1.setJoinCondition(joinCond);
					mappingModel1.setClmnNmrow(colName);
					mappingModel1.setSlfrcdropdown(slsfrcDrpDwnSlctd);
					mappingModel1.setLookUpObject((String)contact.getField("LookUpObject__c"));
					mappingModel1.setLookUpRelationShipName(reltnShpNme);
					mappingModel1.setLookUpExternalId(extrnlId);
					/*mappingModel1.setSlsfrcdscription((String)contact.getField("SFDC_Field_Description__c"));*/
					mappingModel1.setId( (String)contact.getField("Id"));
					mappingModel1.setMappingSeq(i);
					if(!(SiebelObjectController.joinNmRowNumMap.containsKey(joinName))){
						SiebelObjectController.joinNmRowNumMap.put(joinName, j);
					}
					SiebelObjectController.colNmRowNmMap.put(j, colName);
					SiebelObjectController.joinCndtnRowNmMap.put(j, joinCond);
					SiebelObjectController.fieldNamesMap.put(sblFieldNmdropdown,j);
					if(reltnShpNme != null && !reltnShpNme.trim().equals("")){
						SiebelObjectController.relationShpNmRowNmMap.put(j, reltnShpNme);
					}
					if(slsfrcDrpDwnSlctd != null && !slsfrcDrpDwnSlctd.trim().equals("")){
						SiebelObjectController.salesFrcNmRowNmMap.put(j, slsfrcDrpDwnSlctd);
					}
					if(extrnlId != null && !extrnlId.trim().equals("")){
						SiebelObjectController.externalIdRowNmMap.put(j, extrnlId);
					}
					
					SiebelObjectController.rowNumJoinNameMap.put(j, joinName);
					mappingModel1.setTransformText(transformationString);
					mappingData.add(mappingModel1);
				}
				
				/*** Start -logic for removing duplicates**/
				
					if(mappingData!=null && mappingData.size() >0) {
						
					Set<MappingModel> set = new TreeSet<MappingModel>(new Comparator<MappingModel>() {
					
					@Override
					public int compare(MappingModel o1, MappingModel o2) {
						// TODO Auto-generated method stub
						if(o1.getSblFieldNmdropdown()!=null && o2.getSblFieldNmdropdown()!=null){
							if(o1.getSblFieldNmdropdown().equalsIgnoreCase(o2.getSblFieldNmdropdown())){
				        		return 0;
				        	}
						}
						   	return 1;
						}						
					});
					set.addAll(mappingData);
	
					refinedList = new ArrayList<MappingModel>(set);
					
					}				
					
			   /*** End -logic for removing duplicates**/

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
		return refinedList;
	}

	/**
	 * @author piymishra
	 * @param query
	 * @param ProjectId
	 */

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


	public static void main(String args[]){
		
		/*PartnerWSDL pw= new PartnerWSDL();
		pw.login();
		String projectId="a0PG000000B5e3fMAB";
		pw.getProjectName(projectId);*/
	}
	
	public  List<MappingSFDC> getExternalIdList(String sfdcObj){
		List<MappingSFDC> extrnlIdList= null;
		try {
			DescribeSObjectResult describeSObjectResults = partnerConnection.describeSObject(sfdcObj);
		        // Get the name of the sObject
		    	Field[] field=   describeSObjectResults.getFields();
		      //  String objectName = desObj.getName();
		        for(int j=0;j<field.length;j++){
		        	MappingSFDC  mpngSFDC = new MappingSFDC();
		        	String fieldName = field[j].getName();
		            String fieldLabel = field[j].getLabel();
		        	if(field[j].isExternalId()){
		        		if(extrnlIdList == null){
			            	extrnlIdList= new ArrayList<MappingSFDC>();
			            }
		        		//mpngSFDC.setLabel(fieldLabel);
		        		mpngSFDC.setLabel(fieldName);
		        		mpngSFDC.setName(fieldName);
		        		extrnlIdList.add(mpngSFDC);
		        		System.out.println(fieldLabel+">>>>>>>>>"+fieldName);
		        	}
		        		
		        }
		  } catch(ConnectionException ce) {
		    ce.printStackTrace();  
		  }
		  return extrnlIdList;
		}
	
	
	public  List<MappingSFDC> getSFDCFieldList(String sfdcObj){
		List<MappingSFDC> mpngSFDCLookUpList= null;
		List<MappingSFDC> mpngSFDCList= null;
		try {
			DescribeSObjectResult[] describeSObjectResults = partnerConnection.describeSObjects(new String[] { sfdcObj });
		    // Iterate through the list of describe sObject results
		    for (int i=0;i < describeSObjectResults.length; i++){
		        DescribeSObjectResult desObj = describeSObjectResults[i];
		        // Get the name of the sObject
		    	Field[] field=   desObj.getFields();
		      //  String objectName = desObj.getName();
		        for(int j=0;j<field.length;j++){
		        	MappingSFDC  mpngSFDCLookUp = new MappingSFDC();
		        	MappingSFDC  mpngSFDC = new MappingSFDC();
		        	String fieldName = field[j].getName();
		            String fieldLabel = field[j].getLabel();
		            if(mpngSFDCLookUpList == null){
		            	mpngSFDCLookUpList= new ArrayList<MappingSFDC>();
		            }
		            if(mpngSFDCList == null){
		            	mpngSFDCList= new ArrayList<MappingSFDC>();
		            }
		            mpngSFDC.setLabel(fieldLabel);
		            mpngSFDC.setName(fieldName);
	        		mpngSFDCList.add(mpngSFDC);
	        		
		        	if(field[j].getRelationshipName()!=null && field[j].getReferenceTo() != null && field[j].getReferenceTo().length > 0){
		        		mpngSFDCLookUp.setLabel(fieldLabel);
		        		mpngSFDCLookUp.setName(fieldName);
		        		mpngSFDCLookUp.setRelationshipName(field[j].getRelationshipName());
		        		mpngSFDCLookUp.setReferenceTo(field[j].getReferenceTo());
		        		mpngSFDCLookUpList.add(mpngSFDCLookUp);
		        	}
		        }
		     }
		  } catch(ConnectionException ce) {
		    ce.printStackTrace();  
		  }
		  SiebelObjectController.sfdcFldRowNmList = mpngSFDCLookUpList;
		  Collections.sort(mpngSFDCList, new Comparator<MappingSFDC>() {
			  @Override
			  public int compare(MappingSFDC o1, MappingSFDC o2) {
				  return o1.getLabel().compareTo(o2.getLabel());
			  }
		  });
		  return mpngSFDCList;
		}
	

public List<MultiValMappingModel> getSavedMappingMultiValueDBData(String rowId , String entityName) {
        
        List<MultiValMappingModel> mappingData=null;
        try {
               partnerConnection.setQueryOptions(250);
               // SOQL query to use
               // String subprojectId="a0PG000000AtiEAMAZ";
               String soqlQuery1 = "Select Id, Siebel_Field_Name__c, Child_Entity__c, Child_Field__c, Child_Lookup_External_Id__c, Child_Relationship_Name__c, Child_Table__c, Inter_Child_Column__c, Inter_Parent_Column__c, Inter_Table__c,"+
                                                 "Join_Condition__c, Junction_Object__c, Junction_Object_Child_Field__c, Junction_Object_Parent_Field__c, Lookup_External_Id__c, Lookup_Field__c, Lookup_Relationship_Name__c, "+
                            "Mapping_Staging_Table__c, Parent_Lookup_External_Id__c, Parent_Relationship_Name__c, Relationship_Type__c, SFDC_Child_Object__c  from Multi_Valued_Screen__c where Mapping_Staging_Table__c ='"+ rowId + "'";
               //, Siebel_Field_Name__c
               // Make the query call and get the query results
               QueryResult qr1 = partnerConnection.query(soqlQuery1);
               boolean done1 = false;

               mappingData = new ArrayList<MultiValMappingModel>();
               while(!done1){
                     SObject[] records1 = qr1.getRecords();
               for(int i=0;i<records1.length;i++){
                     int j = i+1;
                     SObject contact = records1[i];
                     MultiValMappingModel mappingModel1 = new MultiValMappingModel();
                     mappingModel1.setMappingSeq(i);
                     mappingModel1.setCheckFlag(true);
                    
                     mappingModel1.setId( (String)contact.getField("Id"));
                     mappingModel1.setMappingSeq(i);
                     mappingModel1.setSiebelField((String)contact.getField("Siebel_Field_Name__c"));
                     mappingModel1.setRelationType((String)contact.getField("Relationship_Type__c"));
                     mappingModel1.setChildEntity((String)contact.getField("Child_Entity__c"));
                     mappingModel1.setChildTable((String)contact.getField("Child_Table__c"));
                     mappingModel1.setChildField((String)contact.getField("Child_Field__c"));
                     mappingModel1.setInterTable((String)contact.getField("Inter_Table__c"));
                     mappingModel1.setInterParentColumn((String)contact.getField("Inter_Parent_Column__c"));
                     mappingModel1.setInterChildColumn((String)contact.getField("Inter_Child_Column__c"));
                     mappingModel1.setJoinCondition((String)contact.getField("Join_Condition__c"));
                     mappingModel1.setSfdcChildObject((String)contact.getField("SFDC_Child_Object__c"));
                     mappingModel1.setLookupField((String)contact.getField("Lookup_Field__c"));
                     if( mappingModel1.getSfdcChildObject()!=null){
                    	 mappingModel1.setLookupObjList(getLookupObjFieldList(mappingModel1.getSfdcChildObject()));
                     }
                     mappingModel1.setLookupRelationName((String)contact.getField("Lookup_Relationship_Name__c"));
                     mappingModel1.setLookupExternalId((String)contact.getField("Lookup_External_Id__c"));
                     mappingModel1.setJunctionObject((String)contact.getField("Junction_Object__c"));
             		if(mappingModel1.getJunctionObject()!=null){
             			mappingModel1.setJnObjParentList(getJnObjParentFieldList(mappingModel1.getJunctionObject()));
             			mappingModel1.setJnObjChildList(getJnObjChildFieldList(mappingModel1.getJunctionObject()));
             		}
                     mappingModel1.setJunctionObjParentField((String)contact.getField("Junction_Object_Parent_Field__c"));
                     mappingModel1.setParentRelationName((String)contact.getField("Parent_Relationship_Name__c"));
                     mappingModel1.setParentExternalId((String)contact.getField("Parent_Lookup_External_Id__c"));
                     mappingModel1.setJunctionObjectChildField((String)contact.getField("Junction_Object_Child_Field__c"));
                     mappingModel1.setChildRelationName((String)contact.getField("Child_Relationship_Name__c"));
                     mappingModel1.setChildExternalId((String)contact.getField("Child_Lookup_External_Id__c"));
                     
                      mappingData.add(mappingModel1);
                     
                      
                }
               
                if (qr1.isDone()) {
                            done1 = true;
                     } else {
                            qr1 = partnerConnection.queryMore(qr1.getQueryLocator());
                     }
               
                }
               
 /*            while (!done1) {
                     SObject[] records1 = qr1.getRecords();
                     // Process the query results
                     for (int i = 0; i < records1.length; i++) {
                            MultiValMappingModel mappingModel1 = new MultiValMappingModel();
                            SObject contact = records1[i];

                            mappingModel1.setCheckFlag(Boolean.parseBoolean((String)contact.getField("Select__c")));
                            mappingModel1.setLookUpFlag(Boolean.parseBoolean((String)contact.getField("LookUpField__c")));
                            mappingModel1.setSblFieldNmdropdown((String)contact.getField("Siebel_Field_Name__c"));
                            mappingModel1.setSbldscription((String)contact.getField("Siebel_Field_Description__c"));
                            mappingModel1.setJoinNamerow((String)contact.getField("Join_Name__c"));
                            mappingModel1.setFrgnKeyrow((String)contact.getField("Foreign_Key_Table__c"));
                            mappingModel1.setJoinCondition((String)contact.getField("Join_Condition__c"));
                            mappingModel1.setClmnNmrow((String)contact.getField("Column_Name__c"));
                            mappingModel1.setSlfrcdropdown((String)contact.getField("SFDC_Field_Name__c"));
                            mappingModel1.setLookUpObject((String)contact.getField("LookUpObject__c"));
                            mappingModel1.setSlsfrcdscription((String)contact.getField("SFDC_Field_Description__c"));
                            mappingModel1.setId( (String)contact.getField("Id"));
                            mappingModel1.setMappingSeq(i);
                            
                            mappingData.add(mappingModel1);
                     }

                     if (qr1.isDone()) {
                            done1 = true;
                     } else {
                            qr1 = partnerConnection.queryMore(qr1.getQueryLocator());
                     }

               }*/
        } catch (ConnectionException ce) {
               ce.printStackTrace();
        }
        System.out.println("\nQuery execution completed.");
        return mappingData;
 }

	public  List<MappingSFDC> getLookupObjFieldList(String sfdcObj){
		List<MappingSFDC> mpngSFDCLookUpList= null;
		List<MappingSFDC> mpngSFDCList= null;
		try {
			DescribeSObjectResult[] describeSObjectResults = partnerConnection.describeSObjects(new String[] { sfdcObj });
		    // Iterate through the list of describe sObject results
		    for (int i=0;i < describeSObjectResults.length; i++){
		        DescribeSObjectResult desObj = describeSObjectResults[i];
		        // Get the name of the sObject
		    	Field[] field=   desObj.getFields();
		      //  String objectName = desObj.getName();
		        for(int j=0;j<field.length;j++){
		        	MappingSFDC  mpngSFDCLookUp = new MappingSFDC();
		        	MappingSFDC  mpngSFDC = new MappingSFDC();
		        	String fieldName = field[j].getName();
		            String fieldLabel = field[j].getLabel();
		            if(mpngSFDCLookUpList == null){
		            	mpngSFDCLookUpList= new ArrayList<MappingSFDC>();
		            }
		            if(mpngSFDCList == null){
		            	mpngSFDCList= new ArrayList<MappingSFDC>();
		            }
		            mpngSFDC.setLabel(fieldLabel);
		            mpngSFDC.setName(fieldName);
	        		mpngSFDCList.add(mpngSFDC);
	        		
		        	if(field[j].getRelationshipName()!=null && field[j].getReferenceTo() != null && field[j].getReferenceTo().length > 0){
		        		mpngSFDCLookUp.setLabel(fieldLabel);
		        		mpngSFDCLookUp.setName(fieldName);
		        		mpngSFDCLookUp.setRelationshipName(field[j].getRelationshipName());
		        		mpngSFDCLookUp.setReferenceTo(field[j].getReferenceTo());
		        		mpngSFDCLookUpList.add(mpngSFDCLookUp);
		        	}
		        }
		     }
		  } catch(ConnectionException ce) {
		    ce.printStackTrace();  
		  }
			SiebelObjectController.lookUpRelationMap.put(sfdcObj, mpngSFDCLookUpList);
		  return mpngSFDCLookUpList;
		}
	
	public  List<MappingSFDC> getJnObjParentFieldList(String sfdcObj){
		List<MappingSFDC> mpngSFDCLookUpList= null;
		List<MappingSFDC> mpngSFDCList= null;
		try {
			DescribeSObjectResult[] describeSObjectResults = partnerConnection.describeSObjects(new String[] { sfdcObj });
		    // Iterate through the list of describe sObject results
		    for (int i=0;i < describeSObjectResults.length; i++){
		        DescribeSObjectResult desObj = describeSObjectResults[i];
		        // Get the name of the sObject
		    	Field[] field=   desObj.getFields();
		      //  String objectName = desObj.getName();
		        for(int j=0;j<field.length;j++){
		        	MappingSFDC  mpngSFDCLookUp = new MappingSFDC();
		        	MappingSFDC  mpngSFDC = new MappingSFDC();
		        	String fieldName = field[j].getName();
		            String fieldLabel = field[j].getLabel();
		            if(mpngSFDCLookUpList == null){
		            	mpngSFDCLookUpList= new ArrayList<MappingSFDC>();
		            }
		            if(mpngSFDCList == null){
		            	mpngSFDCList= new ArrayList<MappingSFDC>();
		            }
		            mpngSFDC.setLabel(fieldLabel);
		            mpngSFDC.setName(fieldName);
	        		mpngSFDCList.add(mpngSFDC);
	        		
		        	if(field[j].getRelationshipName()!=null && field[j].getReferenceTo() != null && field[j].getReferenceTo().length > 0){
		        		mpngSFDCLookUp.setLabel(fieldLabel);
		        		mpngSFDCLookUp.setName(fieldName);
		        		mpngSFDCLookUp.setRelationshipName(field[j].getRelationshipName());
		        		mpngSFDCLookUp.setReferenceTo(field[j].getReferenceTo());
		        		mpngSFDCLookUpList.add(mpngSFDCLookUp);
		        	}
		        }
		     }
		  } catch(ConnectionException ce) {
		    ce.printStackTrace();  
		  }
			SiebelObjectController.juncRelationMap.put(sfdcObj, mpngSFDCLookUpList);
		  return mpngSFDCLookUpList;
		}
	
	public  List<MappingSFDC> getJnObjChildFieldList(String sfdcObj){
		List<MappingSFDC> mpngSFDCLookUpList= null;
		List<MappingSFDC> mpngSFDCList= null;
		try {
			DescribeSObjectResult[] describeSObjectResults = partnerConnection.describeSObjects(new String[] { sfdcObj });
		    // Iterate through the list of describe sObject results
		    for (int i=0;i < describeSObjectResults.length; i++){
		        DescribeSObjectResult desObj = describeSObjectResults[i];
		        // Get the name of the sObject
		    	Field[] field=   desObj.getFields();
		      //  String objectName = desObj.getName();
		        for(int j=0;j<field.length;j++){
		        	MappingSFDC  mpngSFDCLookUp = new MappingSFDC();
		        	MappingSFDC  mpngSFDC = new MappingSFDC();
		        	String fieldName = field[j].getName();
		            String fieldLabel = field[j].getLabel();
		            if(mpngSFDCLookUpList == null){
		            	mpngSFDCLookUpList= new ArrayList<MappingSFDC>();
		            }
		            if(mpngSFDCList == null){
		            	mpngSFDCList= new ArrayList<MappingSFDC>();
		            }
		            mpngSFDC.setLabel(fieldLabel);
		            mpngSFDC.setName(fieldName);
	        		mpngSFDCList.add(mpngSFDC);
	        		
		        	if(field[j].getRelationshipName()!=null && field[j].getReferenceTo() != null && field[j].getReferenceTo().length > 0){
		        		mpngSFDCLookUp.setLabel(fieldLabel);
		        		mpngSFDCLookUp.setName(fieldName);
		        		mpngSFDCLookUp.setRelationshipName(field[j].getRelationshipName());
		        		mpngSFDCLookUp.setReferenceTo(field[j].getReferenceTo());
		        		mpngSFDCLookUpList.add(mpngSFDCLookUp);
		        	}
		        }
		      
		     }
		  
		  } catch(ConnectionException ce) {
		    ce.printStackTrace();  
		  }
		  SiebelObjectController.sfdcFldRowNmList = mpngSFDCLookUpList;
		  return mpngSFDCLookUpList;
		}
	
	public List<DescribeSObject> getJunctionObjFromSObject(
			List<DescribeSObject> sobjectResults) {
		List<DescribeSObject> JuncObjList = null;
		try {
			JuncObjList = new ArrayList<DescribeSObject>();
			for (DescribeSObject describeSObject : sobjectResults) {
				int masterReadCount = 0;
				DescribeSObjectResult[] describeSObjectResults = partnerConnection
						.describeSObjects(new String[] { describeSObject
								.getName() });

				for (int i = 0; i < describeSObjectResults.length; i++) {
					DescribeSObjectResult desObj = describeSObjectResults[i];
					Field[] field = desObj.getFields();
					if (field != null) {
						for (int j = 0; j < field.length; j++) {
							if (field[j].isWriteRequiresMasterRead()) {
								masterReadCount++;
							}
						}
					}
					if (masterReadCount == 2) {
						JuncObjList.add(describeSObject);
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JuncObjList;

	}
	
	public void saveMappingMultiValuedDataIntoDB(List<MultiValMappingModel> data,List<MainPage> mainPageData , String projId)
			throws ConnectionException {
		List<SObject> lstContactUpdate= new ArrayList<SObject>();
		List<SObject> lstContactInsert= new ArrayList<SObject>();
		List<MainPage> mainPageList=new ArrayList<MainPage>();
		SObject[] contactUpdate = new SObject[data.size()];
		SObject[] contactInsert = new SObject[data.size()];
		TargetPartner target=new TargetPartner();
		for (Iterator<MultiValMappingModel> iterator = data.iterator(); iterator
				.hasNext();) {
			MultiValMappingModel mappingModel = (MultiValMappingModel) iterator.next();
			
			SObject contact = new SObject();
			contact.setType("Multi_Valued_Screen__c");
			contact.setField("Child_Entity__c",
					mappingModel.getChildEntity());
			contact.setField("Child_Field__c",
					mappingModel.getChildField());
			contact.setField("Mapping_Staging_Table__c",
					mappingModel.getSfdcRowId());
			contact.setField("Child_Lookup_External_Id__c",
					mappingModel.getChildExternalId());
			contact.setField("Child_Relationship_Name__c",
					mappingModel.getChildRelationName());
			contact.setField("Child_Table__c",
					mappingModel.getChildTable());
			contact.setField("Inter_Child_Column__c",mappingModel.getInterChildColumn() );
			contact.setField("Inter_Parent_Column__c", 
					mappingModel.getInterParentColumn());
			contact.setField("Inter_Table__c", 
					mappingModel.getInterTable());
			contact.setField("Junction_Object__c",
					mappingModel.getJunctionObject());
			contact.setField("Junction_Object_Child_Field__c",
					mappingModel.getJunctionObjectChildField());
			contact.setField("Junction_Object_Parent_Field__c",
					mappingModel.getJunctionObjParentField());
			contact.setField("Lookup_External_Id__c",
					mappingModel.getLookupExternalId());
			contact.setField("Lookup_Field__c",
					mappingModel.getLookupField());
			contact.setField("Lookup_Relationship_Name__c",
					mappingModel.getLookupRelationName());
			contact.setField("Parent_Lookup_External_Id__c",
					mappingModel.getParentExternalId());
			contact.setField("Parent_Relationship_Name__c",
					mappingModel.getParentRelationName());
			contact.setField("Relationship_Type__c",
					mappingModel.getRelationType());
			contact.setField("SFDC_Child_Object__c",
					mappingModel.getSfdcChildObject());
			contact.setField("Siebel_Field_Name__c",
					mappingModel.getSiebelField());
			if( mappingModel.getId()==null || mappingModel.getId().trim().equalsIgnoreCase("")){
				lstContactInsert.add(contact);
			}else{
				contact.setField("Id",
						mappingModel.getId());  
				lstContactUpdate.add(contact);
			}
			if(mappingModel.getChildEntity()!=null && mappingModel.getChildEntity().trim().length()>0 && mappingModel.getSfdcChildObject()!=null && mappingModel.getSfdcChildObject().trim().length()>0){
				boolean needInsert=true;
				for (MainPage mainpage : mainPageData) {
					
					if(mainpage.getSiebelObject().equals(mappingModel.getChildEntity()) && mainpage.getSfdcObject().equals(mappingModel.getSfdcChildObject())){
						needInsert=false;
					}
				}
				if(needInsert){
					MainPage mainpage=new MainPage();
					mainpage.setSiebelObject(mappingModel.getChildEntity());
					mainpage.setSfdcObject(mappingModel.getSfdcChildObject());
					mainpage.setPrimBaseTable(mappingModel.getChildTable());
					mainpage.setSequence(mainPageData.size()+1+"");
					mainpage.setSfdcId("");
					mainPageList.add(mainpage);
					mainPageData.add(mainpage);
				}
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
		if(mainPageList.size()>0){
			saveDataDB(mainPageList, null, projId);
		}
		
	}
	public List<DescribeSObjectResult> getJuncNames(String selectedSFDCChildObj) {
		List<DescribeSObject> sobjectResults = null;
		List<DescribeSObjectResult> juncObjResults = new ArrayList<DescribeSObjectResult>();
		try {
			// Make the describeGlobal() call
			
			 DescribeSObjectResult describeSObjectResult = 
					 partnerConnection.describeSObject(selectedSFDCChildObj);
			 for(int l=0;l<describeSObjectResult.getChildRelationships().length;l++){
				 DescribeSObjectResult[] fieldDesc=partnerConnection.describeSObjects(new String[]{describeSObjectResult.getChildRelationships()[l].getChildSObject()});
				 for(int a=0;a<fieldDesc.length;a++){
					 com.sforce.soap.partner.Field[] fd=fieldDesc[a].getFields();
			            int iCheck=0;
			            int iCheck2=0;
			            for(int g=0;g<fd.length;g++){
			            	if((fd[g].getRelationshipName()!=null)&&(fd[g].isCreateable()==true)){
			            		//iCheck++;
			            		if(fd[g].getRelationshipOrder()==0){
			            		System.out.println("Primary Master Details Object " +fd[g].getReferenceTo()[0]);
			            		iCheck++;
			            		}
			            		else if(fd[g].getRelationshipOrder()==1){
			            			System.out.println("Secondary Master Details Object " +fd[g].getReferenceTo()[0]);
			            			iCheck2++;
			            			
			            		}
			            	}
			            	if((iCheck==1)&&(iCheck2==1)){
			            		System.out.println(fieldDesc[a].getName()+" is a junction Object");
			            		juncObjResults.add(fieldDesc[a]);
			            		break;
			            	}
			            	else if(g+1==fd.length)
			            		System.out.println(fieldDesc[a].getName()+" is not a junction Object");
			            }
					 
				 }
			 }
			 
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return juncObjResults;// juncObjResults;
	}
	
	public void saveExtractionQryDB(HttpServletRequest request,String sfdcId, String extrctionQry) throws ConnectionException {
			
			String projectId=null;
			if(request!=null){
				HttpSession session = request.getSession(true);
				projectId=(String) session.getAttribute("projectId");
			}
			login();
			
			List<SObject> lstUpdate= new ArrayList<SObject>();
			
					String sqlQuery = "Select Id from Mapping_Staging_Table__c where Id ='"
							+ sfdcId + "'";
					QueryResult qr = partnerConnection.query(sqlQuery);
					SObject[] records = qr.getRecords();
						SObject updateContact = new SObject();
						updateContact.setType("Mapping_Staging_Table__c");
						updateContact.setId(sfdcId);
						updateContact.setField("Extraction_Query__c",
								extrctionQry);
						lstUpdate.add(updateContact);
			if(lstUpdate != null && lstUpdate.size()>0){
				SObject[] updateContacts = lstUpdate.toArray(new SObject[lstUpdate.size()]);
				SaveResult[] saveUpdateResults = partnerConnection
						.update(updateContacts);
				for (int j = 0; j < saveUpdateResults.length; j++) {
					System.out.println(saveUpdateResults[j].isSuccess());
					System.out.println(saveUpdateResults[j].getErrors());
				}
			}
		}
	//subrat changes for lov transformation 
	public void saveCustomLovDB(HttpServletRequest request,int rowCount) throws ConnectionException {
		String sourceObject = request.getParameter("sblEntity");
		List<SObject> lstContactInsert= new ArrayList<SObject>();
		for(int i=0;i<rowCount;i++){
			
			String sourceField = request.getParameter("clmnNmrow"+i);
			String fieldId = request.getParameter("sfdcId"+i);
			String lovSourceTarget = request.getParameter("lovtransformDropDown"+i);
			String lovSrc[] = lovSourceTarget.split("|");
			SObject contact = new SObject();
			contact.setField("Source_Object__c",sourceObject);
			contact.setField("Source_value__c",lovSrc[0]);
			contact.setField("Target_Value_c", lovSrc[1]);
			contact.setField("Field_Id__c", fieldId);
			contact.setField("Source_Field__c", sourceField);
			lstContactInsert.add(contact);
		}
		SObject[] contactInsert = new SObject[lstContactInsert.size()];
		contactInsert = lstContactInsert.toArray(new SObject[lstContactInsert.size()]);
		getPartnerConnection().create(contactInsert);
	}
	//subrat changes for getting transformation objects .
		public List<Transformation> getTransformationObj(){
			List<Transformation> transformationList = new ArrayList<Transformation>();
				try {
					// SOQL query to use
					String soqlQuery = "SELECT CreatedById,CreatedDate,Database_Name__c,Id,IsDeleted,LastModifiedById,LastModifiedDate,LastReferencedDate,LastViewedDate,Name,Number_of_Arguments__c,OwnerId,SystemModstamp,Transformation_Expression__c,Transformation__c,Help_Note__c FROM Transformations__c";
					// Make the query call and get the query results
					QueryResult qr = partnerConnection.query(soqlQuery);
					//QueryResult qr = partnerConnection.query(soqlQuery);
					boolean done = false;
					
					while (!done) {
							SObject[] records = qr.getRecords();
						// Process the query results
						for (int i = 0; i < records.length; i++) {
							Transformation trans = new Transformation();
							SObject contact = records[i];
							String transform = (String)contact.getField("Transformation__c");
							trans.setTransformation(transform);
							String transformExp = (String)contact.getField("Transformation_Expression__c");
							trans.setExpression(transformExp);
							String noOfArg = (String)contact.getField("Number_of_Arguments__c");
							trans.setArgument(noOfArg);
							String database = (String)contact.getField("Database_Name__c");
							String helpText =(String)contact.getField("Help_Note__c");
							trans.setHelpText(helpText);
							trans.setDatabaseName(database);
							transformationList.add(trans);
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
		
			return transformationList;
			}
		
		public List<LovTransformation> getLovTransformationObj(){
			List<LovTransformation> transformationList = new ArrayList<LovTransformation>();
				try {
					// SOQL query to use
					String soqlQuery ="SELECT LOV_Type__c,Source_Field__c,Source_Object__c,Source_Values__c,Target_Values__c FROM LOV_Master__c WHERE LOV_Type__c = 'ACCOUNT_OWNERSHIP'";
					//String soqlQuery = "SELECT LOV_Type__c,Source_Values__c,Target_Values__c FROM LOV_Master__c";
					// Make the query call and get the query results
					QueryResult qr = partnerConnection.query(soqlQuery);
					//QueryResult qr = partnerConnection.query(soqlQuery);
					boolean done = false;
					
					while (!done) {
							SObject[] records = qr.getRecords();
						// Process the query results
						for (int i = 0; i < records.length; i++) {
							LovTransformation trans = new LovTransformation();
							SObject contact = records[i];
							String lovtype = (String)contact.getField("LOV_Type__c");
							trans.setLovType(lovtype);
							String sourceVal = (String)contact.getField("Source_Values__c");
							trans.setSourceVal(sourceVal);
							String targetVal = (String)contact.getField("Target_Values__c");
							trans.setTargetVal(targetVal);
							transformationList.add(trans);
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
		
			return transformationList;
			}
	}
