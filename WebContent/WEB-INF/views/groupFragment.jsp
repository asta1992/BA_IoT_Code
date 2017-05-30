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
				class="btn btn-primary heading-button" onclick="readAll()">Update
				All Devices</button></span> <span class="pull-right"><button type="button"
				class="btn btn-warning heading-button"
				onclick="writeAllChildDevices('${group.id}')">Write Command</button></span>
				<span class="pull-right"><button type="button"
				class="btn btn-warning heading-button"
				onclick="executeAllChildDevices('${group.id}')">Execute</button></span>
	</h2>
</div>
<div class=row>
	<dl class="dl-horizontal">
		<dt>Registration Id</dt>
		<dd>${group.id}</dd>
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
