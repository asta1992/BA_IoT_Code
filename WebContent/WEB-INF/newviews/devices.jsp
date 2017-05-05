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
<link rel="stylesheet" href="../resources/css/style.css?v=1.1">
<link href="http://code.gijgo.com/1.3.1/css/gijgo.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="http://code.gijgo.com/1.3.1/js/gijgo.js"
	type="text/javascript"></script>
<script src="../resources/js/navigationdata.js" type="text/javascript"></script>
<script src="../resources/js/devicedata.js" type="text/javascript"></script>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"></div>
			<jsp:include page="../newviews/fragments/menuFragment.jsp" />
		</div>

		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
				<h4>Navigation</h4>
				<div id="tree"></div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
				<h2>
					LeshanDevice Client <span class="pull-right"><button
							type="button" class="btn btn-danger heading-button">Delete
							Device</button></span> <span class="pull-right"><button type="button"
							class="btn btn-primary heading-button">Read All</button></span>
				</h2>
				<dl class="dl-horizontal">
					<dt>Registration Id</dt>
					<dd>${registration.id}</dd>
					<dt>IPAddress</dt>
					<dd>2001:cdba:0000:0000:0000:0000:3257:9652</dd>
					<dt>Authentication</dt>
					<dd>Certificate Authentication</dd>
					<dt>Last Updated</dt>
					<dd>03.05.2017 20.19 Uhr</dd>
					<dt>Group Memberships</dt>
					<dd></dd>

				</dl>
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>
								<a data-toggle="collapse" href="#collapse1">Device</a> <span
									class="pull-right"><button type="button"
										class="btn btn-primary btn-xs">Read Multiple</button></span>
							</h5>
						</div>
						<div id="collapse1" class="panel-collapse collapse">
							<div class="panel-body">
								<table width="99%" class="table-hover">

									<tbody>
										<c:forEach var="type" items="${name}" varStatus="status">
											<spring:url value="/devices/${devID}/read/3/0/${type.key}"
												var="read" />
											<spring:url value="/devices/${devID}/write/3/0/${type.key}"
												var="write" />
											<spring:url value="/devices/${devID}/execute/3/0/${type.key}"
												var="execute" />
											<spring:url value="/devices/${devID}/observe/3/0/${type.key}"
												var="observe" />
											<tr>
												<td>3/0/${type.key}</td>
												<td>${type.value}</td>
												<td><div id="${type.key}"></div></td>
												<td><c:choose>
														<c:when
															test="${fn:contains(operation[status.index], 'R')}">
															<span class="pull-right"><button
																	class="btn btn-primary btn-xs"
																	onclick="getData('${read}', '${type.key}')">Read</button></span>
														</c:when>
													</c:choose> <c:choose>
														<c:when
															test="${fn:contains(operation[status.index], 'W')}">
															<span class="pull-right"><button
																	class="btn btn-success btn-xs"
																	onclick="writeData('${write}', '${type.key}')">Write</button></span>
														</c:when>
													</c:choose> <c:choose>
														<c:when
															test="${fn:contains(operation[status.index], 'E') && (operation[status.index] != 'NONE')}">
															<span class="pull-right"><button
																	class="btn btn-warning btn-xs"
																	onclick="execute('${execute}', '${type.key}')">Execute</button></span>
													
													<c:out value="" />
														</c:when>
													</c:choose></td>
												<td><div id="res${type.key}"></div></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>
								<a data-toggle="collapse" href="#collapse2">Connectivity
									Monitoring</a> <span class="pull-right"><button
										type="button" class="btn btn-primary btn-xs">Read
										Multiple</button></span>
							</h5>
						</div>
						<div id="collapse2" class="panel-collapse collapse">
							<div class="panel-body">Lorem ipsum dolor sit amet,
								consectetur adipisicing elit, sed do eiusmod tempor incididunt
								ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis
								nostrud exercitation ullamco laboris nisi ut aliquip ex ea
								commodo consequat.</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>
								<a data-toggle="collapse" href="#collapse3">Location</a> <span
									class="pull-right"><button type="button"
										class="btn btn-primary btn-xs">Read Multiple</button></span>
							</h5>
						</div>
						<div id="collapse3" class="panel-collapse collapse">
							<div class="panel-body">Lorem ipsum dolor sit amet,
								consectetur adipisicing elit, sed do eiusmod tempor incididunt
								ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis
								nostrud exercitation ullamco laboris nisi ut aliquip ex ea
								commodo consequat.</div>
						</div>
					</div>
				</div>


			</div>
		</div>
	</div>
</body>
</html>