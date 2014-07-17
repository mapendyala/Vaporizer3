package com.force.example.fulfillment.order.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import canvas.CanvasContext;
import canvas.CanvasEnvironmentContext;
import canvas.CanvasRequest;
import canvas.SignedRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.force.example.fulfillment.order.model.Order;
import com.force.example.fulfillment.order.service.OrderService;

import com.force.api.ApiException;
import com.force.example.fulfillment.order.model.Invoice;
import com.force.example.fulfillment.order.service.InvoiceService;
import com.force.partner.PartnerWSDL;
import com.force.utility.UtilityClass;
import com.sforce.ws.ConnectionException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/canvasui")
public class CanvasUIController {

    private static final String SIGNED_REQUEST = "signedRequestJson";
    private CanvasContext cc = new CanvasContext();

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    private Validator validator;

    @Autowired
    public CanvasUIController(Validator validator) {
        this.validator = validator;
    }

    @RequestMapping(method= RequestMethod.POST)
    public String postSignedRequest(Model model,@RequestParam(value="signed_request")String signedRequest, HttpServletRequest request){
        String srJson = SignedRequest.verifyAndDecodeAsJson(signedRequest, getConsumerSecret());
        JSONObject json= new JSONObject(srJson);
        CanvasRequest cr = SignedRequest.verifyAndDecode(signedRequest, getConsumerSecret());
        HttpSession session = request.getSession(true);
        model.addAttribute(SIGNED_REQUEST, srJson);
        cc = cr.getContext();
        CanvasEnvironmentContext ce = cc.getEnvironmentContext();
        Map<String, Object> params = ce.getParameters();
        JSONObject parameters=json.getJSONObject("context").getJSONObject("environment").getJSONObject("parameters");
        String projectId=parameters.getString("projectId");
    	
 	   session.setAttribute("projectId", projectId);
 	   PartnerWSDL partnerWSDL= new PartnerWSDL();
 	   partnerWSDL.login();
 	   JSONObject connectionData=partnerWSDL.getConnectionData(projectId);
 	   UtilityClass utilityClass= new UtilityClass();
 	   try {
 		   utilityClass.getConnection(connectionData);
 		   model.addAttribute("databaseUrl","rachita"+connectionData.getString("databaseUrl"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			model.addAttribute("error", "Connection to Siebel database unsuccessful. Either username/password/DatabaseUrl is incorrect.");
		}
 	  return "vaporizer";
    }

    @RequestMapping(method=RequestMethod.GET)
    public String getOrdersPage(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("orders", orderService.listOrders());

        return "orders";
    }

    private static final String getConsumerSecret(){
        String secret = System.getenv("OAUTH_CLIENT_SECRET");
        if (null == secret){
            throw new IllegalStateException("Client secret not found in environment.  You must define the OAUTH_CLIENT_SECRET environment variable.");
        }
        return secret;
    }
}