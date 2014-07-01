<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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

<!-- Latest compiled and minified JavaScript -->
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<title>Vaporizer</title>
</head>
<body>
	<div class="container">
		<div class="header">
			<span class="headerV">V</span><span class="headerRest">aporizer</span>
		</div>
		<div class="mainContent">
			<div class="credential_container">
				<div>
					<div class="table_header_details">Data Migration Details</div>
					<div>
						<table class="table">
							<tr>
								<td class="oddTd">Name</td>
								<td>Sample Project One</td>
							</tr>

						</table>
					</div>
				</div>
				<div>
					<div style="float: left; width: 50%">
						<div class="table_header_details">Seibel</div>
						<div>
						<table class="table">
							<tr>
								<td class="oddTd">Siebel DSN</td>
								<td>SBLDB</td>
							</tr>
							<tr>
								<td class="oddTd">Seibel Login</td>
								<td>SnetUser1</td>
							<tr>
								<td class="oddTd">Seibel Password</td>
								<td>SnetUser1</td>
							</tr>

						</table>
						</div>
					</div>
					<div style="float: right; width: 50%">
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
					</div>
				</div>
			</div>
			<div class="mappingTableSection">
				dedfsdfsf
				<!-- <table class="table">
					<tr class="vaporizerTr">
						<th>Firstname</th>
						<th>Lastname</th>
						<th>Savings</th>
						<th>Firstname</th>
						<th>Lastname</th>
						<th>Savings</th>
						<th>Firstname</th>
						<th>Lastname</th>
						<th>Savings</th>
						<th>Firstname</th>
						<th>Lastname</th>
						<th>Savings</th>

					</tr>
					<tr>
						<td>Peter</td>
						<td>Griffin</td>
						<td>$100</td>
						<td>Peter</td>
						<td>Griffin</td>
						<td>$100</td>
						<td>Peter</td>
						<td>Griffin</td>
						<td>$100</td>
						<td>Peter</td>
						<td>Griffin</td>
						<td>$100</td>

					</tr>


				</table> -->
			</div>
		</div>
	</div>
</body>
</html>