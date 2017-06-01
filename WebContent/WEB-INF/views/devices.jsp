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
<link rel="stylesheet" href="../smartmanager/resources/css/smartmanager/style.css"
	type="text/css">
<link href="../smartmanager/resources/css/lib/gijgo.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap.min.css" type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap-select.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="../smartmanager/resources/css/lib/prettify.min.css">
<link rel="stylesheet" type="text/css"
	href="../smartmanager/resources/css/lib/bootstrap-duallistbox.css">
<script src="../smartmanager/resources/js/lib/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/lib/bootstrap.min.js"></script>
<script src="../smartmanager/resources/js/lib/gijgo.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/lib/bootbox.min.js"></script>
<script src="../smartmanager/resources/js/lib/run_prettify.min.js"
	type="text/javascript"></script>
<script
	src="../smartmanager/resources/js/lib/jquery.bootstrap-duallistbox.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/lib/bootstrap-select.min.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/navigationdata.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/deviceCommunication.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/configurations.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/groupCommunication.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/groupManagement.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/deleteObject.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../views/menuFragment.jsp" />
			</div>
		</div>
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
				<h4>
					Navigation <span class="pull-right">
						<button type="button" class="btn btn-default btn-sm"
							onclick="addNewRootGroup()">Add New Group</button>
					</span>
				</h4>
				<br>
				<div id="tree"></div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12" id="main-content">
			</div>
		</div>
</body>
</html>