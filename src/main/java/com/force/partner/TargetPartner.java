package com.force.partner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;
import com.sforce.soap.partner.PartnerConnection;

import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class TargetPartner {
	
	
	
	public ForceApi getForceApi() {
	    SecurityContext sc = ForceSecurityContextHolder.get(true);
	    System.out.println("SecurityContext sc "+sc);
	            ApiSession s = new ApiSession();
	            System.out.println("ApiSession s "+s);
	            s.setAccessToken(sc.getSessionId());
	            s.setApiEndpoint(sc.getEndPointHost());
	            return new ForceApi(s);
	}
	
	
	
	public String getProjectName(String projectId) {

		String projectName = null;
		try {
			//partnerConnection.setQueryOptions(250);
			if (projectId == null)
				projectId = "a0PG000000B23yKMAR";

			// SOQL query to use
			String soqlQuery = " Select Name, Parent_Project__c, Type__c from Project__c where id= '"
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


	
	public List<MainPage> getSavedDBData(String projectId, List<MainPage> data) {
		try {
			// SOQL query to use
			String soqlQuery = "Select Id, Migrate__c, Sequence__c, Prim_Base_Table__c, Project__c, SFDC_Object__c, Siebel_Object__c, Threshold__c from Mapping_Staging_Table__c where Project__c ='"
					+ projectId + "'";
			// Make the query call and get the query results
			 QueryResult<Map> qr = getForceApi().query(soqlQuery);
			//QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;

			// Loop through the batches of returned results
			data.clear();

			while (!done) {
				System.out.println("done");
				List<Map> records = qr.getRecords();
				// Process the query results
				System.out.println("length" + records.size());
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
					String migrate = (String) contact.get("Migrate__c");
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




}
