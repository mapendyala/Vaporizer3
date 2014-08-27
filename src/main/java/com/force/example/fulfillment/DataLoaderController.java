package com.force.example.fulfillment;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.force.api.QueryResult;
import com.sforce.async.*;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.partner.*;
                        
    
public class DataLoaderController {
	Integer checkI=0;
    File DataFileVal=null;
    File MapFileVal=null;
    public void dataUploadController(String dataFileUrl,String userId,String pwd,String objectName) throws IOException, AsyncApiException, ConnectionException
    {
    	DataLoaderController example = new DataLoaderController();
    	File[] fileList=new File[2];
    	
    	/* Get Data File       */
    	dataFileUrl=dataFileUrl.substring(29,dataFileUrl.length()-2);
    	String dataFile=getUploadedFile(dataFileUrl,false,userId,pwd);
    	dataFile=dataFile.replaceAll("\\\\r", "");
    	dataFile=dataFile.replaceAll("\"", "");
    	example.DataFileVal=generateFile(dataFile,"DataFile.csv");
    	fileList[0]=example.DataFileVal;
    	
    	String mapFile=getUploadedFile(dataFileUrl,true,userId,pwd);
    	//System.out.println("Next...."+mapFileUrl);
    	mapFile=mapFile.replaceAll("\\\\r", "");
    	mapFile=mapFile.replaceAll("\"", "");
    	example.MapFileVal=generateFile(mapFile,"MapFile.sdl");
    	fileList[1]=example.MapFileVal;
    	
    	//For Data file
    	//String dataFileUrl=PersonController2.getFile(fileList[0],"DataFile.csv","application/vnd.ms-excel","a0PG000000Atg1U",null);
    	System.out.println(dataFileUrl);
    	
    	System.out.println(dataFileUrl);
    	//mapFile=mapFile.replaceAll("\\\\r", "");
    	//mapFile=mapFile.replaceAll("\"", "");
    	/*String dataFile=getUploadedFile(dataFileUrl,false);
    	
    	
    	//For Mapping File
    	String mapFileUrl=PersonController2.getFile(fileList[1],"MapFile.sdl","text/plain","a0PG000000Atg1U",dataFileUrl);
    	System.out.println(mapFileUrl);
    	mapFileUrl=mapFileUrl.substring(29,mapFileUrl.length()-2);
    	String mapFile=getUploadedFile(dataFileUrl,true);
    	System.out.println("Next...."+mapFileUrl);
    	mapFile=mapFile.replaceAll("\\\\r", "");
    	mapFile=mapFile.replaceAll("\"", "");
    	example.MapFileVal=generateFile(mapFile,"MapFile.sdl");
    	//File tmp=DataFileVal;
    	/*File DataFileVal = generateFile(dataFile,"DataFile.csv");
        // creates the file
    	DataFileVal.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(DataFileVal); 
        // Writes the content to the file
        writer.write(dataFile); 
        writer.flush();
        writer.close();
    	*/
    	
    	
        
    	example.runSample(objectName, userId, pwd, "DataFile.csv","MapFile.sdl");
    	
    	//example.runSample("Account", "subhchakraborty@deloitte.com.vaporizer", "May@2013", "DataFile.csv","C:\\Users\\subhchakraborty\\Downloads\\Sep.sdl");
        
    }
    
    private static File[] createFileTest(){
    	
    	File file = new File("DataFile.csv");
    	File file2 = new File("mapping.sdl");
    	File[] fileList=new File[2];
    	try{
        // creates the file
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        PrintWriter out = new PrintWriter(fw);
        // ',' divides the word into columns
        out.print("Full Name");// first row first column
        out.print(",");
        
        out.println("Account_Type__c");// first row third column
            
        out.print("Alice Supporter54"); // second row first column.
        out.print(",");
        out.print("Web");// second row second column
      
           
        //Flush the output to the file
        out.flush();
            
        //Close the Print Writer
        out.close();
            
        //Close the File Writer
        fw.close();      
        fileList[0]=file;
        file2.createNewFile();
         fw = new FileWriter(file2);
         out = new PrintWriter(fw);
        // ',' divides the word into columns
      
        
        out.println("Full Name=NAME");// first row third column
            
        out.println("Account_Type__c=TYPE"); // second row first column.
        
      
           
        //Flush the output to the file
        out.flush();
            
        //Close the Print Writer
        out.close();
            
        //Close the File Writer
        fw.close();      
        
        fileList[1]=file2;
        
        
        
        
    	}
    	catch(IOException e){
    		System.out.println(">>>>>>"+e);
    	}
    	return fileList;
    	
    }
    
