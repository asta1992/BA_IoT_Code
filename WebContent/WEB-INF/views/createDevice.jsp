<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smartmanager - Create Device</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel=stylesheet href="resources/css/menu.css">

</head>
<body>
	<div class="container-fluid">
		<div class="row">


			<jsp:include page="../views/fragments/menu.jsp" />



			<div class="col-xs-12 col-xm-6 col-md-6 col-lg-7">
				<c:choose>
					<c:when test="${device['new']}">
						<h2>Device erstellen</h2>
					</c:when>
					<c:otherwise>
						<h2>Update Device</h2>
					</c:otherwise>
				</c:choose>
				<br />

				<spring:url value="/devices" var="deviceActionUrl" />

				<form:form class="form-horizontal" modelAttribute="device"
					action="${deviceActionUrl}">

					<c:choose>
						<c:when test="${device['new']}">
						</c:when>
						<c:otherwise>
							<form:hidden path="id" />
						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<label class="control-label col-sm-2" for="name">Name:</label>
						<div class="col-sm-10">
							<form:input type="text" path="name" class="form-control"
								id="name" placeholder="Name" />
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2" for="protocolType">Protokoll
							Typ:</label>
						<div class="col-sm-10">
							<form:select class="form-control" path="protocolType"
								id="selectedProtocol" name="protocolType">
								<c:forEach var="protocolType" items="${protocolType}">
									<form:option value="${protocolType}">${protocolType}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2" for="authType">Authentikation
							Typ:</label>
						<div class="col-sm-10">
							<form:select class="form-control" id="selectedAuth"
								path="authType" name="authType">
								<c:forEach var="authType" items="${authType}">
									<form:option value="${authType}">${authType}</form:option>
								</c:forEach>
							</form:select>

						</div>
					</div>


					<div class="form-group">
						<label class="control-label col-sm-2" for="endpoint">Endpoint:</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="endpoint"
								id="endpoint" placeholder="Endpoint" />
						</div>
					</div>



					<div class="form-group">
						<label class="control-label col-sm-2" for="username">Benutzername:</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="username"
								id="username" placeholder="Benutzername" />
						</div>
					</div>


					<div class="form-group">
						<label class="control-label col-sm-2" for="password">Password:</label>
						<div class="col-sm-10">
							<form:input type="password" class="form-control" path="password"
								id="password" placeholder="Passwort" />
						</div>
					</div>


					<div class="form-group">
						<div>
							<c:choose>
								<c:when test="${device['new']}">
									<button type="submit" class="btn btn-default pull-right">Erstellen</button>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-default pull-right">Anpassen</button>
								</c:otherwise>
							</c:choose>
							<button onclick="history.back();"
								class="btn btn-default pull-right">Abbrechen</button>
						</div>
					</div>
				</form:form>
			</div>



			<div class="col-xs-12 col-xm-3 col-md-4 col-lg-3">
				<div class="row">
					<div style="height: 50%;">
						<h2>Device Details</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>


