package es.mascotapp.service.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.VeterinarioDAO;
import es.mascotapp.service.entity.Veterinario;
import es.mascotapp.service.service.interfaces.VeterinarioService;

@Service
public class VeterinarioServiceImpl implements VeterinarioService{
	
	@Autowired
	private VeterinarioDAO vetDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Veterinario> findAll() {
		return vetDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Veterinario> findAll(Pageable paginable) {
		return vetDAO.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Veterinario> findById(Long id) {
		return vetDAO.findById(id);
	}

	@Override
	@Transactional
	public Veterinario save(Veterinario veterinario) {
		return vetDAO.save(veterinario);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		vetDAO.deleteById(id);
	}

}
