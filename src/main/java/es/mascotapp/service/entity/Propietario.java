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

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "propietarios")
public class Propietario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 45, nullable=false)
	private String nombre;
	
	@Column(length = 45, nullable=false)
	private String apellidos;
	
	@Column(length = 9, nullable=false, unique=true)
	private String dni;
	
	@Column(length = 60, nullable=false, unique=true)
	private String email;
	
	@Column(length = 50, nullable=false)
	private String password;
	
	@Column(length = 9, nullable=false)
	private String telefono;

	
	
	@ManyToOne
	@JoinColumn(name="veterinario_id")
	private Veterinario veterinario;
	
	@OneToMany(mappedBy="propietario", cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Mascota>mascotas;
	
	
	
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
		
		if(mascotas==null)
			mascotas = new ArrayList<>();
		
		mascotas.add(nMascota);
		nMascota.setPropietario(this);
	}
	
	
}
