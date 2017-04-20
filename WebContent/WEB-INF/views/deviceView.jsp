<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smartmanager - Show Device</</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel=stylesheet href="resources/css/menu.css">
<script src="resources/js/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>



</head>
<body>
	<div class="container-fluid">
		<div class="row">

			<jsp:include page="../views/fragments/menu.jsp" />
			<div class="col-xs-12 col-xm-6 col-md-6 col-lg-7">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>Value</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="type" items="${name}" varStatus="status">
							<spring:url value="/devices/${devID}/read/3/0/${type.key}" var="read" />
							<spring:url value="/devices/${devID}/write/3/0/${type.key}" var="write" />
							<spring:url value="/devices/${devID}/execute/3/0/${type.key}" var="execute" />
							<spring:url value="/devices/${devID}/observe/3/0/${type.key}" var="observe" />
							<tr>
								<td>3/0/${type.key}</td>
								<td>${type.value}</td>
								<td><div id="${type.key}"></div></td>
								<td><c:choose>
										<c:when test="${fn:contains(operation[status.index], 'R')}">
											<button class="btn btn-primary" onclick="getData('${read}', '${type.key}')">Read</button>
										</c:when>
									</c:choose> <c:choose>
										<c:when test="${fn:contains(operation[status.index], 'W')}">
											<button class="btn btn-primary" onclick="location.href='${write}'">Write</button>
										</c:when>
									</c:choose> <c:choose>
										<c:when test="${fn:contains(operation[status.index], 'E') && (operation[status.index] != 'NONE')}">
											<button class="btn btn-danger" onclick="getData('${read}')">Execute</button>
										</c:when>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>


			</div>
			<div class="col-xs-12 col-xm-3 col-md-4 col-lg-3"></div>
		</div>
	</div>

	<script type="text/javascript">
		function getData(url, id) {
			$.ajax({
				dataType : "json",
				url : url,
				success : function(data) {
					$("#" + id).text(data.content.value);
				}
			});
		}
	
	</script>
</body>
</html>



