<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form class="form-horizontal" id="groupWriteForm">
	<div class="row form-group">
		<div class="col-lg-3 col-md-3 col-sm-3">
			<label class="control-label">Object Link</label>
		</div>
		<div class="col-lg-3 col-md-3 col-sm-3">
			<select class="selectpicker" id="objectDropdown"
				onchange="getExecuteableResources()">
				<option value="" disabled selected>Select your option</option>
				<c:forEach var="objectMap" items="${objectMap}">
					<option value="'${objectMap.key}'">${objectMap.key}
						(${objectMap.value})</option>
				</c:forEach>
			</select>

		</div>
		<div class="col-lg-3 col-md-3 col-sm-3">
			<input type="number" id="instanceIdField" class="form-control"
				value="0" disabled onkeyup="updateCompleteObjectId()">
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
</form>
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

#instanceInput {
	/*	width: 30% !important;*/
	
}
</style>
<script>
	$('.selectpicker').selectpicker({
		size : 10
	});
</script>
<script>
	var ctx = "${pageContext.request.contextPath}"
</script>
