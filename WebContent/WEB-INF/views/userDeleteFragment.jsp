<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="form-group">
  <select class="form-control" id="selectedUser">
  <c:forEach var="user" items="${userList}">
			<option>${user.username}</option>
		</c:forEach>
    
  </select>
</div>
<script>var ctx = "${pageContext.request.contextPath}"</script>
