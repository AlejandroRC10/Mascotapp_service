package es.mascotapp.service.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.VeterinarioDAO;
import es.mascotapp.service.entity.Veterinario;
import es.mascotapp.service.service.interfaces.VeterinarioService;

/**
 * Clase que define los métodos que se utilizan en el servicio Implementa
 * VeterinarioService y requiere de los métodos la interface VeterinarioDAO en
 * la implementación
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@Service
public class VeterinarioServiceImpl implements VeterinarioService {

	/**
	 * Inyecta un objeto VeterinarioDAO
	 */
	@Autowired
	private VeterinarioDAO vetDAO;

	/**
	 * Obtiene todos los veterinarios
	 * 
	 * @return Iterable de veterinarios
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Veterinario> findAll() {
		return vetDAO.findAll();
	}

	/**
	 * Obtiene todos los veterinarios paginados, para mostrar los datos por grupos
	 * en búsquedas de muchos registros
	 * 
	 * @return Page de veterinarios
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Veterinario> findAll(Pageable paginable) {
		return vetDAO.findAll(paginable);
	}

	/**
	 * Obtiene un veterinario por su ID
	 * 
	 * @return Optional de veterinario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Veterinario> findById(Long id) {
		return vetDAO.findById(id);
	}

	/**
	 * Crea/guarda un veterinario
	 * 
	 * @return veterinario
	 */
	@Override
	@Transactional
	public Veterinario save(Veterinario veterinario) {
		return vetDAO.save(veterinario);
	}

	/**
	 * Borra un veterinario
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		vetDAO.deleteById(id);
	}

	/**
	 * Obtiene un veterinario por su usuario y contraseña
	 * 
	 * @return Optional de veterinario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Veterinario> findByUsuarioAndPassword(String usuario, String password) {
		return vetDAO.findByUsuarioAndPassword(usuario, password);
	}

	/**
	 * Obtiene un veterinario por su usuario
	 * 
	 * @return Optional de veterinario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Veterinario> findByUsuario(String usuario) {
		return vetDAO.findByUsuario(usuario);
	}

	/**
	 * Obtiene un veterinario por su Nº de colegiado
	 * 
	 * @return Optional de veterinario
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Veterinario> findByNumColegiado(int num) {
		return vetDAO.findByNumColegiado(num);
	}

}
