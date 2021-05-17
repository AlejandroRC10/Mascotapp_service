package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Propietario;

public interface PropietarioService {
	
	public Iterable<Propietario> findAll();
	
	public Page<Propietario>findAll(Pageable pageable);
	
	public Optional<Propietario> findById(Long id);
	
	public Propietario save(Propietario propietario);
	
	public void deleteById(Long id);

	public List<Propietario> findByVeterinarioId(Long id);
	
	public boolean findByEmailAndPassword(String usuario, String password);

}
