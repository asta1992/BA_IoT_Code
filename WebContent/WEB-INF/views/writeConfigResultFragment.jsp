<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:forEach var="result" items="${result}">
	<h3>
		${result.key}
	</h3>		
</c:forEach>


<style>

</style>




<script>
	$('.selectpicker').selectpicker({
		size : 10
	});
</script>