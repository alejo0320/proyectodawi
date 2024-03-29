package org.primefaces.model;
import java.awt.Color;
import java.io.Serializable;
import java.util.Date;



@SuppressWarnings("serial")
public class DefaultScheduleEvent implements ScheduleEvent, Serializable{

	  private String id;
      
      private String title;
      
      private Date startDate;
      
      private Date endDate;
      
      private boolean allDay = true;
      
      private String styleClass;
      
      private Object data;
      
     
    // get y set distrito

	
	// end gets y sets
	

	public DefaultScheduleEvent() {}
      
	public DefaultScheduleEvent(String title, Date start, Date end) {
              this.title = title;
              this.startDate = start;
              this.endDate = end;
      }
      
      public DefaultScheduleEvent(String title, Date start, Date end, boolean allDay) {
              this.title = title;
              this.startDate = start;
              this.endDate = end;
              this.allDay = allDay;
      }
      
      public DefaultScheduleEvent(String title, Date start, Date end, String styleClass) {
              this.title = title;
              this.startDate = start;
              this.endDate = end;
              this.styleClass = styleClass;
      }
      
      public DefaultScheduleEvent(String title, Date start, Date end, Object data) {
              this.title = title;
              this.startDate = start;
              this.endDate = end;
              this.data = data;
      }

      public String getId() {
              return id;
      }
      
      public void setId(String id) {
              this.id = id;
      }

      public String getTitle() {
              return title;
      }
      
      public void setTitle(String title) {
              this.title = title;
      }

      public Date getStartDate() {
              return startDate;
      }

      public void setStartDate(Date startDate) {
              this.startDate = startDate;
      }

      public Date getEndDate() {
              return endDate;
      }

      public void setEndDate(Date endDate) {
              this.endDate = endDate;
      }

      public boolean isAllDay() {
              return allDay;
      }

      public void setAllDay(boolean allDay) {
              this.allDay = allDay;
      }
      
      public void setStyleClass(String styleClass) {
              this.styleClass = styleClass;
      }

      public String getStyleClass() {
              return styleClass;
      }
      
      public Object getData() {
              return data;
      }

      public void setData(Object data) {
              this.data = data;
      }
      
      //atrbtos de cita
      private String codAlu; 
      private int codCita;
      private Date fecReg;
      private String stado;
      private String observacion;
      
      //pra carga de archivo
      private UploadedFile file;

    public UploadedFile getFile() {
  		return file;
  	}

  	public void setFile(UploadedFile file) {
  		this.file = file;
  	}
      
      

	public String getObservacion() {
		return observacion;
	}

	

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getCodAlu() {
		return codAlu;
	}

	public void setCodAlu(String codAlu) {
		this.codAlu = codAlu;
	}

	public int getCodCita() {
		return codCita;
	}

	public void setCodCita(int codCita) {
		this.codCita = codCita;
	}

	public Date getFecReg() {
		return fecReg;
	}

	public void setFecReg(Date fecReg) {
		this.fecReg = fecReg;
	}

	public String getStado() {
		return stado;
	}

	public void setStado(String stado) {
		this.stado = stado;
	}
	
	   public DefaultScheduleEvent(String title,String codAlu,int codCita, Date fecReg,Date startDate,Date endDate,
				 String stado,String observacion) { 
			super();
			this.title = title;
			this.codAlu=codAlu;
			this.codCita = codCita;
			this.fecReg = fecReg;
			this.startDate = startDate;
			this.endDate = endDate;
			this.stado = stado;
			this.observacion=observacion;
			
			
		}
      
      //constructor pra la cargga d archivo
	   public DefaultScheduleEvent(String title,String codAlu,int codCita, Date fecReg,Date startDate,Date endDate,
				 String stado,String observacion,UploadedFile file) { 
			super();
			this.title = title;
			this.codAlu=codAlu;
			this.codCita = codCita;
			this.fecReg = fecReg;
			this.startDate = startDate;
			this.endDate = endDate;
			this.stado = stado;
			this.observacion=observacion;
			this.file=file;
			
		}
      
      
      
	
}
