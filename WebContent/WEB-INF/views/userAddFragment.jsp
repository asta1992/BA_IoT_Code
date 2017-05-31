<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="loginForm" method="post" class="form-horizontal">
	<div class="form-group">
		<label class="col-xs-3 control-label">Username</label>
		<div class="col-xs-5">
			<input id='username' value="" type="text" class="form-control" name="username" required />
		</div>
	</div>

	<div class="form-group">
		<label class="col-xs-3 control-label">Password</label>
		<div class="col-xs-5">
			<input id="firstPassword" type="password" class="form-control" name="password" required />
		</div>
	</div>

	<div class="form-group">
		<label class="col-xs-3 control-label">Confirm Password</label>
		<div class="col-xs-5">
			<input id="secondPassword" type="password" class="form-control" name="password" required />
		</div>
	</div>
</form>









