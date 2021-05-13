package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.CitaDAO;
import es.mascotapp.service.entity.Cita;
import es.mascotapp.service.entity.Historia;
import es.mascotapp.service.service.interfaces.CitaService;

@Service
public class CitaServiceImpl implements CitaService{

	@Autowired
	private CitaDAO citaDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Cita> findAll() {
		return citaDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cita> findAll(Pageable paginable) {
		return citaDAO.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cita> findById(Long id) {
		return citaDAO.findById(id);
	}

	@Override
	@Transactional
	public Cita save(Cita cita) {
		return citaDAO.save(cita);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		citaDAO.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cita> findByMascotaId(Long id) {
		return citaDAO.findByMascotaId(id);
	}
}
