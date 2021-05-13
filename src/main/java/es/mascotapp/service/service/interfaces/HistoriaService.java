package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Historia;
import es.mascotapp.service.entity.Mascota;

public interface HistoriaService {

	public Iterable<Historia> findAll();
	
	public Page<Historia>findAll(Pageable pageable);
	
	public Optional<Historia> findById(Long id);
	
	public Historia save(Historia historia);
	
	public void deleteById(Long id);
	
	public List<Historia> findByMascotaId(Long id);
}
