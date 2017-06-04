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
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/smartmanager/style.css"
	type="text/css">
<link
	href="..${pageContext.request.contextPath}/resources/css/lib/gijgo.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet"
	href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-select.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="..${pageContext.request.contextPath}/resources/css/lib/prettify.min.css">
<link rel="stylesheet" type="text/css"
	href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-duallistbox.css">
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/jquery-2.1.4.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/gijgo.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootbox.min.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/run_prettify.min.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/jquery.bootstrap-duallistbox.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap-select.min.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/devices.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/deviceFragment.js"
	type="text/javascript"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/groupFragment.js"
	type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/smartmanager/map.js"></script>
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/writeableResources.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../../views/menuFragment.jsp" />
			</div>
		</div>

		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
			<div class="row page-heading">
				Navigation <span class="pull-right">
					<button type="button" class="btn btn-default btn-sm"
						onclick="addNewRootGroup()">Add New Group</button>
				</span>
			</div>
			<div id="treeDiv">
				<div id="tree"></div>
			</div>

		</div>
		<div class="col-lg-9 col-md-8 col-sm-8 col-xs-12" id="main-content">
		</div>
	</div>
</body>
</html>