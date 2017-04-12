<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

Data <p/>

<c:forEach var="row" items="${devices}">
    Name: ${row.id}<br/>
    Type: ${row.name}<br/>
    ipAddress: ${row.ipAddress}<br/>
    Credential    
</c:forEach>
<a href="/smartmanager/createDevice">Create a Device</a>
<c:out value="${response}"></c:out>

</body>
</html>