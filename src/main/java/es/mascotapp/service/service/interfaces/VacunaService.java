package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Vacuna;

public interface VacunaService {

	public Iterable<Vacuna> findAll();
	
	public Page<Vacuna>findAll(Pageable pageable);
	
	public Optional<Vacuna> findById(Long id);
	
	public Vacuna save(Vacuna vacuna);
	
	public void deleteById(Long id);
	
	public List<Vacuna> findByMascotaId(Long id);
}
