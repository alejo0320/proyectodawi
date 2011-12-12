package utiles;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entidades.Alumno;
import entidades.Persona;
import entidades.Usuario;

public class EnviaMail {

	
	public void EnviadorMailContrasena(String tipo,String maildestino, String destinatario, Usuario datosusuario){
		 // Propiedades de la conexi�n
	   Properties props = new Properties();
	   props.setProperty("mail.smtp.host", "smtp.gmail.com");
	   props.setProperty("mail.smtp.starttls.enable", "true");
	   props.setProperty("mail.smtp.port", "587");
	   props.setProperty("mail.smtp.user", "proylp2@gmail.com");
	   props.setProperty("mail.smtp.auth", "true");

	   // Preparamos la sesion
	   Session session = Session.getDefaultInstance(props);

	   // Construimos el mensaje
	   MimeMessage message = new MimeMessage(session);

	   try {
		   message.setFrom(new InternetAddress("proylp2@gmail.com"));
		   message.addRecipient(
		       Message.RecipientType.TO,
		       new InternetAddress(maildestino));
		   
		   if(tipo.equals("registro")){
			   message.setSubject("SGAE - Registro Exitoso");
			   
		   }else if (tipo.equals("recuperacion")) {
			   message.setSubject("SGAE - Recuperaci�n de Contrase�a");
		   }
		   
		   
		   message.setText("Estimado "+destinatario+ " su usuario es: " + datosusuario.getPersonas().getStrCodigoPersona() + 
		           " y su contrase�a es: " + datosusuario.getStrContrasena());

		   // Lo enviamos.
		   Transport t = session.getTransport("smtp");
		   t.connect("proylp2@gmail.com", "cibertec");
		   t.sendMessage(message, message.getAllRecipients());
		   // Cierre.
           t.close();
	   } catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("error enviando el correo : "+e.getMessage());
	   }catch(Exception e1){
		   	e1.printStackTrace();
			System.out.println("error enviando el correo : "+e1.getMessage());
	   }
	   
	   
	}

	public void enviarCorreoRegisroAl(Alumno nuevoAlumno,
			Persona apoderadobuscado) {
		// Propiedades de la conexi�n
		   Properties props = new Properties();
		   props.setProperty("mail.smtp.host", "smtp.gmail.com");
		   props.setProperty("mail.smtp.starttls.enable", "true");
		   props.setProperty("mail.smtp.port", "587");
		   props.setProperty("mail.smtp.user", "proylp2@gmail.com");
		   props.setProperty("mail.smtp.auth", "true");

		   // Preparamos la sesion
		   Session session = Session.getDefaultInstance(props);

		   // Construimos el mensaje
		   MimeMessage message = new MimeMessage(session);

		   try {
			   message.setFrom(new InternetAddress("proylp2@gmail.com"));
			   message.addRecipient(
			       Message.RecipientType.TO,
			       new InternetAddress(apoderadobuscado.getStrMail()));

			   message.setSubject("SGAE - Registro de Alumno Exitoso");
			   String cuerpomensaje="Estimado "+apoderadobuscado.getStrNombre()+" "+apoderadobuscado.getStrApellidoPaterno()+ ", \n\n le enviamos la confirmacion del registro del alumno "+nuevoAlumno.getStrNombres()+" "+nuevoAlumno.getStrApellidoPaterno()+" con los siguientes datos: ";
			   
			   for (Method m : nuevoAlumno.getClass().getMethods()){
					if(m.getName().startsWith("get"))
						cuerpomensaje+="\n" + m.getName().substring(6).toUpperCase() + " : " +  m.invoke(nuevoAlumno);
				}
			   message.setText(cuerpomensaje);

			   // Lo enviamos.
			   Transport t = session.getTransport("smtp");
			   t.connect("proylp2@gmail.com", "cibertec");
			   t.sendMessage(message, message.getAllRecipients());
			   // Cierre.
	           t.close();
		   } catch (MessagingException e) {
				e.printStackTrace();
				System.out.println("error enviando el correo : "+e.getMessage());
		   }catch(Exception e1){
			   	e1.printStackTrace();
				System.out.println("error enviando el correo : "+e1.getMessage());
		   }
	}

	public void enviarCorreoRegisroApo(Persona nuevoApoderado) {
		// Propiedades de la conexi�n
		   Properties props = new Properties();
		   props.setProperty("mail.smtp.host", "smtp.gmail.com");
		   props.setProperty("mail.smtp.starttls.enable", "true");
		   props.setProperty("mail.smtp.port", "587");
		   props.setProperty("mail.smtp.user", "proylp2@gmail.com");
		   props.setProperty("mail.smtp.auth", "true");

		   // Preparamos la sesion
		   Session session = Session.getDefaultInstance(props);

		   // Construimos el mensaje
		   MimeMessage message = new MimeMessage(session);

		   try {
			   message.setFrom(new InternetAddress("proylp2@gmail.com"));
			   message.addRecipient(
			       Message.RecipientType.TO,
			       new InternetAddress(nuevoApoderado.getStrMail()));

			   message.setSubject("SGAE - Registro de Nuevo Apoderado Exitoso");
			   String cuerpomensaje="Estimado "+nuevoApoderado.getStrNombre()+" "+nuevoApoderado.getStrApellidoPaterno()+ ", \n\n le enviamos la confirmacion de su registro con los siguientes datos: ";
			   
			   for (Method m : nuevoApoderado.getClass().getMethods()){
					if((m.getName().startsWith("getStr"))||(m.getName().startsWith("getInt"))){
						cuerpomensaje+="\n" + m.getName().substring(6).toUpperCase() + " : " +  m.invoke(nuevoApoderado);
					}
						
				}
			   message.setText(cuerpomensaje);

			   // Lo enviamos.
			   Transport t = session.getTransport("smtp");
			   t.connect("proylp2@gmail.com", "cibertec");
			   t.sendMessage(message, message.getAllRecipients());
			   // Cierre.
	           t.close();
		   } catch (MessagingException e) {
				e.printStackTrace();
				System.out.println("error enviando el correo : "+e.getMessage());
		   }catch(Exception e1){
			   	e1.printStackTrace();
				System.out.println("error enviando el correo : "+e1.getMessage());
		   }
	}
	
	
	
	

   
}
