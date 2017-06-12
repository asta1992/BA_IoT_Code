<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/metisMenu.min.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/smartmanager/style.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/sb-admin-2.min.css" type="text/css">

<script src="..${pageContext.request.contextPath}/resources/js/lib/jquery-2.1.4.min.js"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap.min.js"></script>
<title>Login</title>
</head>
<body onload='document.f.j_username.focus();'>
	<div class="container">


		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Please Sign In</h3>
				</div>
				<div class="panel-body">
					<form role="form" class="form-signin" name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
						<fieldset>
							<div class="form-group">
								<input type="text" id="username" name="j_username" value='' class="form-control" placeholder="Username" required autofocus>
							</div>
							<div class="form-group">
								<input type="password" name="j_password" id="password" class="form-control" placeholder="Password" value="">
							</div>
						</fieldset>
						<div class="form-group">
							<button class="btn btn-lg btn-primary" style="width: 100% !important;" name="submit" type="submit" value="Login">Sign in</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div class="col-md-4 col-md-offset-4">
			<c:if test="${param.error != null}">
				<div class="bs-example">
					<div class="alert alert-danger fade in">
						<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Login failure!</strong> Please try it again.
					</div>
				</div>
			</c:if>
			<c:if test="${param.logout != null}">
				<div class="bs-example">
					<div class="alert alert-success fade in">
						<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Log out complete.</strong>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>