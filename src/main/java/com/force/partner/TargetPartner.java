package com.force.partner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.force.api.ApiSession;
import com.force.api.DescribeGlobal;
import com.force.api.DescribeSObject;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.utility.ChildObjectBO;
import com.force.utility.SfdcObjectBO;
import com.force.utility.UtilityClass;
import com.google.gson.JsonArray;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.bind.XmlObject;

public class TargetPartner {
	
	HttpSession session;
	
	public TargetPartner(HttpSession sess) {
		session=sess;
	}



	public TargetPartner() {
		// TODO Auto-generated constructor stub
	}



	public ForceApi getForceApi() {
		JSONObject authParams=(JSONObject) session.getAttribute("authParams");
        ApiSession s = new ApiSession();
        s.setAccessToken(authParams.getString("oAuthToken"));
        s.setApiEndpoint(authParams.getString("instanceUrl"));
        return new ForceApi(s);
	}
	
	
	/*public static void main(String args[]){
		HttpClient httpClient= new HttpClient();
		JSONObject authParams= new JSONObject();
		PostMethod post = new PostMethod("https://login.salesforce.com/services/oauth2/token");
        //post.addParameter("code", code);
        post.addParameter("grant_type", "password");
        post.addParameter("client_id", "3MVG98XJQQAccJQftNctCshPH7OHgKw4QQrOUSQbbp.dJK7pBXpbwdKGtE2u3U_mCSIWrd9RbAafS6PpwaveH");
        post.addParameter("client_secret", "5595747085030222154");
        post.addParameter("username","rachitjain@deloitte.com.vaporizer");
        post.addParameter("password","deloitte@1");
 
                try {
                 httpClient.executeMethod(post);
                    try {
	                        JSONObject authResponse = new JSONObject(
	                                        post.getResponseBodyAsString());
	                        System.out.println("Auth response: "
	                                + authResponse.toString());
	 
	                       String accessToken = authResponse.getString("access_token");
	                       String instanceUrl = authResponse.getString("instance_url");
	                      
	               		authParams.put("oAuthToken", accessToken);
	               		authParams.put("instanceUrl", instanceUrl);
	                        System.out.println("Got access token: " + accessToken);
	                    } catch (JSONException e) {
	                        e.printStackTrace();
	                        
	                    }
	                } catch (HttpException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
	                    post.releaseConnection();
	                }
                httpClient= new HttpClient();
        PostMethod post1 = new PostMethod(authParams.getString("instanceUrl")+"/services/data/v20.0/sobjects/Test_Object__c/");
        		        post1.setRequestHeader("Authorization", "OAuth "+authParams.getString("oAuthToken"));
        		        try {
        		        	JSONArray arr= new JSONArray();
        		        	JSONObject account = new JSONObject();
        		        	JSONObject account1 = new JSONObject();
        		            try {
        		            	account.put("Name", "hello1");
        		                account.put("Roll__c", 65766);
        		                account1.put("Name", "hello1");
        		                account1.put("Roll__c", 12132);
        		            } catch (JSONException e) {
        		                e.printStackTrace();
        		                
        		            }
        		            arr.put(account);
        		            arr.put(account1);
        		            
							post1.setRequestEntity(new StringRequestEntity(arr.toString(),
							        "application/json", null));
						
        		        System.err.println("outputtt isss "+httpClient.executeMethod(post1));
        		        if (post1.getStatusCode() == HttpStatus.SC_CREATED) {
        		        	                try {
        		        	                    JSONObject response = new JSONObject(
        		        	                                    post1.getResponseBodyAsString());
        		        	                    System.out.println("Create response: "
        		        	                            + response.toString(2));
        		        	                    if (response.getBoolean("success")) {
        		        	                    String    accountId = response.getString("id");
        		        	                        System.out
															.println("New record id " + accountId + "\n\n");
        		        	                    }
        		        	                } catch (JSONException e) {
        		        	                    e.printStackTrace();
        		        	                    //throw new ServletException(e);
        		        	                }
        		        	            }
        		        } catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (HttpException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        		 
		
		//fa.UpdateSObject("Test_Object__c", "Name",js);
		
		System.out.println("bye");
		
	}*/
	public String getProjectName(String projectId) {

		String projectName = null;
		try {
			//partnerConnection.setQueryOptions(250);
			if (projectId == null)
				projectId = "a0PG000000CHjXCMA1";

			// SOQL query to use
			String soqlQuery = " Select Name, Parent_Project__c, Type__c from Project__c where id= '"
					+ projectId + "'";
			// Make the query call and get the query results
			@SuppressWarnings("rawtypes")
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
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
					//qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.projectName "+projectName);
		return projectName;

	}

/*public static void main(String args[]){
	TargetPartner tp= new TargetPartner();
	tp.getProjectName("a0PG000000B5e2yMAB");
	
}*/
	
	public JSONObject getMiddleWareData(String projectId) {

		JSONObject connData = new JSONObject();
		try {
			//partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String soqlQuery = "SELECT MiddleSalesforce_Username__c,MiddleSalesforce_Password__c, MiddleSalesforce_Token__c FROM Project__c  where id='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
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
					connData.put("databaseUrl", databaseUrl);

				}
				if (qr.isDone()) {
					done = true;
				}/* else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}*/

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
			//partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String soqlQuery = "SELECT Siebel_Username__c,Siebel_Password__c, Siebel_Token__c FROM Project__c  where id='"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
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
				} /*else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}*/

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
			System.out.println(">>>>>"+projectId);
			String soqlQuery = "Select Id, Migrate__c, Sequence__c, Prim_Base_Table__c, Project__c, SFDC_Object__c, Siebel_Object__c, Threshold__c from Mapping_Staging_Table__c where Siebel_Object__c != null and Project__c ='"
					+ projectId + "'";
			// Make the query call and get the query results
			 QueryResult<Map> qr = getForceApi().query(soqlQuery);
			//QueryResult qr = partnerConnection.query(soqlQuery);
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
					String threshold = (String) contact
							.get("Threshold__c");
					mainPage.setThreshold(threshold);
					data.add(mainPage);
				}
				Collections.sort(data, MainPage.SequenceComparator);
			//	System.out.println("dataaaa  " + data);
				if (qr.isDone()) {
					done = true;
				} else {
					//qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return data;
	}

