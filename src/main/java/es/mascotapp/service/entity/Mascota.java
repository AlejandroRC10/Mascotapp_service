package es.mascotapp.service.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mascotas")
public class Mascota implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 45, nullable=false)
	private String nombre;
	
	@Column(length = 45)
	private String raza;
	
	@Column(name = "chip", length = 45, nullable=false, unique=true)
	private String num_chip;
	
	@Column(name = "especie", nullable=false)
	private String especie;
	
	@Column(name = "fecha_nacimiento", length = 50, nullable=false)
	@Temporal(TemporalType.DATE)
	private Calendar fecha_nac;
	
	@Column
	private double peso;
	
	@Column(length = 1, nullable=false)
	private String sexo;
	
	
	
	@ManyToOne
	@JoinColumn(name="propietario_id")
	private Propietario propietario;
	
	@OneToMany(mappedBy="mascota", cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Historia>historias;
	
	@OneToMany(mappedBy="mascota", cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Cita>citas;
	
	@OneToMany(mappedBy="mascota", cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Desparasitacion>desparasitaciones;
	
	@OneToMany(mappedBy="mascota", cascade= {CascadeType.ALL})
	@JsonIgnore
	private List<Vacuna>vacunas;
	

	public Mascota() {
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

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String animal) {
		this.especie = animal;
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

	public Propietario getPropietario() {
		return propietario;
	}

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
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
	
	public void addPropietario(Propietario propietario) {
		this.propietario = propietario;
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
	
	public void addDesparasitacion(Desparasitacion nDesparasitacion) {
		
		if(desparasitaciones==null)
			desparasitaciones = new ArrayList<>();
		
		desparasitaciones.add(nDesparasitacion);
		nDesparasitacion.addMascota(this);
	}
	
	public void addVacuna(Vacuna nVacuna) {
		
		if(vacunas==null)
			vacunas = new ArrayList<>();
		
		vacunas.add(nVacuna);
		nVacuna.addMascota(this);
	}


	
}
