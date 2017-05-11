<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="groupSelection">
	<div class="col-md-7">
			<select multiple="multiple" size="15" name="duallistbox_demo"
				class="demo">
				<c:forEach var="allGroups" items="${allGroups}" varStatus="loop">
				<option value="option${loop.index}">${allGroups.name}</option>
				</c:forEach>
			</select>
	</div>
	<script>
	var demo2 = $('.demo').bootstrapDualListbox({
        nonSelectedListLabel: 'Non-selected',
        selectedListLabel: 'Selected',
        preserveSelectionOnMove: 'moved',
        moveOnSelect: false,
    });
	</script>
</div>