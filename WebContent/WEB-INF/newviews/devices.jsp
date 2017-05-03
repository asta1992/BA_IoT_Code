<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Smartmanager</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<link rel=stylesheet href="resources/css/menu.css">
<script
  src="http://code.jquery.com/jquery-3.2.1.js"
  integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
  crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
	
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">
    <link href="https://code.getmdl.io/1.2.1/material.indigo-pink.min.css" rel="stylesheet" type="text/css">
	<script src="http://code.gijgo.com/1.3.0/js/gijgo.js" type="text/javascript"></script>
	<link href="http://code.gijgo.com/1.3.0/css/gijgo.css" rel="stylesheet" type="text/css" />
	 <script src="resources/js/treedata.js" type="text/javascript"></script>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"></div>
			<jsp:include page="../newviews/fragments/menuFragment.jsp" />
		</div>

		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
				<h2>Navigation</h2>
				<div id="tree"></div>
				<script>
				$('#tree').tree({
			        primaryKey: 'id',
					uiLibrary: 'bootstrap',
					border: false,
					dataSource: [
			        { text: 'North America', children: [ { text: 'USA', children: [ { text: 'California' }, { text: 'Miami' } ] }, { text: 'Canada' },  { text: 'Mexico' } ] },
			        { text: 'Europe', children: [ { text: 'France' },  { text: 'Spain' },  { text: 'Italy' } ] },
			        { text: 'South America', children: [ { text: 'Brazil' },  { text: 'Argentina' },  { text: 'Columbia' } ] }
			      ]
			    });
				</script>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
				<h2>
					LeshanDevice Client <span class="pull-right"><button
							type="button" class="btn btn-danger">Delete</button></span> <span
						class="pull-right"><button type="button"
							class="btn btn-primary">Read All</button></span>
				</h2>
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
			</div>
		</div>

	</div>

</body>
</html>