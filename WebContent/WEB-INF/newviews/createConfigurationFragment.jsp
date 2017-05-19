<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="configurationItems">
	<form class="form-horizontal" id="writeForm1">
		<div class="row form-group">
			<div class="col-lg-3 col-md-3 col-sm-3">
				<label class="control-label">Object Link</label>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-3">
				<select class="selectpicker" id="objectDropdown"
					onchange="getWriteableResources()">
					<c:forEach var="objectMap" items="${objectMap}">
						<option value="'${objectMap.key}'">${objectMap.key}
							(${objectMap.value})</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-3">
				<input type="text" id="instanceIdField" class="form-control"
					value="0" disabled="true" onkeyup="updateCompleteObjectId()">
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
		<div class="row form-group">
			<div class="col-log-3 col-md-3"></div>
			<div class="col-lg-9 col-md-9">
				<span class="pull-right">
					<button type="button" class="btn btn-default "
						onclick="createConfiguration()">Delete Configuration Item</button>
			</div>
		</div>
	</form>
</div>
<div class="row" id="addButtonDiv">
	<span class="pull-right">
		<button type="button" class="btn btn-default "
			onclick="addAnotherConfigurationItem()">Add Another
			Configuration Item</button>
	</span>
</div>
<style>
.bootbox-confirm .modal-body {
	height: 470px;
	overflow-y: auto;
}

.bootstrap-select {
	width: 100% !important;
}

.bootstrap-select>.dropdown-toggle {
	/*color: red !important;*/
	
}
</style>
<script>
	$('.selectpicker').selectpicker({
		size : 10
	});
</script>