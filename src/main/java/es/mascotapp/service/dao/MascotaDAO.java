package es.mascotapp.service.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Mascota;

/**
 * Interface que extiende de JpaRepository y permite el uso de los métodos de
 * dicha clase Define los métodos necesarios para realizar consultas sobre la
 * base de datos para la entidad Mascota.
 * 
 * @author Alejandro Rodríguez Campiñez
 * 
 * @version 2021/05/30
 */
@Repository
public interface MascotaDAO extends JpaRepository<Mascota, Long> {
	public List<Mascota> findByNombre(String nombre);

	public List<Mascota> findByPropietarioId(Long id);

	public Optional<Mascota> findByPropietarioIdAndNombre(Long id, String nombre);

	public Optional<Mascota> findByNumChip(int num);
}
