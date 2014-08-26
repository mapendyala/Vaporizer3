package com.force.example.fulfillment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

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

import com.force.api.http.HttpRequest;
import com.force.example.fulfillment.order.controller.ThresholdController;
import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.model.MappingModel;
import com.force.partner.PartnerWSDL;
import com.force.partner.TargetPartner;
import com.force.utility.SiebelObjectBO;

import javax.servlet.http.HttpSession;


/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("data")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	public static String rowCount;
	public List<MainPage> data = new ArrayList<MainPage>();

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
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
		JSONObject connData=partnerWSDL.getTargetOrgDetails("a0PG000000B248h");
		String password=(String)connData.get("password");
		String token=(String)connData.get("token");
		String username=(String)connData.get("username");
		TargetPartner targetPartner= new TargetPartner(username, password+token);
		System.out.println(targetPartner.login());
		System.out.println("In home page");
		return new ModelAndView("vaporizer", "data", data);
	}


	
	
	 @RequestMapping(value="/getSFDCOBject", method=RequestMethod.GET,produces="text/plain")
	 @ResponseBody
	public String getSFDCObject(Locale locale,Model model,HttpServletRequest request,@RequestParam("siebelObject")String siebelObject)
		{
		 System.out.println("This methos is nt getting called"+siebelObject);
		    HttpSession session = request.getSession(true);
		    String projectId=(String) session.getAttribute("projectId");
	    	PartnerWSDL partnerWSDL= new PartnerWSDL();	    	

			String SFDCObjectName=partnerWSDL.getSFDCObjectName(projectId,siebelObject);	  
			System.out.println(SFDCObjectName);
			return SFDCObjectName;
	

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
		List<MappingModel> mappingData=partnerWSDL.getFieldMapping(tableName);
		model.addAttribute("mappingData",mappingData);

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

	@RequestMapping(value = "childSave", method = RequestMethod.POST)
	public ModelAndView save1(@ModelAttribute("data") List<MainPage> data, Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("inside demo");
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
		System.out.println("----------doneeeeeeee");
		//System.out.println(dataForm);
		// System.out.println(dataForm.getData());
		System.out.println("doneeeeeeee-------------");
		//System.out.println(data.get(0).getSiebelObject());
		return new ModelAndView("vaporizer" , "data", data);
	}

	/*@RequestMapping(value = "/Done", method = RequestMethod.GET)
	public String home3(Locale locale, Model model) {
		// TODO!!!
		logger.info("Welcome home! the client locale is "+ locale.toString());
		System.out.println("inside demo");
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

		return "Copy of vaporizer";
	}*/

	@RequestMapping(value="saveData",method = RequestMethod.POST)
	public ModelAndView getSiebelFielddata(HttpServletRequest request, Map<String, Object> model) {
		System.out.println("In main controller");
		//System.out.println(request.getParameter("rowNum"));
		data.clear();
		rowCount= request.getParameter("rowCount");
		String rowNo = request.getParameter("rowNo");
		String page = request.getParameter("pageName");
		/*		String threshold ="thresh"+rowNo;
		String primBase = "prim"+rowNo;
		String siebelTableName = "objectName"+rowNo;*/
		String thresholdValue=request.getParameter("thresh"+rowNo);
		String primBaseValue=request.getParameter("prim"+rowNo);
		String siebelTableNameValue=request.getParameter("objectName"+rowNo);
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

			mainPage.setMigrate(request.getParameter(migrate));
			mainPage.setSequence(request.getParameter(seq));
			mainPage.setSiebelObject(request.getParameter(objName));
			mainPage.setPrimBaseTable(request.getParameter(primTable));
			mainPage.setThreshold(request.getParameter(thresholdId));
			data.add(mainPage);
		}



		if(page.equals("child")){

			return new ModelAndView("ChildBase" , "data", data);}
		else{
			return new ModelAndView("mapping", "data", data);
		}
	}

}
