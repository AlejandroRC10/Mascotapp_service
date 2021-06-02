package es.mascotapp.service.service.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Veterinario;

/**
 * Interface con los métodos a utilizar por el servicio (Controllers)
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
public interface VeterinarioService {

	public Iterable<Veterinario> findAll();

	public Page<Veterinario> findAll(Pageable pageable);

	public Optional<Veterinario> findById(Long id);

	public Veterinario save(Veterinario veterinario);

	public void deleteById(Long id);

	public Optional<Veterinario> findByUsuarioAndPassword(String usuario, String password);

	public Optional<Veterinario> findByUsuario(String usuario);

	public Optional<Veterinario> findByNumColegiado(int num);

}
