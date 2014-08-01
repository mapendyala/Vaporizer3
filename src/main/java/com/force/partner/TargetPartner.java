package com.force.partner;

import java.io.FileNotFoundException;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class TargetPartner {
	
	PartnerConnection partnerConnection = null;
	
	String username ;
	String password ;
	String authEndPoint = "https://login.salesforce.com/services/Soap/u/24.0/";
	public TargetPartner(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
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
			success = true;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
			success=false;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			success=false;
		}
		return success;
	}


}
