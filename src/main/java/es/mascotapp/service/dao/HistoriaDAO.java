package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Historia;

@Repository
public interface HistoriaDAO extends JpaRepository<Historia, Long>{
	public List<Historia>findByMascotaId(Long id);
}
