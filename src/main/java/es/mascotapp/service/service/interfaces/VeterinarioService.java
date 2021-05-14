package es.mascotapp.service.service.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Veterinario;

public interface VeterinarioService {

	public Iterable<Veterinario> findAll();
	
	public Page<Veterinario>findAll(Pageable pageable);
	
	public Optional<Veterinario> findById(Long id);
	
	public Veterinario save(Veterinario veterinario);
	
	public void deleteById(Long id);
	
	public boolean findByUsuarioAndPassword(String usuario, String password);
}
