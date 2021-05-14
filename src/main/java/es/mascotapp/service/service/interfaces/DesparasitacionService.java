package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Desparasitacion;

public interface DesparasitacionService {

	public Iterable<Desparasitacion> findAll();
	
	public Page<Desparasitacion>findAll(Pageable pageable);
	
	public Optional<Desparasitacion> findById(Long id);
	
	public Desparasitacion save(Desparasitacion desparasitacion);
	
	public void deleteById(Long id);
	
	public List<Desparasitacion> findByMascotaId(Long id);
}
