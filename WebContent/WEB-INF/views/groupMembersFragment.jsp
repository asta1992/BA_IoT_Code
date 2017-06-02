<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class ="col-md-12">
		<h2>Members of ${groupName}</h2>
	</div>
</div>
<div class="row">
	<div class ="col-md-11" id="selectBoxes">
		<select multiple="multiple" size="15" name="groups-duallistbox"
			class="groups-boxes">
			<c:forEach var="allComponents" items="${allComponents}" varStatus="loop">
				<option value="option${loop.index}" id="${allComponents.id}">${allComponents.name}</option>
			</c:forEach>
			<c:forEach var="groupMembers" items="${groupMembers}" varStatus="loop">
				<option value="option${loop.index}" id="${groupMembers.id}" selected="selected">${groupMembers.name}</option>
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
		nonSelectedListLabel : 'All Components',
		selectedListLabel : 'Members',
		preserveSelectionOnMove : 'moved',
		moveOnSelect : false,
	});
</script>
<script>var ctx = "${pageContext.request.contextPath}"</script>
