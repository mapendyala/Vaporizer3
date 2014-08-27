<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<!--try piyush-->
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
	
	<!-- Madhuri code -->
	<link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript"
	src="<c:url value="/resources/json.min.js" /> "></script>
	
<script type="text/javascript">

var rowNum = 1;
var primBaseTable;

	function addRow() {
		
		  if($("#rowCount").val()!=''){
			rowNum = Number($("#rowCount").val())+1;
		} 
		 
		$("#rowCount").val(rowNum);
		 
		var objName = "objectName"+(rowNum);
		var srchObj = "searchObject"+(rowNum);
		var primTable = "prim"+(rowNum);
		var thresholdId = "thresh"+(rowNum);
		var SFDCObjName = "SFDCObjName"+(rowNum);
		var migrateId = "migrate"+(rowNum);
		var seqId = "seq"+(rowNum);
		
		 $("#masterTable tbody")
				.append(
						
						"<tr style='height:45px; width:45px;'>"
								+ "<td><input name="+migrateId+" type='checkbox'></td>"
								+ "<td><input name= "+seqId+" hidden value="+rowNum+">"+rowNum+"</td>"
								+ "<td><input size='20' name="+objName+" id = "+objName+" placeholder='Click on Search' readonly style='margin-left:35px;'/><button type='button'id="+srchObj+" style='display: inline;' onclick='getPopup("+rowNum+")'><span class='glyphicon glyphicon-search'></span></button></td>"
								+ "<td><input name="+primTable+" id="+primTable+" readonly style='margin-left:35px;'/></td>"
								+ "<td><input type='text' id ="+thresholdId+" name="+thresholdId+" onchange='makeReadonly("+rowNum+")' style='margin-left:15px;'></td>"
								+ "<td><a href='#' onclick='submitForm("+rowNum+")' style='margin-left:15px;'>Select</a></td>" 
								+ "<td><input id="+SFDCObjName+" name="+SFDCObjName+" readonly style='margin-left:35px;'/><button type='button' style='display: inline;'><span class='glyphicon glyphicon-search'></span></button></td>"
								+ "<td><a href='#' onclick='submit("+rowNum+")' style='margin-left:15px;'>Select</a></td>"
								+ "<td><c:out value='Selected'/></td>"
								+ "<td>"
								+ "<input class='btn btn-inverse' type='button' name='Extract' value='E' />"
								+ "<input class='btn btn-inverse' type='button' name='transform' value='T' style='margin-left:10px;'/>"
				                + "<input class='btn btn-inverse' type='button' name='delete' value='-' style='margin-left:10px;'/>"
				                + "</td>"
				                + "</tr>");
		
	}
