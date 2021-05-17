package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Propietario;

@Repository
public interface PropietarioDAO extends JpaRepository<Propietario, Long>{
	public List<Propietario>findByVeterinarioId(Long id);
	public boolean findByEmailAndPassword(String usuario, String password);
}