    private static File generateFile(String csvStr,String fileName){
    	
    	File file = new File(fileName);
    	try{
    	file.createNewFile();
        FileWriter fw = new FileWriter(file);
        PrintWriter out = new PrintWriter(fw);
    	
    	
    		if(fileName.contains(".csv")){
         // creates the file
         
         
         // ',' divides the word into columns
         String [] arrList=csvStr.split("\\\\n");
         for(int k=0;k<arrList.length;k++){
        	
        	 String row=arrList[k];
        	 String [] rowList=row.split(",");
        	 for(int j=0;j<rowList.length;j++){
        		 
        		 out.print(rowList[j]);
        		 if(j!=rowList.length-1)
        		 out.print(",");
        		 System.out.print(rowList[j]+",");
        		 
        	 }
        	 out.println(); 
        	 System.out.println();
         }
         
         
        /* out.print("This");// first row first column
         out.print(",");
         out.print("is");// first row second column
         out.print(",");
         out.println("amazing");// first row third column
             
         out.print("It's"); // second row first column.
         out.print(",");
         out.print("really");// second row second column
         out.print(",");
         out.print("amazing");// second row third column
       */     
         //Flush the output to the file
               
    	}
    	else{
    		
    		
             
             // ',' divides the word into columns
             String [] arrList=csvStr.split("\\\\n");
             for(int k=0;k<arrList.length;k++){
            	 if(arrList[k]!="")
              	 out.println(arrList[k]); 
              	System.out.println("<<<<<<<<<<<>>>>>>>>>>>>"+arrList[k]); 
             }
             
    		
    		
    		
    	}
    	 out.flush();
         
         //Close the Print Writer
         out.close();
             
         //Close the File Writer
         fw.close();
    	}
    	catch(IOException e){
    		
    		System.out.println(">>>>>>"+e);
    		
    	}
    	
    	
    	
    	
    	
		return file;
    	
    	
    	
    }
    
    
    
    
    
    private static LoginResult loginToSalesforce(
            final String username,
            final String password,
            final String loginUrl) throws ConnectionException {
        final ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(loginUrl);
        config.setServiceEndpoint(loginUrl);
        config.setManualLogin(true);
        return (new PartnerConnection(config)).login(username, password);
    }
    
    private static String getUploadedFile(String fileId,boolean isMap,String userId,String pwd){
    	HttpURLConnection connection = null; 
    	try{
    	final String URL = "https://login.salesforce.com/services/Soap/u/31.0";
        final LoginResult loginResult = loginToSalesforce(userId, pwd, URL);
        
        String sessionId=loginResult.getSessionId();
        System.out.println(loginResult.getSessionId());
        String targetURL ="";
        if(isMap)
           targetURL = "https://na11.salesforce.com/services/apexrest/GetUploadFile/"+fileId+"1";
        else
        	targetURL = "https://na11.salesforce.com/services/apexrest/GetUploadFile/"+fileId;
        System.out.println("URL..."+targetURL);
		//Create connection
          URL url = new URL(targetURL);
          
          connection = (HttpURLConnection)url.openConnection();
          connection.setRequestMethod("GET");
          /*connection.setRequestProperty("Content-Type", 
        		  contentType);
          connection.setRequestProperty("filename", 
        		  fileNane);	*/
          connection.setRequestProperty("Authorization", "Bearer " +loginResult.getSessionId()); 
          //connection.setRequestProperty("Content-Language", "en-US");  
          connection.setDoInput(true);
          connection.setDoOutput(true);
    	
          DataOutputStream wr = new DataOutputStream (
                  connection.getOutputStream ());
  
System.out.println( connection.getResponseMessage());
      //Get Response	
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      String line;
      StringBuffer response = new StringBuffer(); 
      while((line = rd.readLine()) != null) {
        response.append(line+"\n");
       // response.append('\r');
      }
      rd.close();
      System.out.println("......"+response.toString());
     return response.toString();

    } catch (Exception e) {

      e.printStackTrace();
     return null;

    } finally {

     
	if(connection != null) {
        connection.disconnect(); 
      }
    }
  }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    
    
    
    
    
    
    
    
    
    

    /**
     * Creates a Bulk API job and uploads batches for a CSV file.
     */
    
    public void runSample(String sobjectType, String userName,
              String password, String sampleFileName,String mappingFileName)
            throws AsyncApiException, ConnectionException, IOException {
        BulkConnection connection = getBulkConnection(userName, password);
        JobInfo job = createJob(sobjectType, connection);
        List<BatchInfo> batchInfoList = createBatchesFromCSVFile(connection, job,
            sampleFileName,mappingFileName);
        closeJob(connection, job.getId());
        awaitCompletion(connection, job, batchInfoList);
        checkResults(connection, job, batchInfoList);
    }



