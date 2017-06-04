<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
$( document ).ready(function() {
	bootbox.alert({
		size : "small",
		title : "Corrupted Device",
		message : "The device was corrupted. <br> Please choose another one.",
		callback : function() {
			parent.location.reload();
		}
	});
});
</script>