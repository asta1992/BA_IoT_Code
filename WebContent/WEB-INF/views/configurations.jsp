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
<link rel="stylesheet" href="../smartmanager/resources/css/smartmanager/style.css"
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
<script src="../smartmanager/resources/js/lib/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/lib/bootstrap.min.js"></script>
<script src="../smartmanager/resources/js/lib/bootbox.min.js"></script>
<script src="../smartmanager/resources/js/lib/bootstrap-select.min.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/deviceCommunication.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/groupCommunication.js"
	type="text/javascript"></script>
<script src="../smartmanager/resources/js/smartmanager/configurations.js"
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
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<h3>
					Configurations <span class="pull-right">
						<button type="button" class="btn btn-default "
							onclick="createConfiguration()">create new configuration</button>
					</span>
				</h3>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Config</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty configurations}">
							<c:forEach var="configurations" items="${configurations}">
								<tr>
									<td>${configurations.name}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>