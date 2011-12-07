package servicios;

import java.util.ArrayList;

import dao.AsignaturaDAO;
import dao.DAOFactory;
import entidades.Asignatura;

public class AsignaturaDaoService implements AsignaturaService{
	private DAOFactory fabrica = null;
	private AsignaturaDAO asignaturadao;
	
	public AsignaturaDaoService(int whichFactory){
		fabrica = DAOFactory.getDAOFactory(whichFactory);
		this.asignaturadao = fabrica.getAsignaturaDAO();
	}
	
	public ArrayList<Asignatura> obtenerTodosAsignaturas() throws Exception {
		return asignaturadao.obtenerTodos();
	}
	
	public void actualizarAsignatura(Asignatura asignatura) throws Exception {
		asignaturadao.actualizar(asignatura);
	}
	
	public void insertarAsignatura(Asignatura asignatura) throws Exception {
		asignaturadao.insertar(asignatura);
	}
	
	public void inhabilitarAsignatura(Asignatura asignatura) throws Exception {
		asignaturadao.inhabilitar(asignatura);
	}
}