package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Cliente;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Long>{
	public List<Cliente>findByVeterinarioId(Long id);
}
