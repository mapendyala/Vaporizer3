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
	<script type="text/javascript">

var rowNum = 1;
var primBaseTable;

	function addRow() {
		
		  if($("#rowCount").val()!=''){
			rowNum = Number($("#rowCount").val())+1;
		} 
		 
		$("#rowCount").val(rowNum);
		 

		var objName = $("sfdcObj").val();
		var srchObj = "searchObject"+(rowNum);
		var primTable = "prim"+(rowNum);
		var thresholdId = "thresh"+(rowNum);
		var SFDCObjName = "SFDCObjName"+(rowNum);
		var migrateId = "migrate"+(rowNum);

		var seqId = 12;
		
		
		 $("#masterTable tbody")
				.append(
						
						"<tr style='height:45px; width:45px;'>"
								+ "<td><input name="+migrateId+" type='checkbox'></td>"


							
								+ "<td><input value="+objName+" name="+primTable+" id="+primTable+"  style='margin-left:35px;'/></td>"
								+ "<td><input name="+primTable+" id="+primTable+"  style='margin-left:35px;'/></td>"
								+ "<td><input name="+primTable+" id="+primTable+"  style='margin-left:35px;'/></td>"
								+ "<td><input type='dropdown' id="+primTable+"  style='margin-left:35px;'/></td>"
								+ "<td><input name="+primTable+" id="+primTable+" style='margin-left:35px;'/></td>"
								+ "<td>"
								
				                + "</td>"
				                + "</tr>");
		
	}
	function hello(){

		window.alert("hello");
		} 
	</script>
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
		<button class="btn btn-primary" id="addRow" onclick="addRow()">[+]</button>	
			<div class="mappingContainer" style="width:100%;">
				<table id = "masterTable" style="width:100%;"><br/><br/>
				<thread>
					<tr>
					<th class="table_header_details" style="float: center;">Select</th>

			
					<th class="table_header_details" style="float: center;">Siebel Base Table</th>
					<th class="table_header_details" style="float: center;">Siebel Base Table Column</th>
					<th class="table_header_details" style="float: center;">Foreign Key Table Field Name</th>
					<th class="table_header_details" style="float: center;">SFDC Object Name</th>
					<th class="table_header_details" style="float: center;">SFDC Field Name</th>
					
						<%-- <th>Select</th>
						<th>Siebel Base Table</th>
						<th>Siebel Base Table Column</th>
						<th>Siebel Field Description</th>
						<th>Foreign Key Table Field Name</th>
						<th>SFDC Object Name</th>
						<th>SFDC Field Name</th>
						<th>SFDC Field Description</th>
						<th>Lov Mapping</th>--%>

						
									
					</tr>
					</thread>
					
					 <c:if test="${not empty mappingData}"> 
				<c:forEach items="${mappingData}" var="mapping" varStatus="status">
					  <td>
								<c:choose>
								<c:when test="${mainPage.select == 'on'}">
								<input name="select${mapping.mappingSeq}" type='checkbox' checked="checked">
								</c:when>
								<c:otherwise>
								<input name="select${mapping.mappingSeq}" type='checkbox'>
								</c:otherwise>
								</c:choose>
								</td>






				<td><input value="${mapping.siebleBaseTable}" id="siebleBaseTable${mapping.mappingSeq}" name="siebleBaseTable${mapping.mappingSeq}" readonly style='margin-left:45px;'/></td>


							
					<td><input value="${mapping.siebleBaseTableColumn}" id="siebleBaseTableColumn${mapping.mappingSeq}" name="siebleBaseTableColumn${mapping.mappingSeq}" readonly style='margin-left:35px;'/></td>
		
				<td><input value="${mapping.siebleBaseTable}" id="siebleBaseTable${mapping.mappingSeq}" name="siebleBaseTable${mapping.mappingSeq}" readonly style='margin-left:35px;'/></td>
			
						  
								<td><input value="${mapping.sfdcObjectName}" id="sfdcObjectName${mapping.mappingSeq}" name="sfdcObjectName${mapping.mappingSeq}" readonly style='margin-left:35px;'/></td>
							  <td>
							   
							   <select name="colour" id="dropdown" >
							    <option value="dropdown">${mapping.sfdcFieldTable}</option>
							   <c:if test="${not empty mappingField}"> 
				<c:forEach items="${mappingField}" var="field" varStatus="status">
        <%--- <option  value="dropdown">${mappingData.field}</option>--%> 
       
               <option value="dropdown">${field}</option>
              
                  </c:forEach> 
					  </c:if>
            </select>
          				
 <input type="hidden" name="dropdown" id="dropdown" >
 
    <!--  <input type="submit" value="click" name="btn_dropdown">-->
    </td>	  
				
					      <%--  <td><c:out value="${mapping.siebleBaseTable}"/></td>
					         <td><c:out value="${mapping.siebleBaseTableColumn}"/></td>
					         
					           <td><c:out value=""/></td>
					            <td><c:out value="${mapping.sfdcObjectName} "/></td>
					             <td><c:out value="${mapping.sfdcFieldTable}"/></td>--%> 
					           
					                <td></td>
					                
					           
					        <%-- <td><c:out value="${p.quantity}"/></td> --%>
					    </tr>
					</c:forEach> 
					  </c:if> 
				</table>
				<div id="row"><input id="rowCount" name='rowCount' type="hidden" value="${mappingData.size()}"></div>
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