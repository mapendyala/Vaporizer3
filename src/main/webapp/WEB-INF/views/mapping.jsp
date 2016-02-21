<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	
<link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified JavaScriptt --->
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	
	<!-- Madhuri code -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript"
	src="<c:url value="/resources/json.min.js" /> "></script>	
	
<script type="text/javascript">
	var rowNum = 1;
	var primBaseTable;

	$(document).ready(function() {
		var txtLngth = $("#searchQry").val().length;
		$("#searchQry").attr('rows',(Number(txtLngth)/50)+1);
	/* 	$("#searchQry").attr('size',txtLngth);  */
	
		$('#submitForm').click(function(){
			 var msg = "Please ensure to click on Generate Extraction Query button if any changes are made to the field mappings.Do you wish to navigate to main page ? ";
			 var div = $("<div>" + msg + "</div>");
	            div.dialog({
	                title: "Confirmation required",
	                buttons: [
	                    {
	                        text: "Yes",
	                        click: function () {
	                        	div.dialog("close");
	                        	$('#mapForm').submit();
	                        }
	                    },
	                    {
	                        text: "No",
	                        click: function () {
	                            div.dialog("close");
	                        }
	                    }
	                ]
	            });
			
		});
	
		$('#generateExtractQuery').click(function(){
			var rowId = $('#rowId').attr('value');
			var sblBaseTbl = $('#sblBaseTbl').html();
			var sblTblNmVal = $('#sblEntity').html();
			var sfdcObject = $('#sfdcEntity').html();
			 $.ajax({
					type : "GET",
					url : "getextractQuery",
					timeout: 40000,
				 	data :
				 		{
				 		rowId:rowId,sblBaseTbl:sblBaseTbl,sblTblNmVal:sblTblNmVal,sfdcObject:sfdcObject
				 		},
				 		contentType : 'application/text',
				 		success : function(response) {
							if(response != null && response.length != 0){
								$("#extractionQuery").val(response);
							}
						},
				 		error: function(errorThrown){
				 			alert("Error while constructing extraction query.");
				 			$("#extractionQuery").val("");
				 	    } 
			 });  
		});
		
		$('body').delegate('.slsFrcFldUpdate', 'change', function() {
			var slctdSlsFrcFldOption = $(this).attr('value');
			var slctdSblFldId = $(this).attr('id');
			var parentRow = $(this).parent().parent().attr("id");
			 $.ajax({
					type : "GET",
					url : "getLookUpInfo",
				 	data :
				 		{
				 		slctdSlsFrcFldOption:slctdSlsFrcFldOption,
				 		rowNum:rowNum
				 		},
				 		contentType : 'application/text',
				 		success : function(response) {
						var fieldColmnRsponse=response;
							if(response != null && response.length != 0){
								$("#lookUpRltnNme"+parentRow).val(fieldColmnRsponse[0]);
								/* $("#lookUpRltnNme"+parentRow).attr("readonly", true); */ 
								$("#lookUpObj"+parentRow).val(fieldColmnRsponse[1]);
								/* $("#lookUpObj"+parentRow).attr("readonly", true); */
								var lookupfldrow = "lookUpField"+parentRow;
								$("#lookUpField"+parentRow).attr("checked", true);
								if(fieldColmnRsponse[2] != null && fieldColmnRsponse[2].length > 0){
									var mySelect = $("#lookUpExtrnl"+parentRow);
									for( var i=0;i<fieldColmnRsponse[2].length ;i++)
									{
										var text=fieldColmnRsponse[2][i].label;
										
										mySelect.append(
										        $('<option></option>').val(text).html(text)
										    );
							        }
									//$("#lookUpExtrnl"+parentRow).val(fieldColmnRsponse[2][0].label);
									/* $("#lookUpExtrnl"+parentRow).attr("readonly", true); */
								}else{
									
									$("#lookUpExtrnl"+parentRow+" option").remove();
									//$("#"+lookupfldrow+" option").remove();
									//$("#lookUpExtrnl"+parentRow).val("");
									/* $("#lookUpExtrnl"+parentRow).attr("readonly", true); */
								}
							}else{
								var lookupfldrow = "lookUpField"+parentRow;
								$("#lookUpField"+parentRow).attr();	
								$("#lookUpRltnNme"+parentRow).val("");
								/* $("#lookUpRltnNme"+parentRow).attr("disabled", "disabled");  */
								$("#lookUpObj"+parentRow).val("");
								/* $("#lookUpObj"+parentRow).attr("disabled", "disabled"); */
								$("#lookUpExtrnl"+parentRow).val("");
								/* $("#lookUpExtrnl"+parentRow).attr("disabled", "disabled"); */
								$("#lookUpField"+parentRow).attr("checked", false);
								
								$("#lookUpExtrnl"+parentRow+" option").remove();
							}
						},
				 		error: function(errorThrown){
				 			$("#lookUpRltnNme"+parentRow).val("");
				 		/* 	$("#lookUpRltnNme"+parentRow).attr("disabled", "disabled"); */
							$("#lookUpObj"+parentRow).val("");
						/* 	$("#lookUpObj"+parentRow).attr("disabled", "disabled"); */
							$("#lookUpExtrnl"+parentRow).val("");
							/* $("#lookUpExtrnl"+parentRow).attr("disabled", "disabled"); */
							$("#lookUpField"+parentRow).attr("checked", false);
				 	    } 
			 });  
		});
		
		$('body').delegate('.sblFldColFrgnUpdate', 'change', function() {
			var slctdSblFldOption = $(this).val();
			var slctdSblFldId = $(this).attr('id');
			var parentRow = $(this).parent().parent().attr("id");
			var rowNum = $(this).closest('tr').index();
			var sfdcidVal = $('#sfdcId'+rowNum).attr('value');
			if(sfdcidVal != null && sfdcidVal != '' && Number(sfdcidVal.length) > 0){
				var joinNameValue = $('#joinNamerow'+(Number(sfdcidVal.length)-1)).attr('value');
				alert("Please reselect other rows using the join name "+ joinNameValue + " if they are using the join condition of this row");
			}
			 $.ajax({
					type : "GET",
					url : "getFieldColumnVal",
				 	data :
				 		{
				 		sblFldValSlctd:slctdSblFldOption,
				 		rowNum:rowNum
				 		},
				 		contentType : 'application/text',
				 		success : function(response) {
						var fieldColmnRsponse=response;
							if(response != null && response.length != 0){
							$("#joinName"+parentRow).val(fieldColmnRsponse[0]);
							$("#clmnNm"+parentRow).val(fieldColmnRsponse[1]);
							$("#joinCondition"+parentRow).val(fieldColmnRsponse[2]);
							$("#frgnKey"+parentRow).val(fieldColmnRsponse[3]);
							}else{
								  $("#joinName"+parentRow).val("");
								  $("#clmnNm"+parentRow).val("");
								  $("#joinCondition"+parentRow).val("");
								  $("#frgnKey"+parentRow).val("");
							}
						},
				 		error: function(errorThrown){
				 	        $("#joinName"+parentRow).val("");
							$("#clmnNm"+parentRow).val("");
							$("#joinCondition"+parentRow).val("");
							$("#frgnKey"+parentRow).val("");
				 	    } 
			 });  
		});
		
	
	});
	
	
	function addRow() {
		
		if ($("#rowCount").val() != '') {
			rowNum = Number($("#rowCount").val())+1;
		}else{
			rowNum = 0;
		}
		
		$("#rowCount").val(rowNum);
		var checkFlag = "select" + rowNum;
		var siebleBaseTable = "siebleBaseTable" + rowNum;
		var siebleBaseTableColumn = "siebleBaseTableColumn" + rowNum;
		var foreignFieldMapping = "foreignFieldMapping" + rowNum;
		var sfdcObjectName = "sfdcObjectName" + rowNum;
		var dropdown = "dropdown" + rowNum;
		var sfdcId = "sfdcId" + rowNum;
	
		  $("#masterTable tbody").append(

					"<tr id=row"+rowNum+">"
							+ "<td><input name=select"+rowNum+" id=select"+rowNum+"  type='checkbox' checked='checked'></td>"
							+ "<td><select name=sblFieldNmdropdown"+rowNum+" id=sblFieldNmdropdown"+rowNum+" class='sblFldColFrgnUpdate'>"
							+ "<c:if test='${not empty sbllFlddNmList}'>"
							+ "<c:forEach items="${sbllFlddNmList}" var="field" varStatus="status">"
							+ "<option value='${field}'>${field}</option>"
							+ "</c:forEach></c:if></select></td>"
							+ "<td><input type='text' style='margin-left: 35px;' id=joinNamerow"+rowNum+" name=joinNamerow"+rowNum+" /></td>"
							+ "<td><input type='text' style='margin-left: 35px;' id=frgnKeyrow"+rowNum+" name=frgnKeyrow"+rowNum+" /></td>"
							+ "<td><input type='text' style='margin-left: 35px;' id=clmnNmrow"+rowNum+" name=clmnNmrow"+rowNum+" /></td>"
							+ "<td><textarea style='margin-left: 35px;' id=joinConditionrow"+rowNum+" name=joinConditionrow"+rowNum+" cols='40'></textarea></td>"
							+ "<td style='padding:5px;'><select name=slfrcdropdown"+rowNum+ " id=slfrcdropdown"+rowNum+" class='slsFrcFldUpdate'>"
							+ " <c:if test="${not empty mappingField}"> "
							+ " <c:forEach items="${mappingField}" var="field1" varStatus="status">"
							+ " <option value='${field1.name}'>${field1.label}</option>"
							+ " </c:forEach> "
							+ "  </c:if>"
							+ "</select></td>"
							+ "<td style='margin-left: 35px; padding-left:25px;'><input name=lookUpFieldrow"+rowNum+" id=lookUpFieldrow"+rowNum+"  type='checkbox'></td>"
							+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;' name=lookUpObjrow"+rowNum+" id=lookUpObjrow"+rowNum+"></td>"
							+ "<td style='margin-left: 35px;'><input type='text' style='margin-left: 35px;' name=lookUpRltnNmerow"+rowNum+" id=lookUpRltnNmerow"+rowNum+"></td>"
							+ "<td style='margin-left: 35px;'><select style='margin-left: 35px;' name=lookUpExtrnlrow"+rowNum+" id=lookUpExtrnlrow"+rowNum+">"
							
							+ "</select></td>"
							+ "<input type='hidden' id='"+sfdcId+"' name='"+sfdcId+"'></tr>");

							/* <input type='text' style='margin-left: 35px;' name=lookUpExtrnlrow"+rowNum+" id=lookUpExtrnlrow"+rowNum+"> */
							 
 
	}

	function submitForm()	  
	  {		
		  var page = "preMapData";
		  $("#pageName").val(page);
		  $("#mainForm").submit(); 
				 
	  }

