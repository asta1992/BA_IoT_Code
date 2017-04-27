<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smartmanager - Home</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="resources/js/main.js"></script>


</head>
<body>
	<div class="container-fluid">
		<div class="row">

			<jsp:include page="../views/fragments/menu.jsp" />

			<div class="col-xs-12 col-xm-6 col-md-6 col-lg-7">
				<h2>Devices</h2>
				<p>Bereits erfasste Devices:</p>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Registration Id</th>
							<th>Devicename</th>
							<th>Endpoint</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="row" items="${devices}">
							<tr>
									<td><a href="/smartmanager/devices/${row.regId}">${row.regId}</a></td>
									<td>${row.name}</td>
									<td>${row.endpoint}</td>
									<td><spring:url value="/devices/${row.regId}" var="showUrl" />
										<spring:url value="/devices/${row.regId}/delete"
											var="deleteUrl" /></td>
									<td>
										<button class="btn btn-info"
											onclick="location.href='${showUrl}'">Show</button>
										<button class="btn btn-danger"
											onclick="location.href='${deleteUrl}'">Delete</button>
									</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-xs-12 col-xm-3 col-md-4 col-lg-3">
				<div class="row">
					<div style="height: 50%;">
						<h2>Device Details</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>