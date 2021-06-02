package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.DesparasitacionDAO;
import es.mascotapp.service.entity.Desparasitacion;
import es.mascotapp.service.service.interfaces.DesparasitacionService;

/**
 * Clase que define los métodos que se utilizan en el servicio Implementa
 * DesparasitacionService y requiere de los métodos la interface
 * DesparasitacionDAO en su implementación
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@Service
public class DesparasitacionServiceImpl implements DesparasitacionService {

	/**
	 * Inyecta un objeto DesparasitacionDAO
	 */
	@Autowired
	private DesparasitacionDAO despDao;

	/**
	 * Obtiene todas las desparasitaciones
	 * 
	 * @return Iterable de desparasitaciones
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Desparasitacion> findAll() {
		return despDao.findAll();
	}

	/**
	 * Obtiene todas las desparasitaciones paginadas, para mostrar los datos por
	 * grupos en búsquedas de muchos registros
	 * 
	 * @return Page de desparasitaciones
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Desparasitacion> findAll(Pageable paginable) {
		return despDao.findAll(paginable);
	}

	/**
	 * Obtiene una Desparasitacion por su ID
	 * 
	 * @return Optional de Desparasitacion
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Desparasitacion> findById(Long id) {
		return despDao.findById(id);
	}

	/**
	 * Crea/guarda una Desparasitacion
	 * 
	 * @return Desparasitacion
	 */
	@Override
	@Transactional
	public Desparasitacion save(Desparasitacion desp) {
		return despDao.save(desp);
	}

	/**
	 * Borra una Desparasitacion
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		despDao.deleteById(id);
	}

	/**
	 * Obtiene una Desparasitacion por el ID de su mascota
	 * 
	 * @return Optional de Desparasitacion
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Desparasitacion> findByMascotaId(Long id) {
		return despDao.findByMascotaId(id);
	}
}
