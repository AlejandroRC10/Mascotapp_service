package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Mascota;

public interface MascotaService {
	
	public Iterable<Mascota> findAll();
	
	public Page<Mascota>findAll(Pageable pageable);
	
	public Optional<Mascota> findById(Long id);
	
	public Mascota save(Mascota mascota);
	
	public void deleteById(Long id);
	
	public List<Mascota>findByNombre(String nombre);
	
	public List<Mascota> findByClienteId(Long id);

}
