<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form class="form-horizontal" id="groupWriteForm">
    <div class="row form-group">
        <div class="col-lg-3 col-md-3 col-sm-3">
            <label class="control-label">Object Link</label>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-3">
            <select class="selectpicker">
            	<!--<c:forEach var="object" items="${object}">
            		<option data-tokens="${object.objectLink}">${object.objectLink} ${object.name}</option>
            	</c:forEach>-->
                <option data-tokens="1">1 (LWM2M Server)</option>
                <option data-tokens="2">2</option>
                <option data-tokens="3">3 (Device)</option>
                <option data-tokens="4">4</option>
                <option data-tokens="5">5</option>
                <option data-tokens="6">6 (Location)</option>
                <option data-tokens="7">7</option>
                <option data-tokens="8">8</option>
                <option data-tokens="9">9</option>
            </select>

        </div>
        <div class="col-lg-3 col-md-3 col-sm-3">
            <select class="selectpicker">
            	<!--<c:forEach var="object" items="${object}">
            		<option data-tokens="${object.objectLink}">${object.objectLink} ${object.name}</option>
            	</c:forEach>-->
                <option data-tokens="0">0</option>
                <option data-tokens="1">1</option>
                <option data-tokens="2">2</option>
                <option data-tokens="3">3</option>
                <option data-tokens="4">4</option>
                <option data-tokens="5">5</option>
                <option data-tokens="6">6</option>
                <option data-tokens="7">7</option>
                <option data-tokens="8">8</option>
            </select>

        </div>
        <div class="col-lg-3 col-md-3 col-sm-3">
            <select class="selectpicker">
            	<!--<c:forEach var="object" items="${object}">
            		<option data-tokens="${object.objectLink}">${object.objectLink} ${object.name}</option>
            	</c:forEach>-->
                <option data-tokens="1">1</option>
                <option data-tokens="2">2</option>
                <option data-tokens="3">3</option>
                <option data-tokens="4">4</option>
                <option data-tokens="5">5</option>
                <option data-tokens="6">6</option>
                <option data-tokens="7">7</option>
                <option data-tokens="8">8</option>
                <option data-tokens="9">9</option>
            </select>
        </div>
    </div>
    <div class="row form-group">
    	<div class="col-lg-3 col-md-3">
    		<label class="control-label"></label>
    	</div>
    	<div class="col-lg-9 col-md-9" id="completeObjectId">
    	</div>
    </div>
    <div class="row form-group">
        <div class="col-lg-3 col-md-3">
            <label for="value" class="control-label">Value</label>
        </div>
        <div class="col-lg-9 col-md-9">
            <input type="text" class="form-control" id="value">
        </div>
    </div>
</form>
<style>
.bootbox-confirm .modal-body {
	height: 470px;
	overflow-y: auto;
}

.bootstrap-select {
	width: 100% !important;
}

.bootstrap-select > .dropdown-toggle {
	/*color: red !important;*/
}
</style>
<script>
$('.selectpicker').selectpicker({
	  size: 10
	});

</script>