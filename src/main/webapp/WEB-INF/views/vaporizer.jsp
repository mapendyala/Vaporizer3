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
<!-- 	 <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" media="all" />
 -->

	<%-- <script type="text/javascript"
	src="<c:url value="/resources/jquery-1.4.min.js" /> "></script> --%>
<script type="text/javascript"
	src="<c:url value="/resources/json.min.js" /> "></script>
	
	
<!-- <style type="text/css">
.image {
	height: 50px;
	position: fixed;
	top: 350px;
	left: 700px;
}
</style> -->
	
<script type="text/javascript">

var rowNum = 1;

	function addRow() {
		/* var rowCount = $('#masterTable tr').length; */
		
		var objName = "objectName"+(rowNum);
		var srchObj = "searchObject"+(rowNum);
		var SFDCObjName = "SFDCObjName"+(rowNum);
		
		$("#masterTable tbody")
				.append(
						"<tr style='height:45px ;width:45px'>"
								+ "<td><input type='checkbox'></td>"
								+ "<td>"+ rowNum+"</td>"
								+ "<td><input id="+objName+" readonly/><button type='button' id="+srchObj+" style='display: inline;' onclick='getPopup("+rowNum+")'><span class='glyphicon glyphicon-search'></span></button></td>"
								
								+ "<td><input id='primeBaseTable' readonly/></td>"
								+ "<td><input type='number' min='1' max='5' style='width:40px' id = 'threshold' onchange='makeReadonly()'></td>"
								+ "<td><a href='ChildBase'>Select</a></td>"
								+ "<td><input id="+SFDCObjName+" readonly/></td>"
								+ "<td><a href='mapping'>Select</a></td>"
								+ "<td><c:out value='Selected'/></td>"
								+ "<td>"
								+ "<input class='btn btn-inverse' type='button' name='Extract' value='E' />"
								+ "<input class='btn btn-inverse' type='button' name='transform' value='T' style='margin-left:5px;'/>"
				                + "<input class='btn btn-inverse' type='button' name='delete' value='-' style='margin-left:5px;'/>"
				                + "</td>"
				                + "</tr>");
		
		rowNum = rowNum+1;
	}
	</script>
	
	<script type="text/javascript">
	function populateObjectName(id){
	var s =$('input[name=selectedObject]:radio:checked').val();
		$("#"+id).val(s); 
	}
	</script>
	
	<script type="text/javascript">
		
	 function makeReadonly(){
	
		var threshold = $("#threshold").val();
		/* var objName = $("#objName").val(); */
		$.ajax({
			type : "POST",
			url : "object/add",
		 	data : {value:threshold},
			/*  contentType : 'String',
			success :  $("#tableValue").val() */	});
	}
	</script>
	
	
	
	 <script type="text/javascript">
	 
	 
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
		 /*  var selectedRow = $("#row").val(); */
		var id="objectName"+(rowNum);
		 var SFDCObjectId="SFDCObjName"+(rowNum);
		$("#obj").dialog({
			title: "Select an Siebel Object",
			 
			width: 430,
			 
			height: 250,
			 
			buttons: {
				"OK": function () {
					var siebelObject =$('input[name=selectedObject]:radio:checked').val();
					//alert(siebelObject);
					populateObjectName(id);					
					getSFDCOBject(siebelObject,SFDCObjectId);
					$(this).dialog("close");
			
				},"Close": function(){
					$(this).dialog("close");
					
				}
			
			},
			 
			
			 
			});
		} 
	</script>
	
	<script type="text/javascript">
	
	function  getObjectName(){
		var objName = $("#objName").val();
	
		$.ajax({
			type : "POST",
			url : "object/add",
			data : {objectName:objName}
			
			}); 
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
	<br><br><br><br>
		<button class="btn btn-primary" id="addRow" onclick="addRow()">[+]</button>			
		<br><br>


<div class="mappingContainer" style="width:100%;">
			<table id="masterTable" class="table">
				<thead>
					<tr>
						<th class="table_header_details" style="float: center;">Migrate?</th>
						<th class="table_header_details" style="float: center;">Sequence</th>
						<th class="table_header_details" style="float: center;">Siebel
							Object</th>
						<!-- <th class="table_header_details" style="float: center;"></th> -->
						<th class="table_header_details" style="float: center;">Prim
							Base Table</th>
						<th class="table_header_details" style="float: center;">Treshold</th>
						<th class="table_header_details" style="float: center;">Child
							Base Tables</th>
						<th class="table_header_details" style="float: center;">SFDC
							Object</th>
						<th class="table_header_details" style="float: center;">Mapping</th>
						<th class="table_header_details" style="float: center;">Status</th>
						<th class="table_header_details" style="float: center;">Add
							Ons</th>
					</tr>
				</thead>
				<tbody>
				</tbody>

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

<div id=obj hidden="true">
 <input type="text" id = "objName" placeholder="Enter Object Name"><button type='button' style='display: inline;' id="search" onclick='getObjectName()'><span class='glyphicon glyphicon-search'></span></button>
<br>
 <input id="tableValue" name="selectedObject" type="radio" value="Account">Account<br>
<input name="selectedObject" type="radio" value="Contact">Contact<br>
<input name="selectedObject" type="radio" value="Campaign">Campaign<br>
<input name="selectedObject" type="radio" value="Campaign Member">Campaign Member<br>

</div>



	</div>
	
</body>
</html>