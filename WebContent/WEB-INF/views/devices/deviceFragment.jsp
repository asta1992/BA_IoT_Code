<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row page-heading">
	<div class="col-lg-5 col-md-12 col-sm-12 col-xs-12">
		<div style="display: none;" id="componentId">${device.id}</div>
		${device.name}
	</div>
	<div class="col-lg-7 col-md-12 col-sm-12 col-xs-12">
		<span class="pull-right"><button type="button"
				class="btn btn-danger heading-button"
				onclick="deleteDevice('${device.id}', '${device.name}')">Delete
				Device</button>
			<button type="button" class="btn btn-primary heading-button"
				onclick="openDeviceMemberships('${device.id}')">Group
				Memberships</button>
			<button type="button" class="btn btn-primary heading-button"
				onclick="readAll()">Read All</button></span>
	</div>
</div>
<div class=row>
	<div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
		<table class="table table-hover configurations-table">
			<tr>
				<td><b>Device ID:</b></td>
				<td>${device.id}</td>
			</tr>
			<tr>
				<td><b>Registration ID:</b></td>
				<td>${device.regId}</td>
			</tr>
			<tr>
				<td><b>Endpoint:</b></td>
				<td>${device.endpoint}</td>
			</tr>
			<tr>
				<td><b>Last Read:</b></td>
				<td>${device.lastUpdate}</td>
			</tr>
			<tr>
				<td><b>Last Seen:</b></td>
				<td>${device.lastRegistrationUpdate}</td>
			</tr>
		</table>
	</div>
	<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
		<div id="deviceLocation" class="panel panel-default dashboard">
			<div class="panel-heading">Device Location</div>
			<div id="map" class="panel-body map-device"></div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="panel-group" id="accordion">
			<c:forEach var="model" items="${modelDescription}" varStatus="loop">
				<spring:url
					value="/devices/${device.id}/read${objectLinks[loop.index]}"
					var="readMultiple" />
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>
							<a data-toggle="collapse" href="#collapse${loop.index}">${model.key}</a>
							<span class="pull-right"><button type="button"
									class="btn btn-primary btn-xs"
									id="btn-read-multiple${objectLinks[loop.index]}"
									onclick="readMultiple('${readMultiple}')">Read
									Multiple</button></span>
						</h5>
					</div>
					<div id="collapse${loop.index}" class="panel-collapse collapse">
						<div class="panel-body">
							<table style="width: 99%;" class="table-hover">

								<tbody>
									<c:forEach var="resource" items="${model.value}">
										<spring:url
											value="/devices/${device.id}/read${objectLinks[loop.index]}/${resource.id}"
											var="read" />
										<spring:url
											value="/devices/${device.id}/write${objectLinks[loop.index]}/${resource.id}"
											var="write" />
										<spring:url
											value="/devices/${device.id}/execute${objectLinks[loop.index]}/${resource.id}"
											var="execute" />
										<tr>
											<td>${objectLinks[loop.index]}/${resource.id}</td>
											<td>${resource.name}</td>
											<td><div
													id="readResponse${objectLinksDiv[loop.index]}${resource.id}">
													<c:set var="dataMapKey"
														value="${objectLinksDiv[loop.index]}${resource.id}" />
													<c:if test="${not empty device.dataMap[dataMapKey]}">
											${device.dataMap[dataMapKey]}
										</c:if>
												</div></td>
											<td><div
													id="writeResponse${objectLinksDiv[loop.index]}${resource.id}"></div></td>
											<td><c:set var="operations"
													value="${resource.operations}" /> <c:choose>
													<c:when test="${fn:contains(operations, 'R')}">
														<span class="pull-right"><button
																class="btn btn-primary btn-xs"
																onclick="readData('${read}', '${objectLinksDiv[loop.index]}${resource.id}')">Read</button></span>
													</c:when>
												</c:choose> <c:choose>
													<c:when test="${fn:contains(operations, 'W')}">
														<span class="pull-right"><button
																class="btn btn-success btn-xs"
																onclick="writeData('${write}', '${objectLinksDiv[loop.index]}${resource.id}', '${resource.type}')">Write</button></span>
													</c:when>
												</c:choose> <c:choose>
													<c:when test="${fn:contains(operations, 'E')}">
														<span class="pull-right"><button
																class="btn btn-warning btn-xs"
																onclick="execute('${execute}', '${objectLinksDiv[loop.index]}${resource.id}')">Execute</button></span>
													</c:when>
												</c:choose></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD7w-HEi_KCBCkCJNEKJzB2L7NJGl1CF4Y&callback=initMap"></script>
<script>
	var ctx = "${pageContext.request.contextPath}"
</script>
