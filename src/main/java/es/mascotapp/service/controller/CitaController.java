package es.mascotapp.service.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mascotapp.service.dao.MascotaDAO;
import es.mascotapp.service.entity.Cita;
import es.mascotapp.service.entity.Historia;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.CitaService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

	@Autowired
	private CitaService citaService;
	
	@Autowired
	private MascotaDAO mascDAO;
	
	//Crear nueva cita
	@PostMapping("/{id}")
	public ResponseEntity<?>create(@RequestBody Cita cita, @PathVariable(value= "id") Long mascId){
		
		Mascota masc = mascDAO.findById(mascId).get();
		
		cita.addMascota(masc);
		masc.addCita(cita);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(citaService.save(cita));
	}
	
	//Obtener una cita
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long citaId){
		Optional<Cita>oVet = citaService.findById(citaId);
		
		if(!oVet.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oVet);
	}
	
	//Actualizar cita
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Cita citaDetalles, @PathVariable(value = "id") Long citaId){
		Optional<Cita> cita = citaService.findById(citaId);
		
		if(!cita.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		cita.get().setMotivo(citaDetalles.getMotivo());
		cita.get().setFecha(citaDetalles.getFecha());
		cita.get().setDescripcion(citaDetalles.getDescripcion());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(citaService.save(cita.get()));
	}
	
	//Borrar una cita
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long citaId){
		
		if(!citaService.findById(citaId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		citaService.deleteById(citaId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos las citas
	@GetMapping
	public List<Cita> readAll(){
		List<Cita> citas = StreamSupport
				.stream(citaService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return citas;
	}
	
	//Obtener lista de citas por mascota_id
	@GetMapping(params = "mascota_id")
	public List<Cita> findByMascotaId(@RequestParam(value = "mascota_id") Long id){
		List<Cita>citas = StreamSupport
				.stream(citaService.findByMascotaId(id).spliterator(), false)
				.collect(Collectors.toList());
		return citas;
	}
}
