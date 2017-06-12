<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row page-heading">
	<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
		<div style="display: none;" id="componentId">${group.id}</div>
		${group.name}
	</div>
	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
		<span class="pull-right"><button type="button"
				class="btn btn-danger heading-button"
				onclick="deleteGroup('${group.id}', '${group.name}')">Delete
				Group</button></span> <span class="pull-right"></span> <span class="pull-right"></span>
	</div>
</div>
<div class=row>
	<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<a href="#" onclick="openGroupMembers('${group.id}')">Group
					Members</a>
			</div>
			<div class="panel-heading">
				<a href="#" onclick="openGroupMemberships('${group.id}')">Group
					Memberships</a>
			</div>
			<div class="panel-heading">
				<a href="#" onclick="addNewChildGroup('${group.id}')">Add New
					Child Group</a>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<a href="#"
					onclick="writeConfigToChildDevices('${group.id}', '${group.name}')">Write
					Configuration</a>
			</div>
			<div class="panel-heading">
				<a href="#" onclick="executeAllChildDevices('${group.id}')">Execute</a>
			</div>
			<div class="panel-heading">
				<a href="#" onclick="writeAllChildDevices('${group.id}')">Write</a>
			</div>
		</div>
	</div>
	<div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
		<div class="panel panel-default dashboard">
			<div class="panel-heading">Group Devices</div>
			<div id="map" class="panel-body map-group"></div>
		</div>
	</div>
</div>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD7w-HEi_KCBCkCJNEKJzB2L7NJGl1CF4Y&callback=initMap"></script>
<script>
	var ctx = "${pageContext.request.contextPath}"
</script>
