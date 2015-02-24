package com.force.example.fulfillment;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.force.example.fulfillment.order.controller.SiebelObjectController;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.example.fulfillment.order.model.MappingSFDC;
import com.force.partner.PartnerWSDL;
import com.force.partner.TargetPartner;
import com.force.utility.ChildObjectBO;
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


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model, HttpServletRequest request) {
		// TODO!!!
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("here");
		HttpSession session = request.getSession(true);
		
		JSONObject authParams = getOAuthToken();
		
		session.setAttribute("authParams", authParams);
		//String projectId="a0PG000000B2wmDMAR";
		String projectId="a0PG000000B5e3fMAB";
		//String projectId="a0PG000000B3OFn";
		session.setAttribute("projectId", projectId);
		TargetPartner tp= new TargetPartner(session);
		data = tp.getSavedDBData(projectId, data);
		JSONObject middleWareConn= tp.getMiddleWareData(projectId);
		System.out.println("middleWareConn "+middleWareConn);
		session.setAttribute("middleWareConn", middleWareConn);
		System.out.println("data "+data);
		System.out.println("In home page");
		return new ModelAndView("vaporizer", "data", data);
	}

	/*Added by Subhojitcg*/





	@RequestMapping(value="/initiateDataloader", method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String initiateDataLoader(Locale locale,Model model,HttpServletRequest request,@RequestParam("datafileUrl")String datafileUrl,@RequestParam("objectName")String objectName) throws IOException, AsyncApiException, ConnectionException
	{
		TargetPartner tpWSDL= new TargetPartner(request.getSession()); 	  
		
		HttpSession session = request.getSession(true);
		JSONObject connData=tpWSDL.getTargetOrgDetails((String) session.getAttribute("projectId"));
		String password=(String)connData.get("password");
		String token=(String)connData.get("token");
		String username=(String)connData.get("username");
		String[] strList=datafileUrl.split("-");
		datafileUrl="https://"+strList[0]+".salesforce.com/"+strList[1];
		com.force.example.fulfillment.DataLoaderController dt=new com.force.example.fulfillment.DataLoaderController();
		 
		if(objectName==null || objectName=="")
		{
			objectName="Account";
		}
		return dt.dataUploadController(datafileUrl,"subhchakraborty@deloitte.com.vaporizer","Sep@2013",objectName);
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

		List<SfdcObjectBO> objList=new ArrayList<SfdcObjectBO>(); 
		HttpSession session = request.getSession(true);
		String userValue = request.getParameter("objectName");
		System.out.println("uservalues in bean is"+userValue);
		TargetPartner targetPartner= new TargetPartner(request.getSession());	
		return targetPartner.getSFDCOjectListforPopup(userValue);

	} 
	@RequestMapping(value = "/mapping", method = RequestMethod.GET)
	public String home1(Locale locale, Model model,HttpServletRequest request) {
		// TODO!!!
		logger.info("Welcome home! the client locale is "+ locale.toString());
		String threshold=request.getParameter("threshold");
		String primBase=request.getParameter("primBase");
		String siebelTableName=request.getParameter("siebelTableName");
		System.out.println("---------------"+threshold+" "+primBase);
		//ThresholdController tc= new ThresholdController();
		//List<SiebelObjectBO> listSiebelObject = tc.fetchSiebelObjects(request);
		TargetPartner targetPartner= new TargetPartner(request.getSession());
		
		HttpSession session=request.getSession();

		String subprojectId=targetPartner.getsubprojects(siebelTableName);
		JSONObject tableName=targetPartner.getRelatedSiebelTable(subprojectId);
		//List<MappingModel> mappingData=partnerWSDL.getFieldMapping(tableName);
		//model.addAttribute("mappingData",mappingData);

		return "mapping";
	}

	@RequestMapping(value = "/ChildBase", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) {
		// TODO!!!
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("In Child Base");
		
		return "ChildBase";
	}

	@RequestMapping(value = "mappingSave", method = RequestMethod.POST)
	//public ModelAndView save1(@ModelAttribute("data") List<MainPage> data, Locale locale, Model model) {

	public ModelAndView mappingSave(HttpServletRequest request, Map<String, Object> model) throws ConnectionException {	
		HttpSession session = request.getSession(true);
		System.out.println("------mappingSave entry-----");
		TargetPartner targetPartner= new TargetPartner(request.getSession()); 
		PartnerWSDL partnerWSDL = new PartnerWSDL(request.getSession());
		mappingData.clear();
		rowCount= request.getParameter("rowCount");
		for(int i=0;i<Integer.parseInt(rowCount);i++){
			String siebelCheckFlag="select"+i;
			MappingModel mappingModel = new MappingModel();
			if(request.getParameter("sfdcId"+i)!=null){
				mappingModel.setId(request.getParameter("sfdcId"+i));}
			else
				mappingModel.setId("");
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
		if(partnerWSDL.login()){
			partnerWSDL.saveMappingSingleValuedDataIntoDB(mappingData, request, (String)session.getAttribute("projectId"));
		}
		return new ModelAndView("vaporizer" , "data", data);

	}
	@RequestMapping(value = "childSave", method = RequestMethod.POST)
	public ModelAndView save1(@ModelAttribute("data") List<MainPage> data, Locale locale, Model model,HttpServletRequest request) throws ConnectionException{


		logger.info("Welcome home! the client locale is "+ locale.toString());


		System.out.println("inside demo");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		TargetPartner tg= new TargetPartner(request.getSession());
		//partnerWSDL.login();
		String formattedDate = dateFormat.format(date);

		HttpSession session=request.getSession();
		System.out.println("----------doneeeeeeee");
		//System.out.println(dataForm);
		// System.out.println(dataForm.getData());
		System.out.println("doneeeeeeee-------------");
		//System.out.println(data.get(0).getSiebelObject());
		
		data = tg.getSavedDBData((String)session.getAttribute("projectId"), data);


		List<ChildObjectBO> childDataList = new ArrayList<ChildObjectBO>();
		String totalRowCount= request.getParameter("rowCount");
		System.out.println("total row count is"+totalRowCount);

		/*	
		for(int i=1;i<=Integer.parseInt(totalRowCount);i++){
			//mainPage[i] =  new MainPage();
			System.out.println("my i value is "+i);
			ChildObjectBO childObj = new ChildObjectBO();

			String sequenceNumber = "sequenceNum"+i;
			String siebelBaseObjName = "baseObjName"+i;
			String siebelChildObjName = "childObjName"+i;
			String siebelJoinCondition= "joinCondition"+i;

			childObj.setSeqNum(Integer.parseInt(request.getParameter(sequenceNumber)));
			childObj.setBaseObjName(request.getParameter(siebelBaseObjName));
			childObj.setChildObjName(request.getParameter(siebelChildObjName));
			childObj.setJoinCondition(request.getParameter(siebelJoinCondition));
			System.out.println("checkbox value is"+request.getParameter("checkFlag"+i));
			System.out.println("values are"+ childObj.getSeqNum() + "and" + childObj.getBaseObjName());
			System.out.println("rest values are"+ childObj.getChildObjName() + "and" + childObj.getJoinCondition());
			childDataList.add(childObj);
		}*/

		for(int i=1;i<=Integer.parseInt(totalRowCount);i++){
			//mainPage[i] =  new MainPage();
			System.out.println("my i value is "+i);
			ChildObjectBO childObj = new ChildObjectBO();

			String sequenceNumber = "sequenceNum"+i;
			String siebelBaseObjName = "baseObjName"+i;
			String siebelChildObjName = "childObjName"+i;
			String siebelJoinCondition= "joinCondition"+i;
			String siebelCheckFlag="checkFlag"+i;
			String childSfdcId="sfdcId"+i;
			
			
			
			childObj.setSeqNum(Integer.parseInt(request.getParameter(sequenceNumber)));
			childObj.setBaseObjName(request.getParameter(siebelBaseObjName));
			childObj.setChildObjName(request.getParameter(siebelChildObjName));
			childObj.setJoinCondition(request.getParameter(siebelJoinCondition));
			System.out.println("sfdc id is"+request.getParameter(childSfdcId));
			childObj.setChildSfdcId(request.getParameter(childSfdcId));
			String checkedFlagStr=request.getParameter(siebelCheckFlag);

			if(checkedFlagStr != "" && checkedFlagStr !=null)
			{
				if(checkedFlagStr.equals("on"))
				{
					System.out.println("in true block"+i);
					childObj.setCheckFlag(true);
				}
			}
			else
			{
				System.out.println("in false block");
				childObj.setCheckFlag(false);
			}


			System.out.println("checkbox value is"+request.getParameter(siebelCheckFlag));
			/*System.out.println("values are"+ childObj.getSeqNum() + "and" + childObj.getBaseObjName());
			System.out.println("rest values are"+ childObj.getChildObjName() + "and" + childObj.getJoinCondition());*/
			childDataList.add(childObj);
		}
		//System.out.println("child data save list is"+childDataList);
		tg.saveChildDataDB(childDataList,request);

		return new ModelAndView("vaporizer" , "data", data);

	}
	
/*	
	@RequestMapping(value="/getextractData", method = RequestMethod.GET)
	@ResponseBody public String createExtractQuery(HttpServletRequest request){
		System.out.println("In Home controller get extract data method");
		HttpSession session = request.getSession(true);
		String projId  = (String)session.getAttribute("projectId");
		TargetPartner tg= new TargetPartner(request.getSession());
		
		String sfdcId=request.getParameter("sfdcId");
		String siebelTableNameValue = request.getParameter("siebelObjName");
		String subprojectId=tg.getsubprojects(siebelTableNameValue);
		String mappingUrl="";
		if(sfdcId  != null){			
			mappingUrl=tg.getextractionData(projId, sfdcId, subprojectId);
		}else{
			System.out.println("Child Base and Mapping pages have not been selected");
			 
		}
		return mappingUrl;
	}*/
	@RequestMapping(value="/getextractData", method = RequestMethod.GET)
	@ResponseBody public void createExtractQuery(HttpServletRequest request, HttpServletResponse response){
		System.out.println("In Home controller get extract data method");
		HttpSession session = request.getSession(true);
		String projId  = (String)session.getAttribute("projectId");
		TargetPartner tg= new TargetPartner(request.getSession());
		
		String sfdcId=request.getParameter("sfdcId");
		String siebelTableNameValue = request.getParameter("siebelObjName");
		String subprojectId=tg.getsubprojects(siebelTableNameValue);
		String mappingUrl="";
		File mappingFIle = null;
		if(sfdcId  != null){			
			mappingFIle=tg.getextractionData(projId, sfdcId, subprojectId);
		}else{
			System.out.println("Child Base and Mapping pages have not been selected");
			 
		}
		response.setContentType("application/csv");
		response.setHeader("Content-Disposition", "attachment; filename="+mappingFIle);
		//return mapingFIle;
	}


	@RequestMapping(value="/getFieldColumnVal", method = RequestMethod.GET)
	@ResponseBody public List<String> retrieveColumnAndFieldVal(HttpServletRequest request, @RequestParam("sblFldValSlctd")String sblFldValSlctd, @RequestParam("rowNum") String rowNum) throws ConnectionException{
		System.out.println("In retrieveColumnAndFieldVal method");
		System.out.println("Selected Siebel Value : "+ sblFldValSlctd);
		HttpSession session = request.getSession(true);
		SiebelObjectController siObj=new SiebelObjectController();
		// Toget the foreign key, column name thru ajax call.
		List<String> clmNmLst = siObj.fetchColumndAndFrgnKeyName(request, (String)session.getValue("siebelTableNameValue"), sblFldValSlctd, rowNum);

		return clmNmLst;
	}
	
	@RequestMapping(value="/getLookUpInfo", method = RequestMethod.GET)
	@ResponseBody public List<Object> retrieveLookUpFieldInfo(HttpServletRequest request, @RequestParam("slctdSlsFrcFldOption")String slsfrcFldValSlctd, @RequestParam("rowNum") String rowNum) throws ConnectionException{
		System.out.println("In retrieveColumnAndFieldVal method");
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
				PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession());
				prtnrWSDL1.login();
				lookUpFlds.add(mpngDtl.getRelationshipName());
				lookUpObj = mpngDtl.getReferenceTo()[0];
				lookUpFlds.add(lookUpObj);
				
				externlFldLst = prtnrWSDL1.getExternalIdList((String)lookUpObj);
				lookUpFlds.add(externlFldLst);
			}
		}
		
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

	
	@RequestMapping(value = "saveData",method = RequestMethod.POST)
	public ModelAndView getSiebelFielddata(HttpServletRequest request, Map<String, Object> model, Model modelChild) throws ConnectionException {
		HttpSession session = request.getSession(true);
		System.out.println("In main controller");
		TargetPartner tg= new TargetPartner(request.getSession()); 
		PartnerWSDL prtnrWSDL = new PartnerWSDL(request.getSession());
		prtnrWSDL.login();
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
		session.putValue("siebelTableNameValue", siebelTableNameValue);
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

		tg.saveDataDB(data, request, (String)session.getAttribute("projectId"));


		if(page.equals("child")){
			SiebelObjectController siObj=new SiebelObjectController();
			
			List<ChildObjectBO> myChildList=siObj.fetchChildObjects(request, primBaseValue);
			/*if(myChildList!=null)
			{
				System.out.println("mychild list is"+myChildList.size());
			}
			modelChild.addAttribute("myChildList",myChildList);*/
			List<ChildObjectBO> childSavedList=new ArrayList<ChildObjectBO>();
			childSavedList=	tg.getSavedChildDBData((String)session.getAttribute("projectId"), primBaseValue);
			
			if(childSavedList!=null && childSavedList.size()!=0)
			{
				System.out.println("CHild Saved  list size is"+childSavedList.size());
				modelChild.addAttribute("myChildList",childSavedList);
			}
			else if(myChildList!=null && myChildList.size()!=0)
			{
				System.out.println("Siebel BLOCK child list is"+myChildList.size());
				modelChild.addAttribute("myChildList",myChildList);
				
			}
	
				return new ModelAndView("ChildBase" , "data", data);
		}

		// Displays Single Value Field Mapping Screen
		else if(page.equals("map")){
			//Else go to mapping page
			logger.info("Welcome to single valued mapping ");
            System.out.println("---------------"+thresholdValue+" "+primBaseValue);
			String subprojectId=tg.getsubprojects(siebelTableNameValue);
			if(null != subprojectId){
				
				PartnerWSDL prtnrWSDL1 = new PartnerWSDL(request.getSession());
				prtnrWSDL1.login();
				
				JSONObject tableName=tg.getRelatedSiebelTable(subprojectId);//gives siebel and sfdc table name
				String id=tg.getMappingId((String)session.getAttribute("projectId"),mappingData,tableName);

				List<MappingModel> mappingDataSaved=prtnrWSDL.getSavedMappingSingleValueDBData((String)session.getAttribute("projectId"), mappingData, tableName, siebelTableNameValue);
				List<String> childTables=tg.getSavedChild((String)session.getAttribute("projectId"),tableName);
				
				SiebelObjectController siObj=new SiebelObjectController();
				List<Object> myChildList=siObj.fetchColumns(request, primBaseValue,thresholdValue,childTables);
				List<MappingModel> mappingData=tg.getFieldMapping(tableName,myChildList);
				//Gets the list of SFDC Field names
				List<MappingSFDC> sfdcObjList = prtnrWSDL1.getSFDCFieldList((String)sfdcObjectName);
				// To get the list of siebel field names for a siebel entity.
				List<String> sblFldList = new ArrayList<String>();
				sblFldList = siObj.fetchFieldNameList(request, siebelTableNameValue);
				
				List<String> hdrValues = new ArrayList<String>();
				//Siebel Entity
				hdrValues.add(siebelTableNameValue);
				//Siebel Base Table
				hdrValues.add(mappingData.get(0).getSiebleBaseTable());
				//SFDC Entity
				hdrValues.add(mappingData.get(0).getSfdcObjectName());
				
				modelChild.addAttribute("sfdcObj",mappingData.get(0).getSfdcObjectName());
				modelChild.addAttribute("mappingField",sfdcObjList);
				modelChild.addAttribute("sbllFlddNmList",sblFldList);
				modelChild.addAttribute("sblObjName",siebelTableNameValue);
				modelChild.addAttribute("hdrValues",hdrValues);
			/*	if(mappingDataSaved.isEmpty()){
					modelChild.addAttribute("mappingData",mappingData);}
				else*/
					modelChild.addAttribute("mappingData",mappingDataSaved);
				modelChild.addAttribute("MappingId",id);

			}
			return new ModelAndView("mapping", "data", data);
		} 
		// Displays Multi Value Field Mapping Screen
		else if(page.equals("multiMap")){

			//Else go to mapping page
			logger.info("Entering Multimapping ");
            System.out.println("---------------"+thresholdValue+" "+primBaseValue);
			//ThresholdController tc= new ThresholdController();
			//List<SiebelObjectBO> listSiebelObject = tc.fetchSiebelObjects(request);
			String subprojectId=tg.getsubprojects(siebelTableNameValue);
			if(null != subprojectId){
				JSONObject tableName=tg.getRelatedSiebelTable(subprojectId);
				String id=tg.getMappingId((String)session.getAttribute("projectId"),mappingData,tableName);

				List<MappingModel> mappingData1=tg.getSavedMappingDBData((String)session.getAttribute("projectId"),mappingData,tableName);
				List<String>childTables=tg.getSavedChild((String)session.getAttribute("projectId"),tableName);

				SiebelObjectController siObj=new SiebelObjectController();
				List<Object> myChildList=siObj.fetchColumns(request, primBaseValue,thresholdValue,childTables);


			
				//List<String>childTables=partnerWSDL.getSavedChild((String)session.getAttribute("projectId"),tableName);
				//System.out.println(mappingData1.get(0));

				List<MappingModel> mappingData=tg.getFieldMapping(tableName,myChildList);
				ArrayList<String> field= new ArrayList<String>();
						field=tg.getFieldTarget(tableName);
				/*for(int count=0;count<mappingData.size();count++){
					field.add(mappingData.get(count).getSfdcFieldTable());

				}*/
				modelChild.addAttribute("sfdcObj",mappingData.get(0).getSfdcObjectName());
				modelChild.addAttribute("mappingField",field);
				if(mappingData1.isEmpty()){
					modelChild.addAttribute("mappingData",mappingData);}
				else
					modelChild.addAttribute("mappingData",mappingData1);
				modelChild.addAttribute("MappingId",id);

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
                return authParams;
	}

}
