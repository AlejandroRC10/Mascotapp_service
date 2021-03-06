package es.mascotapp.service.service.interfaces;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Cita;

/**
 * Interface con los métodos a utilizar por el servicio (Controllers)
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
public interface CitaService {

	public Iterable<Cita> findAll();

	public Page<Cita> findAll(Pageable pageable);

	public Optional<Cita> findById(Long id);

	public Cita save(Cita cita);

	public void deleteById(Long id);

	public List<Cita> findByMascotaId(Long id);

	public Optional<Cita> findByFecha(Calendar fecha);

}
