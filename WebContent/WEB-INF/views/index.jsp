<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<link rel="stylesheet"
	href="../smartmanager/resources/css/smartmanager/style.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/metisMenu.min.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/morris.css"
	type="text/css">
<link rel="stylesheet"
	href="../smartmanager/resources/css/lib/sb-admin-2.min.css"
	type="text/css">
<script src="../smartmanager/resources/js/lib/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/lib/bootstrap.min.js"></script>
<script src="../smartmanager/resources/js/lib/metisMenu.js"></script>
<script src="../smartmanager/resources/js/lib/sb-admin-2.js"></script>
<script src="../smartmanager/resources/js/smartmanager/dashboard.js"></script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD7w-HEi_KCBCkCJNEKJzB2L7NJGl1CF4Y&callback=initMap"></script>
<style>
#map {
	height: 350px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
				style="padding: 0 0 0 0;">
				<jsp:include page="../views/menuFragment.jsp" />
			</div>
			<div class="row">
				<div class="col-lg-3 col-md-6 col-sm-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-comments fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">26</div>
									<div>New Comments!</div>
								</div>
							</div>
						</div>
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<div class="row">
			<div class="row">
				<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7"></div>
				<div id="map" class="col-lg-5 col-md-5 col-sm-5 col-xs-5">MAP</div>
			</div>
		</div>

	</div>
	</div>
</body>
</html>