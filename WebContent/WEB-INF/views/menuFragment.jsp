<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">SmartManager</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="/smartmanager/">Home</a></li>
				<li><a href="/smartmanager/devices">Devices</a></li>
				<li><a href="/smartmanager/discovery">Discovery</a></li>
				<li><a href="/smartmanager/configurations">Configurations</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">${username}<span
						class="glyphicon glyphicon-user"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/smartmanager/users">Settings</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="<c:url value='j_spring_security_logout'/>">Logout</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>