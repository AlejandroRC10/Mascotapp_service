package es.mascotapp.service.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "propietarios")
public class Propietario implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID del Propietario", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Nombre del Propietario", dataType = "String", example = "Fernando", position = 2)
	@Column(length = 45, nullable = false)
	private String nombre;

	@ApiModelProperty(value = "Apellidos del Propietario", dataType = "String", example = "García Pérez", position = 3)
	@Column(length = 45, nullable = false)
	private String apellidos;

	@ApiModelProperty(value = "DNI del Propietario", dataType = "String", example = "76894523F", position = 4)
	@Column(length = 9, nullable = false, unique = true)
	private String dni;

	@ApiModelProperty(value = "Correo electrónico del Propietario", dataType = "String", example = "garciaperezf@mascotapp.es", position = 5)
	@Column(length = 60, nullable = false, unique = true)
	private String email;

	@ApiModelProperty(value = "Password de logueo en aplicación del Propietario", dataType = "String", example = "garperf.210520", position = 7)
	@Column(length = 50, nullable = false)
	@ColumnTransformer(read = "AES_DECRYPT(UNHEX(password), 'mySecretKey')", write = "HEX(AES_ENCRYPT(?, 'mySecretKey'))")
	private String password;

	@ApiModelProperty(value = "Número de teléfono del Propietario", dataType = "String", example = "6834761724", position = 8)
	@Column(length = 9, nullable = false)
	private String telefono;

	@ApiModelProperty(value = "Veterinario del Propietario", dataType = "Veterinario", position = 9)
	@ManyToOne
	@JoinColumn(name = "veterinario_id")
	private Veterinario veterinario;

	@OneToMany(mappedBy = "propietario", cascade = { CascadeType.ALL })
	@JsonIgnore
	private List<Mascota> mascotas;

	public Propietario() {
	}

	public Propietario(String nombre, String apellidos, String dni, String email, String password, String telefono,
			Veterinario veterinario) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
		this.password = password;
		this.telefono = telefono;
		this.veterinario = veterinario;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Mascota> getMascotas() {
		return mascotas;
	}

	public void setMascotas(List<Mascota> mascotas) {
		this.mascotas = mascotas;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void addVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public void addMascota(Mascota nMascota) {

		if (mascotas == null)
			mascotas = new ArrayList<>();

		mascotas.add(nMascota);
		nMascota.setPropietario(this);
	}

}
