<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class=row>
	<h2>
		${group.name} <span class="pull-right"><button type="button"
				class="btn btn-danger heading-button">Delete Group</button></span> <span
			class="pull-right"><button type="button"
				class="btn btn-primary heading-button" onclick="readAll()">Update
				All Devices</button></span>
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
				onclick="openGroupManagement('${device.id}')">Group Members</button>
		</dt>
		<dd>
			<button type="button" class="btn btn-sm btn-default heading-button" onclick="addNewGroup('${group.id}')">Add New Group</button>
		</dd>

	</dl>
</div>
