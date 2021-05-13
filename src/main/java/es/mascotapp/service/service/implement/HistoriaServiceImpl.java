package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.HistoriaDAO;
import es.mascotapp.service.entity.Historia;
import es.mascotapp.service.service.interfaces.HistoriaService;

@Service
public class HistoriaServiceImpl implements HistoriaService{

	@Autowired
	private HistoriaDAO histDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Historia> findAll() {
		return histDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Historia> findAll(Pageable paginable) {
		return histDAO.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Historia> findById(Long id) {
		return histDAO.findById(id);
	}

	@Override
	@Transactional
	public Historia save(Historia historia) {
		return histDAO.save(historia);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		histDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Historia> findByMascotaId(Long id) {
		return histDAO.findByMascotaId(id);
	}
}
