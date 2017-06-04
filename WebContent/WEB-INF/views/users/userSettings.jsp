<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/smartmanager/style.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" href="..${pageContext.request.contextPath}/resources/css/lib/bootstrap-select.min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="..${pageContext.request.contextPath}/resources/css/lib/prettify.min.css">
<script src="..${pageContext.request.contextPath}/resources/js/lib/jquery-2.1.4.min.js"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap.min.js"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/bootbox.min.js"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/run_prettify.min.js" type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/lib/bootstrap-select.min.js" type="text/javascript"></script>
<script src="..${pageContext.request.contextPath}/resources/js/smartmanager/users.js" type="text/javascript"></script>

</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0 0 0 0;">
				<jsp:include page="../../views/menuFragment.jsp" />
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class=row>
					<h2>${user.username}
						<c:if test="${user.username eq 'admin'}">
							<span class="pull-right">
								<button type="button" class="btn btn-warning heading-button" onclick="showForm()">Create new User</button>
							</span>
							<span class="pull-right">
								<button type="button" class="btn btn-danger heading-button" onclick="deleteUser()">Delete User</button>
							</span>
						</c:if>
						<span class="pull-right">
							<button type="button" class="btn btn-primary heading-button" onclick="editForm('${user.id}')">Change Password</button>
						</span>
					</h2>
				</div>
			</div>
		</div>

		<c:if test="${user.username eq 'admin'}">
			<div class="row-fluid">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="table responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Username</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="managementUsers" items="${managementUsers}">
									<tr>
										<td>${managementUsers.username}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</c:if>
	</div>

</body>
</html>
