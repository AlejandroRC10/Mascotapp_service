package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Desparasitacion;

@Repository
public interface DesparasitacionDAO extends JpaRepository<Desparasitacion, Long>{
	public List<Desparasitacion>findByMascotaId(Long id);
}
