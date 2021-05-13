package es.mascotapp.service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mascotapp.service.entity.Cliente;

public interface ClienteService {
	
	public Iterable<Cliente> findAll();
	
	public Page<Cliente>findAll(Pageable pageable);
	
	public Optional<Cliente> findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void deleteById(Long id);

	public List<Cliente> findByVeterinarioId(Long id);

}
