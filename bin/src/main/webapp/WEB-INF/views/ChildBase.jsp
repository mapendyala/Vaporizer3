<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script type="text/javascript"
	src="<c:url value="/resources/jquery-1.4.min.js" /> "></script>
<script type="text/javascript"
	src="<c:url value="/resources/json.min.js" /> "></script>
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
					<div class="table_header_details">Vaporizer</div>
					<div>
						<!-- <table class="table">
							<tr>
								<td class="oddTd">Name</td>
								<td>Sample Project One</td>
							</tr>

						</table> -->
					</div>
				</div>
				<div>
					<div style="float: left; width: 48%">
						<!-- <div class="table_header_details">Seibel</div> -->
						<div>
							<table class="table">
								<tr>
									<td class="oddTd">Siebel Entity</td>
									<td>Account</td>
								</tr>
								<tr>
									<td class="oddTd">Primary Table</td>
									<td>S_ORG_EXT</td>
								</tr>
								<!-- <tr>
									<td class="oddTd">Seibel Password</td>
									<td>SnetUser1</td>
								</tr> -->

							</table>
						</div>
					</div>
					<!-- <div style="float: right; width: 50%">
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
					</div> -->
				</div>
			</div>
			<div class="mappingContainer" style="height: 500px;width:100%;">
			<form:form method="post" action="childSave" modelAttribute="data">
				<table class="table" style="margin:0px !important;">
					<tr>
						
						<th>S.NO</th>
						<th>Primary Table</th>
						<th>Child Table</th>
						<th>Join Condition</th>
						<!-- <th>Delete</th> -->
						<!-- <th>Child Base tables</th>
						<th>SFDC Object</th>
						<th>Mapping</th>
						<th>Status</th>
						<th>Add Ons</th>	 -->					
					</tr>
					
					
					<c:forEach items="${myChildList}" var="childItem">
    <tr>
        <td><c:out value="${childItem.seqNum}"/></td>
        <td><c:out value="${childItem.baseObjName}"/></td>
        <td><c:out value="${childItem.childObjName}"/></td>
        <td><c:out value="${childItem.joinCondition}"/></td>
    </tr>
</c:forEach> 
					
				</table>
				<div class="buttonContainer">
				<table style="border: 0">
				
					<tr>
						<td colspan="2"
							style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							
							<button id="cancel" type="submit" style="float: right;"
						class="btn btn-block btn-inverse">Done</button>
							
						</td>
					</tr>
					

				</table>
			</form:form>
			</div>
		</div>
			</div>
			
		</div>




	<script type="text/javascript">

						$("#cancel").click(function() {
							
							window.location.href = "Done";
						});
					
					
</script>
	
</body>
</html>