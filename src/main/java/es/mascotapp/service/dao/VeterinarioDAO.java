package es.mascotapp.service.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Veterinario;

/*
 * extends JpaRepository porque realiza paginación desde el lado del servidor
 * ideal para tablas muy extensas con varias páginas, permiten ver los datos por grupo
 */
@Repository
public interface VeterinarioDAO extends JpaRepository<Veterinario, Long>{
	public boolean findByUsuarioAndPassword(String usuario, String password);
}
