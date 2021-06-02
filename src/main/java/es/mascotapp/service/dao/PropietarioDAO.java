package es.mascotapp.service.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Propietario;

/**
 * Interface que extiende de JpaRepository y permite el uso de los métodos de
 * dicha clase Define los métodos necesarios para realizar consultas sobre la
 * base de datos para la entidad Propietario.
 * 
 * @author Alejandro Rodríguez Campiñez
 * 
 * @version 2021/05/30
 */
@Repository
public interface PropietarioDAO extends JpaRepository<Propietario, Long> {
	public List<Propietario> findByVeterinarioId(Long id);

	public Optional<Propietario> findByDniAndVeterinarioId(String dni, Long vet_id);

	public Optional<Propietario> findByDni(String dni);

	public Optional<Propietario> findByEmailAndPassword(String email, String password);

	public Optional<Propietario> findByEmail(String email);
}
