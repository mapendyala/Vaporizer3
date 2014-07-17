<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<!-- rachita try commit -->
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

<!-- Latest compiled and minified JavaScript -->
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<title>Vaporizer</title>
</head>
<body>
	<div class="container">
		
		<div class="mainContent">
			<div class="credential_container">
				<div>
					<div class="table_header_details">Data Migration Details</div>
					<div>
						<table class="table">
							<tr>
								<td class="oddTd">Name</td>
								<td>Sample Project One</td>
							</tr>

						</table>
					</div>
				</div>
				<div>
					<div style="float: left; width: 48%">
						<div class="table_header_details">Seibel</div>
						<div>
							<table class="table">
								<tr>
									<td class="oddTd">Siebel DSN</td>
									<td>SBLDB</td>
								</tr>
								<tr>
									<td class="oddTd">Seibel Login</td>
									<td>SnetUser1</td>
								<tr>
									<td class="oddTd">Seibel Password</td>
									<td>SnetUser1</td>
								</tr>

							</table>
						</div>
					</div>
					<div style="float: right; width: 50%">
						<div class="table_header_details">SFDC</div>
						<div>

							<table class="table">
								<tr>
									<td class="oddTd">SFDC Login</td>
									<td>ranfernandes@deloitte.com.data</td>
								</tr>
								<tr>
									<td class="oddTd">SFDC Password</td>
									<td>93458f2aa223f884bfbb35e36e182ef1</td>
								</tr>
								<tr>
									<td class="oddTd"></td>
									<td></td>
								</tr>
							</table>
						</div>
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
					              <td><a href="">Select</a></td>
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