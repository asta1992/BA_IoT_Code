<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	href="..${pageContext.request.contextPath}/resources/css/lib/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/lib/sb-admin-2.min.css"
	type="text/css">
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/jquery-2.1.4.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootbox.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/metisMenu.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/sb-admin-2.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/home.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/map.js"></script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD7w-HEi_KCBCkCJNEKJzB2L7NJGl1CF4Y&callback=initMap"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../../views/menuFragment.jsp" />
			</div>
		</div>
		<div class="row dashboard-counters">
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
				<a href="${pageContext.request.contextPath}/devices">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-mobile fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${deviceCounter}</div>
									<div>Devices</div>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>

			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
				<a href="${pageContext.request.contextPath}/discovery">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-eye fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${discoveredDeviceCounter}</div>
									<div>Discovered Devices</div>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>

			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
				<a href="${pageContext.request.contextPath}/users">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-user fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${registeredUsers}</div>
									<div>Registered Users</div>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>

			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
				<a href="#">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-clock-o fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${uptime}</div>
									<div>Server Uptime (hours)</div>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>


		</div>
		<div class="row">
			<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
				<div class="panel panel-default dashboard">
					<div class="panel-heading">Unreachable Devices</div>
					<div class="panel-body">
						<div class="table responsive">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>Device Name</th>
										<th>Last seen</th>
										<th><span class="pull-right"><button type="button"
													class="btn btn-danger btn-xs"
													onclick="deleteAllUnreachableDevice()">Delete All</button>
										</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="unreachableDevices"
										items="${unreachableDevices}">
										<tr>
											<td>${unreachableDevices.name}</td>
											<td>${unreachableDevices.lastRegistrationUpdate}</td>
											<td><span class="pull-right">
													<button type="button" class="btn btn-danger btn-xs"
														onclick="deleteUnreachableDevice('${unreachableDevices.id}', '${unreachableDevices.name}')">Delete</button>
											</span></td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
				<div class="panel panel-default dashboard">
					<div class="panel-heading">All Devices</div>
					<div id="map" class="panel-body map-dashboard"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>