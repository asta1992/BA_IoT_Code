<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Create a new Device</h1>

<form method="post" action="${pageContext.request.contextPath}/doCreate">

<table>
<tr>
	<td>
		Name:
	</td>
	<td>
		<input name="name" type="text" />
	</td>
</tr>

<tr>
	<td>
		protocolType:
	</td>
	<td>
		<input name="protocolType" type="text" />
	</td>
</tr>

<tr>
	<td>
		AuthType:
	</td>
	<td>
		<input name="authType" type="text" />
	</td>
</tr>


<tr>
	<td>
		ipAddress:
	</td>
	<td>
		<input name="ipAddress" type="text" />
	</td>
</tr>

<tr>
	<td>
		Username:
	</td>
	<td>
		<input name="username" type="text" />
	</td>
</tr>
<tr>
	<td>
		Password:
	</td>
	<td>
		<input name="password" type="password" />
	</td>
</tr>
<tr>
	<td> </td>
	<td>
		<input value="Create Device" type="submit" />
	</td>
</tr>



</table>


</form>






</body>
</html>