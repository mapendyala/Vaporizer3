package com.force.example.fulfillment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.force.example.fulfillment.order.controller.SiebelObjectController;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.Mapping;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.example.fulfillment.order.model.MappingSFDC;
import com.force.example.fulfillment.order.model.MultiValMappingModel;
import com.force.example.fulfillment.order.model.PreMapData;
import com.force.partner.PartnerWSDL;
import com.force.partner.TargetPartner;
import com.force.utility.SfdcObjectBO;
import com.sforce.async.AsyncApiException;
import com.sforce.ws.ConnectionException;


/**
 * Handles requests for the application home page..
 */
@Controller
@SessionAttributes("data")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	public static String rowCount;
	public List<MainPage> data = new ArrayList<MainPage>();
	public List<MappingModel> mappingData = new ArrayList<MappingModel>();
	public List<MultiValMappingModel> multiMappingData = new ArrayList<MultiValMappingModel>();
    public File dataFile;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView home(Locale locale, Model model, HttpServletRequest request) {
		// TODO!!!
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("here");
		HttpSession session = request.getSession(true);
		
		JSONObject authParams = getOAuthToken();
		
		session.setAttribute("authParams", authParams);
		String projectId = "a0PG000000CIFgnMAH";
		if(request.getParameter("projectId") != null){
			projectId = request.getParameter("projectId");
		}
		session.setAttribute("projectId", projectId);
		TargetPartner tp= new TargetPartner(session);
		data = tp.getSavedDBData(projectId, data);
		JSONObject middleWareConn= tp.getMiddleWareData(projectId);
		System.out.println("middleWareConn "+middleWareConn);
		session.setAttribute("middleWareConn", middleWareConn);
		JSONObject TargetOrgConn= tp.getTargetOrgDetails(projectId);
		session.setAttribute("targetOrgConn", TargetOrgConn);
		return new ModelAndView("vaporizer", "data", data);
	}
	@RequestMapping(value = "/talend", method = RequestMethod.GET)
	public ModelAndView talendHome(Locale locale, Model model, HttpServletRequest request) {
		// TODO!!!
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("here");
		HttpSession session = request.getSession(true);
		
		JSONObject authParams = getOAuthToken();
		
		session.setAttribute("authParams", authParams);
		String projectId = "a0PG000000CIFgnMAH";
		if(request.getParameter("projectId") != null){
			projectId = request.getParameter("projectId");
		}
		session.setAttribute("projectId", projectId);
		TargetPartner tp= new TargetPartner(session);
		data = tp.getSavedDBData(projectId, data);
		JSONObject middleWareConn= tp.getMiddleWareData(projectId);
		System.out.println("middleWareConn "+middleWareConn);
		session.setAttribute("middleWareConn", middleWareConn);
		JSONObject TargetOrgConn= tp.getTargetOrgDetails(projectId);
		session.setAttribute("targetOrgConn", TargetOrgConn);
		return new ModelAndView("talendJobMigration", "data", data);
	}
	/*Added by Subhojitcg*/





	@RequestMapping(value="/initiateDataloader", method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String initiateDataLoader(Locale locale,Model model,HttpServletRequest request,@RequestParam("objectName")String objectName) throws IOException, AsyncApiException, ConnectionException
	{
		TargetPartner tpWSDL= new TargetPartner(request.getSession()); 	  
		HttpSession session = request.getSession(true);
		JSONObject connData=tpWSDL.getTargetOrgDetails((String) session.getAttribute("projectId"));
		String password=(String)connData.get("password");
		String token=(String)connData.get("token");
		String username=(String)connData.get("username");
		
		com.force.example.fulfillment.DataLoaderController dt=new com.force.example.fulfillment.DataLoaderController();
		 
		if(objectName==null || objectName=="")
		{
			objectName="Account";
		}
		if(token!=null)
			password+=token;
		return dt.dataUploadController(dataFile,username,password,objectName);
		// TODO Auto-generated catch block
	}

	/**
	 * @author piymishra
	 * @param locale
	 * @param model
	 * @param request
	 * @param siebelObject
	 * @return
	 */




	@RequestMapping(value="/getSFDCOBject", method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String getSFDCObject(Locale locale,Model model,HttpServletRequest request,@RequestParam("siebelObject")String siebelObject)
	{
		HttpSession session = request.getSession(true);
		String projectId=(String) session.getAttribute("projectId");
		TargetPartner targetPartner= new TargetPartner(request.getSession());	    	

		String SFDCObjectName=targetPartner.getSFDCObjectName(projectId,siebelObject);	  
		System.out.println(SFDCObjectName);
		return SFDCObjectName;

	}
	/**
	 * @author piymishra
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/SFDCObjectList", method=RequestMethod.GET)
	@ResponseBody public List<SfdcObjectBO> fetchSFDCObjects(HttpServletRequest request)
	{

		String userValue = request.getParameter("objectName");
		System.out.println("uservalues in bean is"+userValue);
		TargetPartner targetPartner= new TargetPartner(request.getSession());	
		return targetPartner.getSFDCOjectListforPopup(userValue);

	} 
	/**
	 * @author piymishra
	 * @param name
	 * @param file
	 * @return
	 *  Upload single file using Spring Controller
	 */
	 @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	    public @ResponseBody
	    String uploadFileHandler(
	            @RequestParam("file") MultipartFile file) {
	 
	        if (!file.isEmpty()) {
	        	String name=file.getOriginalFilename();
	            try {
	                byte[] bytes = file.getBytes();
	 
	                // Creating the directory to store file
	                String rootPath = System.getProperty("catalina.home");
	                File dir = new File(rootPath + File.separator + "tmpFiles");
	                if (!dir.exists())
	                    dir.mkdirs();
	                else
	                	FileUtils.cleanDirectory(dir);
	                
	 
	                // Create the file on server
	                File serverFile = new File(dir.getAbsolutePath()
	                        + File.separator + name);
	                BufferedOutputStream stream = new BufferedOutputStream(
	                        new FileOutputStream(serverFile));
	                stream.write(bytes);
	                stream.close();
	                dataFile=serverFile;
	                System.out.println("Server File Location="
	                        + serverFile.getAbsolutePath());
	 
	               return "You successfully uploaded file=" + name;
	            } catch (Exception e) {
	               return "You failed to upload " + name + " => " + e.getMessage();
	            }
	        } else {
	            return "You failed to upload  because the file was empty.";
	        }
	    }

	@RequestMapping(value = "mappingSave", method = RequestMethod.POST)
public ModelAndView mappingSave(HttpServletRequest request, Map<String, Object> model, Model modelChild) throws ConnectionException {	
		
		String page = request.getParameter("pageName");
		
		if(page.equals("preMapData")) {
			
//			System.out.println("Inside preMapData");
			
			
			HttpSession session = request.getSession(true);
//			System.out.println("In loadMapData method");
			TargetPartner tp= new TargetPartner(session); 
			PartnerWSDL prtnrWSDL = new PartnerWSDL(request.getSession(),false);
			prtnrWSDL.login();
			
			String id;
			String selectName;			
			String sblFldName;
			String joinName;
			String frgnKeyName;
			String clmnName;
			String joinCondition;
			String sfdcFldName;
						
			List<MappingModel> mapSavedData = new ArrayList<MappingModel>();
			
			if(session.getAttribute("mappedSavedData")!=null){	
//				System.out.println("Removing mappingSavedData");
				session.removeAttribute("mappedSavedData");
			}
			
			try {
				
				String sObjectName = request.getParameter("siebelEntity");
				List<PreMapData> preMapDatas = tp.getPredefinedMapData(sObjectName);
				int rowCount = Integer.parseInt(request.getParameter("rowCount"));
				
				for(int i=0; i<=rowCount;i++){
				
					if(request.getParameter("select"+i)!=null && !request.getParameter("select"+i).trim().equals("")){
						
						Boolean lookUpFlag = false;
						String lookupFieldName="";
						String lookupObjName="";
						String lookupRltnName="";
						String lookupExtrnlName="";
						List<MappingSFDC> externlFldLst = new ArrayList<MappingSFDC>();
						Set<String> lstExternalIds= new LinkedHashSet<String>();
						
						MappingModel mapping = new MappingModel();
						
						selectName = request.getParameter("select"+i);
						id = request.getParameter("sfdcId"+i);
						sblFldName = request.getParameter("sblFieldNmdropdown"+i);
						joinName = request.getParameter("joinNamerow"+i);
						frgnKeyName = request.getParameter("frgnKeyrow"+i);
						clmnName = request.getParameter("clmnNmrow"+i);
						joinCondition = request.getParameter("joinConditionrow"+i);
						sfdcFldName = request.getParameter("slfrcdropdown"+i);
						lookupFieldName = request.getParameter("lookUpFieldrow"+i);
						lookupObjName = request.getParameter("lookUpObjrow"+i);
						lookupRltnName = request.getParameter("lookUpRltnNmerow"+i);
						lookupExtrnlName = request.getParameter("lookUpExtrnlrow"+i);
						
						if(lookupObjName!=null && !lookupObjName.trim().equals("")) {
							
							lookUpFlag = true;
														
							
							externlFldLst = prtnrWSDL.getExternalIdList(lookupObjName);
							
							if(externlFldLst!=null && externlFldLst.size()>0)
								for(MappingSFDC externalFld: externlFldLst ){
									lstExternalIds.add(externalFld.getLabel());
									System.out.println("Saving mapped saved Data externalFld.getLabel():: "+externalFld.getLabel());
								}
							
						}				
						
//						System.out.println("....Pre existing values exists in mapping page....");
//						System.out.println("name:: "+selectName+" siebelFldName:: "+sblFldName+" lookupFieldName:: "+lookupFieldName+" sfdcFldName:: "+sfdcFldName+" frKeyTblName:: "+frgnKeyName+" sblColName:: "+clmnName+" joinCondition:: "+joinCondition);
						if(selectName!=null && !selectName.trim().equals("")) 
							mapping.setCheckFlag(true);
						
						mapping.setId(id);
						mapping.setMappingSeq(i);
						mapping.setSblFieldNmdropdown(sblFldName);
						mapping.setJoinNamerow(joinName);
						mapping.setFrgnKeyrow(frgnKeyName);
						mapping.setClmnNmrow(clmnName);
						mapping.setJoinCondition(joinCondition);
						mapping.setSlfrcdropdown(sfdcFldName);
						mapping.setLookUpObject(lookupObjName);
						mapping.setLookUpRelationShipName(lookupRltnName);
						mapping.setLookUpExternalId(lookupExtrnlName);
						mapping.setLstExternalIds(lstExternalIds);
						mapping.setLookUpFlag(lookUpFlag);
						
						mapSavedData.add(mapping);
						
					}
				}
				
								
				if(session.getAttribute("mappedSavedData")==null){
//					System.out.println("Creating mappingSavedData");
					session.setAttribute("mappedSavedData", mapSavedData);
					modelChild.addAttribute("mappedSavedData",mapSavedData);
				}			
				
				
				modelChild.addAttribute("mappingData",preMapDatas);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			return new ModelAndView("PredefinedMap","data",data);
			
		}
		else {
			
		HttpSession session = request.getSession(true);
		String rowId= request.getParameter("rowId");
		System.out.println("------mappingSave entry-----");
		PartnerWSDL partnerWSDL = new PartnerWSDL(request.getSession(),true);
		mappingData.clear();
		rowCount= request.getParameter("rowCount");
		TargetPartner tp= new TargetPartner(session);
		data = tp.getSavedDBData((String)session.getAttribute("projectId"), data);
		//TODO : Temporary Work Around.-- Need to removed
		for(int i=0;i<Integer.parseInt(rowCount)+1;i++){
		/*for(int i=1;i<Integer.parseInt(rowCount);i++){*/
			if(request.getParameter("select"+i)!=null && !request.getParameter("select"+i).trim().equals("")){
				String siebelCheckFlag="select"+i;
				MappingModel mappingModel = new MappingModel();
				if(request.getParameter("sfdcId"+i)!=null){
					mappingModel.setId(request.getParameter("sfdcId"+i));}
				else
					mappingModel.setId("");
				
								
				if (rowId != null) {
					mappingModel.setSfdcRowId(rowId);
				} else
					mappingModel.setSfdcRowId("");
				if(request.getParameter(siebelCheckFlag)!=null){
					mappingModel.setCheckFlag(true);
				}else
					mappingModel.setCheckFlag(false);
				if(request.getParameter("lookUpFieldrow"+i)!=null){
					mappingModel.setLookUpFlag(true);
				}else
					mappingModel.setLookUpFlag(false);
				if(request.getParameter("sblFieldNmdropdown"+i)!=null){
					mappingModel.setSblFieldNmdropdown(request.getParameter("sblFieldNmdropdown"+i));}
				else
					mappingModel.setSblFieldNmdropdown("");
				if(request.getParameter("joinNamerow"+i)!=null){
					mappingModel.setJoinNamerow(request.getParameter("joinNamerow"+i));}
				else
					mappingModel.setJoinNamerow("");
				if(request.getParameter("frgnKeyrow"+i)!=null){
					mappingModel.setFrgnKeyrow(request.getParameter("frgnKeyrow"+i));}
				else
					mappingModel.setFrgnKeyrow("");
				if(request.getParameter("clmnNmrow"+i)!=null){
					mappingModel.setClmnNmrow(request.getParameter("clmnNmrow"+i));}
				else
					mappingModel.setClmnNmrow("");
				if(request.getParameter("slfrcdropdown"+i)!=null){
					mappingModel.setSlfrcdropdown(request.getParameter("slfrcdropdown"+i));}
				else
					mappingModel.setSlfrcdropdown("");
				if(request.getParameter("lookUpObjrow"+i)!=null){
					mappingModel.setLookUpObject(request.getParameter("lookUpObjrow"+i));}
				else
					mappingModel.setLookUpObject("");
				if(request.getParameter("joinConditionrow"+i)!=null){
					mappingModel.setJoinCondition(request.getParameter("joinConditionrow"+i));}
				else
					mappingModel.setJoinCondition("");
				if(request.getParameter("siebelEntity")!=null){
					mappingModel.setSiebelEntity(request.getParameter("siebelEntity"));}
				else
					mappingModel.setSiebelEntity("");
				if(request.getParameter("lookUpRltnNmerow"+i)!=null){
					mappingModel.setLookUpRelationShipName(request.getParameter("lookUpRltnNmerow"+i));}
				else
					mappingModel.setLookUpRelationShipName("");
				if(request.getParameter("lookUpExtrnlrow"+i)!=null){
					mappingModel.setLookUpExternalId(request.getParameter("lookUpExtrnlrow"+i));}
				else
					mappingModel.setLookUpExternalId("");
				
				mappingData.add(mappingModel);
		 }		
		}		
		if(partnerWSDL.login()){
			partnerWSDL.saveMappingSingleValuedDataIntoDB(mappingData);
		}
		partnerWSDL.saveExtractionQryDB(request, request.getParameter("mappingSfdcId"), request.getParameter("extractionQuery"));
		return new ModelAndView("vaporizer" , "data", data);
		}

	}
	
	@RequestMapping(value="/getextractData", method = RequestMethod.GET)
	@ResponseBody public void  createExtractQuery(HttpServletRequest request, HttpServletResponse response){
		System.out.println("In Home controller get extract data method");
		HttpSession session = request.getSession(true);
		String projId  = (String)session.getAttribute("projectId");
		TargetPartner tg= new TargetPartner(request.getSession());
		
		String siebelTableNameValue = request.getParameter("siebelObjName");
		String sfdcId=request.getParameter("sfdcId");
		//String subprojectId=tg.getsubprojects(siebelTableNameValue);
		SiebelObjectController sblObjCntrlr = new SiebelObjectController();
		PartnerWSDL prtnrWSDL = new PartnerWSDL(request.getSession(),true);
		prtnrWSDL.login();
		String baseTable = request.getParameter("baseTable");
		String sfdcObject = request.getParameter("sfdcObject");
		
		System.out.println("=========" + baseTable + "====" +  siebelTableNameValue + "====" + sfdcObject);
		File mappingFIle = sblObjCntrlr.getextractionData(request, sfdcId,
				baseTable,  siebelTableNameValue, sfdcObject);
	    
	    String fullPath=mappingFIle.getAbsolutePath();
	    File downloadFile = new File(fullPath);     
	    FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(downloadFile);
		 
	    // get MIME type of the file   
	    final int BUFFER_SIZE = 4096;
	    String mimeType ="application/csv";  
	    System.out.println("MIME type: " + mimeType);    
	    // set content attributes for the response   
	    response.setContentType(mimeType);      
	    response.setContentLength((int) downloadFile.length());     
	    // set headers for the response     
	    String headerKey = "Content-Disposition";     
	    String headerValue = String.format("attachment; filename=\"%s\"",  
	    		downloadFile.getName());   
	    response.setHeader(headerKey, headerValue);   
	    // get output stream of the response  
     OutputStream outStream = response.getOutputStream();   
     byte[] buffer = new byte[BUFFER_SIZE];     
     int bytesRead = -1;      
     // write bytes read from the input stream into the output stream  
     while ((bytesRead = inputStream.read(buffer)) != -1) 
     {            outStream.write(buffer, 0, bytesRead); 
     }       
     inputStream.close();
     outStream.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	   /* FileInputStream fis = null;
		try {
			fis = new FileInputStream(mappingFIle.getAbsoluteFile());
			 InputStream is = fis;
			 org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
		     response.flushBuffer();
		     fis.close();
		     is.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		   
		response.setContentType("application/csv");
		response.setHeader("Content-Disposition", "attachment; filename="+mappingFIle);
		//return new FileSystemResource(mappingFIle);
*/	
	}

	@RequestMapping(value="/getextractQuery", method = RequestMethod.GET)
	@ResponseBody public String  getExtractionQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("rowId")String rowId,@RequestParam("sblBaseTbl")String sblBaseTbl,
			@RequestParam("sblTblNmVal")String sblTblNmVal, @RequestParam("sfdcObject")String sfdcObject){
		System.out.println("In Home controller getExtractionQuery data method");
		SiebelObjectController sblObjCntrlr = new SiebelObjectController();
		String extractionQuery = sblObjCntrlr.getextractionQuery(request, rowId, sblBaseTbl, sblTblNmVal, sfdcObject);
		
		return extractionQuery;
	}

	@RequestMapping(value="/getFieldColumnVal", method = RequestMethod.GET)
	@ResponseBody public List<String> retrieveColumnAndFieldVal(HttpServletRequest request, @RequestParam("sblFldValSlctd")String sblFldValSlctd, @RequestParam("rowNum") String rowNum) throws ConnectionException{
		System.out.println("In retrieveColumnAndFieldVal method");
		System.out.println("Selected Siebel Value : "+ sblFldValSlctd);
		HttpSession session = request.getSession(true);
		SiebelObjectController siObj=new SiebelObjectController();
		// Toget the foreign key, column name thru ajax call.
		List<String> clmNmLst = siObj.fetchColumndAndFrgnKeyName(request, (String)session.getAttribute("siebelTableNameValue"), sblFldValSlctd, rowNum, null);

		return clmNmLst;
	}
	
	@RequestMapping(value="/getLookUpInfo", method = RequestMethod.GET)
	@ResponseBody public List<Object> retrieveLookUpFieldInfo(HttpServletRequest request, @RequestParam("slctdSlsFrcFldOption")String slsfrcFldValSlctd, @RequestParam("rowNum") String rowNum) throws ConnectionException{
		System.out.println("In getLookUpInfo method");
		System.out.println("Selected SalesForce Value : "+ slsfrcFldValSlctd);
		List<Object> lookUpFlds = null;
		List<MappingSFDC> externlFldLst = null;
		List<MappingSFDC> mpngSFDC = SiebelObjectController.sfdcFldRowNmList;
		String lookUpObj = null;
		for(MappingSFDC mpngDtl : mpngSFDC){
			if(mpngDtl.getName().equals(slsfrcFldValSlctd)){
				if(lookUpFlds == null){
					lookUpFlds = new ArrayList<Object>();
				}
				PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),true);
				prtnrWSDL1.login();
				
				String relationShpName = mpngDtl.getRelationshipName();
				lookUpFlds.add(relationShpName);
				lookUpObj = mpngDtl.getReferenceTo()[0];
				lookUpFlds.add(lookUpObj);
				
				externlFldLst = prtnrWSDL1.getExternalIdList((String)lookUpObj);
				lookUpFlds.add(externlFldLst);
				
				SiebelObjectController.relationShpNmRowNmMap.put(Integer.parseInt(rowNum), relationShpName);
				SiebelObjectController.salesFrcNmRowNmMap.put(Integer.parseInt(rowNum), slsfrcFldValSlctd);
				if(externlFldLst!=null){
					if(externlFldLst.get(0).getName() != null && !externlFldLst.get(0).getName().equals("")){
						SiebelObjectController.externalIdRowNmMap.put(Integer.parseInt(rowNum), externlFldLst.get(0).getName());
					}
				}
				
			}
		}
		System.out.println("lookUpFlds "+lookUpFlds);
		return lookUpFlds;
	}

	/*@RequestMapping(value="/getFieldDropdwnVal", method = RequestMethod.GET)
	@ResponseBody public List<List<String>> retrieveFieldDrpDwnVal(HttpServletRequest request, @RequestParam("sblFldValSlctd")String sblFldValSlctd) throws ConnectionException{
		System.out.println("In retrieveFieldDrpDwnVal method");
		System.out.println("Selected Siebel Value : "+ sblFldValSlctd);
		HttpSession session = request.getSession(true);
		TargetPartner tg= new TargetPartner(request.getSession()); 
		//Gets the list of SFDC Field names
		ArrayList<String> field= new ArrayList<String>();
				field=tg.getFieldTarget(tableName);
				
		// To get the list of siebel field names for a siebel entity.
		List<String> sblFldList = new ArrayList<String>();
		sblFldList = siObj.fetchFieldNameList(request, siebelTableNameValue);
		
		return clmNmLst;
	}
*/

	@RequestMapping(value = "checkFieldsMapped",method = RequestMethod.GET)
	@ResponseBody public String checkFieldsSelected(HttpServletRequest request, @RequestParam("sfdcId")String sfdcId) {
		PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),false);
		prtnrWSDL1.login();
		String recrdsExist = prtnrWSDL1.checkFieldsSelctedFrEntity(sfdcId);
		
		return recrdsExist;
	}
	
	@RequestMapping(value = "saveData",method = RequestMethod.POST)
	public ModelAndView getSiebelFielddata(HttpServletRequest request, Map<String, Object> model, Model modelChild) throws ConnectionException {
		HttpSession session = request.getSession(true);
		System.out.println("In main controller");
		TargetPartner tg= new TargetPartner(request.getSession()); 
		PartnerWSDL prtnrWSDL = new PartnerWSDL(request.getSession(),true);
		data.clear();
		rowCount= request.getParameter("rowCount");
		String rowNo = request.getParameter("rowNo");
		String page = request.getParameter("pageName");
		/*		String threshold ="thresh"+rowNo;
		String primBase = "prim"+rowNo;
		String siebelTableName = "objectName"+rowNo;*/
		String thresholdValue=request.getParameter("thresh"+rowNo);
		String primBaseValue=request.getParameter("prim"+rowNo);
		System.out.println("in MY getSiebl meth"+primBaseValue);
		String siebelTableNameValue=request.getParameter("objectName"+rowNo);
		String sfdcObjectName = request.getParameter("SFDCObjName"+rowNo);
		System.out.println("in MY getSiebl Siebel Table"+siebelTableNameValue);
		session.setAttribute("siebelTableNameValue", siebelTableNameValue);
		session.setAttribute("primBaseValue", primBaseValue);
		session.setAttribute("sfdcObjectName", sfdcObjectName);
		System.out.println("RowCount is: " +rowCount);
		for(int i=1;i<=Integer.parseInt(rowCount);i++){
			//mainPage[i] =  new MainPage();
			MainPage mainPage = new MainPage();
			String migrate = "migrate"+i;
			String seq = "seq"+i;
			String objName = "objectName"+i;
			String primTable = "prim"+i;
			String thresholdId = "thresh"+i;
			String SFDCObjName = "SFDCObjName"+i;
			String sfdcId = "SfdcId"+i;

			if(request.getParameter(migrate)==null){
				mainPage.setMigrate(false);
			}else{
				mainPage.setMigrate(true);
			}
			mainPage.setSequence(request.getParameter(seq));
			mainPage.setSiebelObject(request.getParameter(objName));
			mainPage.setPrimBaseTable(request.getParameter(primTable));
			mainPage.setThreshold(request.getParameter(thresholdId));
			if(request.getParameter(sfdcId)==null){
				mainPage.setSfdcId("");
			}else{
				mainPage.setSfdcId(request.getParameter(sfdcId));
			}
			mainPage.setSfdcObject(request.getParameter(SFDCObjName));
			data.add(mainPage);
		}

		prtnrWSDL.saveDataDB(data, request, (String)session.getAttribute("projectId"));


		if(page.equals("map")){
			//Else go to mapping page
			logger.info("Welcome to single valued mapping ");
			String rowId=request.getParameter("rowId" );
			session.setAttribute("rowId", rowId);
			session.setAttribute("rowNo", rowNo);
			
			if(rowId==null || rowId.equals("")){
				Map<String,String> mapSeqId= prtnrWSDL.getIdForSeq((String)session.getAttribute("projectId"));
				rowId=mapSeqId.get(rowNo);
			}
			
			
				//
				PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),false);
				prtnrWSDL1.login();
				//JSONObject tableName = tg.getRelatedSiebelTable(subprojectId);// gives
				
				List<MappingModel> mappingDataSaved = prtnrWSDL
						.getSavedMappingSingleValueDBData(
								rowId,mappingData,sfdcObjectName);
				SiebelObjectController siObj=new SiebelObjectController();
				//Gets the list of SFDC Field names
				List<MappingSFDC> sfdcObjList = prtnrWSDL1.getSFDCFieldList((String)sfdcObjectName);
				// To get the list of siebel field names for a siebel entity.
				List<String> sblFldList = new ArrayList<String>();
				sblFldList = siObj.fetchFieldNameList(request, siebelTableNameValue);
				//caching the sieble field dropdwon values for future use.
				SiebelObjectController.sblFieldNamesLst = sblFldList;
				
				session.setAttribute("sfdcID_Current", request.getParameter("SfdcId"+rowNo));
				String extractionQry = tg.getSavedExtractionQry(request.getParameter("SfdcId"+rowNo));
				
				// retrieve query for Business Component Search Expression :
				String sqlQry = siObj.fetchSqlQryForBizSearchCompExp(request, siebelTableNameValue);
				
				List<String> hdrValues = new ArrayList<String>();
				hdrValues.add(siebelTableNameValue);//Siebel Entity
				hdrValues.add(primBaseValue);//Siebel Base Table
				hdrValues.add(sfdcObjectName);//SFDC Entity
				hdrValues.add(sqlQry);// Business Search Qry
				hdrValues.add(extractionQry);// ExtractionQuery
				
				modelChild.addAttribute("sfdcObj",sfdcObjectName);
				modelChild.addAttribute("mappingField",sfdcObjList);
				modelChild.addAttribute("sbllFlddNmList",sblFldList);
				modelChild.addAttribute("sblObjName",siebelTableNameValue);
				modelChild.addAttribute("hdrValues",hdrValues);
				modelChild.addAttribute("rowId", rowId);
				
				/*
				 * if(mappingDataSaved.isEmpty()){
				 * modelChild.addAttribute("mappingData",mappingData);} else
				 */
					modelChild.addAttribute("mappingData",mappingDataSaved);
				modelChild.addAttribute("MappingId",rowId);

			
			return new ModelAndView("mapping", "data", data);
		} 
		// Displays Multi Value Field Mapping Screen
		else if(page.equals("multiMap")){

			//Else go to mapping page
			logger.info("Entering Multimapping ");
            System.out.println("---------------"+thresholdValue+" "+primBaseValue);
        	String rowId=request.getParameter("rowId" );
			if(rowId==null || rowId.equals("")){
				Map<String,String> mapSeqId= prtnrWSDL.getIdForSeq((String)session.getAttribute("projectId"));
				rowId=mapSeqId.get(rowNo);
			}
			//ThresholdController tc= new ThresholdController();
			//List<SiebelObjectBO> listSiebelObject = tc.fetchSiebelObjects(request);
			String subprojectId=(String)session.getAttribute("projectId");//tg.getsubprojects(siebelTableNameValue);
			if(null != subprojectId){
				PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),false);
				prtnrWSDL1.login();
				SiebelObjectController siObj=new SiebelObjectController();
				List<String> sblFldList = new ArrayList<String>();
				sblFldList = siObj.fetchFieldNameListForMultiVal(request, siebelTableNameValue);
				modelChild.addAttribute("sbllFlddNmList",sblFldList);
				
				/* Sending EmptyList, populating the list dynamically*/
				List<MappingSFDC> lookupObjList = new ArrayList<MappingSFDC>();//prtnrWSDL1.getLookupObjFieldList((String)sfdcObjectName);
				List<MappingSFDC> jnObjParentList =new ArrayList<MappingSFDC>();// prtnrWSDL1.getJnObjParentFieldList((String)sfdcObjectName);
				List<MappingSFDC> jnObjChildList = new ArrayList<MappingSFDC>();//prtnrWSDL1.getJnObjChildFieldList((String)sfdcObjectName);
				
				//JSONObject tableName=tg.getRelatedSiebelTable(subprojectId);
				//String id=tg.getMappingId((String)session.getAttribute("projectId"),mappingData,tableName);

				//List<MappingModel> mappingData1=tg.getSavedMappingDBData((String)session.getAttribute("projectId"),mappingData,tableName);
				
				List<MultiValMappingModel> multivalmapping=prtnrWSDL.getSavedMappingMultiValueDBData(rowId, siebelTableNameValue);
				List<String> hdrValues = new ArrayList<String>();
				//Siebel Entity
				hdrValues.add(siebelTableNameValue);
				//Siebel Base Table
				hdrValues.add(primBaseValue);
				//SFDC Entity
				hdrValues.add(sfdcObjectName);
				//List<String>childTables=tg.getSavedChild((String)session.getAttribute("projectId"),tableName);

				//List<Object> myChildList=siObj.fetchColumns(request, primBaseValue,thresholdValue,childTables);


			
				//List<String>childTables=partnerWSDL.getSavedChild((String)session.getAttribute("projectId"),tableName);
				//System.out.println(mappingData1.get(0));

			//	List<MappingModel> mappingData=tg.getFieldMapping(tableName,myChildList);
			//	ArrayList<String> field= new ArrayList<String>();
				//		field=tg.getFieldTarget(tableName);
				/*for(int count=0;count<mappingData.size();count++){
					field.add(mappingData.get(count).getSfdcFieldTable());

				}*/
				//modelChild.addAttribute("sfdcObj",mappingData.get(0).getSfdcObjectName());
				//modelChild.addAttribute("mappingField",field);
				modelChild.addAttribute("mappingData",multivalmapping);
				modelChild.addAttribute("rowId", rowId);
				modelChild.addAttribute("hdrValues",hdrValues);
				
			/*	if(mappingData1.isEmpty()){
					modelChild.addAttribute("mappingData",mappingData);}
				else
					modelChild.addAttribute("mappingData",mappingData1);*/
				//modelChild.addAttribute("MappingId",id);

			}
			return new ModelAndView("multiMapping", "data", data);
		
		}else{
			return new ModelAndView("vaporizer");
		}
	}
	public JSONObject getOAuthToken(){
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
                return authParams;
	}
	
	
	@RequestMapping(value="/getFieldColumnMultiVal", method = RequestMethod.GET)
	@ResponseBody public List<String> retrieveColumnAndFieldMultiVal(HttpServletRequest request, @RequestParam("sblFldValSlctd")String sblFldValSlctd, @RequestParam("rowNum") String rowNum) throws ConnectionException{
		System.out.println("In retrieveColumnAndFieldMultiVal method");
		System.out.println("Selected Siebel Value : "+ sblFldValSlctd);
		HttpSession session = request.getSession(true);
		SiebelObjectController siObj=new SiebelObjectController();
		// Toget the foreign key, column name thru ajax call.
		List<String> clmNmLst = siObj.fetchColumndAndFrgnKeyNameForMultiVal(request, (String)session.getValue("siebelTableNameValue"), sblFldValSlctd, rowNum);

		String sfdcObjName=getSFDCObject(null, null, request, sblFldValSlctd);
		clmNmLst.add(sfdcObjName);
		return clmNmLst;
	}
	
	/**
	 * @author pshanmukham
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/JunctionObjectList", method=RequestMethod.GET)
	@ResponseBody public List<SfdcObjectBO> fetchJunctionObjects(HttpServletRequest request)
	{

		List<SfdcObjectBO> objList=new ArrayList<SfdcObjectBO>(); 
		HttpSession session = request.getSession(true);
		String userValue = request.getParameter("objectName");
		String selectedSFDCChildObj=request.getParameter("selectedSFDCChildObj");
		System.out.println("uservalues in bean is"+userValue);
		TargetPartner targetPartner= new TargetPartner(request.getSession());	
		return targetPartner.getJuncOjectListforPopup(userValue,selectedSFDCChildObj);

	}  
	
	/**
	 * @author pshanmukham
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getLookupDropDownList", method=RequestMethod.GET)
	@ResponseBody public List<MappingSFDC> getLookupDropDownList(HttpServletRequest request ,@RequestParam("SFDCObject")String sfdcObjectName)
	{
		PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),false);
		prtnrWSDL1.login();
		List<MappingSFDC> jnObjParentList = prtnrWSDL1.getLookupObjFieldList((String)sfdcObjectName);
		
		return jnObjParentList;
	} 
	
	/**
	 * @author pshanmukham
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getJuncDropDownList", method=RequestMethod.GET)
	@ResponseBody public List<MappingSFDC> getJuncObjDropDownList(HttpServletRequest request ,@RequestParam("SFDCObject")String sfdcObjectName)
	{
		PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),false);
		prtnrWSDL1.login();
		List<MappingSFDC> jnObjParentList = prtnrWSDL1.getJnObjParentFieldList((String)sfdcObjectName);
		
		return jnObjParentList;
	} 
	@RequestMapping(value="/getMultivalSFDCLookUpInfo", method = RequestMethod.GET)
	@ResponseBody public List<Object> retrieveSFDCObjLookUpFieldInfo(HttpServletRequest request, @RequestParam("slctdSlsFrcFldOption")String slsfrcFldValSlctd, @RequestParam("SFDCObject") String sfdcObject) throws ConnectionException{
		List<Object> lookUpFlds = null;
		List<MappingSFDC> externlFldLst = null;
		List<MappingSFDC> mpngSFDC = SiebelObjectController.lookUpRelationMap.get(sfdcObject);
		String lookUpObj = null;
		for(MappingSFDC mpngDtl : mpngSFDC){
			if(mpngDtl.getName().equals(slsfrcFldValSlctd)){
				if(lookUpFlds == null){
					lookUpFlds = new ArrayList<Object>();
				}
				PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),false);
				prtnrWSDL1.login();
				
				String relationShpName = mpngDtl.getRelationshipName();
				lookUpFlds.add(relationShpName);
				lookUpObj = mpngDtl.getReferenceTo()[0];
				lookUpFlds.add(lookUpObj);
				
				externlFldLst = prtnrWSDL1.getExternalIdList((String)lookUpObj);
				lookUpFlds.add(externlFldLst);
				
			}
		}
		
		return lookUpFlds;
	}
	
	
	@RequestMapping(value="/getMultivalJuncLookUpInfo", method = RequestMethod.GET)
	@ResponseBody public List<Object> retrieveJunctionObjLookUpFieldInfo(HttpServletRequest request, @RequestParam("slctdSlsFrcFldOption")String slsfrcFldValSlctd, @RequestParam("SFDCObject") String sfdcObject) throws ConnectionException{
		List<Object> lookUpFlds = null;
		List<MappingSFDC> externlFldLst = null;
		List<MappingSFDC> mpngSFDC = SiebelObjectController.juncRelationMap.get(sfdcObject);
		String lookUpObj = null;
		for(MappingSFDC mpngDtl : mpngSFDC){
			if(mpngDtl.getName().equals(slsfrcFldValSlctd)){
				if(lookUpFlds == null){
					lookUpFlds = new ArrayList<Object>();
				}
				PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession(),false);
				prtnrWSDL1.login();
				
				String relationShpName = mpngDtl.getRelationshipName();
				lookUpFlds.add(relationShpName);
				lookUpObj = mpngDtl.getReferenceTo()[0];
				lookUpFlds.add(lookUpObj);
				
				externlFldLst = prtnrWSDL1.getExternalIdList((String)lookUpObj);
				lookUpFlds.add(externlFldLst);
				
			}
		}
		
		return lookUpFlds;
	}
	
	
	@RequestMapping(value = "multiValmappingSave", method = RequestMethod.POST)
	public ModelAndView multiValMappingSave(HttpServletRequest request, Map<String, Object> model) throws ConnectionException {	
		HttpSession session = request.getSession(true);
		String	rowCount= request.getParameter("rowCount");
		String rowId= request.getParameter("rowId");
		PartnerWSDL partnerWSDL = new PartnerWSDL(request.getSession(),true);
		multiMappingData.clear();
		rowCount= request.getParameter("rowCount");
		TargetPartner tp= new TargetPartner(session);
		data = tp.getSavedDBData((String)session.getAttribute("projectId"), data);
		for(int i=0;i<Integer.parseInt(rowCount);i++){
			MultiValMappingModel mapModel=new MultiValMappingModel();
			if (rowId != null) {
				mapModel.setSfdcRowId(rowId);
			} else
				mapModel.setSfdcRowId("");
			if(request.getParameter("sfdcId"+i)!=null){
				mapModel.setId(request.getParameter("sfdcId"+i));}
			else
				mapModel.setId("");
			if(request.getParameter("sblFieldNmdropdown"+i)!=null){
				mapModel.setSiebelField(request.getParameter("sblFieldNmdropdown"+i));
			}
			
			if(request.getParameter("relationtyperow"+i)!=null){
				mapModel.setRelationType(request.getParameter("relationtyperow"+i));
			}
			
			if(request.getParameter("childentityrow"+i)!=null){
				mapModel.setChildEntity(request.getParameter("childentityrow"+i));	
			}
			
			if(request.getParameter("childtablerow"+i)!=null){
				mapModel.setChildTable(request.getParameter("childtablerow"+i));	
			}
			
			if(request.getParameter("childFieldrow"+i)!=null){
				mapModel.setChildField(request.getParameter("childFieldrow"+i));	
			}
			if(request.getParameter("intertablerow"+i)!=null){
				mapModel.setInterTable(request.getParameter("intertablerow"+i));
			}
			
			if(request.getParameter("interparentrow"+i)!=null){
				mapModel.setInterParentColumn(request.getParameter("interparentrow"+i));
			}
			
			if(request.getParameter("interchildrow"+i)!=null){
				mapModel.setInterChildColumn(request.getParameter("interchildrow"+i));
			}
			
			if(request.getParameter("sfdcchildrow"+i)!=null){
				mapModel.setSfdcChildObject(request.getParameter("sfdcchildrow"+i));
			}
			if(request.getParameter("lookUpFieldropdown"+i)!=null){
				mapModel.setLookupField(request.getParameter("lookUpFieldropdown"+i));
			}
			
			if(request.getParameter("lookUpRltnNmerow"+i)!=null){
				mapModel.setLookupRelationName(request.getParameter("lookUpRltnNmerow"+i));
			}
			if(request.getParameter("lookUpExtrnlrow"+i)!=null){
				mapModel.setLookupExternalId(request.getParameter("lookUpExtrnlrow"+i));
			}
			if(request.getParameter("JuncObjrow"+i)!=null){
				mapModel.setJunctionObject(request.getParameter("JuncObjrow"+i));
			}
			if(request.getParameter("JuncObjParentFieldropdown"+i)!=null){
				mapModel.setJunctionObjParentField(request.getParameter("JuncObjParentFieldropdown"+i));
			}
			if(request.getParameter("parntRltnNmerow"+i)!=null){
				mapModel.setParentRelationName(request.getParameter("parntRltnNmerow"+i));
			}
			if(request.getParameter("parntlookUpExtrnlrow"+i)!=null){
				mapModel.setParentExternalId(request.getParameter("parntlookUpExtrnlrow"+i));
			}
			if(request.getParameter("JuncObjChildFieldropdown"+i)!=null){
				mapModel.setJunctionObjectChildField(request.getParameter("JuncObjChildFieldropdown"+i));	
			}
		
			if(request.getParameter("childRltnNmerow"+i)!=null){
				mapModel.setChildRelationName(request.getParameter("childRltnNmerow"+i));
			}
			
			if(request.getParameter("childlookUpExtrnlrow"+i)!=null){
				mapModel.setChildExternalId(request.getParameter("childlookUpExtrnlrow"+i));	
			}
			
			multiMappingData.add(mapModel);
			
		}
		if(partnerWSDL.login()){
			partnerWSDL.saveMappingMultiValuedDataIntoDB(multiMappingData,data,(String)session.getAttribute("projectId"));
		}
		return new ModelAndView("vaporizer" , "data", data);
		}
	
	@RequestMapping(value = "savePreMapData", method = RequestMethod.POST)
	public ModelAndView savePreMapData(HttpServletRequest request, Map<String, Object> model,Model modelChild) throws ConnectionException {
		
		System.out.println("In savePreMapData");
		HttpSession session = request.getSession(true);
		String rowId = (String)session.getAttribute("rowId");
		String siebelTableNameValue = (String)session.getAttribute("siebelTableNameValue");
		String rowNo =(String)session.getAttribute("rowNo");
		String sfdcObjectName = (String)session.getAttribute("sfdcObjectName");
		String primBaseValue = (String)session.getAttribute("primBaseValue");
				
		TargetPartner tp= new TargetPartner(session); 
		PartnerWSDL prtnrWSDL = new PartnerWSDL(session,false);
		prtnrWSDL.login();
		
		logger.info("Welcome to single valued mapping ");
		
		if(rowId==null || rowId.equals("")){
			Map<String,String> mapSeqId= prtnrWSDL.getIdForSeq((String)session.getAttribute("projectId"));
			rowId=mapSeqId.get(rowNo);
		}
//		String subprojectId=tp.getsubprojects(siebelTableNameValue);
//		if(null != subprojectId){
			
			
//			JSONObject tableName = tp.getRelatedSiebelTable(subprojectId);// gives
			
			List<MappingModel> mappingDataSaved = prtnrWSDL
					.getSavedMappingSingleValueDBData(
							rowId,mappingData,sfdcObjectName);
			SiebelObjectController siObj=new SiebelObjectController();
			//Gets the list of SFDC Field names
			List<MappingSFDC> sfdcObjList = prtnrWSDL.getSFDCFieldList((String)sfdcObjectName);
			// To get the list of siebel field names for a siebel entity.
			List<String> sblFldList = new ArrayList<String>();
			sblFldList = siObj.fetchFieldNameList(request, siebelTableNameValue);
			// retrieve query for Business Component Search Expression :
			String sqlQry = siObj.fetchSqlQryForBizSearchCompExp(request, siebelTableNameValue);
			
			List<MappingModel> preMapDataList = new ArrayList<MappingModel>();
			String extractionQry = tp.getSavedExtractionQry((String)(session.getAttribute("sfdcID_Current")));
			List<String> hdrValues = new ArrayList<String>();
			//Siebel Entity
			hdrValues.add(siebelTableNameValue);
			//Siebel Base Table
			hdrValues.add(primBaseValue);
			//SFDC Entity
			hdrValues.add(sfdcObjectName);
			hdrValues.add(sqlQry);// Business Search Qry
			hdrValues.add(extractionQry);
			
				
			// Fetch the PreDefined Map data.			
			String[] lookUpFieldrows = request.getParameterValues("lookUpFieldrow");
			List<MappingModel> mapSavedData = (List<MappingModel>)session.getAttribute("mappedSavedData");
			int i=0;	
			int l=1;
			int mapSavedDataSize=0;
			
			if(lookUpFieldrows!=null && lookUpFieldrows.length>0) {
				
			for (String lookUpFieldrow : lookUpFieldrows){
				
				MappingModel pMapData = new MappingModel();
				String name = lookUpFieldrow;				
				String siebelFldName = (String) request.getParameter("lookUpSiebelFldrow"+lookUpFieldrow);					
				String sfdcFldName = (String) request.getParameter("lookUpSfdcFldrow"+lookUpFieldrow);					
				String sfdcObjName = (String) request.getParameter("lookUpSfdcObjrow"+lookUpFieldrow);					
				String mapTypeName = (String) request.getParameter("lookUpMapTyperow"+lookUpFieldrow); 
								
				String lookupRltName="";
				String lookupObjName="";
				String lookupExtrnlName="";
				Boolean lookUpFlag = false;
				Set<String> lstExternalIds= new LinkedHashSet<String>();
				List<String> clmNmLst = siObj.fetchColumndAndFrgnKeyName(request, (String)session.getAttribute("siebelTableNameValue"), siebelFldName, String.valueOf(i+1).toString(),null);
				List<Object> lookUpLst = this.retrieveLookUpFieldInfo(request, sfdcFldName, String.valueOf(i).toString());
				
				String joinName = clmNmLst.get(0);
				String SblColName = clmNmLst.get(1);
				String joinCondition = clmNmLst.get(2);
				String frKeyTblName = clmNmLst.get(3);
				
				if(lookUpLst!=null && lookUpLst.size()>0){
				
				lookupRltName = String.valueOf(lookUpLst.get(0));
				lookupObjName = String.valueOf(lookUpLst.get(1));
				lookupExtrnlName = String.valueOf(lookUpLst.get(2));
				
				lookUpFlag = true;
				
				System.out.println("lookupRltName:: "+lookupRltName);
				System.out.println("lookupObjName:: "+lookupObjName);
				System.out.println("lookupExtrnlName:: "+lookupExtrnlName);
				
				List<MappingSFDC> externlFldLst = prtnrWSDL.getExternalIdList(lookupObjName);
				
				if(externlFldLst!=null && externlFldLst.size()>0){
					
					for(MappingSFDC externalFld: externlFldLst ){
						lstExternalIds.add(externalFld.getLabel());
						System.out.println("Saving premapped saved Data externalFld.getLabel():: "+externalFld.getLabel());
					}
				}
				
				}
				
				if(mapSavedData!=null && mapSavedData.size()>0) {
					
					mapSavedDataSize = mapSavedData.size();
					
					System.out.println("mapSavedDataSize:: "+mapSavedDataSize);
//					mapSavedDataSize = mapSavedDataSize -1;
					pMapData.setMappingSeq(mapSavedDataSize+l);
					l++;
				}
				else {					
					pMapData.setMappingSeq(i);
				}
				
				pMapData.setCheckFlag(true);
				pMapData.setId(null);
				pMapData.setSblFieldNmdropdown(siebelFldName);
				pMapData.setJoinNamerow(joinName);
				pMapData.setFrgnKeyrow(frKeyTblName);
				pMapData.setClmnNmrow(SblColName);
				pMapData.setJoinCondition(joinCondition);
				pMapData.setSlfrcdropdown(sfdcFldName);
				pMapData.setLookUpObject(lookupObjName);
				pMapData.setLookUpRelationShipName(lookupRltName);
				pMapData.setLookUpExternalId(null);
				pMapData.setLstExternalIds(lstExternalIds);
				pMapData.setLookUpFlag(lookUpFlag);
							
				preMapDataList.add(pMapData);
//				System.out.println("....Request values to mapping page....");
//				System.out.println("name:: "+name+" siebelFldName:: "+siebelFldName+" sfdcObjName:: "+sfdcObjName+" sfdcFldName:: "+sfdcFldName+" frKeyTblName:: "+frKeyTblName+" sblColName:: "+SblColName+" joinCondition:: "+joinCondition);
				i++;
				
			}			
			
		}
			
			modelChild.addAttribute("sfdcObj",sfdcObjectName);
			modelChild.addAttribute("sbllFlddNmList",sblFldList);
			modelChild.addAttribute("sblObjName",siebelTableNameValue);
			modelChild.addAttribute("hdrValues",hdrValues);
			modelChild.addAttribute("rowId", rowId);
			modelChild.addAttribute("mappingField",sfdcObjList);
			
			
			if(session.getAttribute("mappedSavedData")!=null){	
				System.out.println("Mapped Saved Data exists in session");				
									
				if(mapSavedData!=null && mapSavedData.size()>0) {
					
												
				for(int j=0; j < mapSavedData.size(); j++){
					
					for(int k=0; k < preMapDataList.size(); k++) {						
									
  						if(preMapDataList.get(k).getSblFieldNmdropdown().equals(mapSavedData.get(j).getSblFieldNmdropdown())){
							
							preMapDataList.remove(preMapDataList.get(k));
							
						}
						
						
					}
					
				}				
				   preMapDataList.addAll(mapSavedData);
				
				}
				
				modelChild.addAttribute("mappingData",preMapDataList);
				
			}
			else {
				preMapDataList.addAll(mappingDataSaved);
				modelChild.addAttribute("mappingData",preMapDataList);		
			}		
			
			modelChild.addAttribute("MappingId",rowId);

		return new ModelAndView("mapping", "data", data);
		
	}

}
