package jsf.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import servicios.AlumnoService;
import servicios.ApplicationBusinessDelegate;
import servicios.DistritoService;
import servicios.MatriculaService;
import servicios.PersonaService;
import servicios.SeccionprogramadaService;
import utiles.EnviaMail;
import entidades.Alumno;
import entidades.Apoderado;
import entidades.Calendarioacademico;
import entidades.Distrito;
import entidades.Matricula;
import entidades.Persona;
import entidades.Seccionprogramada;

@SuppressWarnings("serial")
@SessionScoped
@ManagedBean
public class AlumnoBean implements Serializable{
	
    private static ApplicationBusinessDelegate abd = new ApplicationBusinessDelegate();
    private static PersonaService apoderadoService=abd.getPersonaService();
	private static AlumnoService alumnoService=abd.getAlumnoService();
	private static DistritoService distritoService=abd.getDistritoService();
	private static SeccionprogramadaService seccionprogService=abd.getSeccionprogramadaService();
	private static MatriculaService matriculaService=abd.getMatriculaService();
	
	private Alumno alumno;
	private Alumno selectedAlumno = new Alumno();
	private Persona persona;
	private Apoderado apoderado;
	private ArrayList<Alumno> alumnos;
	private ArrayList<Distrito> listadistritos;
	public ArrayList<String> listaSecundaria, listaPrimaria,listaGrados;
	private boolean editMode;
	private String strCodigoApoderado,mensaje,seccionAlumno,gradoAlumno,nivelAlumno;
	private int codigoDistrito;
	private StreamedContent image; 
	private String valor;
	
	private Alumno nuevoAlumno =  new Alumno();
	
	public AlumnoBean() {
		System.out.println("Creado AlumnoBean...");
		listaGrados=new ArrayList<String>();
		listaPrimaria=new ArrayList<String>();
		listaSecundaria=new ArrayList<String>();
		CargarDistritos();
		cargarListasGrados();
		
	}
	
	public void cargarImagenActualiza(FileUploadEvent event) {  
    	System.out.println("XD " + event.getFile().getFileName());
    	System.out.println("XD1 " + selectedAlumno.getFotobin());
    	System.out.println("XD2 " + event.getFile().getContents());
    	
    	try {
    		
    		selectedAlumno.setScImagen(new DefaultStreamedContent(event.getFile().getInputstream()));
    	    
    	    System.out.println("XD" + event.getFile().getFileName());
    	    
    	    byte[] foto = event.getFile().getContents();
    	    selectedAlumno.setFotobin(foto);
    	    
    	    System.out.println("XD2 " + selectedAlumno.getFotobin());
    	    
    	    FacesMessage msg = new FacesMessage("Acci�n Completada!!!", event.getFile().getFileName() + " se carg�.");
    	    FacesContext.getCurrentInstance().addMessage(null, msg);
    	  } catch (Exception ex) {
    	 } 
	}
	
