<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="<c:url value="/resources/css/vaporizer.css" />" type="text/css"
	media="screen, projection">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScriptt --->
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	
	<!-- Madhuri code -->
	<link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript" 	src="<c:url value="/resources/json.min.js" /> "></script>
	<script type="text/javascript" 	src="<c:url value="/resources/scripts/jQuery.download.js" /> "></script>
	 

	
<script type="text/javascript">

  function callInformaticaMapping(obj)
	{
		
		var jobURL="";
		if(obj==="Account")
		// jobURL="https://talendjobsaccount.herokuapp.com/services/SIebel_to_SFDC_Account?method=runJob";
		   jobURL="https://app2.informaticacloud.com/saas/app/installedBundle.do?actionMethod=view&id=ZZZZZZ160000000000RX";
		if(obj==="Product")
		 	jobURL="https://app2.informaticacloud.com/saas/app/installedBundle.do?actionMethod=view&id=ZZZZZZ160000000000RZ";
		if(obj==="Opportunity")
			 jobURL="https://app2.informaticacloud.com/saas/app/installedBundle.do?actionMethod=view&id=ZZZZZZ160000000000S0";
		if(obj==="Asset")
			 jobURL="https://app2.informaticacloud.com/saas/app/installedBundle.do?actionMethod=view&id=ZZZZZZ160000000000S2";	
	    if(obj==="Quote")
		     jobURL="https://app2.informaticacloud.com/saas/app/installedBundle.do?actionMethod=view&id=ZZZZZZ160000000000S1";
		if(obj==="Activity")
			     jobURL="https://app2.informaticacloud.com/saas/app/installedBundle.do?actionMethod=view&id=ZZZZZZ160000000000S3";
			     
		window.open(jobURL,"Informatica", "scrollbars=1,resizable=1,width=1000,height=800,left=0,top=0");
		}
	</script>

<title>Vaporizer</title>
</head>
<body>
<div id="statusBlock">

</div>

	
		
		<div class="mainContent">
			<div class="credential_container">
				<div>
					<div class="table_header_details">Data Migration Details</div>					
				</div>
				
			</div>
	<!-- Madhuri code start -->	
		
				<table id = "masterTable" style="width:1500px;">
				
			<thead>
				  <tr align="center">

				  
				    
				    <th class="table_header_details" style="float: center;">Sequence</th>
				    <th class="table_header_details" style="float: center;">Siebel Object </th>
				    <th class="table_header_details" style="float: center;">SFDC Object</th>				    
				    <th class="table_header_details" style="float: center;">Action</th>
				    <th hidden="true" class="table_header_details" style="float: center;">ID</th>
				  </tr>
				 </thead> 
				<tbody>
				 
				    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>1</td> 
							      <td>Account</td> 
							       <td>Account</td> 
							        <td><input class='btn btn-inverse' type='button' name='Extract' value='View Mapping' onclick='callInformaticaMapping("Account")'/>
							       	 <input class='btn btn-inverse' type='button' name='Extract' value='Run Mapping' onclick='callInformaticaMapping("Account")'/>
							        </td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>2</td> 
							      <td>Product</td> 
							       <td>Product</td> 
							        <td><input class='btn btn-inverse' type='button' name='Extract' value='View Mapping' onclick='callInformaticaMapping("Product")'/>
							         <input class='btn btn-inverse' type='button' name='Extract' value='Run Mapping' onclick='callInformaticaMapping("Account")'/></td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>3</td> 
							      <td>Opportunity</td> 
							       <td>Opportunity</td> 
							        <td><input class='btn btn-inverse'  type='button' name='Extract' value='View Mapping' onclick='callInformaticaMapping("Opportunity")'/>
							         <input class='btn btn-inverse' type='button' name='Extract' value='Run Mapping' onclick='callInformaticaMapping("Account")'/></td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>4</td> 
							      <td>Asset</td> 
							       <td>Asset</td> 
							        <td><input class='btn btn-inverse'  type='button' name='Extract' value='View Mapping' onclick='callInformaticaMapping("Asset")'/>
							         <input class='btn btn-inverse' type='button' name='Extract' value='Run Mapping' onclick='callInformaticaMapping("Account")'/></td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>5</td> 
							      <td>Quote</td> 
							       <td>Quote</td> 
							        <td><input class='btn btn-inverse'  type='button' name='Extract' value='View Mapping' onclick='callInformaticaMapping("Quote")'/>
							         <input class='btn btn-inverse' type='button' name='Extract' value='Run Mapping' onclick='callInformaticaMapping("Account")'/></td>			     
									</tr>
							        
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>6</td> 
							      <td>Activity</td> 
							       <td>Activity</td> 
							        <td><input class='btn btn-inverse'  type='button' name='Extract' value='View Mapping' onclick='callInformaticaMapping("Activity")'/>
							         <input class='btn btn-inverse' type='button' name='Extract' value='Run Mapping' onclick='callInformaticaMapping("Account")'/></td>			     
							        
							        </tr>
				               
				             
						</tbody>

						</table>
					
		
	 
		</div>

	
</body>
</html>
