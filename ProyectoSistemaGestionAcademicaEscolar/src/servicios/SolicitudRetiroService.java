package servicios;

import java.util.ArrayList;

import entidades.Boleta;
import entidades.SolicitudRetiro;

public interface SolicitudRetiroService {
	public boolean NoExisteDeudas(Boleta boleta) throws Exception;
	public SolicitudRetiro buscarSolicitudXAlumnoXA�o(SolicitudRetiro sr) throws Exception;
	public void registrarSolictud(SolicitudRetiro sr) throws Exception;
	
	public ArrayList<SolicitudRetiro> obtenerTodasSolicitudes() throws Exception;
	public ArrayList<SolicitudRetiro> obtenerSolicitudesPendientes() throws Exception;
}
