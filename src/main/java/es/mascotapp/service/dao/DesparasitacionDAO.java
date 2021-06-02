package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Desparasitacion;

/**
 * Interface que extiende de JpaRepository y permite el uso de los métodos de
 * dicha clase Define los métodos necesarios para realizar consultas sobre la
 * base de datos para la entidad Desparasitacion.
 * 
 * @author Alejandro Rodríguez Campiñez
 * 
 * @version 2021/05/30
 */
@Repository
public interface DesparasitacionDAO extends JpaRepository<Desparasitacion, Long> {
	public List<Desparasitacion> findByMascotaId(Long id);
}
