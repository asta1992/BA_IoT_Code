<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row form-group">
	<div class="col-lg-3 col-md-3">
		<label for="value" class="control-label">Name</label>
	</div>
	<div class="col-lg-9 col-md-9">
		<input type="text" class="form-control" id="configName"
			value="${configuration.name}">
	</div>
</div>
<div class="row form-group">
	<div class="col-lg-3 col-md-3">
		<label for="value" class="control-label">Description</label>
	</div>
	<div class="col-lg-9 col-md-9">
		<input type="text" class="form-control" id="description"
			value="${configuration.description}">
	</div>
</div>
<div class="row form-group">
	<div class="col-lg-3 col-md-3 col-sm-3">
		<label class="control-label">Object Link</label>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-3">
		<select class="selectpicker" id="objectDropdown"
			onchange="getWriteableResources()">
			<option value="" disabled selected>Select your option</option>
			<c:forEach var="objectMap" items="${objectMap}">
				<option value="'${objectMap.key}'">${objectMap.key}
					(${objectMap.value})</option>
			</c:forEach>
		</select>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-3">
		<input type="text" id="instanceIdField" class="form-control" value="0"
			disabled onkeyup="updateCompleteObjectId()">
	</div>
	<div class="col-lg-3 col-md-3 col-sm-3">
		<select class="selectpicker" id="resourceDropdown"
			onchange="updateCompleteObjectId()">
		</select>
	</div>
</div>
<div class="row form-group">
	<div class="col-lg-3 col-md-3">
		<label class="control-label"></label>
	</div>
	<div class="col-lg-9 col-md-9" id="completeObjectId"></div>
</div>
<div class="row form-group">
	<div class="col-lg-3 col-md-3">
		<label for="value" class="control-label">Value</label>
	</div>
	<div class="col-lg-9 col-md-9">
		<input type="text" class="form-control" id="writeValue">
	</div>
</div>
<div class="row">
	<div class="col-lg-12 col-md-12">
		<span class="pull-right">
			<button type="button" class="btn btn-success btn-md"
				onclick="addConfigurationItem()">
				<span class="glyphicon glyphicon-plus"></span>
			</button>
		</span>
	</div>
</div>
<div class="row">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
		id="configurationItemsDiv">
		<h4>Configuration Items</h4>
		<table class="table table-hover" id="configurationItems">
			<tr>
				<th width="25%">Object Link</th>
				<th width="75%">Value</th>
			</tr>
			<c:forEach var="configurationItem"
				items="${configuration.configurationItems}" varStatus="loop">
				<tr id="${loop.index}Row">
					<td class="objectIdField">${configurationItem.path}</td>
					<td class="valueField">${configurationItem.value} <span
						class="pull-right"><button class="btn btn-danger btn-xs"
								onclick="removeConfigurationItem(${loop.index})">
								<span class="glyphicon glyphicon-minus"></span>
							</button></span>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<style>
.bootbox-confirm .modal-body {
	height: 470px;
	overflow-y: auto;
}

.bootstrap-select {
	width: 100% !important;
}

.glyphicon {
	padding: 5px !important;
}
</style>

<script>
	$('.selectpicker').selectpicker({
		size : 10
	});
</script>
<script>var ctx = "${pageContext.request.contextPath}"</script>
