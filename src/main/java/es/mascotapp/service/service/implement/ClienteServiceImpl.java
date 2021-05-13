package es.mascotapp.service.service.implement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mascotapp.service.dao.ClienteDAO;
import es.mascotapp.service.entity.Cliente;
import es.mascotapp.service.service.interfaces.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteDAO clienteDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Cliente> findAll() {
		return clienteDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable paginable) {
		return clienteDAO.findAll(paginable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> findById(Long id) {
		return clienteDAO.findById(id);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDAO.save(cliente);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		clienteDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findByVeterinarioId(Long id) {
		return clienteDAO.findByVeterinarioId(id);
	}

}
