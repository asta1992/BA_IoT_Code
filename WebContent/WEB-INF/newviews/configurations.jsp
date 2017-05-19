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
<title>Smartmanager - Configurations</title>
<link rel="stylesheet" href="../smartmanager/resources/css/style.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/bootstrap-select.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="../smartmanager/resources/css/prettify.min.css">
<script src="../smartmanager/resources/js/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/bootstrap.min.js"></script>
<script src="../smartmanager/resources/js/bootbox.min.js"></script>
<script src="../smartmanager/resources/js/bootstrap-select.min.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/deviceCommunication.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/groupCommunication.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/createConfiguration.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/createConfigurationItem.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/groupManagement.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/deleteObject.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../newviews/menuFragment.jsp" />
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<h3>
					Configurations <span class="pull-right">
						<button type="button" class="btn btn-default "
							onclick="createConfiguration()">create new configuration</button>
					</span>
				</h3>
			</div>
		</div>
	</div>
</body>
</html>