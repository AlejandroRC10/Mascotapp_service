package es.mascotapp.service.controller;

import java.util.ArrayList;
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

import es.mascotapp.service.entity.Propietario;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.MascotaService;
import es.mascotapp.service.service.interfaces.PropietarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controladora que expone el servicio para la entidad Mascota
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

	/**
	 * Inyección de objeto MascotaService
	 */
	@Autowired
	private MascotaService mascotaService;

	/**
	 * Inyección de objeto PropietarioService
	 */
	@Autowired
	private PropietarioService propService;

	/**
	 * Crear una nueva Mascota
	 * 
	 * @param mascota
	 * @param propId
	 * @return 400 si no se ha podido crear, 201 y la Mascota si se ha creado
	 */
	@ApiOperation(value = "Crear una nueva Mascota", notes = "Provee la operación para crear una nueva Mascota a partir de la Mascota y el ID de Propietario que entran por parámetro, devuelve el objeto creado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Mascota.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/{id}")
	public ResponseEntity<?> create(
			@ApiParam(value = "Datos de la nueva Mascota", type = "Mascota.class") @RequestBody Mascota mascota,
			@ApiParam(value = "ID del Propietario", required = true, type = "long") @PathVariable(value = "id") Long propId) {

		Optional<Propietario> oProp = propService.findById(propId);
		Optional<Propietario> oProp2 = propService.findByDni(mascota.getNum_chip());
		if (oProp.isPresent() && !oProp2.isPresent()) {
			mascota.addPropietario(oProp.get());
			oProp.get().addMascota(mascota);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.save(mascota));
	}

	/**
	 * Se obtiene una Mascota por su ID
	 * 
	 * @param mascId
	 * @return si no encuentra la mascota, 200 y la mascota si la encuentra
	 */
	@ApiOperation(value = "Obtener una Mascota por su ID", notes = "Provee un mecanismo para obtener todos los datos de una Mascota por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Mascota.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> read(
			@ApiParam(value = "ID de la mascota", required = true, type = "long") @PathVariable(value = "id") Long mascId) {
		Optional<Mascota> oMasc = mascotaService.findById(mascId);

		if (!oMasc.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oMasc);
	}

	/**
	 * Actualizar los datos de una Mascota
	 * 
	 * @param mascDetalles nuevos datos de la Mascota
	 * @param mascId
	 * @return 200 Ok si la actualización ha sido correcta, 404 si no se encuentra
	 *         la mascota
	 */
	@ApiOperation(value = "Actualizar los datos de una mascota", notes = "Provee la operación para actualizar una nueva Mascota buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Mascota.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@ApiParam(value = "Nuevos datos para la mascota", type = "Mascota.class") @RequestBody Mascota mascDetalles,
			@ApiParam(value = "ID de la mascota", required = true, type = "long") @PathVariable(value = "id") Long mascId) {
		Optional<Mascota> mascota = mascotaService.findById(mascId);
		Optional<Propietario> oProp2 = propService.findByDni(mascDetalles.getNum_chip());

		if (!mascota.isPresent() || oProp2.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		mascota.get().setNombre(mascDetalles.getNombre());
		mascota.get().setRaza(mascDetalles.getRaza());
		mascota.get().setNum_chip(mascDetalles.getNum_chip());
		mascota.get().setEspecie(mascDetalles.getEspecie());

		Calendar fecha = mascDetalles.getFecha_nac();
		fecha.add(Calendar.DATE, 1);
		mascota.get().setFecha_nac(fecha);

		mascota.get().setPeso(mascDetalles.getPeso());
		mascota.get().setSexo(mascDetalles.getSexo());

		return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.save(mascota.get()));
	}

	/**
	 * Borrar una Mascota
	 * 
	 * @param mascId
	 * @return 200 Ok si ha borrado correctamente, 404 si no se encuentra la mascota
	 */
	@ApiOperation(value = "Borrar una Mascota", notes = "Provee la operación para borrar una Mascota buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Mascota.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@ApiParam(value = "ID de la Mascota", required = true, type = "long") @PathVariable(value = "id") Long mascId) {

		if (!mascotaService.findById(mascId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		mascotaService.deleteById(mascId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtener todas las mascotas
	 * 
	 * @return lista
	 */
	@ApiOperation(value = "Obtener la lista de mascotas", notes = "Provee un mecanismo para obtener todas las mascotas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public List<Mascota> readAll() {
		List<Mascota> mascotas = StreamSupport.stream(mascotaService.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return mascotas;
	}

	/**
	 * Obtiene lista de mascotas por su nombre
	 * 
	 * @param nombre
	 * @return lista
	 */
	@ApiOperation(value = "Obtiene lista de mascotas por su nombre", notes = "Provee un mecanismo para obtener todas las mascota con el nombre que llega por parámetro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = "name")
	public List<Mascota> findByNombre(
			@ApiParam(value = "Nombre de la mascota", required = true, type = "String") @RequestParam(value = "name") String nombre) {

		List<Mascota> mascotas = StreamSupport.stream(mascotaService.findByNombre(nombre).spliterator(), false)
				.collect(Collectors.toList());

		return mascotas;
	}

	/**
	 * Obtiene lista de mascotas por el ID de su Propietario
	 * 
	 * @param id
	 * @return lista
	 */
	@ApiOperation(value = "Obtiene lista de mascotas por el ID de su Propietario", notes = "Provee un mecanismo para obtener todas las mascotas de un Propietario por el ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = "prop_id")
	public List<Mascota> findByPropietarioId(
			@ApiParam(value = "ID del propietario", required = true, type = "long") @RequestParam(value = "prop_id") Long id) {
		List<Mascota> mascotas = StreamSupport.stream(mascotaService.findByPropietarioId(id).spliterator(), false)
				.collect(Collectors.toList());
		return mascotas;
	}

	/**
	 * Obtiene lista de mascotas por su nombre y el ID de su Veterinario
	 * 
	 * @param vetId
	 * @param nombre
	 * @return lista
	 */
	@ApiOperation(value = "Obtiene lista de mascotas por su nombre y el ID de su Propietario", notes = "Provee un mecanismo para obtener todas las mascotas del Veterinario un nombre y el ID del Veterinario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = { "vet_id", "nombre" })
	public List<Mascota> findByNombreIdAndVeterinario(
			@ApiParam(value = "ID del veterinario", required = true, type = "long") @RequestParam(value = "vet_id") Long vetId,
			@ApiParam(value = "Nombre de la mascota", required = true, type = "String") @RequestParam(value = "nombre") String nombre) {

		// Busca las mascotas por su nombre
		List<Mascota> listMascs = mascotaService.findByNombre(nombre);
		List<Mascota> mascotas = new ArrayList<>();

		// recorre la lista obtenida y añade a la nueva lista las que tengan el ID de
		// Veterinario igual que el que entra por parámetro
		for (Mascota m : listMascs) {
			if (m.getPropietario().getVeterinario().getId() == vetId) {
				mascotas.add(m);
			}
		}
		List<Mascota> listAux = StreamSupport.stream(mascotas.spliterator(), false).collect(Collectors.toList());

		return listAux;
	}

}
