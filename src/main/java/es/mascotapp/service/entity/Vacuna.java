package es.mascotapp.service.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

import es.mascotapp.service.entity.enums.EnfermedadVacuna;

@Entity
@Table(name = "vacunas")
public class Vacuna implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	@Enumerated(value=EnumType.STRING)
	private EnfermedadVacuna enfermedad;
	
	@Column(name = "fecha_vacunacion",length = 50, nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar fecha;
	
	@Column(name = "proxima_vacuna", length = 50, nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar proximaFecha;
	
	@Column
	private String observaciones;
	
	@ManyToOne
	@JoinColumn(name="mascota_id")
	private Mascota mascota;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public EnfermedadVacuna getEnfermedad() {
		return enfermedad;
	}


	public void setEnfermedad(EnfermedadVacuna enfermedad) {
		this.enfermedad = enfermedad;
	}


	public Calendar getFecha() {
		return fecha;
	}


	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}


	public Calendar getProximaFecha() {
		return proximaFecha;
	}


	public void setProximaFecha(Calendar proximaFecha) {
		this.proximaFecha = proximaFecha;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public Mascota getMascota() {
		return mascota;
	}


	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}


	public void addMascota(Mascota mascota) {
		this.mascota = mascota;
	}
}