    /**
     * Gets the results of the operation and checks for errors.
     */
    private void checkResults(BulkConnection connection, JobInfo job,
              List<BatchInfo> batchInfoList)
            throws AsyncApiException, IOException {
        // batchInfoList was populated when batches were created and submitted
    	
        for (BatchInfo b : batchInfoList) {
            CSVReader rdr =
              new CSVReader(connection.getBatchResultStream(job.getId(), b.getId()));
            List<String> resultHeader = rdr.nextRecord();
            int resultCols = resultHeader.size();

            List<String> row;
            while ((row = rdr.nextRecord()) != null) {
                Map<String, String> resultInfo = new HashMap<String, String>();
                for (int i = 0; i < resultCols; i++) {
                    resultInfo.put(resultHeader.get(i), row.get(i));
                }
                boolean success = Boolean.valueOf(resultInfo.get("Success"));
                boolean created = Boolean.valueOf(resultInfo.get("Created"));
                String id = resultInfo.get("Id");
                String error = resultInfo.get("Error");
                if (success && created) {
                    System.out.println("Created row with id " + id);
                    
                    if(checkI==0)
                    	 System.out.println("Data File" + id);
                    else
                    	 System.out.println("Mapping id " + id);
                    
                    } else if (!success) {
                    System.out.println("Failed with error: " + error);
                }
            }
            checkI++;  
        }
    }



    private void closeJob(BulkConnection connection, String jobId)
          throws AsyncApiException {
        JobInfo job = new JobInfo();
        job.setId(jobId);
        job.setState(JobStateEnum.Closed);
        connection.updateJob(job);
    }



