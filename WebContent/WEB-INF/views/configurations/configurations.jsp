<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager - Configurations</title>
<link rel="stylesheet" type="text/css" href="..${pageContext.request.contextPath}/resources/css/smartmanager/style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap.min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-select.min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="..${pageContext.request.contextPath}/resources/css/lib/prettify.min.css">
<script src="..${pageContext.request.contextPath}/resources/js/lib/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap.min.js" type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/bootbox.min.js" type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap-select.min.js" type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/smartmanager/configurations.js" type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/smartmanager/writeableResources.js" type="text/javascript"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<jsp:include page="../../views/menuFragment.jsp" />
			</div>
		</div>
		<div class="row page-heading">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				
					Configurations <span class="pull-right">
						<button type="button" class="btn btn-primary " onclick="createConfiguration()">create new configuration</button>
					</span>
				</div></div>
				<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<table class="table table-hover configurations-table">
					<thead>
						<tr>
							<th>Config</th>
							<th>Description</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty configurations}">
							<c:forEach var="configurations" items="${configurations}">
								<tr>
									<td>${configurations.name}</td>
									<td>${configurations.description}<span class="pull-right">
											<button type="button" class="btn btn-primary btn-xs" onclick="editConfiguration('${configurations.id}')">Edit</button>
									</span> <span class="pull-right">
											<button type="button" class="btn btn-danger btn-xs" onclick="deleteConfiguration('${configurations.id}')">Delete</button>
									</span>
									</td>
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