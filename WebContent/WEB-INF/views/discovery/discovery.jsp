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
	href="..${pageContext.request.contextPath}/resources/css/smartmanager/style.css"
	type="text/css">
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-select.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="..${pageContext.request.contextPath}/resources/css/lib/prettify.min.css">
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/jquery-2.1.4.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootbox.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/run_prettify.min.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap-select.min.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/discovery.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../../views/menuFragment.jsp" />
			</div>
		</div>
		<div class="row page-heading">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">Discovery</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
				<span class="pull-right"><button
						class="btn btn-default btn-md"
						onclick="location.href='${pageContext.request.contextPath}/discovery/clean';">
						<span class="glyphicon glyphicon-remove"></span>
					</button></span> <span class="pull-right"><button
						class="btn btn-default btn-md" onclick="window.location.reload();">
						<span class="glyphicon glyphicon-refresh"></span>
					</button></span>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<span class="pull-right"><select class="selectpicker"
					id="groupSelector">
						<option value="_unassigned" id="_unassigned">select a
							group</option>
						<c:forEach var="groups" items="${groups}">
							<option value="'${groups.name}'" id="${groups.id}">${groups.name}</option>
						</c:forEach>
				</select> <select class="selectpicker" id="configSelector">
						<option value="none" id="none">select a configuration</option>
						<c:forEach var="configurations" items="${configurations}">
							<option value="'${configurations.name}'"
								id="${configurations.id}">${configurations.name}</option>
						</c:forEach>
				</select>

					<button class="btn btn-success btn-sm" onclick="addDevice()">Add</button></span>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Devicename</th>
							<th>Device ID</th>
							<th><span class="pull-right"><input type="checkbox" id="checkAll" onclick="checkAll()" class=""></span></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty devices}">
							<c:forEach var="row" items="${devices}">
								<tr>
									<td>${row.name}</td>
									<td>${row.id}</td>
									<td><span class="pull-right"><input type="checkbox" class="discoveryCheckbox" value="${row.id}"></span></td>

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