<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<sj:head jqueryui="true"/>
</head>
<body>
<h4>Turnos</h4>
	<table align="center" border="1">
		<tr>
			<td>Dia</td>
			<td>Hora Inicio</td>
			<td>Hora Fin</td>
			<td>M�dico Tratante</td>
		</tr>
		
		<s:iterator value="listadoTurnos" >
		<tr>
			<td align="center"><s:property value="Dia"/> </td>
			<td align="center"><s:property value="HoraInicio"/>p.m. </td>
			<td align="center"><s:property value="HoraFin"/>p.m. </td>
			<td align="center"><s:property value="ncompleto"/> </td>
		</tr>
		</s:iterator>
</table>
	

</body>
</html>