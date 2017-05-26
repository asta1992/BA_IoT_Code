<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<link rel="stylesheet" href="../smartmanager/resources/css/style.css" type="text/css">
<link rel="stylesheet" href="../smartmanager/resources/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="../smartmanager/resources/css/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" href="../smartmanager/resources/css/bootstrap-select.min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="../smartmanager/resources/css/prettify.min.css">
<script src="../smartmanager/resources/js/jquery-2.1.4.min.js"></script>
<script src="../smartmanager/resources/js/bootstrap.min.js"></script>
<script src="../smartmanager/resources/js/bootbox.min.js"></script>
<script src="../smartmanager/resources/js/run_prettify.min.js" type="text/javascript"></script>
<script src="../smartmanager/resources/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="../smartmanager/resources/js/userManagement.js" type="text/javascript"></script>

</head>
<body>





	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0 0 0 0;">
				<jsp:include page="../newviews/menuFragment.jsp" />
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class=row>
					<h2>${user.username}</h2>

					<c:if test="${user.username eq 'admin'}">
						<span class="pull-right">
							<button type="button" class="btn btn-warning heading-button" onclick="showForm()">Create new User</button>
						</span>
						<span class="pull-right">
							<button type="button" class="btn btn-danger heading-button" onclick="deleteUser()">Delete User</button>
						</span>
					</c:if>
					<span class="pull-right">
						<button type="button" class="btn btn-primary heading-button" onclick="updateUser('${user.id}', '${user.username }')">Change Password</button>
					</span>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
