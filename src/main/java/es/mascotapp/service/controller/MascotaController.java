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

import es.mascotapp.service.dao.ClienteDAO;
import es.mascotapp.service.entity.Cliente;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.MascotaService;


@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
	
	@Autowired
	private MascotaService mascotaService;
	
	@Autowired
	private ClienteDAO clieDAO;
	
	//Crear nueva mascota
	@PostMapping("/{id}")
	public ResponseEntity<?>create(@RequestBody Mascota mascota, @PathVariable(value = "id") Long clieId){
		
		//Busca un cliente por el id que le entra en la petición e inicializa el atributo cliente de la entidad con el valor devuelto 
		Cliente clie = clieDAO.findById(clieId).get();
		
		mascota.addCliente(clie);
		clie.addMascota(mascota);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.save(mascota));
	}
	
	//Obtener un mascota
	@GetMapping("/{id}")
	public ResponseEntity<?>read(@PathVariable(value = "id") Long mascId){
		Optional<Mascota>oMasc = mascotaService.findById(mascId);
		
		if(!oMasc.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oMasc);
	}
	
	//Actualizar mascota
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Mascota mascDetalles, @PathVariable(value = "id") Long mascId){
		Optional<Mascota> mascota = mascotaService.findById(mascId);
		
		if(!mascota.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		mascota.get().setNombre(mascDetalles.getNombre());
		mascota.get().setRaza(mascDetalles.getRaza());
		mascota.get().setNum_chip(mascDetalles.getNum_chip());
		mascota.get().setAnimal(mascDetalles.getAnimal());
		mascota.get().setFecha_nac(mascDetalles.getFecha_nac());
		mascota.get().setPeso(mascDetalles.getPeso());
		mascota.get().setSexo(mascDetalles.getSexo());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.save(mascota.get()));
	}
	
	//Borrar una mascota
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long mascId){
		
		if(!mascotaService.findById(mascId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		mascotaService.deleteById(mascId);
		return ResponseEntity.ok().build();
	}
	
	//Obtener todos los mascotas
	@GetMapping
	public List<Mascota> readAll(){
		List<Mascota> mascotas = StreamSupport
				.stream(mascotaService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return mascotas;
	}
	
	//Obtener lista de mascotas por nombre
	@GetMapping(params="name")
	public List<Mascota> findByNombre(@RequestParam(value = "name") String nombre){
		List<Mascota> mascotas = StreamSupport
				.stream(mascotaService.findByNombre(nombre).spliterator(), false)
				.collect(Collectors.toList());
		return mascotas;
	}
	
	//Obtener lista de mascotas por cliente_id
	@GetMapping(params = "clie_id")
	public List<Mascota> findByClienteId(@RequestParam(value = "clie_id") Long id){
		List<Mascota>mascotas = StreamSupport
				.stream(mascotaService.findByClienteId(id).spliterator(), false)
				.collect(Collectors.toList());
		return mascotas;
	}

}
