package es.mascotapp.service.service.implement;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.CitaDAO;
import es.mascotapp.service.entity.Cita;
import es.mascotapp.service.service.interfaces.CitaService;

/**
 * Clase que define los métodos que se utilizan en el servicio Implementa
 * CitaService y requiere de los métodos la interface CitaDAO en su
 * implementación
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@Service
public class CitaServiceImpl implements CitaService {

	/**
	 * Inyecta un objeto CitaDAO
	 */
	@Autowired
	private CitaDAO citaDAO;

	/**
	 * Obtiene todas las citas
	 * 
	 * @return Iterable de citas
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Cita> findAll() {
		return citaDAO.findAll();
	}

	/**
	 * Obtiene todas las citas paginadas, para mostrar los datos por grupos en
	 * búsquedas de muchos registros
	 * 
	 * @return Page de citas
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Cita> findAll(Pageable paginable) {
		return citaDAO.findAll(paginable);
	}

	/**
	 * Obtiene una Cita por su ID
	 * 
	 * @return Optional de Cita
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Cita> findById(Long id) {
		return citaDAO.findById(id);
	}

	/**
	 * Crea/guarda una Cita
	 * 
	 * @return Cita
	 */
	@Override
	@Transactional
	public Cita save(Cita cita) {
		return citaDAO.save(cita);
	}

	/**
	 * Borra una Cita
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		citaDAO.deleteById(id);
	}

	/**
	 * Obtiene una Cita por el ID de su mascota
	 * 
	 * @return Optional de Cita
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Cita> findByMascotaId(Long id) {
		return citaDAO.findByMascotaId(id);
	}

	/**
	 * Obtiene una Cita por su fecha
	 * 
	 * @return Optional de Cita
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Cita> findByFecha(Calendar fecha) {
		return citaDAO.findByFecha(fecha);
	}
}
