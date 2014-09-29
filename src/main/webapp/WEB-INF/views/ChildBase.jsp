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

<script type="text/javascript">

						$("#cancel").click(function() {
							
							window.location.href = "Done";
						});
					
					
</script>

<title>Vaporizer</title>
</head>
<body>
<form:form method="post" action="childSave" modelAttribute="data">
	<div class="container">
		
		<%-- <form:form method="post" action="childSave" modelAttribute="data"> --%>
		<div class="mainContent">
			<div class="credential_container">
				<div>
					<div class="table_header_details">Vaporizer</div>
					
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
							</table>
						</div>
					</div>
					
				</div>
			</div> <!-- //end of credential -->
			
			
			<div class="mappingContainer" style="height: 500px;width:100%;">
				<table class="table" style="margin:0px !important;">
					<tr>
						<th class="table_header_details" style="float: center;">Select</th>
						<th class="table_header_details" style="float: center;">S.NO</th>
						<th class="table_header_details" style="float: center;">Primary Table</th>
						<th class="table_header_details" style="float: center;">Child Table</th>
						<th class="table_header_details" style="float: center;">Join Condition</th>
						<!-- <th>Delete</th> -->
						<!-- <th>Child Base tables</th>
						<th>SFDC Object</th>
						<th>Mapping</th>
						<th>Status</th>
						<th>Add Ons</th>	 -->					
					</tr>
					
					
					<%-- <c:forEach items="${myChildList}" var="childItem" >
    <tr>
    <td><input type="checkbox"/></td>
        <td name="MySeq${childItem.seqNum}" id="MySeq${childItem.seqNum}"><c:out value="${childItem.seqNum} "/></td>
        <td><c:out value="${childItem.baseObjName}"/></td>
        <td name="childObjName${childItem.seqNum}" id="childObjName${childItem.seqNum}"><c:out value="${childItem.childObjName}"/></td>
        <td><c:out value="${childItem.joinCondition}"/></td>
    </tr>
</c:forEach>   --%>

<c:forEach items="${myChildList}" var="childItem">
	<tr>
	
	 <td>
								<c:choose>
								<c:when test="${childItem.checkFlag == 'true'}">
								<input name="checkFlag${childItem.seqNum}" id="checkFlag${childItem.seqNum}"  type='checkbox' checked="checked">
								</c:when>
								<c:otherwise>
								<input name="checkFlag${childItem.seqNum}" id="checkFlag${childItem.seqNum}"  type='checkbox'>
								</c:otherwise>
								</c:choose>
								</td>
								
	<%-- <td><input type="checkbox" name="checkFlag${childItem.seqNum}" id="checkFlag${childItem.seqNum}" value="${childItem.checkFlag}"/></td> --%>
	
	<td><input value="${childItem.seqNum}" id="sequenceNum${childItem.seqNum}" name="sequenceNum${childItem.seqNum}" readonly style='margin-left:45px;'/></td>	
	
		<td><input value="${childItem.baseObjName}" id="baseObjName${childItem.seqNum}" name="baseObjName${childItem.seqNum}" readonly style='margin-left:45px;'/></td>	
		
		<td><input value="${childItem.childObjName}" id="childObjName${childItem.seqNum}" name="childObjName${childItem.seqNum}" readonly style='margin-left:45px;'/></td>
		
			<td ><input value="${childItem.joinCondition}" id="joinCondition${childItem.seqNum}" name="joinCondition${childItem.seqNum}" readonly style='margin-left:45px;width:130%'/></td>
			
			<td><input type="hidden" value="${childItem.childSfdcId}" id="sfdcId${childItem.seqNum}" name="sfdcId${childItem.seqNum}"></td>
</tr>
</c:forEach> 


				</table>
				<div id="row"><input id="rowCount" name='rowCount' type="hidden" value="${myChildList.size()}"></div>
				
					<div class="buttonContainer">
                           <table style="border: 0">
                           
                                  <tr>
                                         <td colspan="2"
                                                style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
                                                
                                                <input class="btn btn-block btn-inverse" type="submit" id="cancel"
                                                name="Done" value="Done"  />
                                         <!--   
                                                <button id="cancel" type="submit" style="float: right;"
                                         class="btn btn-block btn-inverse">Done</button> -->
                                                
                                         </td>
                                  </tr>
                                  
                                  <!-- <tr>
                                         <td colspan="2"
                                                style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
                                                <input class="btn btn-block btn-inverse" type="submit"
                                                name="Extract" value="Done"  /> -->
                           </table>
                     
                     </div> <!-- //end of button containr -->


				
				
				</div> <!-- //end of mapping container -->
				
				<div></div>
				<div></div>
				
				
			
		</div> <!-- //end of main content -->
		
		</div>
		<!-- <div class="buttonContainer">
				<table style="border: 0">
				
					<tr>
						<td colspan="2"
							style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							
							<input class="btn btn-block btn-inverse" type="submit" id="cancel"
							name="Done" value="Done"  />
							
							<button id="cancel" type="submit" style="float: right;"
						class="btn btn-block btn-inverse">Done</button>
							
						</td>
					</tr>
					
					<tr>
						<td colspan="2"
							style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							<input class="btn btn-block btn-inverse" type="submit"
							name="Extract" value="Done"  />
				</table>
			
			</div>  --><!-- //end of button containr -->
		</form:form>
		
			<!-- </div> -->
			
</body>
</html>