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
	
	<link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>

<!-- Optional theme -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">
	var rowNum = 1;
	var primBaseTable;
	var selectedSFDCChildObj;
	$(document).ready(function() {
		$('body').delegate('.sblFldColFrgnUpdate', 'change', function() {
			var slctdSblFldOption = $(this).val();
			var slctdSblFldId = $(this).attr('id');
			var parentRow = $(this).parent().parent().attr("id");
			
			var rowNum = $(this).closest('tr').index();
			 $.ajax({
					type : "GET",
					url : "getFieldColumnMultiVal",
				 	data :
				 		{
				 		sblFldValSlctd:slctdSblFldOption ,
				 		rowNum:rowNum
				 		},
				 		contentType : 'application/text',
				 		success : function(response) {
						var fieldColmnRsponse=response;
						
						$("#relationtype"+parentRow).val(fieldColmnRsponse[0]);
						$("#childentity"+parentRow).val(fieldColmnRsponse[1]);
						$("#childtable"+parentRow).val(fieldColmnRsponse[2]);
						$("#childField"+parentRow).val(fieldColmnRsponse[3]);
						$("#intertable"+parentRow).val(fieldColmnRsponse[4]);
						$("#interparent"+parentRow).val(fieldColmnRsponse[5]);
						$("#interchild"+parentRow).val(fieldColmnRsponse[6]);
						//$("#sfdcObjectName"+parentRow).val(fieldColmnRsponse[6]);
						var index=Number(rowNum)-1;
						if(fieldColmnRsponse[0]!=null && fieldColmnRsponse[0]=="1:M"){
							$("#JuncObj"+parentRow).attr("disabled", true);
							$("#JuncObjrowBut"+index).attr("disabled", true);
							$("#JuncObjParentFieldropdown"+index).attr("disabled", true);
							$("#parntRltnNme"+parentRow).attr("disabled", true);
							$("#parntlookUpExtrnl"+parentRow).attr("disabled", true);
							$("#JuncObjChildFieldropdown"+index).attr("disabled", true);
							$("#childRltnNme"+parentRow).attr("disabled", true);
							$("#childlookUpExtrnl"+parentRow).attr("disabled", true);
							
							$("#sfdcchild"+parentRow).attr("disabled", false);
							$("#lookUpFieldropdown"+index).attr("disabled", false);
							$("#sfdcChildBut"+index).attr("disabled", false);
							$("#lookUpRltnNme"+parentRow).attr("disabled", false);
							$("#lookUpExtrnl"+parentRow).attr("disabled", false);
							
						}else if(fieldColmnRsponse[0]!=null && fieldColmnRsponse[0]=="M:M"){
							$("#lookUpFieldropdown"+index).attr("disabled", true);
							$("#lookUpRltnNme"+parentRow).attr("disabled", true);
							$("#lookUpExtrnl"+parentRow).attr("disabled", true);
							
							$("#sfdcChildBut"+index).attr("disabled", false);
							$("#JuncObj"+parentRow).attr("disabled", false);
							$("#JuncObjrowBut"+index).attr("disabled", false);
							$("#JuncObjParentFieldropdown"+index).attr("disabled", false);
							$("#parntRltnNme"+parentRow).attr("disabled", false);
							$("#parntlookUpExtrnl"+parentRow).attr("disabled", false);
							$("#JuncObjChildFieldropdown"+index).attr("disabled", false);
							$("#childRltnNme"+parentRow).attr("disabled", false);
							$("#childlookUpExtrnl"+parentRow).attr("disabled", false);
							
						}
						
						
						
						/* 	if(response != null && response.length != 0){
							$("#joinName"+parentRow).val(fieldColmnRsponse[0]);
							$("#clmnNm"+parentRow).val(fieldColmnRsponse[1]);
							$("#joinCondition"+parentRow).val(fieldColmnRsponse[2]);
							$("#frgnKey"+parentRow).val(fieldColmnRsponse[3]);
							}else{
								  $("#joinName"+parentRow).val("");
								  $("#clmnNm"+parentRow).val("");
								  $("#joinCondition"+parentRow).val("");
								  $("#frgnKey"+parentRow).val("");
							} */
						},
				 		error: function(errorThrown){
				 			alert(errorThrown);
				 	      /*   alert("No foreign key an column names for the selected siebel field.");
				 	        $("#joinName"+parentRow).val("");
							$("#clmnNm"+parentRow).val("");
							$("#joinCondition"+parentRow).val("");
							$("#frgnKey"+parentRow).val(""); */
				 	    } 
			 });  
		});
		
	
	
	$('body').delegate('.lookupFieldUpdate', 'change', function() {
		var slctdSlsFrcFldOption = $(this).attr('value');
		var slctdSblFldId = $(this).attr('id');
		var parentRow = $(this).parent().parent().attr("id");
		var SFDCObject=$("#sfdcchild"+parentRow).attr('value');
		 $.ajax({
				type : "GET",
				url : "getMultivalSFDCLookUpInfo",
			 	data :
			 		{
			 		slctdSlsFrcFldOption:slctdSlsFrcFldOption,
			 		SFDCObject:SFDCObject
			 		},
			 		contentType : 'application/text',
			 		success : function(response) {
					var fieldColmnRsponse=response;
						if(response != null && response.length != 0){
							$("#lookUpRltnNme"+parentRow).val(fieldColmnRsponse[0]);
						
							if(fieldColmnRsponse[2] != null && fieldColmnRsponse[2].length > 0){
								$("#lookUpExtrnl"+parentRow).val(fieldColmnRsponse[2][0].label);
								/* $("#lookUpExtrnl"+parentRow).attr("readonly", true); */
							}else{
								$("#lookUpExtrnl"+parentRow).val("");
								/* $("#lookUpExtrnl"+parentRow).attr("readonly", true); */
							}
						}else{
							//$("#lookUpField"+parentRow).attr();	
							$("#lookUpRltnNme"+parentRow).val("");
							$("#lookUpExtrnl"+parentRow).val("");
						}
					},
			 		error: function(errorThrown){
			 			$("#lookUpRltnNme"+parentRow).val("");
						$("#lookUpExtrnl"+parentRow).val("");
			 	    } 
		 });  
	});
	
	$('body').delegate('.JuncObjParentFieldUpdate', 'change', function() {
		var slctdSlsFrcFldOption = $(this).attr('value');
		var slctdSblFldId = $(this).attr('id');
		var parentRow = $(this).parent().parent().attr("id");
		var SFDCObject=$("#JuncObj"+parentRow).attr('value');
		 $.ajax({
				type : "GET",
				url : "getMultivalJuncLookUpInfo",
			 	data :
			 		{
			 		slctdSlsFrcFldOption:slctdSlsFrcFldOption,
			 		SFDCObject:SFDCObject
			 		},
			 		contentType : 'application/text',
			 		success : function(response) {
					var fieldColmnRsponse=response;
						if(response != null && response.length != 0){
							$("#parntRltnNme"+parentRow).val(fieldColmnRsponse[0]);
						
							if(fieldColmnRsponse[2] != null && fieldColmnRsponse[2].length > 0){
								$("#parntlookUpExtrnl"+parentRow).val(fieldColmnRsponse[2][0].label);
							}else{
								$("#parntlookUpExtrnl"+parentRow).val("");
							}
						}else{
							$("#parntRltnNme"+parentRow).val("");
							$("#parntlookUpExtrnl"+parentRow).val("");
						}
					},
			 		error: function(errorThrown){
			 			$("#parntRltnNme"+parentRow).val("");
						$("#parntlookUpExtrnl"+parentRow).val("");
			 	    } 
		 });  
	});
	
	$('body').delegate('.JuncObjChildFieldUpdate', 'change', function() {
		var slctdSlsFrcFldOption = $(this).attr('value');
		var slctdSblFldId = $(this).attr('id');
		var parentRow = $(this).parent().parent().attr("id");
		var SFDCObject=$("#JuncObj"+parentRow).attr('value');
		 $.ajax({
				type : "GET",
				url : "getMultivalJuncLookUpInfo",
			 	data :
			 		{
			 		slctdSlsFrcFldOption:slctdSlsFrcFldOption,
			 		SFDCObject:SFDCObject
			 		},
			 		contentType : 'application/text',
			 		success : function(response) {
					var fieldColmnRsponse=response;
						if(response != null && response.length != 0){
							$("#childRltnNme"+parentRow).val(fieldColmnRsponse[0]);
						
							if(fieldColmnRsponse[2] != null && fieldColmnRsponse[2].length > 0){
								$("#childlookUpExtrnl"+parentRow).val(fieldColmnRsponse[2][0].label);
							}else{
								$("#childlookUpExtrnl"+parentRow).val("");
							}
						}else{
							$("#childRltnNme"+parentRow).val("");
							$("#childlookUpExtrnl"+parentRow).val("");
						}
					},
			 		error: function(errorThrown){
			 			$("#childRltnNme"+parentRow).val("");
						$("#lookUpExtrnl"+parentRow).val("");
			 	    } 
		 });  
	});
	
	});
	
