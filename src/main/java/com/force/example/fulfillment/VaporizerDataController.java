package com.force.example.fulfillment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.force.example.fulfillment.order.model.VaporizerData;
import com.force.example.fulfillment.order.model.VaporizerDataForm;

@Controller
public class VaporizerDataController {

	private static List<VaporizerData> data = new ArrayList<VaporizerData>();
	 
    static {
    	data.add(new VaporizerData(1, "Obama", "barack.o@whitehouse.com"));
    	data.add(new VaporizerData(2, "Bush", "george.b@whitehouse.com"));
    	data.add(new VaporizerData(3, "Clinton", "bill.c@whitehouse.com"));
    	data.add(new VaporizerData(4, "Reagan", "ronald.r@whitehouse.com"));
    }
    
    
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get() {
         
        VaporizerDataForm dataForm = new VaporizerDataForm();
        dataForm.setData(data);
         
        return new ModelAndView("vaporizer1" , "dataForm", dataForm);
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("dataForm") VaporizerDataForm dataForm) {
    	System.out.println("----------save methofff");
        System.out.println(dataForm);
        System.out.println(dataForm.getData());
        System.out.println("save methofff-----------");       
        return new ModelAndView("ChildBase", "dataForm", dataForm);
    }
     
    @RequestMapping(value = "/save1", method = RequestMethod.POST)
    public ModelAndView save1(@ModelAttribute("dataForm") VaporizerDataForm dataForm) {
    	System.out.println("----------doneeeeeeee");
        System.out.println(dataForm);
        System.out.println(dataForm.getData());
        System.out.println("doneeeeeeee-------------");
        return new ModelAndView("vaporizer1" , "dataForm", dataForm);
    }
     
}
