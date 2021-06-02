package es.mascotapp.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Veterinario;

/**
 * 
 * Interface que extiende de JpaRepository porque realiza paginación desde el
 * lado del servidor ideal para tablas muy extensas con varias páginas, permiten
 * ver los datos por grupo. Define los métodos necesarios para realizar
 * consultas sobre la base de datos para la entidad Veterinario.
 * 
 * @author Alejandro Rodríguez Campiñez
 * 
 * @version 2021/05/30
 */
@Repository
public interface VeterinarioDAO extends JpaRepository<Veterinario, Long> {
	public Optional<Veterinario> findByUsuarioAndPassword(String usuario, String password);

	public Optional<Veterinario> findByUsuario(String usuario);

	public Optional<Veterinario> findByNumColegiado(int num);
}
