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

import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.entity.Veterinario;
import es.mascotapp.service.service.interfaces.VeterinarioService;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {
	
	@Autowired
	private VeterinarioService veterinarioService;
	
	//Crear nuevo veterinario
	@PostMapping
	public ResponseEntity<?>create(@RequestBody Veterinario veterinario){
		return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.save(veterinario));
	}
	
	//Obtener un veterinario
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long vetId){
		Optional<Veterinario>oVet = veterinarioService.findById(vetId);
		
		if(!oVet.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oVet);
	}
	
	//Actualizar veterinario
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Veterinario vetDetalles, @PathVariable(value = "id") Long vetId){
		Optional<Veterinario> veterinario = veterinarioService.findById(vetId);
		
		if(!veterinario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		veterinario.get().setNombre(vetDetalles.getNombre());
		veterinario.get().setApellidos(vetDetalles.getApellidos());
		veterinario.get().setDireccion(vetDetalles.getDireccion());
		veterinario.get().setNom_clinica(vetDetalles.getNom_clinica());
		veterinario.get().setNum_colegiado(vetDetalles.getNum_colegiado());
		veterinario.get().setPassword(vetDetalles.getPassword());
		veterinario.get().setUsuario(vetDetalles.getUsuario());
		veterinario.get().setTelefono(vetDetalles.getTelefono());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.save(veterinario.get()));
	}
	
	//Borrar un veterinario
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long vetId){
		
		if(!veterinarioService.findById(vetId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		veterinarioService.deleteById(vetId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos los veterinarios
	@GetMapping
	public List<Veterinario> readAll(){
		List<Veterinario> veterinarios = StreamSupport
				.stream(veterinarioService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return veterinarios;
	}
	
	//Comprobar usuario y contraseña
	@GetMapping(params = {"usuario", "password"})
	public boolean findByUsuarioAndPassword(@RequestParam(value="usuario") String usuario, @RequestParam(value="password") String password) {
		//Optional<Veterinario> vet = veterinarioService.findByUsuarioAndPassword(usuario, password);
		return false;
	}
	
	
}
