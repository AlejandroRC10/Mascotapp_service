package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Cita;
import es.mascotapp.service.entity.Historia;

@Repository
public interface CitaDAO extends JpaRepository<Cita, Long>{
	public List<Cita>findByMascotaId(Long id);
}
