package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.HistoriaDAO;
import es.mascotapp.service.entity.Historia;
import es.mascotapp.service.service.interfaces.HistoriaService;

/**
 * Clase que define los métodos que se utilizan en el servicio Implementa
 * HistoriaService y requiere de los métodos la interface HistoriaDAO en su
 * implementación
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@Service
public class HistoriaServiceImpl implements HistoriaService {

	/**
	 * Inyecta un objeto HistoriaDAO
	 */
	@Autowired
	private HistoriaDAO histDAO;

	/**
	 * Obtiene todas las historias
	 * 
	 * @return Iterable de historias
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Historia> findAll() {
		return histDAO.findAll();
	}

	/**
	 * Obtiene todas las historias paginadas, para mostrar los datos por grupos en
	 * búsquedas de muchos registros
	 * 
	 * @return Page de historias
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Historia> findAll(Pageable paginable) {
		return histDAO.findAll(paginable);
	}

	/**
	 * Obtiene una Historia por su ID
	 * 
	 * @return Optional de Historia
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Historia> findById(Long id) {
		return histDAO.findById(id);
	}

	/**
	 * Crea/guarda una Historia
	 * 
	 * @return Historia
	 */
	@Override
	@Transactional
	public Historia save(Historia historia) {
		return histDAO.save(historia);
	}

	/**
	 * Borra una Historia
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		histDAO.deleteById(id);
	}

	/**
	 * Obtiene una Historia por el ID de su mascota
	 * 
	 * @return Optional de Historia
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Historia> findByMascotaId(Long id) {
		return histDAO.findByMascotaId(id);
	}
}
