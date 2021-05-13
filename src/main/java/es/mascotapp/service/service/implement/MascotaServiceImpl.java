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

@Service
public class MascotaServiceImpl implements MascotaService{
	
	@Autowired
	private MascotaDAO mascotDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Mascota> findAll() {
		return mascotDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Mascota> findAll(Pageable paginable) {
		return mascotDAO.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Mascota> findById(Long id) {
		return mascotDAO.findById(id);
	}

	@Override
	@Transactional
	public Mascota save(Mascota mascota) {
		return mascotDAO.save(mascota);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		mascotDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Mascota> findByNombre(String nombre) {
		return mascotDAO.findByNombre(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Mascota> findByClienteId(Long id) {
		return mascotDAO.findByClienteId(id);
	}

}
