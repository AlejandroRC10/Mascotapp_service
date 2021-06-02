package es.mascotapp.service.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

import es.mascotapp.service.entity.enums.Motivo;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "citas")
public class Cita implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID de la Cita", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Fecha de Cita", dataType = "Calendar", example = "2021-06-12T17:00:00.000+01:00", position = 2)
	@Column(name = "fecha", nullable = false, unique = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fecha;

	@ApiModelProperty(value = "Observaciones si las hubiera, sobre la Cita", dataType = "String", example = "El perro debe ir en ayunas", position = 3)
	@Column
	private String observaciones;

	@ApiModelProperty(value = "Mascota a la que pertenece la Cita", dataType = "Mascota", position = 4)
	@ManyToOne
	@JoinColumn(name = "mascota_id")
	private Mascota mascota;

	@ApiModelProperty(value = "Motivo de la Cita", dataType = "Enum", example = "VACUNACION", position = 5)
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
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
