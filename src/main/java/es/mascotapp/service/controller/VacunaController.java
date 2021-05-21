package es.mascotapp.service.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.mascotapp.service.dao.MascotaDAO;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.entity.Vacuna;
import es.mascotapp.service.service.interfaces.VacunaService;

@RestController
@RequestMapping("/api/vacunas")
public class VacunaController {

	@Autowired
	private VacunaService vacunaService;
	
	@Autowired
	private MascotaDAO mascDAO;
	
	//Crear nueva vacuna
	@PostMapping("/{id}")
	public ResponseEntity<?>create(@RequestBody Vacuna vacuna, @PathVariable(value= "id") Long mascId){
		
		Mascota masc = mascDAO.findById(mascId).get();
		
		vacuna.addMascota(masc);
		masc.addVacuna(vacuna);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vacunaService.save(vacuna));
	}
	
	//Obtener una vacuna
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long vacunaId){
		Optional<Vacuna>oVac = vacunaService.findById(vacunaId);
		
		if(!oVac.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oVac);
	}
	
	//Actualizar vacuna
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Vacuna vacunaDetalles, @PathVariable(value = "id") Long vacunaId){
		Optional<Vacuna> vacuna = vacunaService.findById(vacunaId);
		
		if(!vacuna.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		vacuna.get().setEnfermedad(vacunaDetalles.getEnfermedad());
		
		Calendar fecha = vacunaDetalles.getFecha();
		fecha.add(Calendar.DATE, 1);
		vacuna.get().setFecha(fecha);
		
		Calendar fechaProx = vacunaDetalles.getProximaFecha();
		fechaProx.add(Calendar.DATE, 1);
		vacuna.get().setProximaFecha(fechaProx);
		
		vacuna.get().setObservaciones(vacunaDetalles.getObservaciones());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vacunaService.save(vacuna.get()));
	}
	
	//Borrar una vacuna
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long vacunaId){
		
		if(!vacunaService.findById(vacunaId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		vacunaService.deleteById(vacunaId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos las vacunas
	@GetMapping
	public List<Vacuna> readAll(){
		List<Vacuna> vacunas = StreamSupport
				.stream(vacunaService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return vacunas;
	}
	
	//Obtener lista de vacunas por mascota_id
	@GetMapping(params = "mascota_id")
	public List<Vacuna> findByMascotaId(@RequestParam(value = "mascota_id") Long id){
		List<Vacuna>vacunas = StreamSupport
				.stream(vacunaService.findByMascotaId(id).spliterator(), false)
				.collect(Collectors.toList());
		return vacunas;
	}
}
