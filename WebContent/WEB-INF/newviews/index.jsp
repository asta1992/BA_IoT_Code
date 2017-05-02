<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Smartmanager</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
	<script src="../resources/js/dataLoader.js"></script>
	<script src="../resources/js/eventListeners.js"></script>
</head>
<body>
	<div class="container-fluid">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12-col-xs-12 bg-primary text-white" style="padding: 0 0 0 0;">
                <h1>Smartmanager</h1>
                <br>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-2 col-md-4 col-sm-5 col-xs-12" style="padding: 0 0 0 0;">
                <jsp:include page="../newviews/fragments/menuFragment.jsp" />
            </div>
            <div class="col-lg-10 col-md-8 col-sm-7 col-xs-12">

            </div>
        </div>
    </div>
	
</body>
</html>