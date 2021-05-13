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
import es.mascotapp.service.entity.Cliente;
import es.mascotapp.service.entity.Veterinario;
import es.mascotapp.service.service.interfaces.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private VeterinarioDAO vetDAO;
	
	//Crear nuevo cliente
	@PostMapping("/{id}")
	public ResponseEntity<?>create(@RequestBody Cliente cliente, @PathVariable(value = "id") Long vetId){
		Veterinario vet = vetDAO.findById(vetId).get();
		
		cliente.addVeterinario(vet);
		vet.addCliente(cliente);		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
	}
	
	//Obtener un cliente
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long clienteId){
		Optional<Cliente>oCli = clienteService.findById(clienteId);
		
		if(!oCli.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oCli);
	}
	
	//Actualizar cliente
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente clieDetalles, @PathVariable(value = "id") Long clienteId){
		Optional<Cliente> cliente = clienteService.findById(clienteId);
		
		if(!cliente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.get().setNombre(clieDetalles.getNombre());
		cliente.get().setApellidos(clieDetalles.getApellidos());
		cliente.get().setEmail(clieDetalles.getEmail());
		cliente.get().setPassword(clieDetalles.getPassword());
		cliente.get().setTelefono(clieDetalles.getTelefono());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente.get()));
	}
	
	//Borrar un cliente
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long clienteId){
		
		if(!clienteService.findById(clienteId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		clienteService.deleteById(clienteId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos los clientes
	@GetMapping
	public List<Cliente> readAll(){
		List<Cliente> clientes = StreamSupport
				.stream(clienteService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return clientes;
	}
	
	//Obtener lista de clientes por veterinario_id
	
	@GetMapping(params = "vet_id")
	public List<Cliente> findByVeterinario(@RequestParam(value = "vet_id") Long id){
		List<Cliente>clientes = StreamSupport
				.stream(clienteService.findByVeterinarioId(id).spliterator(), false)
				.collect(Collectors.toList());
		return clientes;
	}
	
}
