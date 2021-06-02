package es.mascotapp.service.entity;

import java.util.Calendar;

import javax.persistence.*;

import es.mascotapp.service.entity.enums.TipoDesparasitacion;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "desparasitaciones")
public class Desparasitacion {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID de la Desparasitacion", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Tipo de Desparasitacion", dataType = "Enum", example = "COLLAR", position = 2)
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private TipoDesparasitacion tipo;

	@ApiModelProperty(value = "Fecha de Desparasitacion", dataType = "Calendar", example = "2021-06-01", position = 3)
	@Column(length = 50, nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar fecha;

	@ApiModelProperty(value = "Fecha de próxima Desparasitacion", dataType = "Calendar", example = "2022-06-01", position = 4)
	@Column(name = "proxima_fecha", length = 50, nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar proximaFecha;

	@ApiModelProperty(value = "Observaciones si las hubiera, sobre la Desparasitacion", dataType = "String", example = "En caso de que la Enfermedad de la Desparasitacion sea 'OTRA', se puede especificar aquí su nombre", position = 5)
	@Column
	private String observaciones;

	@ApiModelProperty(value = "Mascota a la que pertenece la Desparasitacion", dataType = "Mascota", position = 6)
	@ManyToOne
	@JoinColumn(name = "mascota_id")
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
