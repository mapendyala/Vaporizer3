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
                        
  
//Data Loading
public class DataLoaderController {
	Integer checkI=0;
    File DataFileVal=null;
    File MapFileVal=null;
   public static void main(String args[]) throws IOException, AsyncApiException, ConnectionException{
    	DataLoaderController dt=new DataLoaderController();
    	dt.dataUploadController(new File("C:\\Users\\subhchakraborty\\Downloads\\request.csv"),"subhojit_service3@gmail.com","deltaone33HkxP984RH6O1s5muxZ5b7dd9g","Account");
    	
    	
    }
    public String dataUploadController(File dataFileUrl,String userId,String pwd,String objectName) throws IOException, AsyncApiException, ConnectionException
    {
    	DataLoaderController example = new DataLoaderController();
    	
    	
    	
    	
    	
    	
    	String csvFile = "/Users/mkyong/Downloads/GeoIPCountryWhois.csv";
    	DataFileVal=dataFileUrl;
    	FileReader fr = new FileReader(DataFileVal);
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
    	int i=0;
    	File finalData=new File("data.csv");
    	FileWriter fileWriter = new FileWriter(finalData);

    	try {
     
    		br = new BufferedReader(fr);
    		while ((line = br.readLine()) != null) {
     
    		        // use comma as separator
    			String[] cellElement = line.split(cvsSplitBy);
                if(i==0){
                	objectName=cellElement[0].split("#")[0];
                	for(int j=0;j<cellElement.length;j++){
                		System.out.println(cellElement[j]);
                		fileWriter.append(String.valueOf((cellElement[j].split("#")[1]).split(objectName)[1]));
                		System.out.println(String.valueOf((cellElement[j].split("#")[1]).split(objectName)[1]));
                		if(j+1!=cellElement.length)
                		   fileWriter.append(",");
                		else
                			fileWriter.append("\n");
                		
                	}
                	
                }
                else{
                	
                	for(int j=0;j<cellElement.length;j++){
                		fileWriter.append(String.valueOf(cellElement[j]));
                		if(j+1!=cellElement.length)
                		   fileWriter.append(",");
                		else
                			fileWriter.append("\n");
                	}
                	
                	
                }
    		i++;
    		}
     
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		fileWriter.flush();
    		
    		fileWriter.close();

    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
     
    	System.out.println("Done");
    	
        
    return	example.runSample(objectName, userId, pwd, finalData);
    	
    	//example.runSample("Account", "subhchakraborty@deloitte.com.vaporizer", "May@2013", "DataFile.csv","C:\\Users\\subhchakraborty\\Downloads\\Sep.sdl");
        
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
    
   
    /**
     * Creates a Bulk API job and uploads batches for a CSV file.
     */
    public String runSample(String sobjectType, String userName,
              String password, File sampleFileName)
            throws AsyncApiException, ConnectionException, IOException {
        BulkConnection connection = getBulkConnection(userName, password);
        JobInfo job = createJob(sobjectType, connection);
        List<BatchInfo> batchInfoList = createBatchesFromCSVFile(connection, job,
            sampleFileName);
        closeJob(connection, job.getId());
        awaitCompletion(connection, job, batchInfoList);
      return  checkResults(connection, job, batchInfoList);
    }



    /**
     * Gets the results of the operation and checks for errors.
     */
    private String checkResults(BulkConnection connection, JobInfo job,
            List<BatchInfo> batchInfoList)
          throws AsyncApiException, IOException {
      // batchInfoList was populated when batches were created and submitted
  	int noOfSuccess=0;
  	int noOfFailure=0;
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
                  noOfSuccess++;
                  if(checkI==0)
                  	 System.out.println("Data File" + id);
                  else
                  	 System.out.println("Mapping id " + id);
                  
                  } else if (!success) {
                  System.out.println("Failed with error: " + error);
                  noOfFailure++;
              }
          }
          checkI++;  
      }
      return (String.valueOf(noOfSuccess)+"_"+String.valueOf(noOfFailure));
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
        partnerConfig.setAuthEndpoint("https://login.salesforce.com/services/Soap/u/33.0");
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
        String apiVersion = "33.0";
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
          JobInfo jobInfo, File csvFileName)
            throws IOException, AsyncApiException {
        List<BatchInfo> batchInfos = new ArrayList<BatchInfo>();
        FileReader fr=new FileReader(csvFileName);
        BufferedReader rdr = new BufferedReader(fr );
        // read the CSV header row
        byte[] headerBytes = (rdr.readLine() + "\n").getBytes("UTF-8");
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
