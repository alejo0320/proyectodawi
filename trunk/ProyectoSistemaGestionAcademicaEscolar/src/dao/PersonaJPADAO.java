package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import entidades.Apoderado;
import entidades.AsistentaSocial;
import entidades.Perfil;
import entidades.Persona;
import entidades.SecretariaAcademica;

public class PersonaJPADAO implements PersonaDAO {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf=emf;
	}
	
	//Este metodo lista a todos los Empleados de la tabla Persona(NO APODERADOS)
	@SuppressWarnings("rawtypes")
	public ArrayList<Persona> obtenerTodosEmpleados()throws Exception {
		
	    em = emf.createEntityManager();
		
		ArrayList<Persona> empleados = new ArrayList<Persona>();
			
		 List lista = em.createQuery("SELECT p FROM Persona p " +
				                     "inner join p.tbUsuarios u " +
				                     "where u.perfiles.strCodigoPerfil <> 'pf01' " +
				                     "order by p.strApellidoPaterno").getResultList();
		 
		 if(lista.size()>0){
				for ( int i=0; i < lista.size(); i++ ) {
					Persona entidad = (Persona)lista.get(i);
					empleados.add(entidad);
				}
		 }
		 
		 
		 for (Persona x : empleados) {	 
			x.setPerfil(obtienePerfilPersona(x.getStrCodigoPersona()));
		 }
		

		em.close();
		
		return empleados;
		
	}
	
	public Perfil obtienePerfilPersona(String codigoPersona){
		Query q =  em.createQuery("SELECT p FROM Perfil p " +
								  "inner join p.tbUsuarios u " +
								  "where u.personas.strCodigoPersona = ?1");
		q.setParameter(1, codigoPersona);
		
		try {
			Perfil entidadPerfil =(Perfil)q.getSingleResult();
			if(entidadPerfil!=null)
				return entidadPerfil;
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Persona consultarPersona(Persona p) throws Exception {
		em=emf.createEntityManager();
		Query q =  em.createQuery("SELECT p FROM Persona p WHERE p.intDNI=?1");
		q.setParameter(1, p.getIntDNI());
		
		try {
			Persona entidadPersona =(Persona)q.getSingleResult();
			em.close();
			if(entidadPersona!=null)
				return entidadPersona;
			else
				return null;
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public void registrarPersona(Persona nueva) throws Exception {
		em=emf.createEntityManager();
		
		System.out.println("xxxxxx" + nueva.getIntDNI());
		
		nueva.setStrCodigoPersona("PE-" + nueva.getIntDNI());

		//1.inicia la transacción
		em.getTransaction().begin();

		//2.ejecuta las operaciones
		em.persist(nueva);
		em.flush();
		
		//3.ejecuta commit a la transacción
		em.getTransaction().commit();
		em.close();
	}

	//Este metodo guarda al apoderado en su tabla Apoderado
	@Override
	public void guardaApoderado(Apoderado apo) throws Exception {
		// TODO Auto-generated method stub
		em=emf.createEntityManager();

		//1.inicia la transacción
		em.getTransaction().begin();

		//2.ejecuta las operaciones
		em.persist(apo);
		em.flush();
		
		//3.ejecuta commit a la transacción
		em.getTransaction().commit();
		em.close();
	}
	
	//Este metodo guarda a la asistentadocial en su tabla AsistentaSocial
	@Override
	public void guardaAsistentaSocial(AsistentaSocial asistenta) throws Exception {
		// TODO Auto-generated method stub
		em=emf.createEntityManager();

		//1.inicia la transacción
		em.getTransaction().begin();

		//2.ejecuta las operaciones
		em.persist(asistenta);
		em.flush();
		
		//3.ejecuta commit a la transacción
		em.getTransaction().commit();
		em.close();
	}
	
	//Este metodo guarda a la SecretariaAcademica en su tabla Secretaria
	@Override
	public void guardaSecretaria(SecretariaAcademica secretaria) throws Exception {
		// TODO Auto-generated method stub
		em=emf.createEntityManager();

		//1.inicia la transacción
		em.getTransaction().begin();

		//2.ejecuta las operaciones
		em.persist(secretaria);
		em.flush();
		
		//3.ejecuta commit a la transacción
		em.getTransaction().commit();
		em.close();
	}


}
