<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.modal-body {
	height: 470px;
	overflow-y: auto;
}
</style>
<div id="resultsWrapper">
<c:forEach var="entry" items="${result}">
	<h4><b>${entry.key}</b></h4>
	<table class="table table-hover">
	<thead>
		<tr>
			<th>Path</th>
			<th>Result</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${entry.value}">
			<c:forEach var="map" items="${item}">
						<tr>
							<td>${map.key}</td>
							<td>${map.value}</td>
						</tr>
					</c:forEach>
		</c:forEach>
	</tbody>
	</table>
</c:forEach>
</div>
<script>var ctx = "${pageContext.request.contextPath}"</script>

