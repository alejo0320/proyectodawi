<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib uri="/struts-tags" prefix="s" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ingresar al Sistema</title>
</head>
<h1>Sermeden</h1>
<body>
	<form action="logueo">
		<table align="center" >
			<tr>
				<td><s:label value="Usuario"></s:label> </td>
				<td><s:textfield name="elusuario.user"></s:textfield> </td>
			</tr>
			<tr>
				<td><s:label value="Contrase�a"></s:label> </td>
				<td><s:password name="elusuario.contrasena"></s:password> </td>
			</tr>
			<tr>
				<td colspan="2">
					<s:submit value="Ingresar"></s:submit>
				</td>
			</tr>
			<tr>
				<td> <s:a href="a_recuperarPassword">Olvido su contrase�a?</s:a> </td>
			</tr>
			<tr>
				<td><s:property value="mensaje"/></td>
			</tr>
		</table>
	</form>
</body>
</html>