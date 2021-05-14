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

import es.mascotapp.service.dao.VeterinarioDAO;
import es.mascotapp.service.entity.Propietario;
import es.mascotapp.service.entity.Veterinario;
import es.mascotapp.service.service.interfaces.PropietarioService;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

	@Autowired
	private PropietarioService propietarioService;
	
	@Autowired
	private VeterinarioDAO vetDAO;
	
	//Crear nuevo propietario
	@PostMapping("/{id}")
	public ResponseEntity<?>create(@RequestBody Propietario propietario, @PathVariable(value = "id") Long vetId){
		Veterinario vet = vetDAO.findById(vetId).get();
		
		propietario.addVeterinario(vet);
		vet.addPropietario(propietario);		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.save(propietario));
	}
	
	//Obtener un propietario
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long propietarioId){
		Optional<Propietario>oCli = propietarioService.findById(propietarioId);
		
		if(!oCli.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oCli);
	}
	
	//Actualizar propietario
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Propietario propDetalles, @PathVariable(value = "id") Long propietarioId){
		Optional<Propietario> propietario = propietarioService.findById(propietarioId);
		
		if(!propietario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		propietario.get().setNombre(propDetalles.getNombre());
		propietario.get().setApellidos(propDetalles.getApellidos());
		propietario.get().setDni(propDetalles.getDni());
		propietario.get().setEmail(propDetalles.getEmail());
		propietario.get().setPassword(propDetalles.getPassword());
		propietario.get().setTelefono(propDetalles.getTelefono());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.save(propietario.get()));
	}
	
	//Borrar un propietario
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long propietarioId){
		
		if(!propietarioService.findById(propietarioId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		propietarioService.deleteById(propietarioId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos los propietarios
	@GetMapping
	public List<Propietario> readAll(){
		List<Propietario> propietarios = StreamSupport
				.stream(propietarioService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return propietarios;
	}
	
	//Obtener lista de propietarios por veterinario_id
	
	@GetMapping(params = "vet_id")
	public List<Propietario> findByVeterinario(@RequestParam(value = "vet_id") Long id){
		List<Propietario>propietarios = StreamSupport
				.stream(propietarioService.findByVeterinarioId(id).spliterator(), false)
				.collect(Collectors.toList());
		return propietarios;
	}
	
}
