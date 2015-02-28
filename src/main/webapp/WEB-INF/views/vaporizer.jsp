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

var rowNum = 1;
var primBaseTable;
var sfdcObjectForExtarction="";

	function addRow() {
		
		  if($("#rowCount").val()!=''){
			rowNum = Number($("#rowCount").val())+1;
		} 
		 
		$("#rowCount").val(rowNum);
		 
		var objName = "objectName"+(rowNum);
		var srchObj = "searchObject"+(rowNum);
		var srchSFDCObj = "searchFDCObject"+(rowNum);
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
								+ "<td><input id="+SFDCObjName+" name="+SFDCObjName+" readonly='true' style='margin-left:35px;'/><button type='button'id="+srchSFDCObj+" style='display: inline;' onclick='getSFDCPopup("+rowNum+")'><span class='glyphicon glyphicon-search'></span></button></td>"
								+ "<td><a href='#' onclick='submit("+rowNum+")' style='margin-left:15px;'>Select</a></td>"
								+ "<td><c:out value='Selected'/></td>"
								+ "<td>"
								+ "<input class='btn btn-inverse' type='button' name='Extract' value='E' onclick='extract("+rowNum+")'/>"
								/* + "<input class='btn btn-inverse' type='button' name='transform' value='T' hidden = 'true' style='margin-left:10px;'/>"
				                + "<input class='btn btn-inverse' type='button' name='delete' value='-' hidden = 'true' style='margin-left:10px;'/>" */
				                + "</td>"
				                + "</tr>");
		
	}
	
	function populateObjectName(id, primId){
	var s =$('input[name=selectedObject]:radio:checked').val();
	var value = s.split("+");
		$("#"+id).val(value[0]); 
		$("#"+primId).val(value[1]);
	}
	
	//Added by Subhojit
	 function initiateDataLoad()
	 {
		 
		 var dataFileUrlVar= $("[id$='datafileUrl']").val();
		  var str = dataFileUrlVar;
		  sfdcObjectForExtarction="Account";  
		// alert('hiii');
		$("#statusBlock").empty();
		 $("#statusBlock").append('<div id="stat"><h4> Data Loading status: In Progress</h4></div>');
		 $.ajax({
			type : "GET",
			url : "initiateDataloader",
		 	data :
		 		{
		 			objectName:sfdcObjectForExtarction
		 		},
			contentType : 'application/text',
			success : function(response) {
				var str=response;
				
				var strList=str.split("_");
				var total=+strList[0] + +strList[1];
				var successNo=strList[0];
				var failureNo=strList[1];
				$("#statusBlock").html('<h4> Data Loading Status : Complete</h4>');
				$("#statusBlock").append("<h4> No Of Record  : "+total+"</h4>" +
				"<h6> Success : "+successNo+"</h6>"+"<h6> Failure : "+failureNo+"</h6>");
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
	

	 function validateUploadForm()
		{
			 var fileName=$("#fileNameID").val();
		      if(fileName!="")
		    	  {
		    	  $("#csvUploadID").innerHTML = 'Uploading...';
		    	  
		    	  }
		      else
		    	  {
		    	  alert("Please enter file Name.");
		    	  return false;
		    	  }
		}
	
	
	 //function to get SFDC object mapping from siebel object as input
	 
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

				if( response!='No data')
				$("#"+SFDCObjectId).val(response);
				else
					{
					
					$("#"+SFDCObjectId).val('');
					alert("No sfdc mapping available.To select sfdc object, please click on search.");
					//$("#indicator").css({'display':'block'});
					}
					
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
	  function getSFDCPopup(rowNum){
		  //$("#indicator").css({'display':'none'});
			var SFDCObjectId="SFDCObjName"+(rowNum);
			$("#objSFDC").dialog({title: "Select an SFDC Object",
				width: 500,
				height: 500,
				buttons: {
					"OK": function () {
						var SFDCObject =$('input[name=selectedObject]:radio:checked').val();
						if(SFDCObject!=null)
							{
							$("#"+SFDCObjectId).val(SFDCObject);
							if(document.getElementById("dyncTblCntntSFDC")){
								var noOfChilds = $("#dyncTblCntntSFDC").children().length;
								if(noOfChilds > 1){
									$("#dyncTblCntntSFDC").children().remove();
								}
							}
							$("#SFDCObjName").val("");

							$(this).dialog("close");
							}
							else
								{
								alert("Please select a value from list");
								}
						
						
				
					},"Close": function(){
						if(document.getElementById("dyncTblCntntSFDC")){
							var noOfChilds = $("#dyncTblCntntSFDC").children().length;
							if(noOfChilds > 1){
								$("#dyncTblCntntSFDC").children().remove();
							}
						}
						$("#SFDCObjName").val("");
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
function submitForm(rowNum, page){
	alert(page);
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
	function getSFDCObjectName()
	{
		var objName = $("#SFDCObjName").val();
		if(document.getElementById("dyncTblCntntSFDC"))
			{
			var noOfChilds = $("#dyncTblCntntSFDC").children().length;
		if(noOfChilds > 1){
			$("#dyncTblCntntSFDC").children().remove();
		}
			}
		$("#objSFDC").append("<div id='dyncLoaderSFDC' style='text-align:center;margin-top:70px;'><img src='resources/images/ajax-loader.gif'/></div>");
		  $.ajax({
			type : "GET",
			url : "SFDCObjectList",
			data : {objectName:objName},
		 	success : function(data){
		 	
					displaySFDCObjTable(data);
				}  
			});   
	}
	
	function displaySFDCObjTable(data)
	{
		$("#dyncLoaderSFDC").remove();
		if(data.length!=0)
			{
			
			if(document.getElementById("dyncTblCntntSFDC"))
			{
				var noOfChilds = $("#dyncTblCntntSFDC").children().length;
				if(noOfChilds > 1){
					$("#dyncTblCntntSFDC").children().remove();
				}
					for(var i=0 ; i<data.length ;i++ ){
						var tblName = data[i];						
						$("#dyncTblCntntSFDC").append("<input type='radio' name='selectedObject' value='"+tblName.objName+"'><span>"+tblName.objName+"</span><br/>");
											
					}
				
				}
			
			else{
				$("#objSFDC").append("<br/>");
				$("#objSFDC").append("<div id='dyncTblCntntSFDC'>");
				for(var i=0 ; i<data.length ;i++ ){
					var tblName = data[i];					
					$("#dyncTblCntntSFDC").append("<input type='radio' name='selectedObject' value='"+tblName.objName+"'><span>"+tblName.objName+"</span><br/>");
				}
				$("#objSFDC").append("</div>");
			}
			}
		else
			{
			alert("No Object Found");
			}
		
		
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
	
	function extract(rowNum){
		var SFDCObjectId="SFDCObjName"+(rowNum);
		sfdcObjectForExtarction=$("#"+SFDCObjectId).val();
		var sfdcId = $("#"+"SfdcId"+(rowNum)).val();
		var siebelObjName = $("#objectName" +(rowNum)).val();
		var baseTable = $("#prim" +(rowNum)).val();
	/* /* 	 $.ajax({
				type : "GET",
				url : "getextractData",
				data : {sfdcId:sfdcId, siebelObjName:siebelObjName, baseTable:baseTable, sfdcObject:sfdcObjectForExtarction},
				success : function(response){
					if(response!="")
				 	$("#datafileUrl").val(response);
					else
						{
						alert("No location generated");
						}
			
				}  
				});  */  
				var data= "sfdcId="+sfdcId+"&siebelObjName="+siebelObjName+"&baseTable="+baseTable+"&sfdcObject="+sfdcObjectForExtarction+"&format=csv ";

			 $.download('getextractData',
					data,
					 "GET"); 
	 }
	
	function submitDependant(rowNum)
	 
	 {		
		  var page = "dependantEntity";
		  $("#rowNo").val(rowNum); 
		  $("#pageName").val(page);
		  $("#mainForm").submit(); 
				 
	 }
		
	</script>

<title>Vaporizer</title>
</head>
<body>
<div id="statusBlock">

</div>

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
				<table id = "masterTable" style="width:1500px;">
				
			<thead>
				  <tr align="center">
				    <th class="table_header_details" style="float: center;">Migrate?</th>
				    <th class="table_header_details" style="float: center;">Sequence</th>
				    <th class="table_header_details" style="float: center;">Siebel Object</th>
				    <th class="table_header_details" style="float: center;">Prim. Base Table</th>
				    <th class="table_header_details" style="float: center;">Threshold</th>
				    <!-- <th class="table_header_details" style="float: center;">Child Base Tables</th> -->
				    <th class="table_header_details" style="float: center;">Joined Tables</th>
				    <th class="table_header_details" style="float: center;">SFDC Object</th>
				    <!-- <th class="table_header_details" style="float: center;">Mapping</th>-->
				    <th class="table_header_details" style="float: center;">Single Value Field Mapping</th>
				    <th class="table_header_details" style="float: center;">Multi Value Field Mapping</th>
				    <th class="table_header_details" style="float: center;">Dependant Entities</th>
				    <th class="table_header_details" style="float: center;">Status</th>
				    <th class="table_header_details" style="float: center;">Add Ons</th>
				    <th hidden="true" class="table_header_details" style="float: center;">ID</th>
				  </tr>
				 </thead> 
				<tbody>
				
				  <c:if test="${not empty data}"> 
				   <c:forEach items="${data}" var="mainPage"> 
				    
				<tr style="height: 45px; width: 45px;" align="center">
								<td>
								<c:choose>
								<c:when test="${mainPage.migrate == true}">
								<input name="migrate${mainPage.sequence}" type='checkbox' checked="checked">
								</c:when>
								<c:otherwise>
								<input name="migrate${mainPage.sequence}" type='checkbox'>
								</c:otherwise>
								</c:choose>
								</td>
								<td><input type="hidden" name="seq${mainPage.sequence}" value="${mainPage.sequence}">${mainPage.sequence}</td>
								<td width="180px"><input name="objectName${mainPage.sequence}" id="objectName${mainPage.sequence}" value="${mainPage.siebelObject}" size='15' placeholder='Click on Search' readonly style=''/><button type='button' style='display: inline;' onclick="getPopup(${mainPage.sequence})"><span class='glyphicon glyphicon-search'></span></button></td>
								<td><input value="${mainPage.primBaseTable}" id="prim${mainPage.sequence}" name="prim${mainPage.sequence}" readonly style='margin-left:35px;'/></td>
								<td><input type='text' id="thresh${mainPage.sequence}" name="thresh${mainPage.sequence}" value="${mainPage.threshold}" onchange='makeReadonly(${mainPage.sequence})' style='margin-left:15px;'></td>
								<td><a href="#" onclick='submitForm(${mainPage.sequence})' style='margin-left:15px;'>Select</a></td>
								<td width="220px"><input name="SFDCObjName${mainPage.sequence}" id="SFDCObjName${mainPage.sequence}" value="${mainPage.sfdcObject}" readonly /><button type='button' style='display: inline;'><span class='glyphicon glyphicon-search'></span></button></td>
								<td><a href='#' onclick='submit(${mainPage.sequence})' style='margin-left:15px;'>Select</a></td>
								<td><a href='#' onclick='submitForm(${mainPage.sequence},"multiMap")' style='margin-left:15px;'>Select</a></td>
								<td><a href='#' onclick='submitDependant(${mainPage.sequence},"dependantEntity")' style='margin-left:15px;'>Select</a></td>
								<td><c:out value='Selected'/></td>
								<td>
								<input class='btn btn-inverse' type='button' name='Extract' value='E' onclick="extract(${mainPage.sequence})" />
								<!-- <input class='btn btn-inverse' type='button' name='transform' value='T' hidden="true" style='margin-left:5px;'/>
				                <input class='btn btn-inverse' type='button' name='delete' value='-' hidden="true" style='margin-left:5px;'/> -->
				                </td>
				                <td><input id="SfdcId${mainPage.sequence}" name='SfdcId${mainPage.sequence}' type="hidden" value="${mainPage.sfdcId}"></td>
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
							<!-- <input class="btn btn-block btn-inverse" type="button"
							name="Extract" value="Extract All" /> -->
						</td>
					</tr>
					<tr>
						<td style="float: left;  padding: 50px; width: 450px !Important; padding-top: 10px; padding-bottom: 10px;">
							<!-- <input type="text" style="width:100%;" placeholder="CSV Location" id="datafileUrl" /> -->
							<!-- File Uplaod  -->
							<form id="uploadForm" method="POST" onsubmit="return validateUploadForm();" target="formSending" action="uploadFile" enctype="multipart/form-data">
							<table>
							<tr>
							<td> <input type="text" id="fileNameID" placeholder="Enter file name" name="name"></td>
							<td><input id="fileID" type="file" name="file"></td>
							<td></td>
							</tr>
							<tr>
							<td colspan="3"> <input class="btn btn-block btn-inverse"id="csvUploadID" type="submit" value="Upload" target="formSending"></td>
							</tr>
							<tr>
							<td colspan="2">
							<iframe name="formSending" style="height: 50px;width:100%;border: 0px none;font-family:verdana;"></iframe>
							</td>
							</tr>
							</table>
							</form>
							
						</td>
						<td
							style="float: right; padding: 50px;width:350px !Important; padding-top: 10px; padding-bottom: 10px;">
							<input class="btn btn-block btn-inverse" type="button"
							name="Extract" value="Migrate To SFDC" id="dataloadtoSFDC" onclick='initiateDataLoad()'/> 
						</td>
					</tr>
					<tr>
						<td colspan="2"
							style="float: right; width:350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							<!-- <input class="btn btn-block btn-inverse" type="button"
							name="Extract" value="Create CSV File" /> -->
						</td>
					</tr>
					<tr>
						<td colspan="2"
							style="float: right; width: 350px; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
							<!-- <input class="btn btn-block btn-inverse" type="button"
							name="Extract" value="Migrate To SFDC" id="dataloadtoSFDC" onclick='initiateDataLoad()'/>  -->
						</td>
					</tr>	

				</table>
				
			</div> 
		</div>

<div id=obj hidden="true">
 <input type="text" id = "objName" placeholder="Enter Object Name"><button type='button' style='display: inline;' id="search" onclick='getObjectName()'><span class='glyphicon glyphicon-search'></span></button>
 </div>
 
 <div id=objSFDC hidden="true">
 <input type="text" id = "SFDCObjName" placeholder="Enter Object Name"><button type='button' style='display: inline;' id="search" onclick='getSFDCObjectName()'><span class='glyphicon glyphicon-search'></span></button>
 </div>
	</div>
	
</body>
</html>