	 public void cargarImagenInsertar(FileUploadEvent event) {  
		    System.out.println("cargarImagenInsertar");
	    	System.out.println("XD " + event.getFile().getFileName());
	    	
	    	try {
	    	    image = new DefaultStreamedContent(event.getFile().getInputstream());
	    	    
	    	    System.out.println("XD" + event.getFile().getFileName());
	    	    
	    	    byte[] foto = event.getFile().getContents();
	    	    nuevoAlumno.setFotobin(foto);
	    	    
	    	    FacesMessage msg = new FacesMessage("Acci�n Completada!!!", event.getFile().getFileName() + " se carg�.");
	    	    FacesContext.getCurrentInstance().addMessage(null, msg);
	    	  } catch (Exception ex) {
	    		  ex.printStackTrace();
	    	 } 
	}
	 
	
	public void registraAlumno(ActionEvent ae) {  
		System.out.println("insertando alumno");
		
		Date auxi = new Date(nuevoAlumno.getFechaNacimiento().getTime());
		nuevoAlumno.setDtFecNac(auxi);
		String dniingresado=""+nuevoAlumno.getIntDni();
		System.out.println("dni del alumno: "+dniingresado);
		nuevoAlumno.setStrCodigoAlumno("AL-"+dniingresado);
		Distrito tempodis=new Distrito();
		tempodis.setIntIdDistrito(codigoDistrito);
		nuevoAlumno.setDistritos(tempodis);
		
		if(nuevoAlumno.getFotobin()==null){
			InputStream stream = 
				((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/images/noDisponible.jpg");
		byte[] foto;
			try {
				foto = IOUtils.toByteArray(stream);
				nuevoAlumno.setFotobin(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}  		
		}
		
		Seccionprogramada sptempo=new Seccionprogramada();
		if(gradoAlumno.length()==3)
			sptempo.setIntGrado(Integer.parseInt(gradoAlumno.substring(0, 2)));
		else if(gradoAlumno.length()==2)
			sptempo.setIntGrado(Integer.parseInt(gradoAlumno.substring(0, 1)));
		
		sptempo.setStrNivel(nivelAlumno);
		sptempo.setStrSeccion(seccionAlumno);
		
		Seccionprogramada sbuscada =new Seccionprogramada();
		try {
			sbuscada = seccionprogService.obtenerSP(sptempo);
			System.out.println("id seccion programada encontrada: "+sbuscada.getIntIdSeccionProgramada());
		} catch (Exception e1) {
			System.out.println("Error al buscar seccion programada ... ");
			e1.printStackTrace();
		}
		
		Matricula mattempo=new Matricula();
		Calendar c2 = new GregorianCalendar();
		mattempo.setAlumno(nuevoAlumno);
		mattempo.setDtFecMat(new Date(c2.getTimeInMillis()));
		mattempo.setSeccionprogramada(sbuscada);
		//aki
		Calendarioacademico calendatempo=new Calendarioacademico();
		calendatempo.setStrCodcalendario("2012");
		mattempo.setCalendarioacademico(calendatempo);
		
		try {
			for (Method m : nuevoAlumno.getClass().getMethods()){
				if((m.getName().startsWith("getStr"))||(m.getName().startsWith("getInt"))){
					System.out.println("Alumno - "+m.getName().substring(6).toUpperCase() + " : " +  m.invoke(nuevoAlumno));
				}	
			}
			System.out.println(nuevoAlumno.getApoderados().getPersonas().getStrCodigoPersona());
			System.out.println(nuevoAlumno.getDistritos().getIntIdDistrito());
		} catch (Exception e) {
			System.out.println("Error al pintar atributos del nuevoAlumno");
		}

		try {
			Alumno tempoalumno=new Alumno();
			tempoalumno=alumnoService.obtenerAlumno(nuevoAlumno);
			if(tempoalumno!=null){
				System.out.println("Alumno "+tempoalumno.getStrCodigoAlumno()+"("+tempoalumno.getStrNombres()+" "+tempoalumno.getStrApellidoPaterno()+") ya existe !!");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alumno ya se encuentra registrado: " + tempoalumno.getStrNombres() + " " + tempoalumno.getStrApellidoPaterno()));
			}else{
				//validacion de q el dni del alumno y el apoderado q se registran no sean el mismo SOLO al mismo tiempo
				System.out.println("dni apoderado: "+nuevoAlumno.getApoderados().getPersonas().getStrCodigoPersona().substring(3));
				System.out.println("dni alumno: "+nuevoAlumno.getIntDni());
				if(nuevoAlumno.getApoderados().getPersonas().getStrCodigoPersona().substring(3).equalsIgnoreCase(""+nuevoAlumno.getIntDni())){
					System.out.println("Alumno "+nuevoAlumno.getStrCodigoAlumno()+"("+nuevoAlumno.getStrNombres()+" "+nuevoAlumno.getStrApellidoPaterno()+") tiene el mismo DNI de su apoderado [DNI Apoderado:"+nuevoAlumno.getApoderados().getPersonas().getStrCodigoPersona().substring(3)+"] ... ");
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El alumno y su apoderado tienen el mismo dni. DNI del alumno:" + nuevoAlumno.getIntDni() + ". DNI del apoderado: " + nuevoAlumno.getApoderados().getPersonas().getStrCodigoPersona().substring(3)));
				}else{
					System.out.println("pasoooooo");
					alumnoService.registrarAlumno(nuevoAlumno);
					matriculaService.registrarMatricula(mattempo);
					System.out.println("Se registro al alumno .... cargando apoderado ... ");
					String cadena= nuevoAlumno.getApoderados().getPersonas().getStrCodigoPersona().substring(3);
					System.out.println("cadena: "+cadena);
					Persona temporal=new Persona();
					Persona apoderadobuscado=new Persona();
					temporal.setIntDNI(Integer.parseInt(cadena));
					apoderadobuscado=apoderadoService.consultaApoderado(temporal);
					System.out.println("enviando correo al apoderado: "+apoderadobuscado.getIntDNI());
					EnviaMail enviador=new EnviaMail();				
					enviador.enviarCorreoRegisroAl(nuevoAlumno,apoderadobuscado);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alumno Insertado correctamente: " + nuevoAlumno.getStrNombres() + " " + nuevoAlumno.getStrApellidoPaterno()));
					System.out.println("Se registro el Alumno con exito");
					nuevoAlumno =  new Alumno();
				}	
			}		
		} catch (Exception e) {
			System.out.println("Error registrando el alumno: "+e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: No se insert� al alumno","No se insert� el apoderado: "+e.getMessage()));
			nuevoAlumno =  new Alumno();
			e.printStackTrace();
		}
    }
	
	public void actualizaAlumno(ActionEvent ae){
		System.out.println("Actualizando alumno ... ");
		System.out.println(selectedAlumno.getStrNombres());
		System.out.println("XD2 " + selectedAlumno.getFotobin());
		
		try {
			Date auxi=new Date(selectedAlumno.getFecha().getTime());
			selectedAlumno.setDtFecNac(auxi);

			alumnoService.actualizarAlumno(selectedAlumno);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alumno Actualizado correctamente: " + selectedAlumno.getStrNombres() + " " + selectedAlumno.getStrApellidoPaterno()));
			System.out.println("alumno actualizado con exito ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void CargarDistritos(){
		try {
			this.listadistritos=distritoService.obtenerTodosDistritos();
			System.out.println("Cantidad distritos cargados: " + listadistritos.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarListasGrados(){
		try {
			this.listaPrimaria.add("1�");
			this.listaPrimaria.add("2�");
			this.listaPrimaria.add("3�");
			this.listaPrimaria.add("4�");
			this.listaPrimaria.add("5�");
			this.listaPrimaria.add("6�");
			System.out.println("tama�o de listaPrimaria: "+listaPrimaria.size());
			this.listaSecundaria.add("7�");
			this.listaSecundaria.add("8�");
			this.listaSecundaria.add("9�");
			this.listaSecundaria.add("10�");
			this.listaSecundaria.add("11�");
			System.out.println("tama�o de listaSecundaria: "+listaSecundaria.size());
			
			//this.listaSecundaria=seccionprogService.obtenerListaGrados("SECUNDARIA");
			//System.out.println("Cantidad secciones de primaria cargadas: " + listaPrimaria.size());
			//System.out.println("Cantidad secciones de secundaria cargadas: " + listaSecundaria.size());
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	public void cargaComboListadoGrado(){
		System.out.println("en el metodo cargaComboListadoGrado ... - "+nivelAlumno);
		try {
			String valorCombo=nivelAlumno;
			 if(valorCombo.equalsIgnoreCase("PRIMARIA")){
				 System.out.println("cargando lista primaria .... ");
				 this.listaGrados=listaPrimaria;
			 }else if(valorCombo.equalsIgnoreCase("SECUNDARIA")){
				 System.out.println("cargando lista secundaria");
				 this.listaGrados=listaSecundaria;
			 }else if (valorCombo.equalsIgnoreCase("0")){
				 this.listaGrados =  new ArrayList<String>();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public ArrayList<Alumno> getAlumnos() {
		try {
			System.out.println("entro a listar todos ....");
			alumnos = alumnoService.obtenerTodosAlumnos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alumnos;
	}

	public void setAlumnos(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	
	public Alumno getSelectedAlumno() {
		return selectedAlumno;
	}

	public void setSelectedAlumno(Alumno selectedAlumno) {
		this.selectedAlumno = selectedAlumno;
	}

	public boolean isEditMode() {
		return editMode;
	}


	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public Alumno getNuevoAlumno() {
		return nuevoAlumno;
	}

	public void setNuevoAlumno(Alumno nuevoAlumno) {
		this.nuevoAlumno = nuevoAlumno;
	}

	public String getStrCodigoApoderado() {
		return strCodigoApoderado;
	}

	public void setStrCodigoApoderado(String strCodigoApoderado) {
		this.strCodigoApoderado = strCodigoApoderado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Apoderado getApoderado() {
		return apoderado;
	}

	public void setApoderado(Apoderado apoderado) {
		this.apoderado = apoderado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ArrayList<Distrito> getListadistritos() {
		return listadistritos;
	}

	public void setListadistritos(ArrayList<Distrito> listadistritos) {
		this.listadistritos = listadistritos;
	}

	public int getCodigoDistrito() {
		return codigoDistrito;
	}

	public void setCodigoDistrito(int codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}

	public void setListaSecundaria(ArrayList<String> listaSecundaria) {
		this.listaSecundaria = listaSecundaria;
	}

	public void setListaPrimaria(ArrayList<String> listaPrimaria) {
		this.listaPrimaria = listaPrimaria;
	}

	public ArrayList<String> getListaGrados() {
		return listaGrados;
	}

	public void setListaGrados(ArrayList<String> listaGrados) {
		this.listaGrados = listaGrados;
	}

	public ArrayList<String> getListaSecundaria() {
		return listaSecundaria;
	}

	public ArrayList<String> getListaPrimaria() {
		return listaPrimaria;
	}

	public String getSeccionAlumno() {
		return seccionAlumno;
	}

	public void setSeccionAlumno(String seccionAlumno) {
		this.seccionAlumno = seccionAlumno;
	}

	public String getGradoAlumno() {
		return gradoAlumno;
	}

	public void setGradoAlumno(String gradoAlumno) {
		this.gradoAlumno = gradoAlumno;
	}

	public String getNivelAlumno() {
		return nivelAlumno;
	}

	public void setNivelAlumno(String nivelAlumno) {
		this.nivelAlumno = nivelAlumno;
	}

	public StreamedContent getImage() {
		return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
