package dao;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import entidades.Alumno;
import entidades.Apoderado;
import entidades.Calendarioacademico;
import entidades.Persona;

public class AlumnoJPADAO implements AlumnoDAO {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf=emf;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<Alumno> obtenerTodos() throws Exception {
		
		em = emf.createEntityManager();
		
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		
		List lista = em.createQuery("SELECT a FROM Alumno a order by a.strApellidoPaterno").getResultList();
		//List lista = em.createQuery("SELECT a FROM Alumno a").getResultList();
		
		 if(lista.size()>0){
				for ( int i=0; i < lista.size(); i++ ) {
					Alumno entidad = (Alumno)lista.get(i);
					System.out.println("alumno: " + lista.get(i));
					alumnos.add(entidad);
				}
		 }
		 
		 for (Alumno x : alumnos) {
			 StreamedContent image;
			 
			 if(x.getFotobin()!=null){
				 image = new DefaultStreamedContent(new ByteArrayInputStream(x.getFotobin()));
				 
				 x.setScImagen(image);
			 }/*else{
				 InputStream stream = 
					 
					 ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/images/noDisponible.jpg");
				 
				 image = new DefaultStreamedContent(stream);
				 
				 x.setScImagen(image);
			 }*/
			 
		}
		 
		em.close();
		
		return alumnos;
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Alumno> obtenerTodosAlumnoXApoderado(Apoderado apoderado) throws Exception {
		
		em = emf.createEntityManager();
		
		System.out.println(apoderado.getPersonas().getStrCodigoPersona());
		
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		
		Query q = em.createQuery("SELECT a FROM Alumno a where a.apoderados.personas.strCodigoPersona=?1");
		q.setParameter(1, apoderado.getPersonas().getStrCodigoPersona());
		
		 List lista = q.getResultList();
		 
		 if(lista.size()>0){
				for ( int i=0; i < lista.size(); i++ ) {
					Alumno entidad = (Alumno)lista.get(i);
					System.out.println("alumno: " + lista.get(i));
					alumnos.add(entidad);
				}
		 }
		 
		em.close();
		
		return alumnos;
	}

	@Override
	public void insertar(Alumno alumno) throws Exception {
		em=emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(alumno);
		em.flush();
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void actualizar(Alumno alumno) throws Exception {
		
		System.out.println(alumno.getStrCodigoAlumno());
		System.out.println(alumno.getStrNombres());
		System.out.println(alumno.getFotobin());
		
		em = emf.createEntityManager();

		//1.inicia la transacción
		em.getTransaction().begin();
		
		Persona entidadPersona = new Persona();
		entidadPersona.setStrCodigoPersona(alumno.getApoderados().getPersonas().getStrCodigoPersona());
		
		Apoderado entidadApoderado = new Apoderado();
		entidadApoderado.setPersonas(entidadPersona);

		//2. ejecuta las operaciones 
		//2.1 busca Empleado por llave primaria
		Alumno entidadAlumno = em.find(Alumno.class, alumno.getStrCodigoAlumno());
		
		entidadAlumno.setApoderados(entidadApoderado);
		entidadAlumno.setStrNombres(alumno.getStrNombres());
		entidadAlumno.setStrApellidoPaterno(alumno.getStrApellidoPaterno());
		entidadAlumno.setStrApellidoMaterno(alumno.getStrApellidoMaterno());
		entidadAlumno.setDtFecNac(alumno.getDtFecNac());
		entidadAlumno.setStrEstado(alumno.getStrEstado());
		entidadAlumno.setFotobin(alumno.getFotobin());

		//2.3 actualiza Empleado
		em.merge(entidadAlumno);
		em.flush();
				
		//3.ejecuta commit a la transacción
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Alumno obtenerAlumno(Alumno alumno) throws Exception {
		em=emf.createEntityManager();
		System.out.println("codigo del temporal: "+alumno.getStrCodigoAlumno());
		Alumno entidadCargo=(Alumno)em.find(Alumno.class,alumno.getStrCodigoAlumno());
		
		return entidadCargo;
	}

	@Override
	public void inhabilitar(Alumno alumno) throws Exception {
		// TODO Auto-generated method stub
		em=emf.createEntityManager();

		//1.inicia la transacción
		em.getTransaction().begin();

		//2. ejecuta las operaciones 
		//2.1 busca Empleado por llave primaria
		Alumno entidadAlumno = em.find(Alumno.class, alumno.getStrCodigoAlumno());
	
		//2.3 actualiza Empleado
		em.merge(entidadAlumno);
		em.flush();
				
		//3.ejecuta commit a la transacción
		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<Calendarioacademico> listarAniosAcademicos() throws Exception {
		em = emf.createEntityManager();
		
		ArrayList<Calendarioacademico> anios = new ArrayList<Calendarioacademico>();
		
		 List l = em.createQuery("SELECT c FROM Calendarioacademico c").getResultList();
		
		 if(l.size()>0){
				for ( int i=0; i < l.size(); i++ ) {
					Calendarioacademico entidad = (Calendarioacademico)l.get(i);
					System.out.println("anio: "+l.get(i));
					anios.add(entidad);
				}
		 }
		 
		em.close();
		
		return anios;
	}
	


}
