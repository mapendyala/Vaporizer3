<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<!--try piyush-->
<!-- rachita try commit -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/vaporizer.css" />" type="text/css"
	media="screen, projection">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<style type="text/css">
	
	
	.btn .btn-block .btn-inverse :HOVER
{
	background-color: black !Important;
	color: white !Important;
}
</style>
<title>Vaporizer</title>
</head>
<body>
	<div class="container">
		<h6>${error}</h6>
		<h6>${databaseUrl}</h6>
		<div class="mainContent">
			<div class="credential_container">
				<div>
					<div class="table_header_details">Data Migration Details</div>
					<div>
						<table class="table">
							<tr>
								<td class="oddTd">Name</td>
								<td><%=request.getSession().getAttribute("projectId")%></td>
							</tr>

						</table>
					</div>
				</div>
		
			</div>
			<div class="mappingContainer" style="width:100%;">
				<table class="table" style="margin:0px !important;">
					<tr>
						<th>Migrate?</th>
						<th>Sequence</th>
						<th>Siebel Object</th>
						<th>Primary Base Table</th>
						<th>Threshold</th>
						<th>Child Base tables</th>
						<th>SFDC Object</th>
						<th>Mapping</th>
						<th>Status</th>
						<th>Add Ons</th>						
					</tr>
					
					<c:forEach  var="i" begin="1" end="5" >
					
					    <tr>
					        <td><input type="checkbox" /></td>
					        <td><c:out value="${i}"/></td>
					         <td><c:out value="${i}"/></td>
					          <td><c:out value="${i}"/></td>
					           <td><input type="number" name="quantity" min="1" max="5" style="width:40px"></td>
					            <td><a href="">Select</a></td>
					             <td><c:out value="${i}"/></td>
					              <td><a href="mapping">Select</a></td>
					               <td><c:out value="${i}"/></td>
					                <td>
					                <input class="btn btn-inverse" type="button" name="Extract" value="E"/>
					                <input class="btn btn-inverse" type="button" name="transform" value="T"/>
					                <input  class="btn btn-inverse" type="button" name="delete" value="-"/>
					                </td>
					           
					        <%-- <td><c:out value="${p.quantity}"/></td> --%>
					    </tr>
					</c:forEach> 
					
				</table>
			</div>
			<div class="buttonContainer">
				<table style="border: 0">
					<tr>
						<td colspan="2"
							style="float: right; width: 350px; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							<input class="btn btn-block btn-inverse" type="button"
							name="Extract" value="Extract All" />
						</td>
					</tr>
					<tr>
						<td
							style="float: left;  padding: 50px; width: 450px !Important; padding-top: 10px; padding-bottom: 10px;">
							<input type="text" style="width:100%;" placeholder="CSV Location" />
						</td>
						<td
							style="float: right; padding: 50px;width:350px !Important; padding-top: 10px; padding-bottom: 10px;">
							<input class="btn btn-block btn-inverse" type="button"
							name="Location" value="CSV File location" />
						</td>
					</tr>
					<tr>
						<td colspan="2"
							style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							<input class="btn btn-block btn-inverse" type="button"
							name="Extract" value="Create CSV File" />
						</td>
					</tr>
					<!-- try me -->
					<tr>
						<td colspan="2"
							style="float: right; width: 350px; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							<input class="btn btn-block btn-inverse" type="button"
							name="Extract" value="Migrate To SFDC" />
						</td>
					</tr>

				</table>
			</div>
		</div>




	</div>
</body>
</html>
