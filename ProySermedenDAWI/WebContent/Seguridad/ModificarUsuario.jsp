<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modificar Usuario</title>
<sj:head jqueryui="true"/>
</head>
<body>
<h2>Modificar Usuario</h2>
<form action="ActualizarUsuarioAction" method="post">
		<table align="center">
			<tr>
				<td>Nombre</td>
				<td> <s:textfield name="usuario.nombre"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.nombre"></s:fielderror></td>
			</tr>
			<tr>
				<td>Apellido Paterno</td>
				<td> <s:textfield name="usuario.apepat"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.apepat"></s:fielderror></td>
			</tr>
			<tr>
				<td>Apellido Materno</td>
				<td> <s:textfield name="usuario.apemat"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.apemat"></s:fielderror></td>
			</tr>
			<tr>
				<td>DNI</td>
				<td> <s:textfield name="usuario.dni"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.dni"></s:fielderror></td>
			</tr>
			<tr>
				<td>Fecha de Nacimiento</td>
				<td> <sj:datepicker name="usuario.fechanac" displayFormat="dd/mm/yy" changeYear="true" changeMonth="true" yearRange="-55:-15"
					 ></sj:datepicker> 
			   </td>
			   
			</tr>
			<tr>
				<td>Sexo</td>
				<td> <s:radio name="usuario.sexo" list="#{'H':'Masculino','M':'Femenino'}"  ></s:radio> </td>
				<td> <s:fielderror fieldName="usuario.sexo"></s:fielderror></td>
			</tr>
			<tr>
				<td>Email</td>
				<td> <s:textfield name="usuario.email"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.email"></s:fielderror></td>
			</tr>
			<tr>
				<td>Telf. Fijo</td>
				<td> <s:textfield name="usuario.fijo"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.fijo"></s:fielderror></td>
			</tr>
			<tr>
				<td>Celular</td>
				<td> <s:textfield name="usuario.celular"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.celular"></s:fielderror></td>
			</tr>
			<tr>
				<td>Direcci�n</td>
				<td> <s:textfield name="usuario.direccion"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.direccion"></s:fielderror></td>
			</tr>
			<tr>
				<td>Tipo Usuario</td>
				<td> <s:select name="usuario.idPerfil" list="#{'1':'Administrador','2':'Auxiliar','3':'Recepcionista','4':'T�cnico Im�genes','5':'M. Tratante','6':'M. Especialista'}" >				
							</s:select>
			    </td>
			    <td> <s:fielderror fieldName="usuario.idPerfil"></s:fielderror></td>
			</tr>
			<s:if test="usuario.idPerfil==6">
				<tr>
				<td>Especialidad <br>(solo en el caso de Especialistas)</td>
				<td> <s:select name="usuario.idEspecialidad" list="#{'1':'Odontopediatria','2':'Endodoncia','3':'Cirugia Oral','4':'Ortodoncia','5':'Periodoncia','6':'Protesis Dental'}" 
								headerValue="----- Seleccione -----"
								headerKey="0">				
							</s:select>
			    </td>
			    <td> <s:fielderror fieldName="usuario.idEspecialidad"></s:fielderror></td>
				</tr>
			</s:if>
			<tr>
				<td>Usuario Sistema</td>
				<td> <s:textfield name="usuario.user"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.user"></s:fielderror></td>
			</tr>
			<tr>
				<td>Contrase�a</td>
				<td> <s:textfield name="usuario.contrasena"></s:textfield> </td>
				<td> <s:fielderror fieldName="usuario.contrasena"></s:fielderror></td>
			</tr>
			
			<tr>
				<td>Observaciones</td>
				<td> <s:textarea name="usuario.observaciones"></s:textarea> </td>
				<td> <s:fielderror fieldName="usuario.observaciones"></s:fielderror></td>
			</tr>
			<tr>
				<td colspan="2">
					<s:submit value="Actualizar"></s:submit>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>