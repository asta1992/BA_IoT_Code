<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form class="form-horizontal">
	<div class="form-group">
		<label for="objectLink" class="col-sm-2 control-label">Object Link</label>
		<div class="col-sm-10">
			<select class="selectpicker" multiple>
  				<option>Mustard</option>
  				<option>Ketchup</option>
  				<option>Relish</option>
</select>
			
		</div>
	</div>
	<div class="form-group">
		<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
		<div class="col-sm-10">
			<input type="password" class="form-control" id="inputPassword3"
				placeholder="Password">
		</div>
	</div>
</form>