package es.mascotapp.service.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mascotas")
public class Mascota implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 45, nullable=false)
	private String nombre;
	
	@Column(length = 45)
	private String raza;
	
	//tiene que ser único
	@Column(name = "chip", length = 45, nullable=false)
	private String num_chip;
	
	@Column(name = "tipo_animal", nullable=false)
	private String animal;
	
	@Column(name = "fecha_nacimiento", length = 50, nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar fecha_nac;
	
	@Column
	private double peso;
	
	@Column(length = 1, nullable=false)
	private String sexo;
	
	
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy="mascota", cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Historia>historias;
	
	@OneToMany(mappedBy="mascota", cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Cita>citas;
	

	public Mascota() {
	}


	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getNum_chip() {
		return num_chip;
	}

	public void setNum_chip(String num_chip) {
		this.num_chip = num_chip;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public Calendar getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Calendar fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Historia> getHistorias() {
		return historias;
	}

	public void setHistorias(List<Historia> historias) {
		this.historias = historias;
	}

	public List<Cita> getCitas() {
		return citas;
	}

	public void setCitas(List<Cita> citas) {
		this.citas = citas;
	}
	
	public void addCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void addHistoria(Historia nHistoria) {
		
		if(historias==null)
			historias = new ArrayList<>();
		
		historias.add(nHistoria);
		nHistoria.setMascota(this);
	}
	
	public void addCita(Cita nCita) {
		
		if(citas==null)
			citas = new ArrayList<>();
		
		citas.add(nCita);
		nCita.setMascota(this);
	}


	
}
