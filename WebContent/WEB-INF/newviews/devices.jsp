<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="http://code.gijgo.com/1.3.1/js/gijgo.js"
	type="text/javascript"></script>
<link href="http://code.gijgo.com/1.3.1/css/gijgo.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<link rel=stylesheet href="resources/css/menu.css">
<script src="resources/js/navigationdata.js" type="text/javascript"></script>
<script src="resources/js/devicedata.js" type="text/javascript"></script>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"></div>
			<jsp:include page="../newviews/fragments/menuFragment.jsp" />
		</div>

		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
				<h4>Navigation</h4>
				<div id="tree"></div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
				<h4>
					LeshanDevice Client <span class="pull-right"><button
							type="button" class="btn btn-danger">Delete</button></span> <span
						class="pull-right"><button type="button"
							class="btn btn-primary">Read All</button></span>
				</h4>
				<dl class="dl-horizontal">
					<dt>Registration ID</dt>
					<dd>V7Ao74z4lO</dd>
					<dt>IPAddress</dt>
					<dd>2001:cdba:0000:0000:0000:0000:3257:9652</dd>
					<dt>Authentication</dt>
					<dd>Certificate Authentication</dd>
					<dt>Last Updated</dt>
					<dd>03.05.2017 20.19 Uhr</dd>
					<dt>Group Memberships</dt>
					<dd></dd>

				</dl>
			
			<div id="lwm2mproperties"></div>

			</div>
		</div>

	</div>

</body>
</html>