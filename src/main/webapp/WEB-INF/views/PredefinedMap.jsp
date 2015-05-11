<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="<c:url value="/resources/css/vaporizer.css" />" type="text/css"
	media="screen, projection">
	
<link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<title>Vaporizer</title>
</head>
<body>
		
		<form:form method="post" action="savePreMapData" modelAttribute="data">
		<div class="mappingContainer" style="width: 100%;">
		<table id="masterTable" style="width: 100%;">
			<br />
			<br />
			<thread>
			<tr>
				<td class="table_header_details" style="float: center;">Select</td>
				<td class="table_header_details" style="float: center;">Siebel Field</td>
				<td class="table_header_details" style="float: center;">SFDC Field</td>
				<td class="table_header_details" style="float: center;">SFDC Object Name</td>
				<td class="table_header_details" style="float: center;">Mapping Type</td>
			</tr>
			<thread>
			
			<c:if test="${not empty mappingData}" var="mapping">
				<c:forEach items="${mappingData}" var="mapping" varStatus="status"> 	
						
					<tr style="height: 45px; width: 45px;" align="center">
					<td style="margin-left: 35px;">					     
							<input name="lookUpFieldrow" id="lookUpFieldrow${mapping.name}" value="${mapping.name}" type="checkbox">				
					</td>
					<td><input type="text" style="margin-left: 35px;" id="lookUpSiebelFldrow${mapping.name}" name="lookUpSiebelFldrow${mapping.name}" value="${mapping.siebelFldName}" /></td>
					<td><input type="text" style="margin-left: 35px;" id="lookUpSfdcFldrow${mapping.name}" name="lookUpSfdcFldrow${mapping.name}" value="${mapping.sfdcFldName}" /></td>
					<td><input type="text" style="margin-left: 35px;" id="lookUpSfdcObjrow${mapping.name}" name="lookUpSfdcObjrow${mapping.name}" value="${mapping.sfdcObjName}" /></td>
					<td><input type="text" style="margin-left: 35px;" id="lookUpMapTyperow${mapping.name}" name="lookUpMapTyperow${mapping.name}" value="${mapping.mapTypeName}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		
		</table>
		</div>
		
		<div class="buttonContainer">

						<table style="border:2px;width:100%;">

							<tr>
								<td 
									style="float: center; width: 350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
									<input class="btn btn-block btn-inverse" type="submit"
									name="Extract" value="Done" /> <!-- <button id="cancel" type="button" style="float: right;"
						class="btn btn-block btn-inverse" onclick="javascript:history.go(-1);">Back</button> --> <!-- <button id="cancel" type="submit" style="float: right;"
						class="btn btn-block btn-inverse">Done</button> -->
								</td>
							</tr>


						</table>
		</div>
		</form:form>
		

</body>
</html>