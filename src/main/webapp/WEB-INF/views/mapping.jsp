<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	



<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
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
					<br/>
							<tr>
									<td colspan="2"
							style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">Field Mapping</td>
							</tr>
							
<div class="sampleContainer" style="width:250px;">
				<table class="table" style="margin:2px;">
					<tr><br/>
					<td style="width: 45px;text-align: left;" align="left">Siebel Entity</td>
					
					<td style="width: 45px;text-align: left;" align="left">${mappingData[0].siebleBaseTable }</td>
					
					
					</tr>
					
					<tr>
					<td style="width: 45px;text-align: left;" align="left">SFDC Entity</td>
					
					<td style="width: 45px;text-align: left;" align="left">${mappingData[0].sfdcObjectName }</td></tr>
					
					
					</table></div>
						
					</div>
				</div>
		
			<div class="mappingContainer" style="width:100%;">
				<table class="table" style="margin:0px !important;"><br/><br/>
					<tr>
						<th>Select</th>
						<th>Siebel Base Table</th>
						<th>Siebel Base Table Column</th>
						<th>Siebel Field Description</th>
						<th>Foreign Key Table Field Name</th>
						<th>SFDC Object Name</th>
						<th>SFDC Field Name</th>
						<th>SFDC Field Description</th>
						<th>Lov Mapping</th>
											
					</tr>
					
				<c:forEach items="${mappingData}" var="mapping" varStatus="status">
					<c:choose>
   <c:when test="${status.index % 2 == 0}">
     <tr bgcolor="#CECEF6"> 
   </c:when>
                              

   <c:otherwise>
                              

     <tr bgcolor="#FBFBEF">
                              

   </c:otherwise>
 </c:choose> 
                            
					
					   
					        <td><input type="checkbox" /></td>
					        <td><c:out value="${mapping.siebleBaseTable}"/></td>
					         <td><c:out value="${mapping.siebleBaseTableColumn}"/></td>
					          <td><c:out value=""/></td>
					           <td><c:out value=""/></td>
					            <td><c:out value="${mapping.sfdcObjectName} "/></td>
					             <td><c:out value="${mapping.sfdcFieldTable}"/></td>
					              <td><c:out value=""/></td>
					                <td></td>
					                
					           
					        <%-- <td><c:out value="${p.quantity}"/></td> --%>
					    </tr>
					</c:forEach> 
					
				</table>
			</div>
			<div class="buttonContainer">
			<form:form method="post" action="childSave" modelAttribute="data">
				<table style="border: 0">
				
					<tr>
						<td colspan="2"
							style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							<input class="btn btn-block btn-inverse" type="submit"
							name="Extract" value="Done"  />
							
							<!-- <button id="cancel" type="button" style="float: right;"
						class="btn btn-block btn-inverse">Done</button> -->
					<!-- <button id="cancel" type="submit" style="float: right;"
						class="btn btn-block btn-inverse">Done</button> -->
							
						</td>
					</tr>
					

				</table>
				</form:form>	
			</div>
		</div>




	</div>
</body>
<script type="text/javascript">

						$("#cancel").click(function() {
							
							window.location.href = "Done";
						});
					
					
</script>
</html>