package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.DesparasitacionDAO;
import es.mascotapp.service.entity.Desparasitacion;
import es.mascotapp.service.service.interfaces.DesparasitacionService;

@Service
public class DesparasitacionServiceImpl implements DesparasitacionService{

	@Autowired
	private DesparasitacionDAO despDao;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Desparasitacion> findAll() {
		return despDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Desparasitacion> findAll(Pageable paginable) {
		return despDao.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Desparasitacion> findById(Long id) {
		return despDao.findById(id);
	}

	@Override
	@Transactional
	public Desparasitacion save(Desparasitacion desp) {
		return despDao.save(desp);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		despDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Desparasitacion> findByMascotaId(Long id) {
		return despDao.findByMascotaId(id);
	}
}
