package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.PropietarioDAO;
import es.mascotapp.service.entity.Propietario;
import es.mascotapp.service.service.interfaces.PropietarioService;

/**
 * Clase que define los métodos que se utilizan en el servicio Implementa
 * PropietarioService y requiere de los métodos la interface PropietarioDAO en
 * la implementación
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@Service
public class PropietarioServiceImpl implements PropietarioService {

	/**
	 * Inyecta un objeto PropietarioDAO
	 */
	@Autowired
	private PropietarioDAO propietarioDAO;

	/**
	 * Obtiene todos los propietarios
	 * 
	 * @return Iterable de propietarios
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Propietario> findAll() {
		return propietarioDAO.findAll();
	}

	/**
	 * Obtiene todos los propietarios paginados, para mostrar los datos por grupos
	 * en búsquedas de muchos registros
	 * 
	 * @return Page de propietarios
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Propietario> findAll(Pageable paginable) {
		return propietarioDAO.findAll(paginable);
	}

	/**
	 * Obtiene un propietario por su ID
	 * 
	 * @return Optional de propietario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findById(Long id) {
		return propietarioDAO.findById(id);
	}

	/**
	 * Crea/guarda un propietario
	 * 
	 * @return propietario
	 */
	@Override
	@Transactional
	public Propietario save(Propietario propietario) {
		return propietarioDAO.save(propietario);
	}

	/**
	 * Borra un propietario
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		propietarioDAO.deleteById(id);
	}

	/**
	 * Obtiene un propietario por el ID de su veterinario
	 * 
	 * @return Optional de propietario
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Propietario> findByVeterinarioId(Long id) {
		return propietarioDAO.findByVeterinarioId(id);
	}

	/**
	 * Obtiene un propietario por su email y contraseña
	 * 
	 * @return Optional de propietario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findByEmailAndPassword(String usuario, String password) {
		return propietarioDAO.findByEmailAndPassword(usuario, password);
	}

	/**
	 * Obtiene un propietario por su DNI y el ID de su veterinario
	 * 
	 * @return Optional de propietario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findByDniAndVeterinarioId(String dni, Long id) {
		return propietarioDAO.findByDniAndVeterinarioId(dni, id);
	}

	/**
	 * Obtiene un propietario por su DNI
	 * 
	 * @return Optional de propietario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findByDni(String dni) {
		return propietarioDAO.findByDni(dni);
	}

	/**
	 * Obtiene un propietario por su email
	 * 
	 * @return Optional de propietario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findByEmail(String email) {
		return propietarioDAO.findByEmail(email);
	}

}
