<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smartmanager - Create Device</</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel=stylesheet href="resources/css/menu.css">

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
								<li class="col-lg-12 col-md-12"><a href="/smartmanager/devices">Devices erfassen</a></li>
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
				<h2>Create a new Device</h2>
				<p />
				<form class="form-horizontal method="POST" action="${pageContext.request.contextPath}/doCreate">
					<div class="form-group">
						<label class="control-label col-sm-2" for="name">Devicename:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="name" placeholder="Devicename">
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2" for="protocolType">Protokoll Typ:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="protocolType" placeholder="Protokoll Typ">
						</div>
					</div>


					<div class="form-group">
						<label class="control-label col-sm-2" for="authType">Authentikation Typ:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="authType" placeholder="Authentikation Typ">
						</div>
					</div>


					<div class="form-group">
						<label class="control-label col-sm-2" for="ipAddress">IP-Adresse:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="ipAddress" placeholder="IP-Adresse">
						</div>
					</div>



					<div class="form-group">
						<label class="control-label col-sm-2" for="username">Benutzername:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="username" placeholder="Benutzername">
						</div>
					</div>


					<div class="form-group">
						<label class="control-label col-sm-2" for="password">Password:</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="password" placeholder="Passwort">
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-default">Erfassen</button>
							<button onclick="history.back();" class="btn btn-default">Abbrechen</button>
						</div>
					</div>
				</form>


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


