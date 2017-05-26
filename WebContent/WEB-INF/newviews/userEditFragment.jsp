<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="row">
	<div class ="col-md-12">
		<h2>Users</h2>
	</div>
</div>
<div class="form-group">
  <label for="selectedUser">Select list:</label>
  <select class="form-control" id="selectedUser">
  <c:forEach var="user" items="${userList}">
			<option>${user.username}</option>
		</c:forEach>
    
  </select>
</div>