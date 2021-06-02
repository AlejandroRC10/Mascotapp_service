package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Propietario;

/**
 * Interface con los métodos a utilizar por el servicio (Controllers)
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
public interface PropietarioService {

	public Iterable<Propietario> findAll();

	public Page<Propietario> findAll(Pageable pageable);

	public Optional<Propietario> findById(Long id);

	public Propietario save(Propietario propietario);

	public void deleteById(Long id);

	public List<Propietario> findByVeterinarioId(Long id);

	public Optional<Propietario> findByEmailAndPassword(String usuario, String password);

	public Optional<Propietario> findByDniAndVeterinarioId(String dni, Long vet_id);

	public Optional<Propietario> findByDni(String dni);

	public Optional<Propietario> findByEmail(String email);

}