function callMapping(rowNum){
	
	var threshold=$("#"+ "thresh"+(rowNum)).val();
	var primBase=$("#"+ "prim"+(rowNum)).val();
	var siebelTableName = $("#objectName"+(rowNum)).val();
	window.location.href="/mapping?threshold="+threshold+"&primBaseName="+primBase+"&siebelTableName="+siebelTableName;
	
}
	function populateObjectName(id, primId){
	var s =$('input[name=selectedObject]:radio:checked').val();
	var value = s.split("+");
		$("#"+id).val(value[0]); 
		$("#"+primId).val(value[1]);
	}
	
	
	 function initiateDataLoad()
	 {
		 alert('hiii'); 
		 var dataFileUrlVar= $("[id$='dataFileUrl']").val();
		 alert('hiii');
		 $.ajax({
			type : "GET",
			url : "initiateDataLoader",
		 	data :
		 		{
		 		dataFileUrl:dataFileUrlVar		 		
		 		},
			contentType : 'application/text',
			success : function(response) {
				
				//$("#"+SFDCObjectId).val(response); 
			}
		});  
	 }
	
	
	
	
		
	 function makeReadonly(rowNum){
		 var thresholdId = "thresh"+(rowNum);
		var threshold = $("#"+thresholdId).val();
		var primTableId = "prim"+(rowNum);
		var primBaseTable =  $("#"+primTableId).val();
		$.ajax({
			type : "POST",
			url : "set/Threshold",
		 	data : {threshold:threshold, primBaseName:primBaseTable}
			});
		$("#"+thresholdId).attr('readonly',true);
	}
	

	
	
	
	 
	 
	 function getSFDCOBject(siebelObject,SFDCObjectId)
	 {
		 
		
		 $.ajax({
			type : "GET",
			url : "getSFDCOBject",
		 	data :
		 		{
		 		siebelObject:siebelObject		 		
		 		},
			contentType : 'application/text',
			success : function(response) {
				
				$("#"+SFDCObjectId).val(response); 
			}
		});  
	 }

	  function getPopup(rowNum){

		var id="objectName"+(rowNum);	
		var primId = "prim"+(rowNum);
		 var SFDCObjectId="SFDCObjName"+(rowNum);
		$("#obj").dialog({title: "Select an Siebel Object",
			width: 500,
			height: 500,
			buttons: {
				"OK": function () {
					populateObjectName(id, primId);
					var siebelObject =$('input[name=selectedObject]:radio:checked').val();
					var value = siebelObject.split("+");
					getSFDCOBject(value[0],SFDCObjectId);
					if(document.getElementById("dyncTblCntnt")){
						var noOfChilds = $("#dyncTblCntnt").children().length;
						if(noOfChilds > 1){
							$("#dyncTblCntnt").children().remove();
						}
					}
					$("#objName").val("");

					$(this).dialog("close");
			
				},"Close": function(){
					if(document.getElementById("dyncTblCntnt")){
						var noOfChilds = $("#dyncTblCntnt").children().length;
						if(noOfChilds > 1){
							$("#dyncTblCntnt").children().remove();
						}
					}
					$("#objName").val("");
					$(this).dialog("close");
				}
			},
			});
		} 
	  
	  
	  function submitForm(rowNum)
	  
	  {		
		  var page = "child";
		  $("#rowNo").val(rowNum); 
		  $("#pageName").val(page);
		  $("#mainForm").submit(); 
				 
	  }
	  
 function submit(rowNum)
	  
	  {		
		  var page = "map";
		  $("#rowNo").val(rowNum); 
		  $("#pageName").val(page);
		  $("#mainForm").submit(); 
				 
	  }
	
	function  getObjectName(){
		var objName = $("#objName").val();
		  $.ajax({
			type : "POST",
			url : "get/SiebelObject",
			data : {objectName:objName},
		 	success : function(data){
					displaySblObjTable(data);
				}  
			});   
	}
	
	function displaySblObjTable(data){
		if(document.getElementById("dyncTblCntnt")){
			var noOfChilds = $("#dyncTblCntnt").children().length;
			if(noOfChilds > 1){
				$("#dyncTblCntnt").children().remove();
				for(var i=0 ; i<data.length ;i++ ){
					var tblName = data[i];
					var selValue = obj.concat("+",primBase); 
					$("#dyncTblCntnt").append("<input type='radio' name='selectedObject' value='"+selValue+"'><span>"+tblName.objName+"</span><br/>");
										
				}
			}else{
				for(var i=0 ; i<data.length ;i++ ){
					var tblName = data[i];
					var selValue = tblName.objName.concat("+").concat(tblName.primName);
					$("#dyncTblCntnt").append("<input type='radio' name='selectedObject' value='"+selValue+"'><span>"+tblName.objName+"</span><br/>");
									
				}
			}
		}
		else{
			$("#obj").append("<br/>");
			$("#obj").append("<div id='dyncTblCntnt'>");
			for(var i=0 ; i<data.length ;i++ ){
				var tblName = data[i];
				var selValue = tblName.objName.concat("+").concat(tblName.primName);
				$("#dyncTblCntnt").append("<input type='radio' name='selectedObject' value='"+selValue+"'><span>"+tblName.objName+"</span><br/>");
			}
			$("#obj").append("</div>");
		}
		
	}
		
	</script>

