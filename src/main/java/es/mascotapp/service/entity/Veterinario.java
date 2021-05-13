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

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "veterinarios")
public class Veterinario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 45, nullable=false)
	private String nombre;
	
	@Column(length = 45, nullable=false)
	private String apellidos;
	
	@Column(length = 45)
	private String nom_clinica;
	
	@Column(nullable=false, unique=true)
	private int num_colegio;
	
	@Column(length = 50, nullable=false, unique=true)
	private String usuario;
	
	@Column(length = 50, nullable=false)
	private String password;
	
	@Column(length = 60, nullable=false)
	private String direccion;
	
	@Column(length = 9, nullable=false)
	private String telefono;
	
	
	@OneToMany(mappedBy="veterinario",cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Cliente> clientes;
	

	
	public Veterinario() {
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
		return num_colegio;
	}
	public void setNum_colegiado(int num_colegiado) {
		this.num_colegio = num_colegiado;
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
	
	public int getNum_colegio() {
		return num_colegio;
	}

	public void setNum_colegio(int num_colegio) {
		this.num_colegio = num_colegio;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}


	
	public void addCliente(Cliente nCliente) {
		
		if(clientes==null)
			clientes = new ArrayList<>();
		
		clientes.add(nCliente);
		nCliente.setVeterinario(this);
	}
	
	
}
