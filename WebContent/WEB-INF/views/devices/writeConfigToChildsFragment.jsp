<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form class="form-horizontal" id="groupWriteForm">
	<div class="row form-group">
		<div class="col-lg-4 col-md-4 col-sm-4">
			<label class="control-label">Choose Configuration</label>
		</div>
		<div class="col-lg-8 col-md-8 col-sm-8">
			<select id="configSelector" class="selectpicker">
				<option disabled selected value="none" id="none">select a configuration</option>
				<c:forEach var="configurations" items="${configurations}">
					<option value="${configurations.id}">${configurations.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
</form>
<style>
.modal-body {
	height: 270px;
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
<script>var ctx = "${pageContext.request.contextPath}"</script>
