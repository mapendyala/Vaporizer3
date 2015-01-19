package com.force.example.fulfillment.order.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import canvas.CanvasContext;
import canvas.CanvasEnvironmentContext;
import canvas.CanvasRequest;
import canvas.SignedRequest;

import com.force.example.fulfillment.order.model.MainPage;
import com.force.example.fulfillment.order.service.InvoiceService;
import com.force.example.fulfillment.order.service.OrderService;
import com.force.partner.TargetPartner;
import com.force.utility.UtilityClass;
//import com.deloitte.bean.Team;

@Controller
@RequestMapping(value = "/canvasui")
@SessionAttributes("data")
public class CanvasUIController {

	private static final String SIGNED_REQUEST = "signedRequestJson";
	private CanvasContext cc = new CanvasContext();
	public List<MainPage> data = new ArrayList<MainPage>();

	@Autowired
	private OrderService orderService;

	@Autowired
	private InvoiceService invoiceService;

	

	@Autowired
	public CanvasUIController(Validator validator) {
		
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView postSignedRequest(Model model,
			@RequestParam(value = "signed_request") String signedRequest,
			HttpServletRequest request) {
		String srJson = SignedRequest.verifyAndDecodeAsJson(signedRequest,
				getConsumerSecret());
		System.out.println("srJson  " + srJson);
		JSONObject json = new JSONObject(srJson);
		CanvasRequest cr = SignedRequest.verifyAndDecode(signedRequest,
				getConsumerSecret());
		HttpSession session = request.getSession(true);
		model.addAttribute(SIGNED_REQUEST, srJson);
		cc = cr.getContext();
		CanvasEnvironmentContext ce = cc.getEnvironmentContext();
		Map<String, Object> params = ce.getParameters();
		System.out.println("params  " + params);
		JSONObject parameters = json.getJSONObject("context")
				.getJSONObject("environment").getJSONObject("parameters");
		String oAuthToken = json.getJSONObject("client")
				.getString("oauthToken");
		String instanceUrl = json.getJSONObject("client").getString(
				"instanceUrl");
		JSONObject authParams = new JSONObject();
		authParams.put("oAuthToken", oAuthToken);
		authParams.put("instanceUrl", instanceUrl);
		session.setAttribute("authParams", authParams);
		
		String projectId = parameters.getString("projectId");
		System.out.println("=================" + projectId);
		session.setAttribute("projectId", projectId);
		TargetPartner targetPart = new TargetPartner(session);

		data = targetPart.getSavedDBData(projectId, data);
		JSONObject middleWareConn= targetPart.getMiddleWareData(projectId);
		session.setAttribute("middleWareConn", middleWareConn);
		String projectName = targetPart.getProjectName(projectId);
		session.setAttribute("projectName", projectName);/* added by piyush */
		JSONObject connectionData = targetPart.getConnectionData(projectId);
		UtilityClass utilityClass = new UtilityClass();
		try {
			utilityClass.getConnection(connectionData);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			model.addAttribute(
					"error",
					"Connection to Siebel database unsuccessful. Either username/password/DatabaseUrl is incorrect.");
		}

		return new ModelAndView("vaporizer", "data", data);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getOrdersPage(Model model,
			@ModelAttribute("data") List<MainPage> data,
			HttpServletRequest request) {

		System.out.println("Data size:" + data.size());
		HttpSession session = request.getSession(true);
		TargetPartner targetPartner= new TargetPartner(session);
		data = targetPartner.getSavedDBData((String) session.getAttribute("projectId"), data);
		return new ModelAndView("vaporizer", "data", data);
	}

	private static final String getConsumerSecret() {
		String secret = System.getenv("OAUTH_CLIENT_SECRET");
		if (null == secret) {
			throw new IllegalStateException(
					"Client secret not found in environment.  You must define the OAUTH_CLIENT_SECRET environment variable.");
		}
		return secret;
	}
}
