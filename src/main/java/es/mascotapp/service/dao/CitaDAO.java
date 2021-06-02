package es.mascotapp.service.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Cita;

/**
 * Interface que extiende de JpaRepository y permite el uso de los métodos de
 * dicha clase Define los métodos necesarios para realizar consultas sobre la
 * base de datos para la entidad Cita.
 * 
 * @author Alejandro Rodríguez Campiñez
 * 
 * @version 2021/05/30
 */
@Repository
public interface CitaDAO extends JpaRepository<Cita, Long> {
	public List<Cita> findByMascotaId(Long id);

	public Optional<Cita> findByFecha(Calendar fecha);
}