</script>
<title>Vaporizer</title>
<style type="text/css">
.no-js #loader { display: none;  }
.js #loader { display: block; position: absolute; left: 100px; top: 0; }
.se-pre-con {
	position: fixed;
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background: url(resources/images/ajax-loader.gif) center no-repeat #fff;
}
</style>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.js"></script>
 
<script type="text/javascript">
	$(window).load(function() {
		
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");;
	});
	</script>
</head>
<body>
<div class="se-pre-con"></div>
	<div class="container">

		<div class="mainContent">
			<div class="credential_container">
				<div>
					<div class="table_header_details">Vaporizer</div>
					<div style="width: 350px !Important;  height:20px;padding-top:15px;padding-left:5px">Field Mapping</div>
					<div class="sampleContainer" style="width: 250px; float:left;"><table class="table" style="margin: 2px;">
								<tr>
									<br />
									<td style="width: 65px; text-align: left;" align="left">Siebel
										Entity</td>

									<td style="width: 45px; text-align: left;" align="left" id="sblEntity">${hdrValues[0]}</td>
					

								</tr>
								<tr>
									<br />
									<td style="width: 45px; text-align: left;" align="left">Siebel Base table</td>

									<td style="width: 45px; text-align: left;" align="left" id="sblBaseTbl">${hdrValues[1]}</td>


								</tr>
								<tr>
									<td style="width: 65px; text-align: left;" align="left">SFDC
										Entity</td>

									<td style="width: 45px; text-align: left;" align="left" id="sfdcEntity">${hdrValues[2]}</td>
								</tr>


							</table>
					</div>
					<div style="float:left; height:100px; margin-left:20%;margin-top:5%">Business Component Search Expression</div>
					<div style="float:left; height:100px; margin-left:1%;margin-top:5%">
					<%-- <span  style="border: grey 1px solid;padding:4px;">${hdrValues[3]}</span> --%>
					<textarea readonly="true" id="searchQry" cols="50">${hdrValues[3]}</textarea>
					</div>
				</div> <br/>
				<div style="clear:both;"><button class="btn btn-primary" id="addRow" onclick="addRow()">[+]</button> </div>
				<form:form method="post" id="mainForm" action="mappingSave" modelAttribute="data">
					<div class="mappingContainer" style="width: 100%;">
					<input type='hidden' id="siebelEntity" name="siebelEntity" value=${sblObjName} />
						<table id="masterTable" style="width: 100%;">
							<br />
							<br />
							<thread>
							<tr>
								<th class="table_header_details" style="float: center;">Select</th>
								<th class="table_header_details" style="float: center;">Siebel Field Name</th>
								<th class="table_header_details" style="float: center;">Join Name</th>
								<th class="table_header_details" style="float: center;">Foreign Key Table Name</th>
								<th class="table_header_details" style="float: center;">Siebel Column</th>
								<th class="table_header_details" style="float: center;">Join Condition</th>
								<th class="table_header_details" style="float: center;">SFDC Field Name</th>
								<th class="table_header_details" style="float: center;">Lookup Field</th>
								<th class="table_header_details" style="float: center;">Lookup Object</th>
								<th class="table_header_details" style="float: center;">Lookup Relationship Name</th>
								<th class="table_header_details" style="float: center;">Lookup External Id Field</th>
							</tr>
							</thread>
							 <c:set var="seq" value="${1}" />
							 <c:set var="sblFldFlag1" value="false" />
							 <c:set var="sfdcFldFlag1" value="false" />
							 <c:if test="${not empty mappingData}" var="mapping">
							 <% //out.println("Inside Mapped Data List");%>
								<c:forEach items="${mappingData}" var="mapping" varStatus="status"> 
							     	
									 <tr id="row${mapping.mappingSeq}">
										<td>
										<c:choose>
										<c:when test="${mapping.checkFlag}">
										<input name="select${mapping.mappingSeq}" type='checkbox' checked="checked">
										</c:when>
										<c:otherwise>
										<input name="select${mapping.mappingSeq}" type='checkbox'>
										</c:otherwise>
										</c:choose></td>
										<!-- Siebel Field Name : Drop Down  --> 
										<td><select name="sblFieldNmdropdown${mapping.mappingSeq}"
											id="sblFieldNmdropdown${mapping.mappingSeq}" class='sblFldColFrgnUpdate'>
												<c:if test="${not empty sbllFlddNmList}">
													<c:forEach items="${sbllFlddNmList}" var="field" varStatus="status">
														<c:set var="temp5" value="${field}" />
                                						<c:set var="temp6" value="${mapping.sblFieldNmdropdown}" />
													<%-- temp: [<c:out value="${temp1}" />]
                                						temp1: [<c:out value="${temp2}" />]  --%>
														<c:choose>
															<c:when test="${temp5 == temp6}">
												                <option value='${temp6}' selected>${temp6}</option>
												            </c:when>
												            <c:otherwise>
												                <option value='${field}'>${field}</option>
												            </c:otherwise>
											            </c:choose>
													</c:forEach>
												</c:if>
										</select></td>	
										<!-- Join name -->
										<td><input type="text" style="margin-left: 35px;" id="joinNamerow${mapping.mappingSeq}" name="joinNamerow${mapping.mappingSeq}" value="${mapping.joinNamerow}" /></td>
										<!-- Foreign Key Table -->
										<td><input type="text" style="margin-left: 35px;" id="frgnKeyrow${mapping.mappingSeq}" name="frgnKeyrow${mapping.mappingSeq}" value="${mapping.frgnKeyrow}" /></td>
										<!-- TODO : To load the column name value dynamically -->
										<td><input type="text" style='margin-left: 35px;' id="clmnNmrow${mapping.mappingSeq}" name="clmnNmrow${mapping.mappingSeq}" value="${mapping.clmnNmrow}" /></td>
										<td><textarea style='margin-left: 35px;' id="joinConditionrow${mapping.mappingSeq}" name="joinConditionrow${mapping.mappingSeq}" cols="40">${mapping.joinCondition}</textarea></td>
										<td style="padding:5px;"><select name="slfrcdropdown${mapping.mappingSeq}" id="slfrcdropdown${mapping.mappingSeq}" class='slsFrcFldUpdate'>
												<c:if test="${not empty mappingField}">
													<c:forEach items="${mappingField}" var="field" varStatus="status">
														<c:set var="temp7" value="${field.name}" />
                                						<c:set var="temp8" value="${mapping.slfrcdropdown}" />
														<c:choose>
															<c:when test="${temp7 == temp8}">
												                <option value='${field.name}' selected>${field.label}</option>
												            </c:when>
												            <c:otherwise>
												                <option value='${field.name}'>${field.label}</option>
												            </c:otherwise>
											            </c:choose>
													</c:forEach>
												</c:if>
										</select></td>
										<!-- Look Up Field -->
										<td style="margin-left: 35px;padding-left : 25px;">
										<c:choose>
										<c:when test="${mapping.lookUpFlag}">
										<input name="lookUpFieldrow${mapping.mappingSeq}" id="lookUpFieldrow${mapping.mappingSeq}" type='checkbox' checked="checked">
										</c:when>
										<c:otherwise>
										<input name="lookUpFieldrow${mapping.mappingSeq}" id="lookUpFieldrow${mapping.mappingSeq}" type='checkbox'>
										</c:otherwise>
										</c:choose></td>
										<!-- Look Up Object -->
										<td><input type="text" style="margin-left: 35px;" id="lookUpObjrow${mapping.mappingSeq}" name="lookUpObjrow${mapping.mappingSeq}" value="${mapping.lookUpObject}" /></td>
										<!-- Lookup Relationship Name -->
										<td><input type="text" style="margin-left: 35px;" id="lookUpRltnNmerow${mapping.mappingSeq}" name="lookUpRltnNmerow${mapping.mappingSeq}" value="${mapping.lookUpRelationShipName}"/></td>
										<!-- Lookup External Id Field -->
										<td>
										<c:choose>
										<c:when test="${not empty mapping.lookUpExternalId}">
										<select style="margin-left: 35px;" id="lookUpExtrnlrow${mapping.mappingSeq}" name="lookUpExtrnlrow${mapping.mappingSeq}" value="${mapping.lookUpExternalId}">
												<c:if test="${not empty mapping.lstExternalIds}">
													<c:forEach items="${mapping.lstExternalIds}" var="field" varStatus="status">
												       		<option value='${field}' selected>${field}</option>
													</c:forEach>
												</c:if>
										</select>
										</c:when>
										<c:otherwise>
										<select style="margin-left: 35px;" id="lookUpExtrnlrow${mapping.mappingSeq}" name="lookUpExtrnlrow${mapping.mappingSeq}" value="">
												<c:if test="${not empty mapping.lstExternalIds}">
													<c:forEach items="${mapping.lstExternalIds}" var="field" varStatus="status">
												       		<option value='${field}' selected>${field}</option>
													</c:forEach>
												</c:if>
										</select>
										</c:otherwise>	
										</c:choose>
										</td>
										<%-- <td><input type="text" style="margin-left: 35px;" id="lookUpExtrnlrow${mapping.mappingSeq}" name="lookUpExtrnlrow${mapping.mappingSeq}" value="${mapping.lookUpExternalId}"/></td> --%>
										<input type='hidden' id="sfdcId${mapping.mappingSeq}" name="sfdcId${mapping.mappingSeq}" value="${mapping.id}" />
									 </tr> 																	
								</c:forEach>								
								</c:if>							
						</table>
						<div id="row">
						<!-- TODO:  Understand the below usage -->
							<input id="rowCount" name='rowCount' type="hidden" value="${mappingData.size()}"> 
								<!-- <input id="rowCount" name='rowCount' type="hidden"
								value=""> -->
						</div>
						<input id="mappingSfdcId" name='mappingSfdcId' type="hidden"
							value="${MappingId}">
					</div>
					<div class="buttonContainer">

						<table style="border: 0">

							<tr>
							
							<td colspan="2"
							style="float: right; width: 350px; padding: 50px; padding-top: 10px; padding-bottom: 10px;">						
								<input class="btn btn-block btn-inverse" type="button" name="Load" value="Load Pre-Defined Mapping Objects" onclick="javascript:submitForm();" />
							</td>
								<td colspan="2"
									style="float: right; width: 350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
									<input class="btn btn-block btn-inverse" type="submit"
									name="Extract" value="Done" /> <!-- <button id="cancel" type="button" style="float: right;"
						class="btn btn-block btn-inverse">Done</button> --> <!-- <button id="cancel" type="submit" style="float: right;"
						class="btn btn-block btn-inverse">Done</button> -->

								</td>
								<td colspan="2"
									style="float: right; width: 350px !Important; padding: 50px; padding-top: 10px; padding-bottom: 10px;">
									<input class="btn btn-block btn-inverse" type="button" id="generateExtractQuery"
									name="GenerateExtract" value="Generate Extraction Query" />
								</td>
							</tr>


						</table>
						
						<input id="rowId" name='rowId' type="hidden" value="${rowId}">
						<input id="pageName" name='pageName' type="hidden">

					</div>
					<div class="mappingContainer" style="width: 100%;padding: 50px;">
					<textarea  id="extractionQuery" name="extractionQuery" contenteditable="true" cols="200" rows="3" draggable="true">${hdrValues[4]}</textarea>
					</div>
					</form:form>	
			</div>
		  
		  
						
			
						


		</div>
</body>
<script type="text/javascript">
	$("#cancel").click(function() {

		window.location.href = "Done";
	});
</script>
</html>