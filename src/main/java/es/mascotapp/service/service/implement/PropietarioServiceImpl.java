package es.mascotapp.service.service.implement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.PropietarioDAO;
import es.mascotapp.service.entity.Propietario;
import es.mascotapp.service.service.interfaces.PropietarioService;

@Service
public class PropietarioServiceImpl implements PropietarioService{
	
	@Autowired
	private PropietarioDAO propietarioDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Propietario> findAll() {
		return propietarioDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Propietario> findAll(Pageable paginable) {
		return propietarioDAO.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findById(Long id) {
		return propietarioDAO.findById(id);
	}

	@Override
	@Transactional
	public Propietario save(Propietario propietario) {
		return propietarioDAO.save(propietario);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		propietarioDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Propietario> findByVeterinarioId(Long id) {
		return propietarioDAO.findByVeterinarioId(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findByEmailAndPassword(String usuario, String password) {
		return propietarioDAO.findByEmailAndPassword(usuario, password);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Propietario> findByDni(String dni) {
		return propietarioDAO.findByDni(dni);
	}

}
