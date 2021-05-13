package es.mascotapp.service.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column( )
	private String nombre;
	private String nombreUsuario;
	private String email;
	private String password;
	private Set<Rol> roles = new HashSet<>();
}
