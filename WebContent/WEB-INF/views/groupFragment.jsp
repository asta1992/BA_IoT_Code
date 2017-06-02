<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class=row>
	<h2>
		${group.name} <span class="pull-right"><button type="button"
				class="btn btn-danger heading-button"
				onclick="deleteGroup('${group.id}', '${group.name}')">Delete
				Group</button></span> <span class="pull-right"><button type="button"
				class="btn btn-warning heading-button"
				onclick="writeAllChildDevices('${group.id}')">Write Command</button></span>
		<span class="pull-right"><button type="button"
				class="btn btn-warning heading-button"
				onclick="executeAllChildDevices('${group.id}')">Execute</button></span>
	</h2>
</div>
<div class=row>
	<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
		<dl class="dl-horizontal">
			<dt>Group ID</dt>
			<dd id="componentId">${group.id}</dd>
			<dt>Group Name</dt>
			<dd>${group.name}</dd>

			<dt>
				<button type="button" class="btn btn-sm btn-default heading-button"
					onclick="openGroupMembers('${group.id}')">Group Members</button>
			</dt>
			<dd>
				<button type="button" class="btn btn-sm btn-default heading-button"
					onclick="openGroupMemberships('${group.id}')">Group
					Memberships</button>
			</dd>
			<dt>
				<button type="button" class="btn btn-sm btn-default heading-button"
					onclick="addNewChildGroup('${group.id}')">Add New Child
					Group</button>
			</dt>
			<dd>
				<button type="button" class="btn btn-sm btn-default heading-button"
					onclick="writeConfigToChildDevices('${group.id}', '${group.name}')">Write
					Configuration</button>
			</dd>

		</dl>
	</div>
	<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
		<div class="panel panel-default dashboard">
			<div class="panel-heading">Group Devices</div>
			<div id="map" class="panel-body map-group"></div>
		</div>
	</div>
</div>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD7w-HEi_KCBCkCJNEKJzB2L7NJGl1CF4Y&callback=initMap"></script>
<script src="..${pageContext.request.contextPath}/resources/js/smartmanager/dashboard.js"></script>
<script>var ctx = "${pageContext.request.contextPath}"</script>
