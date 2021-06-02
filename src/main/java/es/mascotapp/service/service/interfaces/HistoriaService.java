package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Historia;

/**
 * Interface con los métodos a utilizar por el servicio (Controllers)
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
public interface HistoriaService {

	public Iterable<Historia> findAll();

	public Page<Historia> findAll(Pageable pageable);

	public Optional<Historia> findById(Long id);

	public Historia save(Historia historia);

	public void deleteById(Long id);

	public List<Historia> findByMascotaId(Long id);
}
