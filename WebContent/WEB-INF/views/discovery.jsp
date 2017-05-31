<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<link rel="stylesheet"
	href="../smartmanager/resources/css/smartmanager/style.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap-select.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="../smartmanager/resources/css/lib/prettify.min.css">
<script src="../smartmanager/resources/js/lib/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/lib/bootstrap.min.js"></script>
<script src="../smartmanager/resources/js/lib/bootbox.min.js"></script>
<script src="../smartmanager/resources/js/lib/run_prettify.min.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/lib/bootstrap-select.min.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/discovery.js"
	type="text/javascript"></script>
<script
	src="../smartmanager/resources/js/smartmanager/groupManagement.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../views/menuFragment.jsp" />
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

				<h3>
					Discovery <span class="pull-right"><button class="btn btn-default btn-lg" onclick="window.location.reload();"><span class="glyphicon glyphicon-refresh"></span></button></span>
				</h3>

				<table class="table table-hover">
					<thead>
						<tr>
							<th>Devicename</th>
							<th>Device ID</th>
							<th>Group Membership</th>
							<th>Initial Configuration</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty devices}">
							<c:forEach var="row" items="${devices}">
								<tr>
									<td>${row.name}</td>
									<td>${row.id}</td>
									<td><select class="selectpicker" id="groupSelector">
											<c:forEach var="groups" items="${groups}">
												<option value="'${groups.name}'" id="${groups.id}">${groups.name}</option>
											</c:forEach>
									</select></td>
									<td><select class="selectpicker" id="configSelector">
											<option value="none" id="none">none</option>
											<c:forEach var="configurations" items="${configurations}">
												<option value="'${configurations.name}'"
													id="${configurations.id}">${configurations.name}</option>
											</c:forEach>
									</select></td>
									<td>
										<button class="btn btn-success btn-sm"
											onclick="addDevice('${row.id}')">Add</button>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>