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

import es.mascotapp.service.entity.Cita;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.CitaService;
import es.mascotapp.service.service.interfaces.MascotaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controladora que expone el servicio para la entidad Cita
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@RestController
@RequestMapping("/api/citas")
public class CitaController {

	/**
	 * Inyección de objeto CitaService
	 */
	@Autowired
	private CitaService citaService;

	/**
	 * Inyección de objeto MascotaService
	 */
	@Autowired
	private MascotaService mascService;

	/**
	 * Crear nueva Cita para la Mascota cuyo id entra por parámetro
	 * 
	 * @param cita
	 * @return 400 si no se ha podido crear, 201 y la Cita si se ha creado
	 */
	@ApiOperation(value = "Crear nueva Cita", notes = "Provee la operación para crear una nueva Cita a partir de la Cita que entra por parámetro"
			+ "para la Mascota cuyo id viene también por parámetro, realiza una búsqueda de la Mascota y si existe devuelve el objeto creado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Cita.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/{id}")
	public ResponseEntity<?> create(
			@ApiParam(value = "Datos de la nueva Cita", type = "Desparasitacion.class") @RequestBody Cita cita,
			@ApiParam(value = "ID de la Mascota", required = true, type = "long") @PathVariable(value = "id") Long mascId) {
		Optional<Mascota> masc = mascService.findById(mascId);
		Optional<Cita> c = citaService.findByFecha(cita.getFecha());

		if (masc.isPresent() && !c.isPresent()) {
			cita.addMascota(masc.get());
			masc.get().addCita(cita);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(citaService.save(cita));
	}

	/**
	 * Se obtiene una Cita por su ID
	 * 
	 * @param despId
	 * @return 404 si no encuentra la Cita, 200 y la Cita si la encuentra
	 */
	@ApiOperation(value = "Se obtiene una Cita por su ID", notes = "Provee un mecanismo para obtener todos los datos de una Cita por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Cita.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> read(
			@ApiParam(value = "ID de la Cita", required = true, type = "long") @PathVariable(value = "id") Long citaId) {
		Optional<Cita> oVet = citaService.findById(citaId);

		if (!oVet.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oVet);
	}

	/**
	 * Actualiza los datos de una Cita
	 * 
	 * @param citaDetalles nuevos datos de cita
	 * @param citaId       de Cita a actualizar
	 * @return 200 Ok si la actualización ha sido correcta, 404 si no se encuentra
	 *         la Cita
	 */
	@ApiOperation(value = "Actualizar los datos de una Cita", notes = "Provee la operación para actualizar una nueva Cita buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Cita.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@ApiParam(value = "Nuevos datos para la Cita", type = "Cita.class") @RequestBody Cita citaDetalles,
			@ApiParam(value = "ID de la Cita", required = true, type = "long") @PathVariable(value = "id") Long citaId) {
		Optional<Cita> cita = citaService.findById(citaId);

		if (!cita.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		cita.get().setMotivo(citaDetalles.getMotivo());

		Calendar fecha = citaDetalles.getFecha();
		fecha.add(Calendar.DATE, 1);
		cita.get().setFecha(fecha);

		cita.get().setObservaciones(citaDetalles.getObservaciones());

		return ResponseEntity.status(HttpStatus.CREATED).body(citaService.save(cita.get()));
	}

	/**
	 * Borra una Cita
	 * 
	 * @param citaId
	 * @return 200 Ok si ha borrado correctamente, 404 si no se encuentra la Cita
	 */
	@ApiOperation(value = "Borrar una Cita", notes = "Provee la operación para borrar una Cita buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Cita.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@ApiParam(value = "ID de la Cita", required = true, type = "long") @PathVariable(value = "id") Long citaId) {

		if (!citaService.findById(citaId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		citaService.deleteById(citaId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtener todas las citas
	 * 
	 * @return lista
	 */
	@ApiOperation(value = "Obtener la lista de citas", notes = "Provee un mecanismo para obtener todas las citas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public List<Cita> readAll() {
		List<Cita> citas = StreamSupport.stream(citaService.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return citas;
	}

	/**
	 * Obtener lista de citas por el ID de Mascota
	 * 
	 * @param id Mascota
	 * @return lista de citas
	 */
	@ApiOperation(value = "Obtener lista de citas por el ID de Mascota", notes = "Provee un mecanismo para obtener la lista de citas de una Mascota")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = "mascota_id")
	public List<Cita> findByMascotaId(
			@ApiParam(value = "ID de la mascota", required = true, type = "long") @RequestParam(value = "mascota_id") Long id) {
		List<Cita> citas = StreamSupport.stream(citaService.findByMascotaId(id).spliterator(), false)
				.collect(Collectors.toList());

		return citas;
	}
}
