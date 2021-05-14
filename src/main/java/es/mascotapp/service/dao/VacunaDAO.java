package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Vacuna;

@Repository
public interface VacunaDAO extends JpaRepository<Vacuna, Long>{
	public List<Vacuna>findByMascotaId(Long id);
}
