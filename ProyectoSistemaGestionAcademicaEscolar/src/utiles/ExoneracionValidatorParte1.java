package utiles;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import entidades.Alumno;
import entidades.SolicitudRetiro;

import servicios.ApplicationBusinessDelegate;
import servicios.SolicitudExoneracionService;

public class ExoneracionValidatorParte1 implements Validator {

	private static ApplicationBusinessDelegate abd = new ApplicationBusinessDelegate();
	
	private static SolicitudExoneracionService exoneracionService = abd.getExoneracionService();

    public void validate(FacesContext context, UIComponent component, Object value)
    throws ValidatorException
    {
        String valor = (String) value;
        
        System.out.println("--->" + valor);
        
        Alumno tmpAlumno = new Alumno();
        tmpAlumno.setStrCodigoAlumno(valor);
        
        boolean condicionExoneracion = false;
		try {
			condicionExoneracion = exoneracionService.CumpleCalendarioExoneracion(2011);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!condicionExoneracion) {
		    throw new ValidatorException(new FacesMessage("Ud. est� fuera del perido de exoneraci�n"));    
		}else {
			
			SolicitudRetiro tmpSR = null;
			try {
				tmpSR = exoneracionService.verificarExistenciaSR(tmpAlumno);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(tmpSR!=null){
				if(tmpSR.getStrEstado().equalsIgnoreCase("APROBADA")){
					throw new ValidatorException(new FacesMessage("El alumno ya fue retirado del ciclo acad�mico escolar. NO PUEDE EFECTUAR ESTE TR�MITE"));   
				}else if(tmpSR.getStrEstado().equalsIgnoreCase("PENDIENTE")){
					throw new ValidatorException(new FacesMessage("El alumno tiene una solictud de RETIRO 'PENDIENTE'. NO PUEDE EFECTUAR ESTE TR�MITE"));   
				}
			}
			
		}
		
    }

}
