<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smartmanager - Home</</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel=stylesheet href="resources/css/menu.css">
<script src="resources/js/main.js"></script>


</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-xm-3 col-md-2 col-lg-2" style="padding: 0 0 0 0;">
				<div class="sidebar-nav">
					<div class="navbar navbar-default" role="navigation">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
								<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"> </span> <span class="icon-bar"></span>
							</button>
							<span class="visible-xs navbar-brand">Sidebar menu</span>
						</div>
						<div class="navbar-collapse collapse sidebar-navbar-collapse">
							<ul class="nav navbar-nav">
								<li class="col-lg-12 col-md-12"><a href="/smartmanager/">Home</a></li>
								<li class="col-lg-12 col-md-12"><a href="/smartmanager/devices/add">Devices erfassen</a></li>
								<li class="col-lg-12 col-md-12"><a href="/smartmanager/discovery" class="col-lg-12 col-md-12">Discovery <span class="badge">12</span></a></li>
								<li class="col-lg-12 col-md-12"><a href="/smartmanager/settings" class="col-lg-12 col-md-12">Settings</a></li>
								<li class="col-lg-12 col-md-12"><a href="/smartmanager/accountsettings" class="col-lg-12 col-md-12">Account Settings</a></li>
							</ul>
						</div>
						<!--/.nav-collapse -->
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-xm-6 col-md-6 col-lg-7">
				<h2>Devices</h2>
				<p>Bereits erfasste Devices:</p>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Devicename</th>
							<th>Endpoint</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="row" items="${devices}">
							<tr>
								<td><a href="/smartmanager/devices/${row.id}">${row.id}</a></td>
								<td>${row.name}</td>
								<td>${row.endpoint}</td>
								<td>
								<spring:url value="/devices/${row.id}" var="deviceUrl" />
								<spring:url value="/devices/${row.id}/delete" var="deleteUrl" />
								<spring:url value="/devices/${row.id}/update" var="updateUrl" />

								<button class="btn btn-info" onclick="location.href='${deviceUrl}'">Show</button>
								<button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
								<button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
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