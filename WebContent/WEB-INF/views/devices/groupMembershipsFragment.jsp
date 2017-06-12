<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class ="col-md-12">
		<h2>Memberships of ${componentName}</h2>
	</div>
</div>
<div class="row">
	<div class ="col-md-11" id="selectBoxes">
		<select multiple="multiple" size="15" name="groups-duallistbox"
			class="groups-boxes">
			<c:forEach var="allGroups" items="${allGroups}" varStatus="loop">
				<option value="option${loop.index}" id="${allGroups.id}">${allGroups.name}</option>
			</c:forEach>
			<c:forEach var="deviceGroups" items="${deviceGroups}" varStatus="loop">
				<option value="option${loop.index}" id="${deviceGroups.id}" selected="selected">${deviceGroups.name}</option>
			</c:forEach>
			
		</select>
	</div>
</div>
<style>
.bootbox-confirm .modal-body {
	height: 470px;
	overflow-y: auto;
}

#bootstrap-duallistbox-nonselected-list_groups-duallistbox {
	height: 250px !important;
}

#bootstrap-duallistbox-selected-list_groups-duallistbox {
	height: 250px !important;
	
}

</style>
<script>
	$('.groups-boxes').bootstrapDualListbox({
		nonSelectedListLabel : 'All Groups',
		selectedListLabel : 'Memberships',
		preserveSelectionOnMove : 'moved',
		moveOnSelect : false,
	});
	
	var ctx = "${pageContext.request.contextPath}"
</script>
