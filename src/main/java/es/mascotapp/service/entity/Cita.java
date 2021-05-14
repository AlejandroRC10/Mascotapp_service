package es.mascotapp.service.entity;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;


import es.mascotapp.service.entity.enums.Motivo;

@Entity
@Table(name = "citas")
public class Cita implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//tiene que ser único
	@Column(name = "fecha", nullable=false, unique=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fecha;
	
	@Column
	private String observaciones;
	
	@ManyToOne
	@JoinColumn(name="mascota_id")
	private Mascota mascota;
	
	@Column(nullable=false)
	@Enumerated(value=EnumType.STRING)
	private Motivo motivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
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
