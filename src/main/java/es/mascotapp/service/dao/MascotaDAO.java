package es.mascotapp.service.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mascotapp.service.entity.Mascota;

@Repository
public interface MascotaDAO extends JpaRepository<Mascota, Long>{
	public List<Mascota>findByNombre(String nombre);
	public List<Mascota>findByPropietarioId(Long id);
	public Optional<Mascota>findByPropietarioIdAndNombre(Long id, String nombre);
}
