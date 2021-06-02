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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "veterinarios")
public class Veterinario implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID del Veterinario", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Nombre del Veterinario", dataType = "String", example = "Juan", position = 2)
	@Column(length = 45, nullable = false)
	private String nombre;

	@ApiModelProperty(value = "Apellidos del Veterinario", dataType = "String", example = "Fernández Marín", position = 3)
	@Column(length = 45, nullable = false)
	private String apellidos;

	@ApiModelProperty(value = "Nombre de clínica del Veterinario", dataType = "String", example = "Veterinaria FerMar", position = 4)
	@Column(length = 45)
	private String nom_clinica;

	@ApiModelProperty(value = "Nº de colegiado del Veterinario", dataType = "int", example = "130", position = 5)
	@Column(nullable = false, unique = true)
	private int numColegiado;

	@ApiModelProperty(value = "Usuario de aplicación del Veterinario", dataType = "String", example = "JuanFer", position = 6)
	@Column(length = 50, nullable = false, unique = true)
	private String usuario;

	@ApiModelProperty(value = "Password de logueo en la aplicación del Veterinario", dataType = "String", example = "JuanFerMa_310520", position = 7)
	@Column(nullable = false)
	@ColumnTransformer(read = "AES_DECRYPT(UNHEX(password), 'mySecretKey')", write = "HEX(AES_ENCRYPT(?, 'mySecretKey'))")
	private String password;

	@ApiModelProperty(value = "Dirección del Veterinario", dataType = "String", example = "C/Nueva, 12", position = 8)
	@Column(length = 60, nullable = false)
	private String direccion;

	@ApiModelProperty(value = "Número de teléfono del Veterinario", dataType = "String", example = "6954381762", position = 9)
	@Column(length = 9, nullable = false)
	private String telefono;

	@OneToMany(mappedBy = "veterinario", cascade = { CascadeType.ALL })
	@JsonIgnore
	private List<Propietario> propietarios;

	public Veterinario() {
	}

	public Veterinario(String nombre, String apellidos, String nom_clinica, int num_colegiado, String usuario,
			String password, String direccion, String telefono) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nom_clinica = nom_clinica;
		this.numColegiado = num_colegiado;
		this.usuario = usuario;
		this.password = password;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNom_clinica() {
		return nom_clinica;
	}

	public void setNom_clinica(String nom_clinica) {
		this.nom_clinica = nom_clinica;
	}

	public int getNum_colegiado() {
		return numColegiado;
	}

	public void setNum_colegiado(int num_colegiado) {
		this.numColegiado = num_colegiado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@JsonIgnore
	public List<Propietario> getPropietario() {
		return propietarios;
	}

	@JsonIgnore
	public void setPropietarios(List<Propietario> propietarios) {
		this.propietarios = propietarios;
	}

	public void addPropietario(Propietario nPropietario) {

		if (propietarios == null)
			propietarios = new ArrayList<>();

		propietarios.add(nPropietario);
		nPropietario.setVeterinario(this);
	}

}
