<%@ taglib uri="/struts-tags" prefix="s" %>
<link href="css/menu.css" rel="stylesheet" type="text/css" />


<TABLE width="100%" border="0">
		  <TR><td>&nbsp;</td></TR>
          <TR>
		  	   <TD>
		  	   <div>
					<ul id="navlist">
						<li><A class=SiteLinkBold href="#">Principal</A></li>
				</ul>
				</div>
	           </TD>
		  </TR> 
		   <TR><td>&nbsp;</td></TR>
		   <s:iterator value="#session.b_menu">
		   	 <TR> 
		  	   <TD>
		  	   <div>
					<ul id="navlist">
						<li>
						<s:if test="descripcion == 'Editar Mis Datos'">
						<s:url id="cargarDatos" action="buscarDatosPaciente">
							<s:param name="dniBuscado">
						 		<s:property value="#session.b_usuario.dni"/>
							</s:param>
						</s:url>
						<s:a href="%{cargarDatos}" id="current"><s:property value="descripcion"/></s:a>
						</s:if>
						<s:else>
						<A class=SiteLinkBold href="<s:property value="enlace"/>" id="current"><s:property value="descripcion"/></A>
						</s:else>
						</li>
				</ul>
				</div>
	           </TD>
		  </TR>
		   </s:iterator>

		  <TR><td>&nbsp;</td></TR>
		  <TR>
			    <TD>
			    <div>
					<ul id="navlist">
						<li>
						<A class="SiteLinkBold" HREF="LogueoSistema.jsp">Salir</A>
						</li>
				</ul>
				</div>	
				</TD>
		  </TR>
		<!--  <TR><td>&nbsp;</td></TR>
		  <TR>
			    <TD class="TextoMenu">
                   <s:if test='#session.b_usuario.sexo == "H"'>
                            Bienvenido  Sr:<br>
                    </s:if>
                    <s:if test='#session.b_usuario.sexo == "M"'>
                            Bienvenida  Sra:<br>
                    </s:if> 
                    
                    <s:property value="#session.b_usuario.nombre"/>
                    
                    <s:property value="#session.b_usuario.apepat"/>
				</TD>
		  </TR>	 Este cambio es mio-->  
</TABLE>