package es.mascotapp.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Mascota;

@Repository
public interface MascotaDAO extends JpaRepository<Mascota, Long>{
	public List<Mascota>findByNombre(String nombre);
	public List<Mascota>findByClienteId(Long id);
}
