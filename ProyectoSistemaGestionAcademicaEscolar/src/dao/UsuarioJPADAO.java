package dao;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import entidades.Perfil;
import entidades.Permiso;
import entidades.Persona;
import entidades.Usuario;

public class UsuarioJPADAO implements UsuarioDAO {


	private EntityManagerFactory emf;
	private EntityManager em;
	
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf=emf;
	}
	
	@Override
	public Usuario validarUsuario(Usuario elusuario) throws Exception{
		
		em = emf.createEntityManager();
		
		Usuario entidadUsuario = null;
		
		Query q =  em.createQuery("SELECT u FROM Usuario u WHERE u.personas.strCodigoPersona=?1 and u.strContrasena=?2",Usuario.class);
		q.setParameter(1, elusuario.getPersonas().getStrCodigoPersona());
		q.setParameter(2, elusuario.getStrContrasena());
		
		System.out.println(elusuario.getPersonas().getStrCodigoPersona());
		
		entidadUsuario =(Usuario)q.getSingleResult();

		em.close();
		
		if(entidadUsuario!=null){
		StreamedContent image;	
			if(entidadUsuario.getPersonas().getFotobin()!=null){
				 image = new DefaultStreamedContent(new ByteArrayInputStream(entidadUsuario.getPersonas().getFotobin()));

				 entidadUsuario.getPersonas().setScImagen(image);
			 }
			
			return entidadUsuario;
		}else{
			return null;
		}
		
	}
	
	@Override
	public Usuario obtieneUsuario(Usuario elusuario) throws Exception{
		
		em = emf.createEntityManager();
		
		Usuario entidadUsuario = null;
		
		Query q =  em.createQuery("SELECT u FROM Usuario u WHERE u.personas.strCodigoPersona=?1",Usuario.class);
		q.setParameter(1, elusuario.getPersonas().getStrCodigoPersona());
		
		System.out.println(elusuario.getPersonas().getStrCodigoPersona());
		
		entidadUsuario =(Usuario)q.getSingleResult();
		
		em.close();
		
		if(entidadUsuario!=null){
			StreamedContent image;	
			if(entidadUsuario.getPersonas().getFotobin()!=null){
				 image = new DefaultStreamedContent(new ByteArrayInputStream(entidadUsuario.getPersonas().getFotobin()));

				 entidadUsuario.getPersonas().setScImagen(image);
			 }
			
			return entidadUsuario;
		}else{
			return null;
		}
		
	}
	
	public void actualizarPerfil(Usuario usuario) throws Exception{
		em = emf.createEntityManager();

		em.getTransaction().begin();
		
		Usuario entidadUsuario = null;
		
		Query q =  em.createQuery("SELECT u FROM Usuario u WHERE u.personas.strCodigoPersona=?1",Usuario.class);
		q.setParameter(1, usuario.getPersonas().getStrCodigoPersona());
		
		entidadUsuario = (Usuario)q.getSingleResult();
		
		Perfil entidadPerfil = new Perfil();
		entidadPerfil.setStrCodigoPerfil(usuario.getPerfiles().getStrCodigoPerfil());
		
		entidadUsuario.setPerfiles(entidadPerfil);
		
		em.merge(entidadUsuario);
		em.flush();
				
		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<Permiso> listarMenus(Usuario elusuario) throws Exception {

		em = emf.createEntityManager();

		ArrayList<Permiso> permisosUser = new ArrayList<Permiso>();
		 
		Query q =  em.createQuery("SELECT c1 FROM Permiso c1 INNER JOIN c1.tbPerfiles c2 where c2.strCodigoPerfil=?1" );
		q.setParameter(1, elusuario.getPerfiles().getStrCodigoPerfil());

		List l=q.getResultList();
		if(l.size()>0){
			for ( int i=0; i < l.size(); i++ ) {
				Permiso entidad = (Permiso)l.get(i);
				System.out.println("permiso: "+l.get(i));
				permisosUser.add(entidad);
			}
		}
		
		em.close();

		return permisosUser;
	
	}

	@Override
	public Usuario buscaContrasena(Persona persona) throws Exception {

		em=emf.createEntityManager();
		Query q =  em.createQuery("SELECT u FROM Usuario u WHERE u.personas.strCodigoPersona=?1");
		q.setParameter(1, persona.getStrCodigoPersona());
		
			Usuario entidadUsuario =(Usuario)q.getSingleResult();
			em.close();
			if(entidadUsuario!=null){
				return entidadUsuario;
			}else{
				return null;
			}
	
	}

	@Override
	public void registrarUsuario(Usuario nuevo) throws Exception {
		em=emf.createEntityManager();
		
		Persona persona = new Persona();
		/*if(persona.getStrCodigoPersona().startsWith("PE-")){
			System.out.println("tudo bem");
		}else{*/
			persona.setStrCodigoPersona("PE-" + nuevo.getStrContrasena());
		/*}*/

		nuevo.setPersonas(persona);

		//1.inicia la transacción
		em.getTransaction().begin();

		//2.ejecuta las operaciones
		em.persist(nuevo);
		em.flush();
		
		//3.ejecuta commit a la transacción
		em.getTransaction().commit();
		em.close();
	}
	
	public void cambiaContrasena(Usuario usuario) throws Exception {
		em = emf.createEntityManager();

		em.getTransaction().begin();
		
		Usuario entidadUsuario = null;
		
		Query q =  em.createQuery("SELECT u FROM Usuario u WHERE u.personas.strCodigoPersona=?1",Usuario.class);
		q.setParameter(1, usuario.getPersonas().getStrCodigoPersona());
		
		entidadUsuario = (Usuario)q.getSingleResult();
		
		entidadUsuario.setStrContrasena(usuario.getStrContrasena());
		
		em.merge(entidadUsuario);
		em.flush();
				
		em.getTransaction().commit();
		em.close();
	}
	

}
