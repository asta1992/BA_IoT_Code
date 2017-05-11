<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<link rel="stylesheet" href="../smartmanager/resources/css/style.css"
	type="text/css">
<link href="../smartmanager/resources/css/gijgo.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="../smartmanager/resources/css/prettify.min.css">
<link rel="stylesheet" type="text/css"
	href="../smartmanager/resources/css/bootstrap-duallistbox.css">
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/bootstrap.min.js"></script>
<script src="../smartmanager/resources/js/gijgo.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/bootbox.min.js"></script>
<script src="../smartmanager/resources/js/navigationdata.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/devicedata.js"
	type="text/javascript"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/prettify/r298/run_prettify.min.js"></script>
<script src="js/jquery.bootstrap-duallistbox.js"></script>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"></div>
			<jsp:include page="../newviews/menuFragment.jsp" />
		</div>

		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
				<h4>Navigation</h4>
				<div id="tree"></div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12" id="main-content">

			</div>
		</div>
	</div>
</body>
</html>