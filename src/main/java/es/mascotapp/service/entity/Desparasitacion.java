package es.mascotapp.service.entity;

import java.util.Calendar;

import javax.persistence.*;

import es.mascotapp.service.entity.enums.TipoDesparasitacion;

@Entity
@Table(name = "desparasitaciones")
public class Desparasitacion {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	@Enumerated(value=EnumType.STRING)
	private TipoDesparasitacion tipo;
	
	@Column(length = 50, nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar fecha;
	
	@Column(name = "proxima_fecha", length = 50, nullable=false)
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

	public TipoDesparasitacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDesparasitacion tipo) {
		this.tipo = tipo;
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
