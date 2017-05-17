<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<link rel="stylesheet" href="../smartmanager/resources/css/style.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/bootstrap-theme.min.css"
	type="text/css">
<script src="../smartmanager/resources/js/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../newviews/menuFragment.jsp" />
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

				<h3>Discovery</h3>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Registration Id</th>
							<th>Devicename</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty devices}">
							<c:forEach var="row" items="${devices}">
								<tr>
									<td><a href="/smartmanager/devices/${row.id}">${row.id}</a></td>
									<td>${row.name}</td>
									<td><spring:url value="/devices/${row.id}/add"
											var="addUrl" />
										<button class="btn btn-info btn-xs"
											onclick="location.href='${addUrl}'">Add</button>
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