<title>Vaporizer</title>
</head>
<body>

	<div id="dialog" class="container">
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
							<td><%=request.getSession().getAttribute("projectName")%></td>
							</tr>

						</table>
					</div>
				</div>
				
			</div>
	<!-- Madhuri code start -->	
		<button class="btn btn-primary" id="addRow" onclick="addRow()">[+]</button>			
		<br><br>
			<form:form action = "saveData" method = "post" id="mainForm" name="master" commandName="data">
				<table id = "masterTable" style="width:100%;">
				
			<thead>
				  <tr>
				    <th class="table_header_details" style="float: center;">Migrate?</th>
				    <th class="table_header_details" style="float: center;">Sequence</th>
				    <th class="table_header_details" style="float: center;">Siebel Object</th>
				    <th class="table_header_details" style="float: center;">Prim Base Table</th>
				    <th class="table_header_details" style="float: center;">Threshold</th>
				    <th class="table_header_details" style="float: center;">Child Base Tables</th>
				    <th class="table_header_details" style="float: center;">SFDC Object</th>
				    <th class="table_header_details" style="float: center;">Mapping</th>
				    <th class="table_header_details" style="float: center;">Status</th>
				    <th class="table_header_details" style="float: center;">Add Ons</th>
				  </tr>
				 </thead> 
				<tbody>
				
				  <c:if test="${not empty data}"> 
				   <c:forEach items="${data}" var="mainPage"> 
				    
				<tr style="height: 45px; width: 45px;" >
								<td>
								<c:choose>
								<c:when test="${mainPage.migrate == 'on'}">
								<input name="migrate${mainPage.sequence}" type='checkbox' checked="checked">
								</c:when>
								<c:otherwise>
								<input name="migrate${mainPage.sequence}" type='checkbox'>
								</c:otherwise>
								</c:choose>
								</td>
								<td><input type="hidden" name="seq${mainPage.sequence}" value="${mainPage.sequence}">${mainPage.sequence}</td>
								<td><input name="objectName${mainPage.sequence}" id="objectName${mainPage.sequence}" value="${mainPage.siebelObject}" size='20' placeholder='Click on Search' readonly style='margin-left:35px;'/><button type='button' style='display: inline;' onclick="getPopup(${mainPage.sequence})"><span class='glyphicon glyphicon-search'></span></button></td>
								<td><input value="${mainPage.primBaseTable}" id="prim${mainPage.sequence}" name="prim${mainPage.sequence}" readonly style='margin-left:35px;'/></td>
								<td><input type='text' id="thresh${mainPage.sequence}" name="thresh${mainPage.sequence}" value="${mainPage.threshold}" onchange='makeReadonly(${mainPage.sequence})' style='margin-left:15px;'></td>
								<td><a href="#" onclick='submitForm(${mainPage.sequence})' style='margin-left:15px;'>Select</a></td>
								<td><input  readonly style='margin-left:35px;'/><button type='button' style='display: inline;'><span class='glyphicon glyphicon-search'></span></button></td>
								<td><a href='#' onclick='submit(${mainPage.sequence})' style='margin-left:15px;'>Select</a></td>
								<td><c:out value='Selected'/></td>
								<td>
								<input class='btn btn-inverse' type='button' name='Extract' value='E' />
								<input class='btn btn-inverse' type='button' name='transform' value='T' style='margin-left:5px;'/>
				                <input class='btn btn-inverse' type='button' name='delete' value='-' style='margin-left:5px;'/>
				                </td>
				                </tr>
				               
				               </c:forEach> 
				                 </c:if> 
						</tbody>

						</table>
						 <div id="row"><input id="rowCount" name='rowCount' type="hidden" value="${data.size()}"></div>
						  <div id="page"><input id="pageName" name='pageName' type="hidden" readonly></div>
						 <div id="No"><input id="rowNo" name='rowNo' type="hidden" readonly></div>
			</form:form>
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
							name="Extract" value="Migrate To SFDC" id="dataloadtoSFDC" onclick='initiateDataLoad()'/> />
						</td>
					</tr>	

				</table>
			</div> 
		</div>

<div id=obj hidden="true">
 <input type="text" id = "objName" placeholder="Enter Object Name"><button type='button' style='display: inline;' id="search" onclick='getObjectName()'><span class='glyphicon glyphicon-search'></span></button>
 </div>
	</div>
	
</body>
</html>
