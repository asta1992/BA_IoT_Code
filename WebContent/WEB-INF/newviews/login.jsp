<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../smartmanager/resources/css/style.css" type="text/css">
<link rel="stylesheet" href="../smartmanager/resources/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="../smartmanager/resources/css/bootstrap-theme.min.css" type="text/css">
<script src="../smartmanager/resources/js/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/bootstrap.min.js"></script>
<title>Login</title>
</head>
<body onload='document.f.j_username.focus();'>
	<div class="container">
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

		<form class="form-signin" name='f' action='smartmanager/j_spring_security_check' method='POST'>
			<h2 class="form-signin-heading">Please log in</h2>
			<label for="username" class="sr-only">Username</label> <input type="text" id="username" name="j_username" value='' class="form-control" placeholder="Username" required autofocus> <label for="password" class="sr-only">Password</label> <input
				type="password" name="j_password" id="password" class="form-control" placeholder="Password" required>

			<button class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="Login">Sign in</button>
		</form>

	</div>
</body>
</html>