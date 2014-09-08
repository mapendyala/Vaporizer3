package com.force.partner;

import java.io.FileNotFoundException;
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

public class PartnerWSDL {

	PartnerConnection partnerConnection = null;
	public PartnerConnection getPartnerConnection() {
		return partnerConnection;
	}


	public void setPartnerConnection(PartnerConnection partnerConnection) {
		this.partnerConnection = partnerConnection;
	}

	/*
	 * String username = "smansuri@deloitte.com.dev"; String password =
	 * "shama33#ruqxL5DhkZdN6Z4DNrsytv091";
	 */
	String username = "rachitjain@deloitte.com.vaporizer";
	String password = "deloitte@13";
	/*
	 * String username = "mrgr@deloitte.com"; String password =
	 * "deloitte.2ltle01z2C81Xg6q8x4oXQOAhe";
	 */
	String authEndPoint = "https://login.salesforce.com/services/Soap/u/24.0/";


	public boolean login() {
		boolean success = false;
		try {
			/*Properties configProp = new Properties();

			InputStream in = this.getClass().getResourceAsStream(
					"/resources/config.properties");

			try {

				configProp.load(in);

			} catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}
*/
		/*	String username = configProp.getProperty("salesforce_username");

			String password = configProp.getProperty("salesforce_password");

			String authEndPoint = configProp
					.getProperty("salesforce_authEndPoint");*/

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


		String projectName=null;
		try {
			partnerConnection.setQueryOptions(250);
			if(projectId==null)
				projectId="a0PG000000B23yKMAR";
			// SOQL query to use
		   String soqlQuery = " Select Name, Parent_Project__c, Type__c from Project__c where id= '"+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					projectName=(String) records[i].getField("Name");
					
				}
				System.out.println("==========================================================="+projectName);
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
			if(projectId==null)
				projectId="a0PG000000B23yKMAR";
			// SOQL query to use
		   String soqlQuery = " Select Salesforce_Password__c, Salesforce_Token__c, Salesforce_Username__c from Project__c where id= '"+ projectId + "'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					String password=(String) records[i].getField("Salesforce_Password__c");
					String token=(String) records[i].getField("Salesforce_Token__c");
					System.out.println("token "+token);
					String username=(String) records[i].getField("Salesforce_Username__c");
					connData.put("password", password);
					if(token==null){
						token="";
					}
					connData.put("token", token);
					connData.put("username", username);
					System.out.println("connData "+connData);
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
	public String getSFDCObjectName( String projectId, String seibelBaseTable) {

		String SFDCObjectName = "No data";
		try {
			login();
			partnerConnection.setQueryOptions(250);
			
			//String	projectId="a0PG000000AtiE5";
		String s = seibelBaseTable	+ "_PreDefined_Mapping";
			// SOQL query to use
			String soqlQuery = " Select id, Object_API_Name__c, Project__r.Name, Project__r.Parent_Project__c, Table_Name__c, Type__c from Table__c where "
					

					+ "  Project__r.Name='"+ s+"' and Parent_Table__c = null and Type__c ='Salesforce'";

//String soqlQuery = " Select  Object_API_Name__c,Id, Project__r.Name, Table_Name__c, Type__c from Table__c where  Project__r.Name='"+ s+"'";
		

			System.out.println(soqlQuery);
			//String soqlQuery ="Select id, Object_API_Name__c, Table_Name__c, Type__c from Table__c where Project__c ='"+projectId+"' and Parent_Table__c = null and Type__c ='Salesforce' and Object_API_Name__c='"+seibelBaseTable+"'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {


				SObject[] records = qr.getRecords();


				System.out
				.println("========================================================="
						+ records.length);
				// Process the query results

				for (int i = 0; i < records.length; i++) {


					SFDCObjectName = (String) records[i]
							.getField("Table_Name__c");



				}
				System.out
				.println("========================================================="
						+ SFDCObjectName);
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
		public  List<MappingModel> getFieldMapping(JSONObject tableData){
			List<MappingModel> mappingData= new LinkedList<MappingModel>();
			String siebelTableName=tableData.getString("siebelTableName");
			String sfdcTablename=tableData.getString("sfdcTableName");
 ArrayList<String> fieldId = new ArrayList<String>();
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			//String sourceBaseTableId="a0QG000000BGG0VMAX";
			String soqlQuery = "Select Source_Field__c, Field_Target__c, Source_Base_Table__c from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"+tableData.getString("id")+"'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {
				
				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					MappingModel mapping= new MappingModel();
					SObject contact = records[i];
					String siebelTableColumn= (String) contact
							.getField("Source_Field__c");
					String sfdcTableColumn = (String) contact
							.getField("Field_Target__c");
					
					mapping.setSfdcFieldTable(sfdcTableColumn);
					mapping.setSfdcObjectName(sfdcTablename);
					mapping.setSiebleBaseTable(siebelTableName);
					mapping.setSiebleBaseTableColumn(siebelTableColumn);
					System.out.println("mappingsss " + siebelTableColumn
							+ " " + sfdcTableColumn);
					mappingData.add(mapping);
				}
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
			String s = sfdcTablename	+ "_PreDefined_Mapping";
			// SOQL query to use
			/*String soqlQuery = " Select id, Object_API_Name__c, Project__r.Name, Project__r.Parent_Project__c, Table_Name__c, Type__c from Table__c where Project__r.Parent_Project__c ='"
					+ projectId
					+ "' and  Project__r.Name='"+ s+"' and Parent_Table__c = null and Type__c ='Salesforce'";
				
*/			partnerConnection.setQueryOptions(250);
		String soqlQuery1 = " Select  Object_API_Name__c,Id, Project__r.Name, Table_Name__c, Type__c from Table__c where  Project__r.Name='"+ s+"'";
		System.out.println(soqlQuery1);
			//String soqlQuery ="Select id, Object_API_Name__c, Table_Name__c, Type__c from Table__c where Project__c ='"+projectId+"' and Parent_Table__c = null and Type__c ='Salesforce' and Object_API_Name__c='"+seibelBaseTable+"'";
			// Make the query call and get the query results
			QueryResult qr1 = partnerConnection.query(soqlQuery1);
			boolean done1 = false;
			while (!done1) {
				System.out.println("11111");
				SObject[] records1 = qr1.getRecords();
			for (int i = 0; i < records1.length; i++) {
				
					if(((String) records1[i].getField("Type__c")).equals("Siebel Child")){
					fieldId.add((String) records1[i].getField("Id"));}
					
				}
				//System.out.println("==="+ SFDCObjectName);
				if (qr1.isDone()) {
					System.out.println("33333333");
					done1 = true;
				} else {
					System.out.println("44444");
					qr1 = partnerConnection.queryMore(qr.getQueryLocator());
				}
			}
			for (int j=0;j<fieldId.size();j++){
				String id=fieldId.get(j);
			String soqlQuery2 = "Select Source_Field__c, Field_Target__c, Source_Base_Table__c from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"+id+"'";
			// Make the query call and get the query results
			QueryResult qr2 = partnerConnection.query(soqlQuery2);
			boolean done2 = false;

			//int loopCount = 0;
			// Loop through the batches of returned results
			while (!done2) {
				
				SObject[] records2 = qr2.getRecords();
				// Process the query results
				for (int i = 0; i < records2.length; i++) {
					MappingModel mapping= new MappingModel();
					SObject contact = records2[i];
					String siebelTableColumn= (String) contact
							.getField("Source_Field__c");
					String sfdcTableColumn = (String) contact
							.getField("Field_Target__c");
					
					mapping.setSfdcFieldTable(sfdcTableColumn);
					mapping.setSfdcObjectName(sfdcTablename);
					mapping.setSiebleBaseTable(siebelTableName);
					mapping.setSiebleBaseTableColumn(siebelTableColumn);
					System.out.println("mappingsss " + siebelTableColumn
							+ " " + sfdcTableColumn);
					mappingData.add(mapping);
				}
				if (qr2.isDone()) {
					done2 = true;
				} else {
					qr2 = partnerConnection.queryMore(qr2.getQueryLocator());
				}

			}




































































			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} 
		System.out.println("\nQuery execution completed.");
		return mappingData;
		//return connData;
		//Project__c p = [Select Id, Name, Parent_Project__c, Type__c from Project__c where Parent_Project__c='a0PG000000AtiE5' and Name='Account_PreDefined_Mapping'];
	}

	
	public  String getsubprojects(String siebelTableName){
		String subprojectId=null;
		String parentProjectId="a0PG000000AtiE5";
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String tabelName=siebelTableName+"_PreDefined_Mapping";
			String soqlQuery = "Select Id, Name, Parent_Project__c, Type__c from Project__c where Parent_Project__c='"+parentProjectId+"' and Name='"+tabelName+"'";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			while (!done) {

				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];
					
					 subprojectId = (String) contact
							.getField("Id");
					
					System.out.println("subprojectId " + 
							subprojectId);
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
		//return connData;
		//Project__c p = [Select Id, Name, Parent_Project__c, Type__c from Project__c where Parent_Project__c='a0PG000000AtiE5' and Name='Account_PreDefined_Mapping'];
	}

	public  JSONObject getRelatedSiebelTable(String subprojectId){
		JSONObject tableData= new JSONObject();
		try {
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			//String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery = "Select Id, Object_API_Name__c, Table_Name__c, Type__c from Table__c where Project__c ='"+subprojectId+"'and Parent_Table__c = null and Type__c ='Siebel'";
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
					String id = (String) contact
							.getField("Id");
					String siebelTableName = (String) contact
							.getField("Object_API_Name__c");
					String sfdcTableName = (String) contact
							.getField("Table_Name__c");
					String type = (String) contact
							.getField("Type__c");
					tableData.put("id", id);
					tableData.put("siebelTableName", siebelTableName);
					tableData.put("sfdcTableName", sfdcTableName);
					
					System.out.println("table data " + id
							+ " " + siebelTableName + " " + sfdcTableName);
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
		//return connData;
		//Project__c p = [Select Id, Name, Parent_Project__c, Type__c from Project__c where Parent_Project__c='a0PG000000AtiE5' and Name='Account_PreDefined_Mapping'];
	}

	public void saveDataDB(List<MainPage> data, HttpServletRequest request, String projId) throws ConnectionException{
		 HttpSession session = request.getSession(true);
		
		//String [] dataArray = data.toArray(new String[data.size()]);
		 login();
	     SObject[] contacts = new SObject[data.size()];
	     int counter=0;
		//data.get(0).getMigrate();
		for (Iterator<MainPage> iterator = data.iterator(); iterator.hasNext();) {
			MainPage mainPage = (MainPage) iterator.next();
			if(mainPage.getSfdcId().equals("")){
			 SObject contact = new SObject();
		     contact.setType("Mapping_Staging_Table__c");
		   /*  if((mainPage.getMigrate()).equals("on")){
		    	 contact.setField("Migrate__c", true);	 
		     }else{*/
		    	 contact.setField("Migrate__c", Boolean.parseBoolean(mainPage.getMigrate()));	 
		     
		    	 contact.setField("Sequence__c", mainPage.getSequence());
		     contact.setField("Prim_Base_Table__c", mainPage.getPrimBaseTable());
		     contact.setField("Project__c", ((String)session.getAttribute("projectId")));
		     contact.setField("SFDC_Object__c", mainPage.getSfdcObject());	
		     contact.setField("Siebel_Object__c", mainPage.getSiebelObject());
		     contact.setField("Threshold__c", mainPage.getThreshold());
		     contacts[counter] = contact;
		     counter++;
		     // Add this sObject to an array 
		     SaveResult[] saveResults = getPartnerConnection().create(contacts);
		     for(int j=0;j<saveResults.length;j++){
	    			System.out.println(saveResults[j].isSuccess());
	    			//System.out.println(results[i].getErrors()[i].getMessage());
	    		}
		   //  contacts[0] = contact;
			}else{
				 String sqlQuery = "Select Id from Mapping_Staging_Table__c where Id ='"+mainPage.getSfdcId()+"'";
		    		QueryResult qr = partnerConnection.query(sqlQuery);
		    		SObject[] records = qr.getRecords();
		    		for(int i=0;i<records.length;i++){
		    		SObject updateContact = new SObject();
					updateContact.setType("Mapping_Staging_Table__c");
					// Set the ID of the contact to update
					updateContact.setId(mainPage.getSfdcId());
					// Set the Phone field with a new value
					updateContact.setField("Migrate__c", Boolean.parseBoolean(mainPage.getMigrate()));
					updateContact.setField("Prim_Base_Table__c", mainPage.getPrimBaseTable());
					updateContact.setField("Project__c", ((String)session.getAttribute("projectId")));
					updateContact.setField("SFDC_Object__c", mainPage.getSfdcObject());	
					updateContact.setField("Siebel_Object__c", mainPage.getSiebelObject());
					updateContact.setField("Threshold__c", mainPage.getThreshold());
					updateContact.setField("Sequence__c", mainPage.getSequence());
					// updateContact.setField("Phone", "(415) 555-1212");
					// Create another contact that will cause an error
					// because it has an invalid ID.

					SaveResult[] saveResults = partnerConnection
							.update(new SObject[] { updateContact });
					for(int j=0;j<saveResults.length;j++){
		    			System.out.println(saveResults[j].isSuccess());
		    			//System.out.println(results[i].getErrors()[i].getMessage());
		    		}
		    		
			}
		    		
		    		
		}
		
		}
	     }
		
		public List<MainPage> getSavedDBData(String projectId, List<MainPage> data){
			try {
				partnerConnection.setQueryOptions(250);
				// SOQL query to use
				//String subprojectId="a0PG000000AtiEAMAZ";
				String soqlQuery = "Select Id, Migrate__c, Sequence__c, Prim_Base_Table__c, Project__c, SFDC_Object__c, Siebel_Object__c, Threshold__c from Mapping_Staging_Table__c where Project__c ='"+projectId+"'";
				// Make the query call and get the query results
				QueryResult qr = partnerConnection.query(soqlQuery);
				boolean done = false;

				int loopCount = 0;
				// Loop through the batches of returned results
				data.clear();
				
while(!done){
					SObject[] records = qr.getRecords();
					// Process the query results
					for (int i = 0; i < records.length; i++) {
						MainPage mainPage = new MainPage();
						SObject contact = records[i];
						String id = (String) contact
								.getField("Id");
						mainPage.setSfdcId(id);
						String siebelTableName = (String) contact
								.getField("Siebel_Object__c");
						mainPage.setSiebelObject(siebelTableName);
						String sfdcTableName = (String) contact
								.getField("SFDC_Object__c");
						mainPage.setSfdcObject(sfdcTableName);
						String migrate =  (String) contact
								.getField("Migrate__c");
						mainPage.setMigrate(migrate);
						String seq = (String) contact
								.getField("Sequence__c");
						mainPage.setSequence(seq);
						String primBaseTable = (String) contact.getField("Prim_Base_Table__c");
						mainPage.setPrimBaseTable(primBaseTable);
						String projId = (String) contact.getField("Project__c");
						mainPage.setProjId(projId);
						String threshold = (String) contact.getField("Threshold__c");
						mainPage.setThreshold(threshold);
						data.add(mainPage);
					}
					Collections.sort(data, new Comparator<MainPage>(){
						@Override
				        public int compare(MainPage  mainPage1, MainPage  mainPage2)
				        {

				            return  mainPage1.getSequence().compareTo(mainPage2.getSequence());
				        }
				    });
					
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
		
		public void getMappingRecords(String projectId){
			String selectTables = "";
			String fromTables = "";
			String joinTables ="";
			try{
				//String sqlQuery = "Select id, Object_API_Name__c, Project__c, Table_Name__c, Type__c from Table__c where Project__c= '"+projectId+"'";
				String sqlQuery = "Select id, Object_API_Name__c, Project__c, Table_Name__c, Type__c from Table__c where Project__c= 'Account_PreDefined_Mapping'";
				QueryResult qr = partnerConnection.query(sqlQuery);
				boolean done = false;
				while(!done){
					SObject[] records = qr.getRecords();
					System.out.println(records.length);
					// Process the query results
					for (int i = 0; i < records.length; i++) {
						SObject contact = records[i];
						System.out.println(records[i]);
						String type = (String) contact.getField("Type__c");
					
								
						if(type.equals("Siebel")) {
							String table = (String) contact.getField("Table_Name__c");
							fromTables = fromTables+" "+"SIEBEL."+table+",";	
							String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
									+ "Source_Base_Table__c = '"+(String) contact.getField("id")+"'";
							QueryResult result = partnerConnection.query(mappingQuery);
							SObject[] mappingRecords = result.getRecords();
							for(int j=0;j<mappingRecords.length;j++){
								SObject mappingRecord = records[i];
								String salesForceField = (String) mappingRecord.getField("Field_Target__c");
								String siebelField = (String) mappingRecord.getField("Source_Field__c");
								String siebelBaseTable = (String) mappingRecord.getField("Source_Base_Table__r.Object_API_Name__c");
								selectTables = selectTables+" "+ siebelBaseTable+"."+siebelField +" "+"as"+ " "+	siebelField+"="+salesForceField+",";							
								
							}
								
							
							}else{
								if(type.equals("Siebel Child")){
									String table = (String) contact.getField("Table_Name__c");
									fromTables = fromTables+" "+"SIEBEL."+table+",";	
									joinTables = joinTables+table+",";
									String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
											+ "Source_Base_Table__c = '"+(String) contact.getField("id")+"'";
									QueryResult result = partnerConnection.query(mappingQuery);
									SObject[] mappingRecords = result.getRecords();
									for(int j=0;j<mappingRecords.length;j++){
										SObject mappingRecord = records[i];
										String salesForceField = (String) mappingRecord.getField("Field_Target__c");
										String siebelField = (String) mappingRecord.getField("Source_Field__c");
										String childBaseTable = (String) mappingRecord.getField("Source_Base_Table__r.Object_API_Name__c");
										selectTables = selectTables+" "+ childBaseTable+"."+siebelField +" "+"as"+ " "+	siebelField+"="+salesForceField+",";							
										
									}
									
								}
							}
						
						}
					}
				String query = "Select"+selectTables+" "+"from"+" "+fromTables+" "+"LEFT OUTER JOIN"+" "+joinTables+" "+"on"+" ";
					
					if (qr.isDone()) {
						done = true;
					} else {
						qr = partnerConnection.queryMore(qr.getQueryLocator());
					}

				}

			 catch (ConnectionException ce) {
				ce.printStackTrace();
			} 
			System.out.println("\nQuery execution completed.");
		}
		
	/*public List<String > addObjectToTable(List<SiebelObject> listOfObjects,
			String projectId) {
		List<String > result = null;
		try {
			SObject[] tableObjects = new SObject[listOfObjects.size()];
			int counter = 0;
			// iterate over the list of objects to get siebelObject
			for (Iterator<SiebelObject> iterator = listOfObjects.iterator(); iterator
					.hasNext();) {
				SiebelObject siebelObject = iterator.next();
				// Create a new sObject of type Contact
				// and fill out its fields.
				System.out.println("we are ereeeeeeeeeeeeeeeeeeee");
				SObject tableObject = new SObject();
				tableObject.setType("Table__c");
				tableObject.setField("Project__c", projectId);
				tableObject.setField("Name", siebelObject.getName());
				tableObject.setField("Entity__c", siebelObject.getEntityType());
				tableObject.setField("Type__c", siebelObject.getType());
				tableObject.setField("Table_Name__c", siebelObject.getTableName());
				tableObject
						.setField("Siebel_ID__c", siebelObject.getSiebelId());
				tableObject.setField("Table_Comments__c",
						siebelObject.getDescription());
				tableObject.setField("Repository__c",
						siebelObject.getRepositoryName());

				tableObjects[counter] = tableObject;
				counter++;
			}
			result = createSalesForceObject( tableObjects);

		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return result;
	}

	private String createSalesForceObject(String result,
			SObject[] tableObjects, List<SiebelField> listOfFields,
			String projectId) throws ConnectionException {
		// Make a create call and pass it the array of sObjects
		SaveResult[] results = partnerConnection.create(tableObjects);

		System.out.println("results:::::arr" + results.length);
		for (int i = 0; i < results.length; i++) {

			System.out.println("resulting array which is to be inserted"
					+ results.length);
		}

		for (int l = 0; l < listOfFields.size(); l++) {

			System.out.println("Siebel field pick type"
					+ listOfFields.get(l).getPickType());
		}

		// Iterate through the results list
		// and write the ID of the new sObject
		// or the errors if the object creation failed.
		// In this case, we only have one result
		// since we created one contact.
		for (int j = 0; j < results.length; j++) {
			System.out.println("getting values " + results[j].isSuccess()
					+ "  "
					+ listOfFields.get(j).getSiebelPicklistType().equals("Y")
					+ "   " + listOfFields.get(j).getName() + "pick type::"
					+ listOfFields.get(j).getPickType());
			if (results[j].isSuccess()
					&& listOfFields.get(j).getSiebelPicklistType().equals("Y")) {
				// here logic should be for inserting picklist values,if parent
				// table is successfully inserted
				System.out.println("pick type:::values::"
						+ listOfFields.get(j).getPickType());

				result = results[j].getId();// parent table ID
				System.out
						.println("\nA Field was added was created with an ID of: "
								+ result);
				List<SiebelListOfValues> pickListValue = AddObjectFieldController
						.getPickListOfValues(projectId, listOfFields.get(j)
								.getPickType());
				insertIntoPickListTable(pickListValue, result);

			} else {
				// There were errors during the create call,
				// go through the errors array and write
				// them to the console
				for (int i = 0; i < results[j].getErrors().length; i++) {
					Error err = results[j].getErrors()[i];
					System.out.println("Errors were found on item " + j);
					System.out.println("Error code: "
							+ err.getStatusCode().toString());
					System.out.println("Error message: " + err.getMessage());
				}
			}
		}
		return result;
	}

	public List<String> createSalesForceObject(SObject[] tableObjects)
			throws ConnectionException {
		// Make a create call and pass it the array of sObjects
		SaveResult[] results = partnerConnection.create(tableObjects);
		// Iterate through the results list
		// and write the ID of the new sObject
		// or the errors if the object creation failed.
		// In this case, we only have one result
		// since we created one contact.
		List<String> idList= new LinkedList<String>();
		String result;
		for (int j = 0; j < results.length; j++) {
			if (results[j].isSuccess()) {
				// here logic should be for picklist values

				result = results[j].getId();
				idList.add(result);
				System.out
						.println("\nA table object was created with an ID of: "
								+ result);

				
				 * for(){
				 * 
				 * tableObjects[0]. }
				 
			} else {
				// There were errors during the create call,
				// go through the errors array and write
				// them to the console
				for (int i = 0; i < results[j].getErrors().length; i++) {
					Error err = results[j].getErrors()[i];
					System.out.println("Errors were found on item " + j);
					System.out.println("Error code: "
							+ err.getStatusCode().toString());
					System.out.println("Error message: " + err.getMessage());
				}
			}
		}
		return idList;
	}

	public String addFieldsToTable(List<SiebelField> listOfFields,
			String projectId, String siebelId, String tableId) {
		String result=null;
		try {
			SObject[] tableObjects = new SObject[listOfFields.size()];
			int counter = 0;
			// iterate over the list of objects to get siebelObject
			for (Iterator<SiebelField> iterator = listOfFields.iterator(); iterator
					.hasNext();) {
				SiebelField siebelField = iterator.next();

				System.out.println("siebel pick type:::"
						+ siebelField.getPickType());

				// Create a new sObject of type Contact
				// and fill out its fields.
				System.out.println("we are ereeeeeeeeeeeeeeeeeeee");
				SObject tableObject = new SObject();
				tableObject.setType("Field__c");
				tableObject.setField("Name", siebelField.getName());
				tableObject.setField("Field_Type__c", siebelField.getType());
				tableObject.setField("Repository__c",
						siebelField.getRepositoryName());
				tableObject.setField("Column_Name__c",
						siebelField.getColumnName());
				
				tableObject.setField("Table__c", tableId);

				tableObjects[counter] = tableObject;
				counter++;
			}
			result = createSalesForceObject(result, tableObjects, listOfFields,
					projectId);

		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return result;
	}

	public List<String > insertIntoPickListTable(List<SiebelListOfValues> pickList,
			String tableId) {
		List<String > result = null;
		try {
			SObject[] tableObjects = new SObject[pickList.size()];
			int counter = 0;
			for (int i = 0; i < pickList.size(); i++) {

				// table id for each picklist
				SiebelListOfValues listValue = pickList.get(i);
				System.out.println("inserting into picklist --------" + tableId
						+ "     " + listValue.getValue());
				SObject tableObject = new SObject();
				tableObject.setType("Picklist__c");//
				// tableObject.setField("field", listValue.getId().toString());
				tableObject.setField("Value__c", listValue.getValue());// Integer
				tableObject.setField("Selected__c", listValue.getSelected());// Boolean;
				tableObject.setField("Field__c", tableId);// Boolean;
				tableObjects[counter] = tableObject;
				counter++;

			}

			result = createSalesForceObject(tableObjects);

		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return result;
	}
	// method to get the table ids and the corr siebelids
	
	public  Map<String,String>  getSiebleIdForTableId(List<String> tableIds) {
		Map<String,String> mapofSiebelTableIds= new HashMap<String,String>();
		try {
			// Set query batch size
			
			partnerConnection.setQueryOptions(250);
			String ids=new String();
			// SOQL query to use
			for (Iterator iterator = tableIds.iterator(); iterator.hasNext();) {
				String tableId = (String) iterator.next();
				if(iterator.hasNext())
				ids+=ids+"'"+tableId+"',";
				else
					ids+=ids+"'"+tableId+"'";
			}
			System.out.println(ids);
			String soqlQuery = "SELECT Id,Siebel_ID__c FROM Table__C where id in ( "+ids+" )";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {
				System.out.println("Records in results set " + loopCount++
						+ " - ");
				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject table = records[i];
					Object id = table.getField("Id");
					Object siebelId = table.getField("Siebel_ID__c");
					System.out.println("7777777777 "+id+"  "+siebelId+" 77777777777777");
					mapofSiebelTableIds.put(id.toString(), siebelId.toString());
					

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
		return mapofSiebelTableIds;
	}


	
	
	 * public String createSample() { String result = null; try { // Create a
	 * new sObject of type Contact // and fill out its fields. SObject contact =
	 * new SObject(); contact.setType("Merchandise__c");
	 * contact.setField("Name", "Mobilessss"); contact.setField("Price__c",
	 * "100.00"); contact.setField("Quantity__c", "5");
	 * 
	 * SObject[] contacts = new SObject[1]; contacts[0] = contact; result =
	 * createSalesForceObject(result, contacts); } catch (ConnectionException
	 * ce) { ce.printStackTrace(); } return result; }
	 public void getNames() {
		try {
			// Make the describeGlobal() call
			DescribeGlobalResult describeGlobalResult = partnerConnection
					.describeGlobal();

			// Get the sObjects from the describe global result
			DescribeGlobalSObjectResult[] sobjectResults = describeGlobalResult
					.getSobjects();

			// Write the name of each sObject to the console
			for (int i = 0; i < sobjectResults.length; i++) {
				System.out.println(sobjectResults[i].getName());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void describeObjects() {
		try {
			DescribeSObjectResult[] describeSObjectResults = partnerConnection
					.describeSObjects(new String[] { "Merchandise__c" });

			// Iterate through the list of describe sObject results
			for (int i = 0; i < describeSObjectResults.length; i++) {
				DescribeSObjectResult desObj = describeSObjectResults[i];
				// Get the name of the sObject
				String objectName = desObj.getName();
				System.out.println("sObject name: " + objectName);

				// For each described sObject, get the fields
				Field[] fields = desObj.getFields();

				// Get some other properties
				// if (desObj.getActivateable())
				// System.out.println("\tActivateable");

				// Iterate through the fields to get properties for each field
				for (int j = 0; j < fields.length; j++) {
					Field field = fields[j];
					System.out.println("\tField: " + field.getName());
					System.out.println("\t\tLabel: " + field.getLabel());
					
					 * if (field.isCustom())
					 * System.out.println("\t\tThis is a custom field.");
					 
					System.out.println("\t\tType: " + field.getType());
					
					 * if (field.getLength() > 0)
					 * System.out.println("\t\tLength: " + field.getLength());
					 * if (field.getPrecision() > 0)
					 * System.out.println("\t\tPrecision: " +
					 * field.getPrecision());
					 

					// Determine whether this is a picklist field
					
					 * if (field.getType() == FieldType.picklist) { // Determine
					 * whether there are picklist values PicklistEntry[]
					 * picklistValues = field.getPicklistValues(); if
					 * (picklistValues != null && picklistValues[0] != null) {
					 * System.out.println("\t\tPicklist values = "); for (int k
					 * = 0; k < picklistValues.length; k++) {
					 * System.out.println("\t\t\tItem: " +
					 * picklistValues[k].getLabel()); } } }
					 

					// Determine whether this is a reference field
					
					 * if (field.getType() == FieldType.reference) { //
					 * Determine whether this field refers to another object
					 * String[] referenceTos = field.getReferenceTo(); if
					 * (referenceTos != null && referenceTos[0] != null) {
					 * System
					 * .out.println("\t\tField references the following objects:"
					 * ); for (int k = 0; k < referenceTos.length; k++) {
					 * System.out.println("\t\t\t" + referenceTos[k]); } } }
					 

				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void updateSample() {
		try {
			// Create an sObject of type contact
			SObject updateContact = new SObject();
			updateContact.setType("Contact");
			// Set the ID of the contact to update
			updateContact.setId("003i000000bi3DJAAY");
			// Set the Phone field with a new value
			updateContact.setField("Phone", "rrrrrrrrrrrrr");
			// updateContact.setField("Phone", "(415) 555-1212");
			// Create another contact that will cause an error
			// because it has an invalid ID.

			SaveResult[] saveResults = partnerConnection
					.update(new SObject[] { updateContact });
			// Iterate through the results and write the ID of
			// the updated contacts to the console, in this case one contact.
			// If the result is not successful, write the errors
			// to the console. In this case, one item failed to update.
			for (int j = 0; j < saveResults.length; j++) {
				System.out.println("\nItem: " + j);
				if (saveResults[j].isSuccess()) {
					System.out.println("Contact with an ID of "
							+ saveResults[j].getId() + " was updated.");
				} else {
					// There were errors during the update call,
					// go through the errors array and write
					// them to the console.
					for (int i = 0; i < saveResults[j].getErrors().length; i++) {
						Error err = saveResults[j].getErrors()[i];
						System.out.println("Errors were found on item " + j);
						System.out.println("Error code: "
								+ err.getStatusCode().toString());
						System.out
								.println("Error message: " + err.getMessage());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void querySample() {
		try {
			// Set query batch size
			partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String soqlQuery = "SELECT Id,FirstName, LastName FROM Contact";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {
				System.out.println("Records in results set " + loopCount++
						+ " - ");
				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];
					Object id = contact.getField("Id");
					Object firstName = contact.getField("FirstName");
					Object lastName = contact.getField("LastName");
					System.out.println("conatcttttttttttt idddddddd " + id);

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
	}

	// Code for inserting into Code table

	public List<String> insertScriptIntoSalesForce(
			List<SalesForceScriptObject> sfdcScript) {
		List<String> result = null;
		try {
			// Create a new sObject of type Contact
			// and fill out its fields.
			SObject contact_test = new SObject();

			System.out.println("inserting into salesforce record");

			SObject[] tableObjects = new SObject[sfdcScript.size()];
			int counter = 0;
			for (int i = 0; i < sfdcScript.size(); i++) {

				// table id for each picklist
				SalesForceScriptObject listValue = sfdcScript.get(i);

				SObject tableObject = new SObject();
				tableObject.setType("Business_Component__c");//
				tableObject.setField("Name",
						listValue.getName());
				tableObject.setField("Body__c",
						listValue.getBody());
				tableObject.setField("Project__c",
						listValue.getProjectId());
				tableObject.setField("Source__c",
						listValue.getSource());
				tableObject.setField("Table__c",
						listValue.getTableId());
				
				
				tableObjects[counter] = tableObject;
				counter++;

				
				 * Deployed__c Draft__c Editable__c Object_Name__c
				 * Primary_Version__c Project_Run__c Script__c Script_1__c
				 * Sforce_Object__c Siebel_Name__c Source_Code__c Sub_Type__c
				 * Table__c Type__c
				 
			}

			result = createSalesForceObject( tableObjects);

		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return result;
	}

	public static List<SalesForceScriptObject> getSiebelScript(
			String projectId, String objectName) throws SQLException {

		List<SalesForceScriptObject> scriptObjectList = null;
		try {

			PartnerWSDL partnerWSDL = new PartnerWSDL();
			partnerWSDL.login();

			JSONObject connectionData = partnerWSDL
					.getConnectionData(projectId);
			Connection connection = new UtilityClass()
					.getConnection(connectionData);
			System.out.println("Connecting to database...");

			String query = "";

			query += "SELECT a.NAME,a.SCRIPT FROM SIEBEL.S_BUSCOMP_SCRIPT a, SIEBEL.S_BUSCOMP b where a.BUSCOMP_ID = b.ROW_ID ";
			query += "AND ";
			query += "  b.NAME = '" + objectName + "' ";

			System.out.println("Running query " + query);
			Statement statement = connection.createStatement(
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet recordSet = statement.executeQuery(query);

			scriptObjectList = new ArrayList<SalesForceScriptObject>();

			while (recordSet.next()) {
				// SiebelField field = new SiebelField();
				SalesForceScriptObject scriptObject = new SalesForceScriptObject();
				scriptObject.setName(recordSet.getString(1));
				scriptObject.setbo(recordSet.getString(2));

				// Script_Name__c
				scriptObjectList.add(scriptObject);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scriptObjectList;
	}

	// added for injecting mock object for testcases

	public PartnerConnection getPartnerConnection() {
		return partnerConnection;
	}

	public void setPartnerConnection(PartnerConnection partnerConnection) {
		this.partnerConnection = partnerConnection;
	}

	public static void main(String args[]) {
		PartnerWSDL partnerWSDL = new PartnerWSDL();
		
		try {
			partnerWSDL.getSiebelScript("a0R90000007BBFa", "Account");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  partnerWSDL.login();
		  List<String> str= new LinkedList<String>();
		  str.add("7897897");
		  str.add("99999999");
		  str.add("9999777779");
		  partnerWSDL.getSiebleIdForTableId(str);
		String str="https://c.ap1.visual.force.com/apex/projectdetailpage2?pageID=2#$projectId=a0R90000007BBFaEAO#$ObjectID=j_id0:j_id10:j_id11:j_id12:block:j_id26:0:objects_field_edit";
		str=str.replaceAll("#\\$", "%23%24");
		System.out.println(str);
		 
	}*/
}
