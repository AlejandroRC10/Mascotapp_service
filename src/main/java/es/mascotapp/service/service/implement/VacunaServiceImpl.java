package es.mascotapp.service.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.VacunaDAO;
import es.mascotapp.service.entity.Vacuna;
import es.mascotapp.service.service.interfaces.VacunaService;


@Service
public class VacunaServiceImpl implements VacunaService{
	@Autowired
	private VacunaDAO vacDao;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Vacuna> findAll() {
		return vacDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vacuna> findAll(Pageable paginable) {
		return vacDao.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Vacuna> findById(Long id) {
		return vacDao.findById(id);
	}

	@Override
	@Transactional
	public Vacuna save(Vacuna vacuna) {
		return vacDao.save(vacuna);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		vacDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Vacuna> findByMascotaId(Long id) {
		return vacDao.findByMascotaId(id);
	}
}
