package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.VacunaDAO;
import es.mascotapp.service.entity.Vacuna;
import es.mascotapp.service.service.interfaces.VacunaService;

/**
 * Clase que define los métodos que se utilizan en el servicio Implementa
 * VacunaService y requiere de los métodos la interface VacunaDAO en su
 * implementación
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@Service
public class VacunaServiceImpl implements VacunaService {

	/**
	 * Inyecta un objeto VacunaDAO
	 */
	@Autowired
	private VacunaDAO vacDao;

	/**
	 * Obtiene todas las vacunas
	 * 
	 * @return Iterable de vacunas
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Vacuna> findAll() {
		return vacDao.findAll();
	}

	/**
	 * Obtiene todas las vacunas paginadas, para mostrar los datos por grupos en
	 * búsquedas de muchos registros
	 * 
	 * @return Page de vacunas
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Vacuna> findAll(Pageable paginable) {
		return vacDao.findAll(paginable);
	}

	/**
	 * Obtiene una vacuna por su ID
	 * 
	 * @return Optional de vacuna
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Vacuna> findById(Long id) {
		return vacDao.findById(id);
	}

	/**
	 * Crea/guarda una vacuna
	 * 
	 * @return vacuna
	 */
	@Override
	@Transactional
	public Vacuna save(Vacuna vacuna) {
		return vacDao.save(vacuna);
	}

	/**
	 * Borra una vacuna
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		vacDao.deleteById(id);
	}

	/**
	 * Obtiene una Vacuna por el ID de su mascota
	 * 
	 * @return Optional de Vacuna
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Vacuna> findByMascotaId(Long id) {
		return vacDao.findByMascotaId(id);
	}

}