	public JSONObject getTargetOrgDetails(String projectId) {
		JSONObject connData = new JSONObject();

		try {
		//	partnerConnection.setQueryOptions(250);
			if (projectId == null)
				projectId = "a0PG000000CHjXCMA1";

			// SOQL query to use
			String soqlQuery = " Select Salesforce_Password__c, Salesforce_Token__c, Salesforce_Username__c from Project__c where id= '"
					+ projectId + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();

				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					String password = (String) records.get(i)
							.get("Salesforce_Password__c");
					String token = (String) records.get(i)
							.get("Salesforce_Token__c");
					String username = (String) records.get(i)
							.get("Salesforce_Username__c");
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
					//qr = partnerConnection.queryMore(qr.getQueryLocator());
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
			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					SFDCObjectName = (String) records.get(i)
							.get("Table_Name__c");
				}
				if (qr.isDone()) {
					done = true;
				} else {
					//qr = partnerConnection.queryMore(qr.getQueryLocator());
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
			DescribeGlobal describeGlobalResult = getForceApi().describeGlobal();

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

	public String getsubprojects(String siebelTableName) {
		String subprojectId = null;
		String parentProjectId = "a0PG000000AtiE5";
		try {
			//partnerConnection.setQueryOptions(250);
			// SOQL query to use
			String tabelName = siebelTableName + "_PreDefined_Mapping";
			String soqlQuery = "Select Id, Name, Parent_Project__c, Type__c from Project__c where Parent_Project__c='"
					+ parentProjectId + "' and Name='" + tabelName + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					Map contact = records.get(i);

					subprojectId = (String) contact.get("Id");

					System.out.println("subprojectId " + subprojectId);
				}
				if (qr.isDone()) {
					done = true;
				} else {
					//qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return subprojectId;
		// return connData;
		// Project__c p = [Select Id, Name, Parent_Project__c, Type__c from
		// Project__c where Parent_Project__c='a0PG000000AtiE5' and
		// Name='Account_PreDefined_Mapping'];
	}

	public JSONObject getRelatedSiebelTable(String subprojectId) {
		JSONObject tableData = new JSONObject();
		try {
			//partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery = "Select Id, Object_API_Name__c, Table_Name__c, Type__c from Table__c where Project__c ='"
					+ subprojectId
					+ "'and Parent_Table__c = null and Type__c ='Siebel'";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;

			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					Map contact = records.get(i);
					String id = (String) contact.get("Id");
					String siebelTableName = (String) contact
							.get("Object_API_Name__c");
					String sfdcTableName = (String) contact
							.get("Table_Name__c");
					String type = (String) contact.get("Type__c");
					tableData.put("id", id);
					tableData.put("siebelTableName", siebelTableName);
					tableData.put("sfdcTableName", sfdcTableName);

					System.out.println("table data " + id + " "
							+ siebelTableName + " " + sfdcTableName);
				}
				if (qr.isDone()) {
					done = true;
				} else {
					//qr = partnerConnection.queryMore(qr.getQueryLocator());
				}

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return tableData;
		// return connData;
		// Project__c p = [Select Id, Name, Parent_Project__c, Type__c from
		// Project__c where Parent_Project__c='a0PG000000AtiE5' and
		// Name='Account_PreDefined_Mapping'];
	}
	
	
	public void saveMappingDataIntoDB(List<MappingModel> data,
			HttpServletRequest request, String attribute)
			throws ConnectionException {

		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);

		// String [] dataArray = data.toArray(new String[data.size()]);
		
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
			String saveResults = getForceApi().createSObject("Field_Mapping_Data_Migration__c", contactInsert);
			/*for (int j = 0; j < saveResults.length; j++) {
				System.out.println(saveResults[j].isSuccess());
				// System.out.println(saveResults[j].getErrors()[j].getMessage());
			}*/
		}
		if(lstContactUpdate.size()>0){
			contactUpdate=lstContactUpdate.toArray(new SObject[lstContactUpdate.size()]);
			//SaveResult[] saveResults = getForceApi().updateSObject(arg0, arg1, arg2)pdate(contactUpdate);
			/*for (int j = 0; j < saveResults.length; j++) {
				System.out.println(saveResults[j].isSuccess());
				// System.out.println(saveResults[j].getErrors()[j].getMessage());
			}*/
		}
		/*SaveResult[] saveResults = getPartnerConnection().create(contacts);
		for (int j = 0; j < saveResults.length; j++) {
			System.out.println(saveResults[j].isSuccess());
			// System.out.println(saveResults[j].getErrors()[j].getMessage());
		}*/
	}

	public void saveChildDataDB(List<ChildObjectBO> data,
			HttpServletRequest request) throws ConnectionException {
		HttpSession session = request.getSession(true);

		// String [] dataArray = data.toArray(new String[data.size()]);
		
		SObject[] contacts = new SObject[data.size()];
		int counter = 0;
		// data.get(0).getMigrate();
		System.out.println("in WSDL save child method");
		int i = 1;
		
		List<SObject> lstContactUpdate= new ArrayList<SObject>();
		List<SObject> lstContactInsert= new ArrayList<SObject>();
		SObject[] contactUpdate = new SObject[data.size()];
		SObject[] contactInsert = new SObject[data.size()];
		
		for (ChildObjectBO childObj : data) {
			
			if( childObj.getChildSfdcId()==null || childObj.getChildSfdcId().equalsIgnoreCase("")){
			
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
				
				lstContactInsert.add(contact);
				// System.out.println("child Saved value is"+
				// contact.getField("Child_Table__c"));
				/*contacts[counter] = contact;*/
				counter++;
			
				
			}
			else
			{
				System.out.println("in UPDATING for loop" + i);
				SObject contact = new SObject();
				contact.setType("Child_Base__c");
				contact.setField("Id",childObj.getChildSfdcId());
				contact.setField("Primary_Table__c", childObj.getBaseObjName());
				contact.setField("Project__c",
						((String) session.getAttribute("projectId")));
				contact.setField("Child_Table__c", childObj.getChildObjName());
				contact.setField("Join_Condition__c",
						childObj.getJoinCondition());
				contact.setField("Saved__c ", childObj.isCheckFlag());

				// System.out.println("child Saved value is"+
				// contact.getField("Child_Table__c"));
				/*contacts[counter] = contact;*/
				lstContactUpdate.add(contact);
				counter++;
				
			}
			
		} //end of for loop
//here I save it
		
		if(lstContactInsert.size()>0){
			System.out.println("in insert save loop");
			contactInsert=lstContactInsert.toArray(new SObject[lstContactInsert.size()]);
			//SaveResult[] saveResults = getPartnerConnection().create(contactInsert);
			/*for (int j = 0; j < saveResults.length; j++) {
				System.out.println("save"+saveResults[j].isSuccess());
				// System.out.println(saveResults[j].getErrors()[j].getMessage());
			}*/
		}
		if(lstContactUpdate.size()>0){
			System.out.println("in update save loop");
			contactUpdate=lstContactUpdate.toArray(new SObject[lstContactUpdate.size()]);
			/*SaveResult[] saveResults = getPartnerConnection().update(contactUpdate);
			for (int j = 0; j < saveResults.length; j++) {
				System.out.println("update"+saveResults[j].isSuccess());
				// System.out.println(saveResults[j].getErrors()[j].getMessage());
			}*/
		}
		
		
		
	}
	public  File getextractionData(String projectId, String sfdcId, String subProjectId) 
    {
        
        String selectTables = "";
        String fromTables = "";
        String joinTables ="";
        String query = null;
        String mainTable = null;
        int h=0;
        HashMap<String, String> siebelFieldMap = new HashMap<String, String>();
        HashMap<String, String> sfdcFieldMap = new HashMap<String, String>();
        int childTableCounter = 1;
        String sTab="";
        
               
                String soqlQuery2 = "Select Field_Target__c, Source_Field__c, Table_Name__c from Field_Mapping_Data_Migration__c  where Mapping_Staging_Table__c ='"+sfdcId+"'";
                System.out.println(">>>>"+sfdcId);
                try{
                    QueryResult<Map> qr = getForceApi().query(soqlQuery2);
                    @SuppressWarnings("rawtypes")
					QueryResult<Map> qr1=null;
                    boolean done = false;
                    while(!done){
                        List<Map> records = qr.getRecords();
                        System.out.println(records.size());
                        //if there is no user defined data
                        if((records.size() == 0)||(sfdcId=="")){
                        	System.out.println("Predefined data>>>>>>>>>>>>>>");
                            String soqlQuery1 = "Select id, Object_API_Name__c, Project__c,Join_Condition__c, Table_Name__c, Type__c from Table__c where Project__c= '"+subProjectId+"'";
                             qr1 = getForceApi().query(soqlQuery1);
                            records = qr1.getRecords();
                            List<Map> records4=new ArrayList();
                            
                            for (int i = 0; i < records.size(); i++) {
                                Map contact = records.get(i);
                                
                                String type = (String) contact.get("Type__c");
                                h++;
                                int mainCount=0;
                                System.out.println("........."+type);
                                if((type!=null)&&(type.equals("Siebel"))) {
                                	 sTab="SIEBEL."+mainTable+" "+"mainTableAlias";
                                     mainTable = (String) contact.get("Object_API_Name__c");   
                                    fromTables = fromTables+" "+"SIEBEL."+mainTable+" "+"mainTableAlias";   
                                    String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
                                            + "Source_Base_Table__c = '"+(String) contact.get("Id")+"'";
                                    QueryResult<Map> result = getForceApi().query(mappingQuery);
                                    List<Map> mappingRecords = result.getRecords();
                                    
                                    for (int p = 0; p < mappingRecords.size(); p++) {
                                    	if((String) mappingRecords.get(p).get("Field_Target__c")!=null){
                                    	if((((String) mappingRecords.get(p).get("Field_Target__c")).equals("CREATEDBYID"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("CREATEDDATE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("SHIPPINGLONGITUDE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("SHIPPINGLATITUDE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("BILLINGLONGITUDE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("BILLINGLATITUDE"))){
                                  		  System.out.println("sfdc continue"+(String) mappingRecords.get(p).get("Field_Target__c")); 
                                    	}
                                  		  
                                  	
                                    	else{
                                    		records4.add(mappingRecords.get(p));
                                    		
                                    	}
                                    	}
                                    	
                                    }
                                   // mappingRecords=records4.toArray(new SObject[records4.size()]);
                                    for(int j=0;j<mappingRecords.size();j++){
                                        Map mappingRecord = mappingRecords.get(j);
                                        String salesForceField = (String) mappingRecord.get("Field_Target__c");
                                        String siebelField = (String) mappingRecord.get("Source_Field__c");
                                        XmlObject sourceTable = (XmlObject) mappingRecord.get("Source_Base_Table__r");
                                        String siebelBaseTable = (String) sourceTable.getChild("Object_API_Name__c").getValue();
                                        siebelFieldMap.put("siebelFieldKey"+j, siebelField);
                                        sfdcFieldMap.put("sfdcFieldKey"+j, salesForceField);
                                        System.out.println(j+"Sibel "+siebelField);
                                        System.out.println(j+"sfdc "+salesForceField);
                                        //selectTables = selectTables+" "+ siebelBaseTable+"."+siebelField +" "+"as"+ " "+"\""+ siebelField+"="+salesForceField+"\""+",";                           
                                        selectTables = selectTables+" "+ "mainTableAlias"+"."+siebelField+",";
                                    }
                                        
                                    mainCount=siebelFieldMap.size();
                                    }else{
                                        if((type!=null)&&(type.equals("Siebel Child"))){
                                            String childTable = (String) contact.get("Table_Name__c");
                                            String joinCondition = (String) contact.get("Join_Condition__c");
                                            if(joinCondition.contains(childTable)){
                                                joinCondition = joinCondition.replace(childTable, "childTableAlias"+childTableCounter);
                                            }
                                            if(joinCondition.contains(mainTable)){
                                                joinCondition = joinCondition.replace(mainTable, "mainTableAlias");
                                            }
                                            joinTables = joinTables+" "+"LEFT OUTER JOIN "+"SIEBEL."+childTable+" "+"childTableAlias"+childTableCounter+" on "+joinCondition;
                                            String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
                                                    + "Source_Base_Table__c = '"+(String) contact.get("Id")+"'";
                                            QueryResult<Map> result = getForceApi().query(mappingQuery);
                                            
                                            List<Map> mappingRecords = result.getRecords();
                                            records4=new ArrayList();
                                            for (int p = 0; p < mappingRecords.size(); p++) {
                                            	if((String) mappingRecords.get(p).get("Field_Target__c")!=null){
                                            	if((((String) mappingRecords.get(p).get("Field_Target__c")).equals("CREATEDBYID"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("CREATEDDATE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("SHIPPINGLONGITUDE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("SHIPPINGLATITUDE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("BILLINGLONGITUDE"))||(((String) mappingRecords.get(p).get("Field_Target__c")).equals("BILLINGLATITUDE"))){
                                          		  System.out.println("sfdc continue"+(String) mappingRecords.get(p).get("Field_Target__c")); 
                                            	}
                                          		  
                                          	
                                            	else{
                                            		records4.add(mappingRecords.get(p));
                                            		
                                            	}
                                            	}
                                            	
                                            }
                                            
                                            //mappingRecords=records4.toArray(new SObject[records4.size()]);
                                            
                                            
                                            for(int j=0;j<mappingRecords.size();j++){
                                                Map mappingRecord = mappingRecords.get(j);
                                                
                                                String salesForceField = (String) mappingRecord.get("Field_Target__c");
                                                String siebelField = (String) mappingRecord.get("Source_Field__c");
                                                XmlObject sourceTable = (XmlObject) mappingRecord.get("Source_Base_Table__r");
                                                String siebelBaseTable = (String) sourceTable.getChild("Object_API_Name__c").getValue();
                                                //siebelFieldMap.put("siebelFieldKey"+mainCount, siebelField);
                                               // sfdcFieldMap.put("sfdcFieldKey"+mainCount, salesForceField);
                                               // System.out.println((mainCount++)+"Sibel "+siebelField);
                                               // System.out.println(mainCount+"sfdc "+salesForceField);
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
                        
                        QueryResult<Map> qr2 = getForceApi().query(soqlQuery2);
                        List<Map> records2 = qr2.getRecords();
                        
                        String soqlQuery3 = "Select Id, Child_Table__c, Join_Condition__c,Primary_Table__c from Child_Base__c where Project__c='"+subProjectId+"'";
                        QueryResult<Map> qr3 = getForceApi().query(soqlQuery3);
                        List<Map> records3 = qr3.getRecords();
                        List<Map> records4 =new ArrayList();
                        Map childBase = null;
                        for (int i = 0; i < records2.size(); i++) {
                        	if((String) records2.get(i).get("Field_Target__c")!=null){
                        	if((((String) records2.get(i).get("Field_Target__c")).equals("CREATEDBYID"))||(((String) records2.get(i).get("Field_Target__c")).equals("CREATEDDATE"))){
                      		  System.out.println("sfdc continue"+(String) records2.get(i).get("Field_Target__c")); 
                        	}
                      		  
                      	
                        	else{
                        		records4.add(records2.get(i));
                        		
                        	}
                        	}
                        	
                        }
                        //records2=records4.toArray(new SObject[records4.size()]);
                        for (int i = 0; i < records2.size(); i++) {
                            Map contact = records2.get(i);
                            if(contact.get("Table_Name__c")==null){
                            	System.out.println("continue");
                            	continue;
                            	
                            	
                            	
                            }
                            
                            boolean bFlag=false;
                            for (int op=0; op<records3.size();op++){
                            	//if((String)records3[op].getField("Table_Name__c")!=null)
                            	
                                if(records3.get(op).get("Child_Table__c")==contact.get("Table_Name__c")){
                                    bFlag=true;
                                    childBase = records3.get(op);
                                    //id = records3[j].getField("Id");
                                }
                            }
                            //its a main table
                            if(bFlag==false) {
                            	// System.out.println("sfdce"+(((String) contact.getField("Field_Target__c")).equals("CREATEDBYID"))); 
                            	
                                mainTable = (String) contact.get("Table_Name__c");   
                                sTab="SIEBEL."+mainTable+" "+"mainTableAlias";
                                fromTables = fromTables+" "+"SIEBEL."+mainTable+" "+"mainTableAlias";   
                                //String mappingQuery = "Select Field_Target__c, Source_Field__c, Source_Base_Table__c, Source_Base_Table__r.Object_API_Name__c from Field_Mapping_Data_Migration__c where "
                                //  + "Source_Base_Table__c = '"+(String) contact.getField("Id")+"'";
                                //  QueryResult result = partnerConnection.query(mappingQuery);
                                //SObject[] mappingRecords = result.getRecords();

                                //SObject mappingRecord = mappingRecords[i];
                                String salesForceField = (String) contact.get("Field_Target__c");
                                String siebelField = (String) contact.get("Source_Field__c");
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
                                
                                String childTable = (String) childBase.get("Table_Name__c");
                                String joinCondition = (String) childBase.get("Join_Condition__c");
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

                                String salesForceField = (String) contact.get("Field_Target__c");
                                String siebelField = (String) contact.get("Source_Field__c");
                                XmlObject sourceTable = (XmlObject) contact.get("Source_Base_Table__r");
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
                    
                     if(qr1!=null){                                   
                      //String mappingFileUR= ExtractDataFromSiebel(query, sfdcFieldMap, siebelFieldMap, projectId);
                        if (qr1.isDone()) {
                            done = true;
                    	}
    					 else {
                            //qr1 = getForceApi().queryMore(qr1.getQueryLocator());
                        }  
                     }   
                  
                    
                    //String mappingFileUR= ExtractDataFromSiebel(query, sfdcFieldMap, siebelFieldMap, projectId);
                   /* if (qr.isDone()) {
                        done = true;
                    } else {
                        qr = partnerConnection.queryMore(qr.getQueryLocator());
                    }*/  
                }
                    } 
                     
                 // }
                 catch (Exception ce) {
                    ce.printStackTrace();
                } 
                System.out.println("########"+selectTables);
                if((selectTables!="")&&(h==0)){
                    selectTables = selectTables.substring(0, selectTables.length()-1);
                    System.out.println("########"+selectTables);
                    System.out.println("########"+fromTables);
                     query = "Select"+selectTables+" "+"FROM"+" "+sTab+" "+joinTables+" ";
                        
                        System.out.println(query);
                }
                if((selectTables!="")&&(h>0)){
					selectTables = selectTables.substring(0, selectTables.length()-1);
					 query = "Select"+selectTables+" "+"FROM"+" "+fromTables+" "+joinTables+" ";
						
						System.out.println(query);

            }
                System.out.println("\nQuery execution completed.");
                //query="Select mainTableAlias.CREATED_BY, mainTableAlias.CREATED, mainTableAlias.DESC_TEXT, mainTableAlias.MAIN_FAX_PH_NUM, mainTableAlias.NAME, mainTableAlias.MAIN_PH_NUM FROM SIEBEL.S_ORG_EXT mainTableAlias";
                File mappingFileURL=ExtractDataFromSiebel(query, sfdcFieldMap, siebelFieldMap, projectId);
                return mappingFileURL;
        }

	public File ExtractDataFromSiebel(String query,Map sfdcMapping,Map siebelNames, String ProjectId) {

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
                 // String sFileName = "tryAndTest";
                  String sFileName = new Date()+"_"+ Calendar.getInstance().getTime();
                  sFileName=sFileName.trim();
                  System.out.println(sFileName+"==================================");
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
          mappingFileURL=  new PartnerWSDL(session).getFile(file, "19SepDemoFile.csv", "application/vnd.ms-excel", ProjectId, null);
          String SDlFileURl= new PartnerWSDL(session).getFile(mappingFile, "195SepDemoMappingFile.sdl", "application/vnd.ms-excel", ProjectId, mappingFileURL);
          System.out.println("filr path : " +mappingFileURL+":::::::"+SDlFileURl);
          com.force.example.fulfillment.DataLoaderController dt=new com.force.example.fulfillment.DataLoaderController();
         // dt.dataUploadController(mappingFileURL,"subhchakraborty@deloitte.com.vaporizer","Sep@2013","Account");

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

	public void saveDataDB(List<MainPage> data, HttpServletRequest request,
			String projId) throws ConnectionException {
		HttpSession session = request.getSession(true);

		SObject[] contacts = new SObject[data.size()];
		int counter = 0;
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
						((String) session.getAttribute("projectId")));
				contact.setField("SFDC_Object__c", mainPage.getSfdcObject());
				contact.setField("Siebel_Object__c", mainPage.getSiebelObject());
				contact.setField("Threshold__c", mainPage.getThreshold());
				contacts[counter] = contact;
				counter++;
				// Add this sObject to an array
				/**************SaveResult[] saveResults = getForceApi().create(
						contacts);*******************/
				/*for (int j = 0; j < saveResults.length; j++) {
					//System.out.println(saveResults[j].isSuccess());
					// System.out.println(results[i].getErrors()[i].getMessage());
				}*/
			} else {
				String sqlQuery = "Select Id from Mapping_Staging_Table__c where Id ='"
						+ mainPage.getSfdcId() + "'";
				QueryResult<Map> qr = getForceApi().query(sqlQuery);
				List<Map> records = qr.getRecords();
				for (int i = 0; i < records.size(); i++) {
					Map<String, Object> updateContact = new HashMap<String, Object>();
					//updateContact.setType("Mapping_Staging_Table__c");
					//updateContact.setId(mainPage.getSfdcId());
					updateContact.put("Migrate__c",
							mainPage.getMigrate());
					updateContact.put("Prim_Base_Table__c",
							mainPage.getPrimBaseTable());
					updateContact.put("Project__c",
							((String) session.getAttribute("projectId")));
					updateContact.put("SFDC_Object__c",
							mainPage.getSfdcObject());
					updateContact.put("Siebel_Object__c",
							mainPage.getSiebelObject());
					updateContact.put("Threshold__c",
							mainPage.getThreshold());
					updateContact.put("Sequence__c",
							mainPage.getSequence());

					/***SaveResult[] saveResults = getForceApi()
							.update(new SObject[] { updateContact });********/
					/*for (int j = 0; j < saveResults.length; j++) {
						//System.out.println(saveResults[j].isSuccess());
						// System.out.println(results[i].getErrors()[i].getMessage());
					}*/

				}

			}

		}
	}

	public List<ChildObjectBO> getSavedChildDBData(String projectId,
			String primTable) {
		List<ChildObjectBO> childData = new ArrayList<ChildObjectBO>();
		try {
			System.out.println("in getSave Child Data proj is" + projectId
					+ "and prim is" + primTable);
			//partnerConnection.setQueryOptions(250);

			// String subprojectId="a0PG000000AtiEAMAZ";
			// String soqlQuery =
			// "Select Id, Migrate__c, Sequence__c, Prim_Base_Table__c, Project__c, SFDC_Object__c, Siebel_Object__c, Threshold__c from Mapping_Staging_Table__c where Project__c ='"+projectId+"'";

			String soqlQuery = "Select Id, Saved__c , Primary_Table__c, Child_Table__c, Join_Condition__c, Project__c"
					+ " from Child_Base__c where Project__c ='"
					+ projectId
					+ "' and Primary_Table__c = '" + primTable + "' ";

			// Make the query call and get the query results
			QueryResult<Map> qryResult = getForceApi().query(soqlQuery);
			boolean done = false;
			int loopCount = 0;
			// Loop through the batches of returned results
			childData.clear();
			int counter = 1;
			while (!done) {
				List<Map> records = qryResult.getRecords();
				System.out.println("records len from DB is" + records.size());

				// Process the query results
				for (int i = 0; i < records.size(); i++) {

					ChildObjectBO childPageObj = new ChildObjectBO();
					Map contact = records.get(i);
					/*
					 * String id = (String) contact .getField("Id");
					 * mainPage.setSfdcId(id);
					 */

					boolean checkFlag = Boolean.parseBoolean((String) contact
							.get("Saved__c"));
					childPageObj.setCheckFlag(checkFlag);

					String siebelBaseTable = (String) contact
							.get("Primary_Table__c");
					childPageObj.setBaseObjName(siebelBaseTable);

					String siebelChildTable = (String) contact
							.get("Child_Table__c");
					childPageObj.setChildObjName(siebelChildTable);

					String joinCondtn = (String) contact
							.get("Join_Condition__c");
					childPageObj.setJoinCondition(joinCondtn);

					childPageObj.setSeqNum(counter);
					
					String childSfdcId=(String)contact.get("Id");
					System.out.println("childSfdc id in get method is"+childSfdcId);
					childPageObj.setChildSfdcId(childSfdcId);

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
					/*qryResult = partnerConnection.queryMore(qryResult
							.getQueryLocator());*/
				}

			} // end of while

		} catch (Exception ce) {
			System.out.println("in catch block");
			ce.printStackTrace();
		}
		/*System.out.println("\n Child Saved Data Query execution completed.");*/
		return childData;
	}
	public String getMappingId(String projectId,
			List<MappingModel> mappingData, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		MappingModel mappingModel = new MappingModel();
		String id = "";
		try {
			//partnerConnection.setQueryOptions(250);
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
					// mappingModel.setMappingSfdcId(id);
					// mappingData.add(mappingModel);
					// mappingData.add(mappingModel);
				}

				if (qr.isDone()) {
					done = true;
				}/* else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}*/

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
		MappingModel mappingModel = new MappingModel();
		String id = "0";
		try {
		//	partnerConnection.setQueryOptions(250);
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

			int loopCount = 0;
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
				}/* else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}*/

			}

		} catch (Exception ce) {
			ce.printStackTrace();
		}
		try {
			//partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery1 = "Select Id, Field_Target__c, Source_Field__c, Table_Name__c from Field_Mapping_Data_Migration__c where Mapping_Staging_Table__c ='"
					+ id + "'";
			// Make the query call and get the query results
			QueryResult<Map> qr1 = getForceApi().query(soqlQuery1);
			boolean done1 = false;

			int loopCount1 = 0;
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
					mappingModel1.setId( (String)contact.get("Id"));
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
				}/* else {
					qr1 = partnerConnection.queryMore(qr1.getQueryLocator());
				}*/

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
		return mappingData;
	}
	public List<String> getSavedChild(String projectId, JSONObject tableData) {
		// List<MappingModel> mappingData= new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		ArrayList<String> child = new ArrayList<String>();
		try {

			//partnerConnection.setQueryOptions(250);
			// SOQL query to use
			// String subprojectId="a0PG000000AtiEAMAZ";
			String soqlQuery = "Select   Child_Table__c  from Child_Base__c where Project__c='"
					+ projectId
					+ "' and Primary_Table__c='"
					+ siebelTableName
					+ "' and Saved__c=true";
			// Make the query call and get the query results
			QueryResult<Map> qr = getForceApi().query(soqlQuery);
			boolean done = false;
			// Loop through the batches of returned results
			while (!done) {

				List<Map> records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.size(); i++) {
					Map contact = records.get(i);

					child.add((String) contact.get("Child_Table__c"));
				}

				if (qr.isDone()) {
					done = true;
				}/** else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}**/

			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return child;
	}

	public List<MappingModel> getFieldMapping(JSONObject tableData,
			List<Object> myChildList) {
		List<MappingModel> mappingData = new LinkedList<MappingModel>();
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		ArrayList<String> fieldId = new ArrayList<String>();
		try {
			//partnerConnection.setQueryOptions(250);
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
				} /*else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}*/

			}
			String s = sfdcTablename + "_PreDefined_Mapping";

			//partnerConnection.setQueryOptions(250);
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
				} /*else {
					qr1 = partnerConnection.queryMore(qr.getQueryLocator());
				}*/
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
					}/* else {
						qr2 = partnerConnection
								.queryMore(qr2.getQueryLocator());
					}*/

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

public ArrayList<String> getFieldTarget(JSONObject tableData){
		
		String siebelTableName = tableData.getString("siebelTableName");
		String sfdcTablename = tableData.getString("sfdcTableName");
		String SFDTargetName = "No data";
		ArrayList<String> field=new ArrayList<String>();
		ArrayList<String> fieldId = new ArrayList<String>();
		String s = sfdcTablename + "_PreDefined_Mapping";

		
		
		try {
			//partnerConnection.setQueryOptions(250);
			
			// SOQL query to use
			// String sourceBaseTableId="a0QG000000BGG0VMAX";
			String soqlQuery = " Select  Object_API_Name__c,Id, Project__r.Name, Table_Name__c, Type__c from Table__c where  Project__r.Name='"
					+ s + "'";
			//String soqlQuery = "Select Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__r.Table_Name__c ='"+sfdcTablename+"'";
		
			
				QueryResult<Map> qr = getForceApi().query(soqlQuery);
				boolean done = false;
				int loopCount = 0;
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
					}/* else {
						qr = partnerConnection.queryMore(qr.getQueryLocator());
					}*/
				}
				//String soqlQuery1 = "Select Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__r.Table_Name__c ='"+sfdcTablename+"'";
				String soqlQuery1 = "Select  Field_Target__c from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"+ tableData.getString("id")+"'";
				QueryResult<Map> qr1 =getForceApi().query(soqlQuery1);
				boolean done1 = false;
				//int loopCount = 0;
			while (!done1) {

					List<Map> records1 = qr1.getRecords();
					// Process the query results
					for (int i = 0; i < records1.size(); i++) {
						SFDTargetName = (String) records1.get(i).get("Field_Target__c");
						field.add(SFDTargetName);
					}
					if (qr1.isDone()) {
						done1 = true;
					}/* else {
						qr1 = partnerConnection.queryMore(qr.getQueryLocator());
					}*/

				}
			for (int j = 0; j < fieldId.size(); j++) {
				String id = fieldId.get(j);
				String soqlQuery2 ="Select  Field_Target__c  from Field_Mapping_Data_Migration__c where Source_Base_Table__c ='"
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
						SFDTargetName = (String) records2.get(i).get("Field_Target__c");
					field.add(SFDTargetName);}
					if (qr2.isDone()) {
						done2 = true;
					}/* else {
						qr2 = partnerConnection
								.queryMore(qr2.getQueryLocator());
					}*/

				}
			}
			} catch (Exception ce) {
				ce.printStackTrace();
			}
			System.out.println("\nQuery execution completed.");

			return field;
		
		}

}
