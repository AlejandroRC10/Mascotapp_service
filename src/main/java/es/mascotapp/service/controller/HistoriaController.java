package es.mascotapp.service.controller;

import java.util.Calendar;
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
import es.mascotapp.service.entity.Historia;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.HistoriaService;

@RestController
@RequestMapping("/api/historias")
public class HistoriaController {

	@Autowired
	private HistoriaService historiaService;
	
	@Autowired
	private MascotaDAO mascDAO;
	
	//Crear nueva historia
	@PostMapping("/{id}")
	public ResponseEntity<?>create(@RequestBody Historia historia, @PathVariable(value= "id") Long mascId){
		Mascota masc = mascDAO.findById(mascId).get();
		
		historia.addMascota(masc);
		masc.addHistoria(historia);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(historiaService.save(historia));
	}
	
	//Obtener una historia
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long histId){
		Optional<Historia>oHist = historiaService.findById(histId);
		
		if(!oHist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oHist);
	}
	
	//Actualizar historia
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Historia histDetalles, @PathVariable(value = "id") Long histId){
		Optional<Historia> historia = historiaService.findById(histId);
		
		if(!historia.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Calendar fecha = histDetalles.getFecha();
		fecha.add(Calendar.DATE, 1);
		historia.get().setFecha(fecha);
		
		historia.get().setEnfermedad(histDetalles.getEnfermedad());
		historia.get().setTratamiento(histDetalles.getTratamiento());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(historiaService.save(historia.get()));
	}
	
	//Borrar una historia
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long histId){
		
		if(!historiaService.findById(histId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		historiaService.deleteById(histId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos las historias
	@GetMapping
	public List<Historia> readAll(){
		List<Historia> historias = StreamSupport
				.stream(historiaService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return historias;
	}
	
	//Obtener lista de historias por mascota_id
		@GetMapping(params = "mascota_id")
		public List<Historia> findByMascotaId(@RequestParam(value = "mascota_id") Long id){
			List<Historia>historias = StreamSupport
					.stream(historiaService.findByMascotaId(id).spliterator(), false)
					.collect(Collectors.toList());
			return historias;
		}
}
