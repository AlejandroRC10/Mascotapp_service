package es.mascotapp.service.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

import es.mascotapp.service.entity.enums.EnfermedadVacuna;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "vacunas")
public class Vacuna implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID de la Vacuna", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Enfermedad contra la que se Vacuna", dataType = "Enum", example = "RABIA", position = 2)
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private EnfermedadVacuna enfermedad;

	@ApiModelProperty(value = "Fecha de vacunación", dataType = "Calendar", example = "2021-05-02", position = 3)
	@Column(name = "fecha_vacunacion", length = 50, nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar fecha;

	@ApiModelProperty(value = "Fecha de próxima vacunación", dataType = "Calendar", example = "2022-05-02", position = 4)
	@Column(name = "proxima_vacuna", length = 50, nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar proximaFecha;

	@ApiModelProperty(value = "Observaciones si las hubiera, sobre la Vacuna", dataType = "String", example = "En caso de que la Enfermedad de la Vacuna sea 'OTRA', se puede especificar aquí su nombre", position = 5)
	@Column
	private String observaciones;

	@ApiModelProperty(value = "Mascota a la que pertenece la Vacuna", dataType = "Mascota", position = 6)
	@ManyToOne
	@JoinColumn(name = "mascota_id")
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