//	);
	

	function addRow() {
		if ($("#rowCount").val() != '') {
			rowNum = Number($("#rowCount").val());
		}

		$("#rowCount").val(rowNum + 1);
		

		var checkFlag = "checkFlag" + rowNum;
		var siebleBaseTable = "relationtyperow" + rowNum;
		var siebleBaseTableColumn = "relationtyperow" + rowNum;
		var foreignFieldMapping = "childentityrow" + rowNum;
		var sfdcObjectName = "childtablerow" + rowNum;
		var dropdown = "dropdown" + rowNum;
		var sfdcId = "sfdcId" + rowNum;
		$("#masterTable tbody")
				.append(

						"<tr id=row"+rowNum+" style='border-bottom: 3px'>"
								+ "<td><input name='"+checkFlag+"' id='"+checkFlag+"'  type='checkbox' checked='checked'></td>"
								+ "<td><select name=sblFieldNmdropdown"+rowNum+" id=sblFieldNmdropdown"+rowNum+" class='sblFldColFrgnUpdate'>"
								+ "<c:if test='${not empty sbllFlddNmList}'>"
								+ "<c:forEach items="${sbllFlddNmList}" var="field" varStatus="status">"
								+ "<option value='${field}'>${field}</option>"
								+ "</c:forEach></c:if></select></td>"
								/* + "<td><input value='${mappingData[0].siebleBaseTable}' id='"+siebleBaseTable+"' name='"+siebleBaseTable+"' style='margin-left: 45px;' /></td>" */
								+ "<td><input name='"+siebleBaseTableColumn+"' id='"+siebleBaseTableColumn+"'  style='margin-left:35px;'/></td>"
								+ "<td><input  name='"+foreignFieldMapping+"' id='"+foreignFieldMapping+"'  style='margin-left:35px;'/></td>"
								+ "<td><input  name='"+sfdcObjectName+"' id='"+sfdcObjectName+"' style='margin-left:35px;'/></td>"
								+ "<td><input  name='childFieldrow"+rowNum+"' id='childFieldrow"+rowNum+"' style='margin-left:35px;'/></td>"
								+ "<td><input  name='intertablerow"+rowNum+"' id='intertablerow"+rowNum+"' style='margin-left:35px;'/></td>"
								+ "<td><input  name='interparentrow"+rowNum+"' id='interparentrow"+rowNum+"' style='margin-left:35px;'/></td>"
								+ "<td><input  name='interchildrow"+rowNum+"' id='interchildrow"+rowNum+"' style='margin-left:35px;'/></td>"
								
								/* + "<td> <table> <tr> <td> <input  name='sfdcchildrow"+rowNum+"' id='sfdcchildrow"+rowNum+"' style='margin-left:35px;'/> </td> <td><button type='button'id='sfdcChildBut"+rowNum+"' style='display: inline;margin-right:35px;' onclick='getSFDCPopup("+rowNum+")'><span class='glyphicon glyphicon-search'></span></button></td></tr></table></td>" */
								+ "<td> <input  name='sfdcchildrow"+rowNum+"' id='sfdcchildrow"+rowNum+"' style='margin-left:35px;'/></td><td> <button type='button'id='sfdcChildBut"+rowNum+"' style='display: inline;margin-right:35px;' onclick='getSFDCPopup("+rowNum+")'><span class='glyphicon glyphicon-search'></span></button></td>"
								+ "<td align='center'><select name='lookUpFieldropdown"+rowNum+"' id=lookUpFieldropdown"+rowNum+" class='lookupFieldUpdate'>"
								+ "<c:if test='${not empty lookUpFieldList}'>"
								+ "<c:forEach items="${lookUpFieldList}" var="field" varStatus="status">"
								+ " <option value='${field.name}'>${field.label}</option>"
								+ "</c:forEach></c:if></select></td>"
								+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;' name='lookUpRltnNmerow"+rowNum+"' id='lookUpRltnNmerow"+rowNum+"'></td>"
								+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;' name=lookUpExtrnlrow"+rowNum+" id=lookUpExtrnlrow"+rowNum+"></td>"
								
								/* + "<td><table><tr><td><input  name='JuncObjrow"+rowNum+"' id='JuncObjrow"+rowNum+"' style='margin-left:35px;'/></td><td><button type='button'id='JuncObjrowBut"+rowNum+"' style='display: inline;margin-right:35px;' onclick='getJuncObjPopup("+rowNum+")'><span class='glyphicon glyphicon-search'></span></button></td></tr></table></td>" */
								+ "<td> <input  name='JuncObjrow"+rowNum+"' id='JuncObjrow"+rowNum+"' style='margin-left:35px;'/> </td><td><button type='button'id='JuncObjrowBut"+rowNum+"' style='display: inline;margin-right:35px;' onclick='getJuncObjPopup("+rowNum+")'><span class='glyphicon glyphicon-search'></span></button></td>"
								+ "<td align='center'><select name='JuncObjParentFieldropdown"+rowNum+"' id=JuncObjParentFieldropdown"+rowNum+" class='JuncObjParentFieldUpdate'>"
								+ "<c:if test='${not empty lookUpFieldList}'>"
								+ "<c:forEach items="${jnObjParentList}" var="field" varStatus="status">"
								+ " <option value='${field.name}'>${field.label}</option>"
								+ "</c:forEach></c:if></select></td>"
								+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;' name='parntRltnNmerow"+rowNum+"' id='parntRltnNmerow"+rowNum+"'></td>"
								+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;margin-right: 35px;' name='parntlookUpExtrnlrow"+rowNum+"' id='parntlookUpExtrnlrow"+rowNum+"'></td>"
								
								
								+ "<td align='center'><select name='JuncObjChildFieldropdown"+rowNum+"' id=JuncObjChildFieldropdown"+rowNum+" class='JuncObjChildFieldUpdate'>"
								+ "<c:if test='${not empty lookUpFieldList}'>"
								+ "<c:forEach items="${jnObjChildList}" var="field" varStatus="status">"
								+ " <option value='${field.name}'>${field.label}</option>"
								+ "</c:forEach></c:if></select></td>"
								+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;' name='childRltnNmerow"+rowNum+"' id='childRltnNmerow"+rowNum+"'></td>"
								+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;' name='childlookUpExtrnlrow"+rowNum+"' id='childlookUpExtrnlrow"+rowNum+"'></td>"
								
								
								
							/* 	+ "<td><select name='"+dropdown+"' id='"+dropdown+"'>"
								+ " <option value=''></option>"
								+ " <c:if test="${not empty mappingField}"> "
								+ "<c:forEach items="${mappingField}" var="field" varStatus="status">"
								+ " <option value='${field}'>${field}</option>"
								+ "    </c:forEach> "
								+ "  </c:if>"
								+ "</select></td>" */
								+ "</td><input type='hidden' id='"+sfdcId+"' name='"+sfdcId+"'></tr>"

				);

	}
	
	  function getSFDCPopup(rowNum){
		  //$("#indicator").css({'display':'none'});
			var SFDCObjectId="sfdcchildrow"+(rowNum);
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
							populateLookUpDropdown(SFDCObject,rowNum);

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
					//	$("#sfdcchildrow").val("");
						$(this).dialog("close");
					}
				},
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
		
		 function getJuncObjPopup(rowNum){
			  //$("#indicator").css({'display':'none'});
				var SFDCObjectId="JuncObjrow"+(rowNum);
				selectedSFDCChildObj=$("#sfdcchildrow"+rowNum).val();
		/* 		if(selectedSFDCChildObj==null ||selectedSFDCChildObj=""){
					alert("Please select the SFDC Child Object");
					return;
				}
				 */
				$("#objJunction").dialog({title: "Select an Junction Object",
					width: 500,
					height: 500,
					buttons: {
						"OK": function () {
							var SFDCObject =$('input[name=selectedObject]:radio:checked').val();
							if(SFDCObject!=null)
								{
								$("#"+SFDCObjectId).val(SFDCObject);
								if(document.getElementById("dyncTblCntntjunc")){
									var noOfChilds = $("#dyncTblCntntjunc").children().length;
									if(noOfChilds > 1){
										$("#dyncTblCntntjunc").children().remove();
									}
								}
							//	$("#sfdcchildrow").val("");
								populateJunctDropdown(SFDCObject,rowNum);

								$(this).dialog("close");
								}
								else
									{
									alert("Please select a value from list");
									}
							
							
					
						},"Close": function(){
							if(document.getElementById("dyncTblCntntjunc")){
								var noOfChilds = $("#dyncTblCntntjunc").children().length;
								if(noOfChilds > 1){
									$("#dyncTblCntntjunc").children().remove();
								}
							}
						//	$("#sfdcchildrow").val("");
							$(this).dialog("close");
						}
					},
					});
				}
		
		
		
		function getJunctionObjectName()
		{
			var objName = $("#JunctObjName").val();
			if(document.getElementById("dyncTblCntntjunc"))
				{
				var noOfChilds = $("#dyncTblCntntjunc").children().length;
			if(noOfChilds > 1){
				$("#dyncTblCntntjunc").children().remove();
			}
				}
			$("#objJunction").append("<div id='dyncLoaderjunc' style='text-align:center;margin-top:70px;'><img src='resources/images/ajax-loader.gif'/></div>");
			  $.ajax({
				type : "GET",
				url : "JunctionObjectList",
				data : {objectName:objName,selectedSFDCChildObj:selectedSFDCChildObj},
			 	success : function(data){
						displayJuncObjTable(data);
					}  
				});   
		}
		
		function displayJuncObjTable(data)
		{
			$("#dyncLoaderjunc").remove();
			if(data.length!=0)
				{
				
				if(document.getElementById("dyncTblCntntjunc"))
				{
					var noOfChilds = $("#dyncTblCntntjunc").children().length;
					if(noOfChilds > 1){
						$("#dyncTblCntntjunc").children().remove();
					}
						for(var i=0 ; i<data.length ;i++ ){
							var tblName = data[i];	
							
							$("#dyncTblCntntjunc").append("<input type='radio' name='selectedObject' value='"+tblName.objName+"'><span>"+tblName.objName+"</span><br/>");
												
						}
					
					}
				
				else{
					$("#objJunction").append("<br/>");
					$("#objJunction").append("<div id='dyncTblCntntjunc'>");
					for(var i=0 ; i<data.length ;i++ ){
						var tblName = data[i];					
						$("#dyncTblCntntjunc").append("<input type='radio' name='selectedObject' value='"+tblName.objName+"'><span>"+tblName.objName+"</span><br/>");
					}
					$("#objJunction").append("</div>");
				}
				}
			else
				{
				alert("No Object Found");
				}
			
			
		}
		
		
		function populateJunctDropdown(SFDCObject,rowNum){
			 $.ajax({
					type : "GET",
					url : "getJuncDropDownList",
				 	data :
				 		{
				 		SFDCObject:SFDCObject
				 		},
				 		contentType : 'application/text',
				 		success : function(response) {
				 			var select = $("#JuncObjChildFieldropdown"+rowNum);
				 			var options="";
				 			for(var j=0;j<response.length;j++){
				 				options=options +  "<option value='"+response[j].name+"'>"+response[j].label+"</option>"
				 			}
				 			$("#JuncObjChildFieldropdown"+rowNum).html(options);
				 			$("#JuncObjParentFieldropdown"+rowNum).html(options);
				 			$("#JuncObjChildFieldropdown"+rowNum).attr("selectedIndex", -1);
				 			$("#JuncObjParentFieldropdown"+rowNum).attr("selectedIndex", -1);
				 			
						},
				 		error: function(errorThrown){
				 			alert(errorThrown);
				 	    } 
			 });
			
		}
		
		function populateLookUpDropdown(SFDCObject,rowNum){
			 $.ajax({
					type : "GET",
					url : "getLookupDropDownList",
				 	data :
				 		{
				 		SFDCObject:SFDCObject
				 		},
				 		contentType : 'application/text',
				 		success : function(response) {
				 			var options="";
				 			for(var j=0;j<response.length;j++){
				 				options=options +  "<option value='"+response[j].name+"'>"+response[j].label+"</option>"
				 			}
				 			$("#lookUpFieldropdown"+rowNum).html(options);
				 			$("#lookUpFieldropdown"+rowNum).attr("selectedIndex", -1);
						},
				 		error: function(errorThrown){
				 			alert(errorThrown);
				 	    } 
			 });
			
		}
	function openModal() {
	        document.getElementById('modal').style.display = 'block';
	        document.getElementById('fade').style.display = 'block';
	}

	function closeModal() {
	    document.getElementById('modal').style.display = 'none';
	    document.getElementById('fade').style.display = 'none';
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
						<br />
						<tr>
							<td colspan="2"
								style="float: right; width: 350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">Multi
								Field Mapping</td>
						</tr>

						<div class="sampleContainer" style="width: 350px;">
							<table class="table" style="margin: 2px;">
								<tr>
									<br />
									<td style="width: 65px; text-align: left;" align="left">Siebel
										Entity</td>

									<td style="width: 45px; text-align: left;" align="left">${hdrValues[0]}</td>
					

								</tr>
								<tr>
									<br />
									<td style="width: 45px; text-align: left;" align="left">Siebel Base table</td>

									<td style="width: 45px; text-align: left;" align="left">${hdrValues[1]}</td>


								</tr>
								<tr>
									<td style="width: 65px; text-align: left;" align="left">SFDC
										Entity</td>

									<td style="width: 45px; text-align: left;" align="left">${hdrValues[2]}</td>
								</tr>


							</table>
						</div>

					</div>
				</div>
				<button class="btn btn-primary" id="addRow" onclick="addRow()">[+]</button>
				<form:form method="post" action="multiValmappingSave" modelAttribute="data">
					<div class="mappingContainer" style="width: 100%;">
						<table id="masterTable" style="width: 100%;border-collapse: separate;border-spacing: 0 1em;">
							<br />
							<br />
							<thread>
							<tr>
								<th class="table_header_details" style="float: center;">Select</th>
								<th class="table_header_details" style="float: center;">Siebel
									Field</th>
								<th class="table_header_details" style="float: center;">Relationship
									Type</th>
								<th class="table_header_details" style="float: center;">Child
									Entity</th>
								<th class="table_header_details" style="float: center;">Child
									Table</th>
								<th class="table_header_details" style="float: center;">Child
									Field</th>
								<th class="table_header_details" style="float: center;">Inter
									Table</th>
								<th class="table_header_details" style="float: center;">Inter
									Parent Column</th>
								<th class="table_header_details" style="float: center;">Inter
									Child Column</th>
								<!-- <th class="table_header_details" style="float: center;">Join
									Condition</th> -->
								<th colspan="2" class="table_header_details" style="float: center;">SFDC
									Child Object</th>
								<th class="table_header_details" style="float: center;">Look Up
									 Field</th>
								<th class="table_header_details" style="float: center;">Look Up
									 Relationship Name</th>
								<th class="table_header_details" style="float: center;">Look Up
									 External ID</th>
								<th colspan="2" class="table_header_details" style="float: center;">Junction
									Object</th>
								<th class="table_header_details" style="float: center;">Junction
									Object Parent Field</th>
								<th class="table_header_details" style="float: center;">Parent
									 Relationship Name</th>
								<th class="table_header_details" style="float: center;">Parent Lookup
									 External ID</th>
								<th class="table_header_details" style="float: center;">Junction
									Object Child Field</th>
								<th class="table_header_details" style="float: center;">Child
									 Relationship Name</th>
								<th class="table_header_details" style="float: center;">Child Look Up
									 External ID</th>
								<!-- <th class="table_header_details" style="float: center;">SFDC
									Field Description</th>
								<th class="table_header_details" style="float: center;">Lov
									Mapping</th> -->
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
							
							
							<c:if test="${not empty mappingData}" var="mapping">
									<c:forEach items="${mappingData}" var="mapping" varStatus="status"> 
										<tr id="row${mapping.mappingSeq}" style="border-bottom: 3px">
											<td>
												<c:choose>
												<c:when test="${mapping.checkFlag}">
												<input name="select${mapping.mappingSeq}" type='checkbox' checked="checked">
												</c:when>
												<c:otherwise>
												<input name="select${mapping.mappingSeq}" type='checkbox'>
												</c:otherwise>
												</c:choose>
											</td>
											<td>
													<select name="sblFieldNmdropdown${mapping.mappingSeq}"
														id="sblFieldNmdropdown${mapping.mappingSeq}" class='sblFldColFrgnUpdate'>
															<c:if test="${not empty sbllFlddNmList}">
																<c:forEach items="${sbllFlddNmList}" var="field" varStatus="status">
																	<c:set var="temp" value="${field}" />
			                                						<c:set var="temp1" value="${mapping.siebelField}" />
																<%-- 	temp: [<c:out value="${temp}" />]
			                                						temp1: [<c:out value="${temp1}" />] --%>
																	<c:choose>
																		<c:when test="${temp == temp1}">
															                <option value='${temp1}' selected>${temp1}</option>
															            </c:when>
															            <c:otherwise>
															                <option value='${field}'>${field}</option>
															            </c:otherwise>
														            </c:choose>
																</c:forEach>
															</c:if>
													</select>	
											</td>
											<td>
												<input type="text" style="margin-left: 35px;" id="relationtyperow${mapping.mappingSeq}" name="relationtyperow${mapping.mappingSeq}" value="${mapping.relationType}" />
											</td>
											<td>
												<input type="text" style="margin-left: 35px;" id="childentityrow${mapping.mappingSeq}" name="childentityrow${mapping.mappingSeq}" value="${mapping.childEntity}" />
											</td>	
											<td>
												<input type="text" style="margin-left: 35px;" id="childtablerow${mapping.mappingSeq}" name="childtablerow${mapping.mappingSeq}" value="${mapping.childTable}" />
											</td>
											<td>
												<input type="text" style="margin-left: 35px;" id="childFieldrow${mapping.mappingSeq}" name="childFieldrow${mapping.mappingSeq}" value="${mapping.childField}" />
											</td>	
											<td>
												<input type="text" style="margin-left: 35px;" id="intertablerow${mapping.mappingSeq}" name="intertablerow${mapping.mappingSeq}" value="${mapping.interTable}" />
											</td>
											<td>
												<input type="text" style="margin-left: 35px;" id="interparentrow${mapping.mappingSeq}" name="interparentrow${mapping.mappingSeq}" value="${mapping.interParentColumn}" />
											</td>
											<td>
												<input type="text" style="margin-left: 35px;" id="interchildrow${mapping.mappingSeq}" name="interchildrow${mapping.mappingSeq}" value="${mapping.interChildColumn}" />
											</td>
										<%-- 	<td>
												<input type="text" style="margin-left: 35px;" id="joinCondition${mapping.mappingSeq}" name="joinCondition${mapping.mappingSeq}" value="${mapping.joinCondition}" />
											</td> --%>
											<td>
												<input type="text" style="margin-left: 35px;" id="sfdcchildrow${mapping.mappingSeq}" name="sfdcchildrow${mapping.mappingSeq}" value="${mapping.sfdcChildObject}" />
											</td><td>	
												<button type="button" id="sfdcChildBut${mapping.mappingSeq}" style='display: inline;margin-right:35px;' onclick="getSFDCPopup(${mapping.mappingSeq})"><span class='glyphicon glyphicon-search'></span></button>
											</td>
											<td align="center">
													<select name="lookUpFieldropdown${mapping.mappingSeq}"
														id="lookUpFieldropdown${mapping.mappingSeq}" class='lookupFieldUpdate' <c:if test="${mapping.relationType=='M:M'}"><c:out value="disabled='disabled'"/></c:if>>
															<c:if test="${not empty mapping.lookupObjList}">
																<c:forEach items="${mapping.lookupObjList}" var="field" varStatus="status">
																	<c:set var="temp" value="${field}" />
			                                						<c:set var="temp1" value="${mapping.lookupField}" />
																<%-- 	temp: [<c:out value="${temp}" />]
			                                						temp1: [<c:out value="${temp1}" />] --%>
																	<c:choose>
																		<c:when test="${temp.name== temp1}">
															                <option value='${temp1}' selected>${temp.label}</option>
															            </c:when>
															            <c:otherwise>
															                <option value='${field.name}'>${field.label}</option>
															            </c:otherwise>
														            </c:choose>
																</c:forEach>
															</c:if>
													</select>	
											</td>
											<td>
											<input type="text" style="margin-left: 35px;" id="lookUpRltnNmerow${mapping.mappingSeq}" name="lookUpRltnNmerow${mapping.mappingSeq}" value="${mapping.lookupRelationName}" <c:if test="${mapping.relationType=='M:M'}"><c:out value="disabled='disabled'"/></c:if>/>
											</td>
											<td>
											<input type="text" style="margin-left: 35px;" id="lookUpExtrnlrow${mapping.mappingSeq}" name="lookUpExtrnlrow${mapping.mappingSeq}" value="${mapping.lookupExternalId}" <c:if test="${mapping.relationType=='M:M'}"><c:out value="disabled='disabled'"/></c:if>/>
											</td>
											
											<td>
												<input type="text" style="margin-left: 35px;" id="JuncObjrow${mapping.mappingSeq}" name="JuncObjrow${mapping.mappingSeq}" value="${mapping.junctionObject}" <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if> />
											</td><td>
												<button type="button" id="JuncObjrow${mapping.mappingSeq}" style='display: inline;margin-right:35px;' onclick="getJuncObjPopup(${mapping.mappingSeq})" <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if>><span class='glyphicon glyphicon-search'></span></button> 
											</td>
											
											<td align="center">
													<select name="JuncObjParentFieldropdown${mapping.mappingSeq}"
														id="JuncObjParentFieldropdown${mapping.mappingSeq}" class='JuncObjParentFieldUpdate' <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if>>
															<c:if test="${not empty mapping.jnObjParentList}">
																<c:forEach items="${mapping.jnObjParentList}" var="field" varStatus="status">
																	<c:set var="temp" value="${field}" />
			                                						<c:set var="temp1" value="${mapping.junctionObjParentField}" />
																<%-- 	temp: [<c:out value="${temp}" />]
			                                						temp1: [<c:out value="${temp1}" />] --%>
																	<c:choose>
																		<c:when test="${temp.name == temp1}">
															                <option value='${temp1}' selected>${temp.label}</option>
															            </c:when>
															            <c:otherwise>
															                <option value='${field.name}'>${field.label}</option>
															            </c:otherwise>
														            </c:choose>
																</c:forEach>
															</c:if>
													</select>	
											</td>
											<td>
											<input type="text" style="margin-left: 35px;" id="parntRltnNmerow${mapping.mappingSeq}" name="parntRltnNmerow${mapping.mappingSeq}" value="${mapping.parentRelationName}" <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if>/>
											</td>
											<td>
											<input type="text" style="margin-left: 35px;" id="parntlookUpExtrnlrow${mapping.mappingSeq}" name="parntlookUpExtrnlrow${mapping.mappingSeq}" value="${mapping.parentExternalId}" <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if>/>
											</td>
											
											<td align="center">
													<select name="JuncObjChildFieldropdown${mapping.mappingSeq}"
														id="JuncObjChildFieldropdown${mapping.mappingSeq}" class='JuncObjChildFieldUpdate' <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if>>
															<c:if test="${not empty mapping.jnObjChildList}">
																<c:forEach items="${mapping.jnObjChildList}" var="field" varStatus="status">
																	<c:set var="temp" value="${field}" />
			                                						<c:set var="temp1" value="${mapping.junctionObjectChildField}" />
																<%-- 	temp: [<c:out value="${temp}" />]
			                                						temp1: [<c:out value="${temp1}" />] --%>
																	<c:choose>
																		<c:when test="${temp.name == temp1}">
															                <option value='${temp1}' selected>${temp.label}</option>
															            </c:when>
															            <c:otherwise>
															                <option value='${field.name}'>${field.label}</option>
															            </c:otherwise>
														            </c:choose>
																</c:forEach>
															</c:if>
													</select>	
											</td>
											<td>
											<input type="text" style="margin-left: 35px;" id="childRltnNmerow${mapping.mappingSeq}" name="childRltnNmerow${mapping.mappingSeq}" value="${mapping.childRelationName}" <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if>/>
											</td>
											<td>
											<input type="text" style="margin-left: 35px;" id="childlookUpExtrnlrow${mapping.mappingSeq}" name="childlookUpExtrnlrow${mapping.mappingSeq}" value="${mapping.childExternalId}" <c:if test="${mapping.relationType=='1:M'}"><c:out value="disabled='disabled'"/></c:if> />
											</td>
										
											<input type='hidden' id="sfdcId${mapping.mappingSeq}" name="sfdcId${mapping.mappingSeq}" value="${mapping.id}" />
										
										
										</tr>
										</c:forEach>
							</c:if>	
										


						</table>

					</div>
					 <input id="rowCount" name='rowCount' type="hidden"
								value="${mappingData.size()}"> 
					
					<div class="buttonContainer">

						<table style="border: 0">

							<tr>
								<td colspan="2"
									style="float: right; width: 350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
									<input class="btn btn-block btn-inverse" type="submit"
									name="Extract" value="Done" /> <!-- <button id="cancel" type="button" style="float: right;"
						class="btn btn-block btn-inverse">Done</button> --> <!-- <button id="cancel" type="submit" style="float: right;"
						class="btn btn-block btn-inverse">Done</button> -->

								</td>
							</tr>


						</table>

					</div>
			</div>

<input id="rowId" name='rowId' type="hidden" value="${rowId}">

			</form:form>

		</div>
		
<div id=objSFDC hidden="true">
<input type="text" id = "SFDCObjName" placeholder="Enter Object Name"><button type='button' style='display: inline;' id="search" onclick='getSFDCObjectName()'><span class='glyphicon glyphicon-search'></span></button>
</div>

<div id=objJunction hidden="true">
<input type="text" id = "JunctObjName" placeholder="Enter Object Name"><button type='button' style='display: inline;' id="searchJn" onclick='getJunctionObjectName()'><span class='glyphicon glyphicon-search'></span></button>
</div>

</body>
<script type="text/javascript">
	$("#cancel").click(function() {

		window.location.href = "Done";
	});
</script>
</html>