package com.force.example.fulfillment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.force.partner.PartnerWSDL;
import javax.servlet.http.HttpSession;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
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
		
		return "vaporizer";
	}
	
	
	 @RequestMapping(value="/getSFDCOBject", method=RequestMethod.GET,produces="text/plain")
	 @ResponseBody
		public String getSFDCObject(Locale locale,Model model,HttpServletRequest request,@RequestParam("siebelObject")String siebelObject)
		{
		    HttpSession session = request.getSession(true);
		    String projectId=(String) session.getAttribute("projectId");
	    	PartnerWSDL partnerWSDL= new PartnerWSDL();
	    	String SFDCObjectName=partnerWSDL.getSFDCObjectName(projectId,siebelObject);
	   	 	//model.addAttribute("SFDCMappedObject",SFDCObjectName);
	    	return SFDCObjectName;
		}
	    
	
	@RequestMapping(value = "/mapping", method = RequestMethod.GET)
	public String home1(Locale locale, Model model) {
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
		
		return "mapping";
	}
	
	@RequestMapping(value = "/ChildBase", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) {
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
		
		return "ChildBase";
	}
	
}
