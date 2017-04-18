<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smartmanager - Show Device</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel=stylesheet href="resources/css/menu.css">
<script src="resources/js/main.js"></script>

 
</head>
<body>
	<div class="container-fluid">
		<div class="row">

			<jsp:include page="../views/fragments/menu.jsp" />

			<div class="col-xs-12 col-xm-6 col-md-6 col-lg-7">
				Grosser Bereich
			</div>
			<div class="col-xs-12 col-xm-3 col-md-4 col-lg-3">
				Detail Bereich
			</div>

		</div>
	</div>
</body>
</html>