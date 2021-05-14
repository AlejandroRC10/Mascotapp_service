package es.mascotapp.service.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.mascotapp.service.dao.MascotaDAO;
import es.mascotapp.service.entity.Desparasitacion;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.DesparasitacionService;

@RestController
@RequestMapping("/api/desparasitaciones")
public class DesparasitacionController {
	@Autowired
	private DesparasitacionService despService;
	
	@Autowired
	private MascotaDAO mascDAO;
	
	//Crear nueva desparasitacion
	@PostMapping("/{id}")
	public ResponseEntity<?>create(@RequestBody Desparasitacion desp, @PathVariable(value= "id") Long mascId){
		
		Mascota masc = mascDAO.findById(mascId).get();
		
		desp.addMascota(masc);
		masc.addDesparasitacion(desp);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(despService.save(desp));
	}
	
	//Obtener una desparasitacion
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long despId){
		Optional<Desparasitacion>oDesp = despService.findById(despId);
		
		if(!oDesp.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oDesp);
	}
	
	//Actualizar desparasitacion
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Desparasitacion despDetalles, @PathVariable(value = "id") Long despId){
		Optional<Desparasitacion> desp = despService.findById(despId);
		
		if(!desp.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		desp.get().setTipo(despDetalles.getTipo());
		desp.get().setFecha(despDetalles.getFecha());
		desp.get().setProximaFecha(despDetalles.getProximaFecha());
		desp.get().setObservaciones(despDetalles.getObservaciones());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(despService.save(desp.get()));
	}
	
	//Borrar una desparasitacion
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long despId){
		
		if(!despService.findById(despId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		despService.deleteById(despId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos las desparasitaciones
	@GetMapping
	public List<Desparasitacion> readAll(){
		List<Desparasitacion> desparasitaciones = StreamSupport
				.stream(despService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return desparasitaciones;
	}
	
	//Obtener lista de desparasitaciones por mascota_id
	@GetMapping(params = "mascota_id")
	public List<Desparasitacion> findByMascotaId(@RequestParam(value = "mascota_id") Long id){
		List<Desparasitacion>desparasitaciones = StreamSupport
				.stream(despService.findByMascotaId(id).spliterator(), false)
				.collect(Collectors.toList());
		return desparasitaciones;
	}
}
