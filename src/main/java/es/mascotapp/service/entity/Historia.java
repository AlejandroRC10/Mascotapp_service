package es.mascotapp.service.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "historias")
public class Historia implements Serializable {

	private static final long serialVersionUID = 4493013353428753184L;

	@ApiModelProperty(value = "ID de la Historia", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ApiModelProperty(value = "Fecha de la Historia", dataType = "Calendar", example = "2021-04-12", position = 2)
	@Column(length = 50, nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar fecha;

	@ApiModelProperty(value = "Enfermedad por la que necesita cuidados", dataType = "String", example = "Herida infectada en la pezuña", position = 3)
	@Column(nullable = false)
	private String enfermedad;

	@ApiModelProperty(value = "Tratamiento para la enfermedad", dataType = "String", example = "Antibióticos 1 vez al día durante 7 días", position = 4)
	@Column(nullable = false)
	private String tratamiento;

	@ApiModelProperty(value = "Mascota a la que pertenece la Historia", dataType = "Mascota", position = 5)
	@ManyToOne
	@JoinColumn(name = "mascota_id")
	private Mascota mascota;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public String getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
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
