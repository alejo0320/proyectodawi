package jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.UnselectEvent;

import servicios.AlumnoService;
import servicios.ApplicationBusinessDelegate;
import servicios.SolicitudRetiroService;
import utiles.EnviaMail;
import entidades.Alumno;
import entidades.Certificadoa;

@SessionScoped
@ManagedBean
public class EnviaCertificadosBean  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ApplicationBusinessDelegate abd = new ApplicationBusinessDelegate();
	private static SolicitudRetiroService retiroService = abd.getRetiroService();
	private static AlumnoService alumnoService = abd.getAlumnoService();
	EnviaMail enviador=new EnviaMail();
	
	private boolean editMode;
	private String elfiltro;
	private ArrayList<Certificadoa> listaCertificados;  
	  
    private Certificadoa selectedCerti;  
  
    private Certificadoa[] selectedCertificados;  
   
   // private CarDataModel mediumCarsModel;
	
	public EnviaCertificadosBean() {
		System.out.println("creando EnviaCertificadosBean ... ");
		
	}

	public void cargaCertificados() {
		try {
			System.out.println("elfiltro: "+elfiltro);
			listaCertificados=new ArrayList<Certificadoa>();
			listaCertificados = retiroService.obtenerCertificadosXAlumno(elfiltro);
			System.out.println("listaCertificados cargada - tama�o: "+listaCertificados.size());
		}catch (NullPointerException ne){
			ne.printStackTrace();
			System.out.println("lista vacia: "+ne.getMessage());
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: "+e.getMessage());
		}
		//return listaCertificados;
	}
	
	public void enviarCertificados() {
		System.out.println("enviando certificados x Correo ... ");
		if(elfiltro!=null && !elfiltro.equalsIgnoreCase("")){
			Alumno temporal=new Alumno();
			temporal.setStrCodigoAlumno(elfiltro);
			Alumno elalumno;
			try {
				elalumno = alumnoService.obtenerAlumno(temporal);
				if(elalumno!=null){
					if(selectedCertificados.length>0){
						for(int k=0;k<selectedCertificados.length;k++){
							String correodestino=elalumno.getApoderados().getPersonas().getStrMail();
							System.out.println("enviando certificados a "+correodestino);
							//aki va el envio de certificado por correo
							enviador.enviarCorreoCertificado(selectedCertificados[k],elalumno);
							FacesMessage msg = new FacesMessage("Certificados Enviados","Se envi� el certificado de Asistencia Social del alumno "+elalumno.getStrNombres()+" "+elalumno.getStrApellidoPaterno());
				    	    FacesContext.getCurrentInstance().addMessage(null, msg);
				    	    listaCertificados.clear();
						}
					}
				}
				else{
					System.out.println("elalumno es nulo");
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: No se envi� los Certificados","El Alumno "+ temporal.getStrCodigoAlumno()+" no se encuentra registrado en la BD !" ));
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","Hubo un error al enviar los certificados: "+e.getMessage() ));
				//e.printStackTrace();
			}	
		}
		else{
			System.out.println("el filtro es nulo o vacio");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: No se envi� los Certificados","Ingrese un c�digo de Alumno" ));
		}
		
	}
	
	
	    public void onRowUnselect(UnselectEvent event)
	    {
	    	Certificadoa c =(Certificadoa) event.getObject();
	        System.err.println("onRowUnselect car " + c.getIntCodcertificado() + " " + c.getIntCodcita()+ "/" + c.getCodmotivo());    
	    }

		public Certificadoa[] getSelectedCertificados() {
	        System.out.println(">> obteniendo selectedCertificados");       
	        if (this.selectedCertificados != null)        
	            for (int i = 0; i < selectedCertificados.length; i++)        
	                System.out.println("   iteracion " + i + ",id=" + this.selectedCertificados[i].getIntCodcertificado() + ", cita=" + this.selectedCertificados[i].getIntCodcita() + ", motivo=" + this.selectedCertificados[i].getCodmotivo());                   
	        return this.selectedCertificados;
	    }


		public void setSelectedCertificados(Certificadoa[] e) {
	        // Always passed a non null value (empty list)
	        // Its possible for the same object to be in list > once because of a bug in primefaces on refresh, page forward/back
	        this.selectedCertificados = e;
	        System.out.println(">> seteando selectedCertificados");       
	        for (int i = 0; i < selectedCertificados.length; i++)        
	            System.out.println("   iteracion " + i + ",id=" + this.selectedCertificados[i].getIntCodcertificado() + ", cita=" + this.selectedCertificados[i].getIntCodcita() + ", motivo=" + this.selectedCertificados[i].getCodmotivo());                    
	    }

		public List<Certificadoa> getListaCertificados() {
			try {
				System.out.println("elfiltro: "+elfiltro);
				
				listaCertificados = retiroService.obtenerCertificadosXAlumno(elfiltro);
				System.out.println("listaCertificados cargada - tama�o: "+listaCertificados.size());
			}catch (NullPointerException ne){
				System.out.println("null pointer: "+ne.getMessage());
			} 
			catch (Exception e) {
				//e.printStackTrace();
				System.out.println("error: "+e.getMessage());
			}
			return listaCertificados;
		}

		public void setListaCertificados(ArrayList<Certificadoa> listaCertificados) {
			this.listaCertificados = listaCertificados;
		}

		public Certificadoa getSelectedCerti() {
			return selectedCerti;
		}

		public void setSelectedCerti(Certificadoa selectedCerti) {
			this.selectedCerti = selectedCerti;
		}

		public boolean isEditMode() {
			return editMode;
		}

		public void setEditMode(boolean editMode) {
			this.editMode = editMode;
		}

		public String getElfiltro() {
			return elfiltro;
		}

		public void setElfiltro(String elfiltro) {
			this.elfiltro = elfiltro;
		}
	
	
}
