package es.mascotapp.service.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Propietario;

@Repository
public interface PropietarioDAO extends JpaRepository<Propietario, Long>{
	public List<Propietario>findByVeterinarioId(Long id);
	public Optional<Propietario> findByDni(String dni);
	public Optional<Propietario> findByEmailAndPassword(String usuario, String password);
}
