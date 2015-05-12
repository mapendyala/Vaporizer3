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

function callTalenJob()
{
	
	var jobURL="";
	if(obj==="Account")
	 jobURL="https://talendjobsaccount.herokuapp.com/services/SIebel_to_SFDC_Account?method=runJob";
	if(obj==="Contact")
	 jobURL="https://talendjobscontact.herokuapp.com/services/Siebel_to_SFDC_Contact?method=runJob";
	if(obj==="Lead")
		 jobURL="https://talendjobslead.herokuapp.com/services/Siebel_to_SFDC_Lead?method=runJob";
	if(obj==="Opportunity")
		 jobURL="https://talendjobsopportunity.herokuapp.com/services/Siebel_to_SFDC_Opportunity?method=runJob";	

	window.open(jobURL,"JobRunning", "width=300, height=200");
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
					<div>
						<table class="table">
							<tr>
								<td class="oddTd">Name</td>
							<td><%=request.getSession().getAttribute("projectName")%></td>
							</tr>

						</table>
					</div>
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
							        <td><input class='btn btn-inverse' type='button' name='Extract' value='Run Talend Job' onclick='callTalenJob("Account")'/>
							        </td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>2</td> 
							      <td>Contact</td> 
							       <td>Contact</td> 
							        <td><input class='btn btn-inverse' type='button' name='Extract' value='Run Talend Job' onclick='callTalenJob("Contact")'/></td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>3</td> 
							      <td>Opportunity</td> 
							       <td>Opportunity</td> 
							        <td><input class='btn btn-inverse'  type='button' name='Extract' value='Run Talend Job' onclick='callTalenJob("Opportunity")'/></td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>4</td> 
							      <td>Lead</td> 
							       <td>Lead</td> 
							        <td><input class='btn btn-inverse'  type='button' name='Extract' value='Run Talend Job' onclick='callTalenJob("Lead")'/></td>			     
							        
							        </tr>
							        	    
				<tr style="height: 45px; width: 45px;" align="center">
							     <td>5</td> 
							      <td>Case</td> 
							       <td>Case</td> 
							        <td><input class='btn btn-inverse' disabled="disabled" type='button' name='Extract' value='Run Talend Job' onclick='callTalenJob(Account)'/></td>			     
							        
							        </tr>
				               
				             
						</tbody>

						</table>
					
		
	 
		</div>

	
</body>
</html>
