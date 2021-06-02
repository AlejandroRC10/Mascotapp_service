package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.MascotaDAO;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.MascotaService;

/**
 * Clase que define los métodos que se utilizan en el servicio Implementa
 * MascotaService y requiere de los métodos la interface MascotaDAO en su
 * implementación
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@Service
public class MascotaServiceImpl implements MascotaService {

	/**
	 * Inyecta un objeto MascotaDAO
	 */
	@Autowired
	private MascotaDAO mascotDAO;

	/**
	 * Obtiene todas las mascotas
	 * 
	 * @return Iterable de mascotas
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Mascota> findAll() {
		return mascotDAO.findAll();
	}

	/**
	 * Obtiene todas las mascotas paginadas, para mostrar los datos por grupos en
	 * búsquedas de muchos registros
	 * 
	 * @return Page de mascotas
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Mascota> findAll(Pageable paginable) {
		return mascotDAO.findAll(paginable);
	}

	/**
	 * Obtiene una Mascota por su ID
	 * 
	 * @return Optional de Mascota
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Mascota> findById(Long id) {
		return mascotDAO.findById(id);
	}

	/**
	 * Crea/guarda una Mascota
	 * 
	 * @return Mascota
	 */
	@Override
	@Transactional
	public Mascota save(Mascota mascota) {
		return mascotDAO.save(mascota);
	}

	/**
	 * Borra una Mascota
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		mascotDAO.deleteById(id);
	}

	/**
	 * Obtiene una lista de mascotas por su nombre
	 * 
	 * @return lista
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Mascota> findByNombre(String nombre) {
		return mascotDAO.findByNombre(nombre);
	}

	/**
	 * Obtiene una lista de mascotas por el ID de su propietario
	 * 
	 * @return lista
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Mascota> findByPropietarioId(Long id) {
		return mascotDAO.findByPropietarioId(id);
	}

	/**
	 * Obtiene una Mascota por su nombre y el ID de su propietario
	 * 
	 * @return Optional de Mascota
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Mascota> findByPropietarioIdAndNombre(Long id, String nombre) {
		return mascotDAO.findByPropietarioIdAndNombre(id, nombre);
	}

	/**
	 * Obtiene una Mascota por su Nº de chip
	 * 
	 * @return Optional de Mascota
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Mascota> findByNumChip(int num) {
		return mascotDAO.findByNumChip(num);
	}

}
