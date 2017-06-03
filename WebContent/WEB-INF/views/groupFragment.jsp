<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row component-heading">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div style="display: none;" id="componentId">${group.id}</div>
		
			${group.name} <span class="pull-right"><button type="button"
					class="btn btn-danger heading-button"
					onclick="deleteGroup('${group.id}', '${group.name}')">Delete
					Group</button></span> <span class="pull-right"></span> <span class="pull-right"></span>
		
	</div>
</div>
<div class=row>
	<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">

		<ul>
			<li><button type="button" class="btn btn-sm btn-default"
			onclick="openGroupMembers('${group.id}')">Group Members</button></li>
			<li><button type="button" class="btn btn-sm btn-default"
			onclick="openGroupMemberships('${group.id}')">Group
			Memberships</button></li>
			<li><button type="button" class="btn btn-sm btn-default"
			onclick="addNewChildGroup('${group.id}')">Add New Child
			Group</button></li>
			<li><button type="button" class="btn btn-sm btn-default"
			onclick="writeConfigToChildDevices('${group.id}', '${group.name}')">Write
			Configuration</button></li>
			<li><button type="button" class="btn btn-sm btn-default"
			onclick="executeAllChildDevices('${group.id}')">Execute</button></li>
			<li><button type="button" class="btn btn-sm btn-default"
			onclick="writeAllChildDevices('${group.id}')">Write Command</button></li>
		</ul>

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
<script
	src="..${pageContext.request.contextPath}/resources/js/smartmanager/dashboard.js"></script>
<script>
	var ctx = "${pageContext.request.contextPath}"
</script>
