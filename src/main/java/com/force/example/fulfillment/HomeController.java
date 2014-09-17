package com.force.example.fulfillment;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.force.api.http.HttpRequest;
import com.force.example.fulfillment.order.controller.SiebelObjectController;
import com.force.example.fulfillment.order.controller.ThresholdController;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.partner.PartnerWSDL;
import com.force.partner.TargetPartner;
import com.force.utility.ChildObjectBO;
import com.force.utility.SfdcObjectBO;
import com.force.utility.SiebelObjectBO;
import com.force.utility.UtilityClass;
import com.sforce.async.AsyncApiException;
import com.sforce.ws.ConnectionException;

import javax.servlet.http.HttpSession;


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

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		ArrayList<String> siebelList=new ArrayList<String>();
		siebelList.add("Account");
		siebelList.add("Contact");
		siebelList.add("Order");
		siebelList.add("Vaporizer");
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("siebelList",siebelList);

		PartnerWSDL partnerWSDL= new PartnerWSDL(); 	  
		//  System.out.println(projectName); 	  
		partnerWSDL.login();
		HttpSession session = request.getSession(true);
		String projectId="a0PG000000B23yKMAR";
		session.setAttribute("projectId", projectId);
		partnerWSDL.login();
		JSONObject connData=partnerWSDL.getTargetOrgDetails("a0PG000000B248h");
		String password=(String)connData.get("password");
		String token=(String)connData.get("token");
		String username=(String)connData.get("username");
		TargetPartner targetPartner= new TargetPartner(username, password+token);
		data = partnerWSDL.getSavedDBData(projectId, data);
		System.out.println(targetPartner.login());
		System.out.println("In home page");
		return new ModelAndView("vaporizer", "data", data);
	}

	/*Added by Subhojitcg*/



	@RequestMapping(value="/initiateDataloader", method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String initiateDataLoader(Locale locale,Model model,HttpServletRequest request,@RequestParam("datafileUrl")String datafileUrl) throws IOException, AsyncApiException, ConnectionException
	{


		PartnerWSDL partnerWSDL= new PartnerWSDL(); 	  


		partnerWSDL.login();
		HttpSession session = request.getSession(true);
		JSONObject connData=partnerWSDL.getTargetOrgDetails((String) session.getAttribute("projectId"));
		String password=(String)connData.get("password");
		String token=(String)connData.get("token");
		String username=(String)connData.get("username");
		String[] strList=datafileUrl.split("-");
		datafileUrl="https://"+strList[0]+".salesforce.com/"+strList[1];
		com.force.example.fulfillment.DataLoaderController dt=new com.force.example.fulfillment.DataLoaderController();
		String objectName="Account";


		return dt.dataUploadController(datafileUrl,username,password+token,objectName);


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
		PartnerWSDL partnerWSDL= new PartnerWSDL();	    	

		String SFDCObjectName=partnerWSDL.getSFDCObjectName(projectId,siebelObject);	  
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
		PartnerWSDL partnerWSDL= new PartnerWSDL();	
		return partnerWSDL.getSFDCOjectListforPopup(userValue);

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
		PartnerWSDL partnerWSDL= new PartnerWSDL();
		partnerWSDL.login();
		HttpSession session=request.getSession();

		String subprojectId=partnerWSDL.getsubprojects(siebelTableName);
		JSONObject tableName=partnerWSDL.getRelatedSiebelTable(subprojectId);
		//List<MappingModel> mappingData=partnerWSDL.getFieldMapping(tableName);
		//model.addAttribute("mappingData",mappingData);

		return "mapping";
	}

	@RequestMapping(value = "/ChildBase", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) {
		// TODO!!!
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("In Child Base");
		//MainController mc = new MainController();
		//mc.setRowLength(request);
		//mc.getSiebelFielddata(request, model, data);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		ArrayList<String> siebelList=new ArrayList<String>();
		siebelList.add("Account");
		siebelList.add("Contact");
		siebelList.add("Order");
		siebelList.add("Vaporizer");
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("siebelList",siebelList);

		return "ChildBase";
	}

	@RequestMapping(value = "mappingSave", method = RequestMethod.POST)
	//public ModelAndView save1(@ModelAttribute("data") List<MainPage> data, Locale locale, Model model) {

		public ModelAndView mappingSave(HttpServletRequest request, Map<String, Object> model) throws ConnectionException {	
		HttpSession session = request.getSession(true);
		System.out.println("In main controller");
		PartnerWSDL partnerWSDL= new PartnerWSDL(); 

	//	logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("inside demo");
		System.out.println("In main controller");
		//System.out.println(request.getParameter("rowNum"));
		mappingData.clear();
		rowCount= request.getParameter("rowCount");
		String rowNo = request.getParameter("rowNum");
		String page = request.getParameter("pageName");
		String siebleBaseTable=request.getParameter("siebleBaseTable"+"1");
		String sfdcObjectName=request.getParameter("sfdcObjectName"+rowNo);
		String drop=request.getParameter("dropdown"+rowNo);
		
		for(int i=1;i<=Integer.parseInt(rowCount);i++){
		String a=Integer.toString(i);
			MappingModel mappingModel = new MappingModel();
		String siebleBaseTable1 = request.getParameter("siebleBaseTable"+i);
		 String siebleBaseTableColumn = "siebleBaseTableColumn"+a;
		String sfdcObjectName1 = "sfdcObjectName"+a;
			 String sfdcFieldTable = "sfdcFieldTable"+a;
			 
		 String mappingSeq = "mappingSeq"+a;
		 mappingModel.setSfdcFieldTable(request.getParameter("dropdown"+i));
		 mappingModel.setSfdcObjectName(request.getParameter("sfdcObjectName"+i));
		 mappingModel.setSiebleBaseTable(request.getParameter("siebleBaseTable"+i));
		 mappingModel.setSiebleBaseTableColumn(request.getParameter("siebleBaseTableColumn"+i));
		 mappingModel.setForeignFieldMapping(request.getParameter("foreignFieldMapping"+i));
	     System.out.println("Inside Drop value is"+request.getParameter("dropdown"+i));
	     if(request.getParameter("mappingSfdcId")==null){
	    	 mappingModel.setMappingSfdcId("");
			}else{
				 mappingModel.setMappingSfdcId(request.getParameter("mappingSfdcId"));
			}
		/*	String migrate = "migrate"+i;
			String seq = "seq"+i;
			String objName = "objectName"+i;
			String primTable = "prim"+i;
			String thresholdId = "thresh"+i;
			String SFDCObjName = "SFDCObjName"+i;

			mainPage.setMigrate(request.getParameter(migrate));
			mainPage.setSequence(request.getParameter(seq));
			mainPage.setSiebelObject(request.getParameter(objName));
			mainPage.setPrimBaseTable(request.getParameter(primTable));
			mainPage.setThreshold(request.getParameter(thresholdId));*/
		 mappingData.add(mappingModel);}
		partnerWSDL.saveMappingDataIntoDB(mappingData, request, (String)session.getAttribute("projectId"));
		Date date = new Date();



		//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		//String formattedDate = dateFormat.format(date);
		ArrayList<String> siebelList=new ArrayList<String>();
		siebelList.add("Account");
		siebelList.add("Contact");
		siebelList.add("Order");
		siebelList.add("Vaporizer");


		//model.addAttribute("serverTime", formattedDate );
	//	model.addAttribute("siebelList",siebelList);
		System.out.println("----------doneeeeeeee");
		//System.out.println(dataForm);
		// System.out.println(dataForm.getData());
		System.out.println("doneeeeeeee-------------");
		//System.out.println(data.get(0).getSiebelObject());


























		return new ModelAndView("vaporizer" , "data", data);
		
	}
	@RequestMapping(value = "childSave", method = RequestMethod.POST)
	public ModelAndView save1(@ModelAttribute("data") List<MainPage> data, Locale locale, Model model,HttpServletRequest request) throws ConnectionException{
		logger.info("Welcome home! the client locale is "+ locale.toString());


		System.out.println("inside demo");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		PartnerWSDL partnerWSDL= new PartnerWSDL();
		String formattedDate = dateFormat.format(date);



		ArrayList<String> siebelList=new ArrayList<String>();
		siebelList.add("Account");
		siebelList.add("Contact");
		siebelList.add("Order");
		siebelList.add("Vaporizer");
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("siebelList",siebelList);


		System.out.println("----------doneeeeeeee");
		//System.out.println(dataForm);
		// System.out.println(dataForm.getData());
		System.out.println("doneeeeeeee-------------");
		//System.out.println(data.get(0).getSiebelObject());

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
			childObj.setSeqNum(Integer.parseInt(request.getParameter(sequenceNumber)));
			childObj.setBaseObjName(request.getParameter(siebelBaseObjName));
			childObj.setChildObjName(request.getParameter(siebelChildObjName));
			childObj.setJoinCondition(request.getParameter(siebelJoinCondition));
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
			System.out.println("values are"+ childObj.getSeqNum() + "and" + childObj.getBaseObjName());
			System.out.println("rest values are"+ childObj.getChildObjName() + "and" + childObj.getJoinCondition());
			childDataList.add(childObj);
		}
		System.out.println("child data save list is"+childDataList);
		partnerWSDL.saveChildDataDB(childDataList,request);

		return new ModelAndView("vaporizer" , "data", data);

	}




















	@RequestMapping(value="/getextractData", method = RequestMethod.GET)
	public void createExtractQuery(HttpServletRequest request){
		System.out.println("In controller");
		HttpSession session = request.getSession(true);
		PartnerWSDL partnerWSDL= new PartnerWSDL();
		partnerWSDL.login();
		partnerWSDL.getMappingRecords((String)session.getAttribute("projectId"));
	}




	@RequestMapping(value="saveData",method = RequestMethod.POST)
	public ModelAndView getSiebelFielddata(HttpServletRequest request, Map<String, Object> model, Model modelChild) throws ConnectionException {
		HttpSession session = request.getSession(true);
		System.out.println("In main controller");
		PartnerWSDL partnerWSDL= new PartnerWSDL(); 


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
		System.out.println("in MY getSiebl Siebel Table"+siebelTableNameValue);
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
				mainPage.setMigrate("false");
			}else{
				mainPage.setMigrate("true");
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

		partnerWSDL.saveDataDB(data, request, (String)session.getAttribute("projectId"));


		if(page.equals("child")){
			System.out.println("in MY child bliok");
			SiebelObjectController siObj=new SiebelObjectController();
			//ArrayList<String> myList=new ArrayList<String>();
			List<ChildObjectBO> myChildList=siObj.fetchChildObjects(request, primBaseValue);
			if(myChildList!=null)
			{
				System.out.println("mychild list is"+myChildList.size());
			}
			modelChild.addAttribute("myChildList",myChildList);


			return new ModelAndView("ChildBase" , "data", data);
		}

		else{
			//Else go to mapping page
logger.info("Welcome to mapping ");
			
			System.out.println("---------------"+thresholdValue+" "+primBaseValue);
			//ThresholdController tc= new ThresholdController();
			//List<SiebelObjectBO> listSiebelObject = tc.fetchSiebelObjects(request);
			String subprojectId=partnerWSDL.getsubprojects(siebelTableNameValue);
			if(null != subprojectId){
			JSONObject tableName=partnerWSDL.getRelatedSiebelTable(subprojectId);
			String id=partnerWSDL.getMappingId((String)session.getAttribute("projectId"),mappingData,tableName);
			List<MappingModel> mappingData1=partnerWSDL.getSavedMappingDBData((String)session.getAttribute("projectId"),mappingData,tableName);
			
			SiebelObjectController siObj=new SiebelObjectController();
			List<Object> myChildList=siObj.fetchColumns(request, primBaseValue,thresholdValue);
			partnerWSDL.login();
			
			//System.out.println(mappingData1.get(0));
			List<MappingModel> mappingData=partnerWSDL.getFieldMapping(tableName,myChildList);
			ArrayList<String> field=new ArrayList<String>();
			for(int count=0;count<mappingData.size();count++){
				field.add(mappingData.get(count).getSfdcFieldTable());
			}
			modelChild.addAttribute("sfdcObj",mappingData.get(0).getSfdcObjectName());
			modelChild.addAttribute("mappingField",field);
			modelChild.addAttribute("mappingData",mappingData);
			modelChild.addAttribute("MappingId",id);
			}
			return new ModelAndView("mapping", "data", data);
		}
	}

}
