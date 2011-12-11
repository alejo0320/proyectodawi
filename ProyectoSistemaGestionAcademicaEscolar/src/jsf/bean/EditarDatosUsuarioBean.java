package jsf.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import servicios.ApplicationBusinessDelegate;
import servicios.PersonaService;
import servicios.UsuarioService;

import entidades.Distrito;
import entidades.Persona;
import entidades.Usuario;


@SuppressWarnings("serial")
@SessionScoped
@ManagedBean 
public class EditarDatosUsuarioBean implements Serializable{
	
	private static ApplicationBusinessDelegate abd = new ApplicationBusinessDelegate();
	
	private static UsuarioService usuarioService = abd.getUsuarioService();
	private static PersonaService personaService = abd.getPersonaService();

	private Usuario usuario;
	
	private boolean editMode;
	
	private String password;
	
	public EditarDatosUsuarioBean(){
		System.out.println("Creando EditarDatosUsuario...");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public void cargaDatos(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		Usuario sessionUsuario = (Usuario)session.getAttribute("b_usuario");
		
		try {
			usuario = usuarioService.obtieneUsuario(sessionUsuario);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void actualizaDatos(){
		System.out.println("---------------------------------------------------------------");
		System.out.println("--------------------------Actualiza Empleado---------------------");
		System.out.println("---------------------------------------------------------------");
		
		System.out.println("-->"+usuario.getPersonas().getStrCodigoPersona());
		System.out.println("-->"+usuario.getPersonas().getDistritos().getIntIdDistrito());
		
		try {
			
			Persona tmpPersona = new Persona();
			tmpPersona.setStrCodigoPersona(usuario.getPersonas().getStrCodigoPersona());
			tmpPersona.setStrNombre(usuario.getPersonas().getStrNombre());
			tmpPersona.setStrApellidoPaterno(usuario.getPersonas().getStrApellidoPaterno());
			tmpPersona.setStrApellidoMaterno(usuario.getPersonas().getStrApellidoMaterno());
			tmpPersona.setIntDNI(usuario.getPersonas().getIntDNI());
			Distrito entidadDistrito = new Distrito();
			entidadDistrito.setIntIdDistrito(usuario.getPersonas().getDistritos().getIntIdDistrito());
			tmpPersona.setDistritos(entidadDistrito);
			tmpPersona.setStrDireccion(usuario.getPersonas().getStrDireccion());
			tmpPersona.setStrMail(usuario.getPersonas().getStrMail());
			tmpPersona.setStrTelefono(usuario.getPersonas().getStrTelefono());
			tmpPersona.setStrCelular(usuario.getPersonas().getStrCelular());
			tmpPersona.setStrSexo(usuario.getPersonas().getStrSexo());
				
			personaService.actulizarPersona(tmpPersona);
				
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Actulizaci�n de Datos","Se actualizaron sus datos correctamente"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cambiaContrasena(){
		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		Usuario sessionUsuario = (Usuario)session.getAttribute("b_usuario");
		
		System.out.println(password);
		
		Persona tmpPersona = new Persona();
		tmpPersona.setStrCodigoPersona(sessionUsuario.getPersonas().getStrCodigoPersona());
		
		Usuario tmpUsuario = new Usuario();
		tmpUsuario.setPersonas(tmpPersona);
		tmpUsuario.setStrContrasena(password);
		
		try {
			usuarioService.cambiaContrasena(tmpUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Actulizaci�n de Contrase�a","Actualizaci�n de contrase�a correcta"));

	}
	

}