    /**
     * Wait for a job to complete by polling the Bulk API.
     * 
     * @param connection
     *            BulkConnection used to check results.
     * @param job
     *            The job awaiting completion.
     * @param batchInfoList
     *            List of batches for this job.
     * @throws AsyncApiException
     */
    private void awaitCompletion(BulkConnection connection, JobInfo job,
          List<BatchInfo> batchInfoList)
            throws AsyncApiException {
        long sleepTime = 0L;
        Set<String> incomplete = new HashSet<String>();
        for (BatchInfo bi : batchInfoList) {
            incomplete.add(bi.getId());
        }
        while (!incomplete.isEmpty()) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {}
            System.out.println("Awaiting results..." + incomplete.size());
            sleepTime = 10000L;
            BatchInfo[] statusList =
              connection.getBatchInfoList(job.getId()).getBatchInfo();
            for (BatchInfo b : statusList) {
                if (b.getState() == BatchStateEnum.Completed
                  || b.getState() == BatchStateEnum.Failed) {
                    if (incomplete.remove(b.getId())) {
                        System.out.println("BATCH STATUS:\n" + b);
                    }
                }
            }
        }
    }



    /**
     * Create a new job using the Bulk API.
     * 
     * @param sobjectType
     *            The object type being loaded, such as "Account"
     * @param connection
     *            BulkConnection used to create the new job.
     * @return The JobInfo for the new job.
     * @throws AsyncApiException
     */
    private JobInfo createJob(String sobjectType, BulkConnection connection)
          throws AsyncApiException {
        JobInfo job = new JobInfo();
        job.setObject(sobjectType);
        job.setOperation(OperationEnum.insert);
        job.setContentType(ContentType.CSV);
        job = connection.createJob(job);
        System.out.println(job);
        return job;
    }

    

    /**
     * Create the BulkConnection used to call Bulk API operations.
     */
    private BulkConnection getBulkConnection(String userName, String password)
          throws ConnectionException, AsyncApiException {
        ConnectorConfig partnerConfig = new ConnectorConfig();
        partnerConfig.setUsername(userName);
        partnerConfig.setPassword(password);
        partnerConfig.setAuthEndpoint("https://login.salesforce.com/services/Soap/u/31.0");
        // Creating the connection automatically handles login and stores
        // the session in partnerConfig
        new PartnerConnection(partnerConfig);
        // When PartnerConnection is instantiated, a login is implicitly
        // executed and, if successful,
        // a valid session is stored in the ConnectorConfig instance.
        // Use this key to initialize a BulkConnection:
        ConnectorConfig config = new ConnectorConfig();
        config.setSessionId(partnerConfig.getSessionId());
        // The endpoint for the Bulk API service is the same as for the normal
        // SOAP uri until the /Soap/ part. From here it's '/async/versionNumber'
        String soapEndpoint = partnerConfig.getServiceEndpoint();
        String apiVersion = "31.0";
        String restEndpoint = soapEndpoint.substring(0, soapEndpoint.indexOf("Soap/"))
            + "async/" + apiVersion;
        config.setRestEndpoint(restEndpoint);
        // This should only be false when doing debugging.
        config.setCompression(true);
        // Set this to true to see HTTP requests and responses on stdout
        config.setTraceMessage(false);
        BulkConnection connection = new BulkConnection(config);
        return connection;
    }



    /**
     * Create and upload batches using a CSV file.
     * The file into the appropriate size batch files.
     * 
     * @param connection
     *            Connection to use for creating batches
     * @param jobInfo
     *            Job associated with new batches
     * @param csvFileName
     *            The source file for batch data
     */
    private List<BatchInfo> createBatchesFromCSVFile(BulkConnection connection,
          JobInfo jobInfo, String csvFileName,String mappingFileName)
            throws IOException, AsyncApiException {
        List<BatchInfo> batchInfos = new ArrayList<BatchInfo>();
        BufferedReader reader = new BufferedReader(new FileReader(mappingFileName));
        String line = null;
        String[] strList;
        String[] actName = null;
        int i=0;
        Map mapA = new HashMap();
        while ((line = reader.readLine()) != null) {
        	if(line!=""){
        	strList=line.split("=");
        	if(strList.length==2){
        	System.out.println(line+">>>>>>"+strList[1]);
        	//actName[i]=strList[1];
        	mapA.put(strList[0],strList[1]);
        	i++;
        	}
        	}
        }
        System.out.println(".........."+mapA.get("Account Type"));
        
        BufferedReader rdr = new BufferedReader( new FileReader(csvFileName));
       // );
        // read the CSV header row
        byte[] headerBytes = (rdr.readLine() + "\n").getBytes("UTF-8");
       
        System.out.println(mapA.keySet()+"..........Map...."+mapA.values());
        String doc2 = new String(headerBytes, "UTF-8");
        System.out.println("....."+doc2);
        String arr[]=doc2.split(",");
       String headerString="";
        for(int j=0;j<arr.length;j++){
        	System.out.println(arr[j]+"..........hhhhin....");
        	arr[j]=(String)mapA.get(arr[j].replaceAll("\\s+$", ""));
        	System.out.println(arr[j]+"..........hhhhout....");
        	if(j+1!=arr.length)
        	headerString+=arr[j]+",";
        	else
        		headerString+=arr[j];
        }
        System.out.println(new String(headerBytes)+"..........hhhh...."+headerString);
        headerBytes=(headerString+"\n").getBytes("UTF-8");
        int headerBytesLength = headerBytes.length;
        File tmpFile = File.createTempFile("bulkAPIInsert", ".csv");
        
        // Split the CSV file into multiple batches
        try {
            FileOutputStream tmpOut = new FileOutputStream(tmpFile);
            int maxBytesPerBatch = 10000000; // 10 million bytes per batch
            int maxRowsPerBatch = 10000; // 10 thousand rows per batch
            int currentBytes = 0;
            int currentLines = 0;
            String nextLine;
            while ((nextLine = rdr.readLine()) != null) {
                byte[] bytes = (nextLine + "\n").getBytes("UTF-8");
                // Create a new batch when our batch size limit is reached
                if (currentBytes + bytes.length > maxBytesPerBatch
                  || currentLines > maxRowsPerBatch) {
                    createBatch(tmpOut, tmpFile, batchInfos, connection, jobInfo);
                    currentBytes = 0;
                    currentLines = 0;
                }
                if (currentBytes == 0) {
                    tmpOut = new FileOutputStream(tmpFile);
                    tmpOut.write(headerBytes);
                    currentBytes = headerBytesLength;
                    currentLines = 1;
                }
                tmpOut.write(bytes);
                currentBytes += bytes.length;
                currentLines++;
            }
            // Finished processing all rows
            // Create a final batch for any remaining data
            if (currentLines > 1) {
                createBatch(tmpOut, tmpFile, batchInfos, connection, jobInfo);
            }
        } finally {
            tmpFile.delete();
        }
        return batchInfos;
    }

    /**
     * Create a batch by uploading the contents of the file.
     * This closes the output stream.
     * 
     * @param tmpOut
     *            The output stream used to write the CSV data for a single batch.
     * @param tmpFile
     *            The file associated with the above stream.
     * @param batchInfos
     *            The batch info for the newly created batch is added to this list.
     * @param connection
     *            The BulkConnection used to create the new batch.
     * @param jobInfo
     *            The JobInfo associated with the new batch.
     */
    private void createBatch(FileOutputStream tmpOut, File tmpFile,
      List<BatchInfo> batchInfos, BulkConnection connection, JobInfo jobInfo)
              throws IOException, AsyncApiException {
        tmpOut.flush();
        tmpOut.close();
        FileInputStream tmpInputStream = new FileInputStream(tmpFile);
        try {
            BatchInfo batchInfo =
              connection.createBatchFromStream(jobInfo, tmpInputStream);
            System.out.println(batchInfo);
            batchInfos.add(batchInfo);

        } finally {
            tmpInputStream.close();
        }
    }


}