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
import es.mascotapp.service.entity.Desparasitacion;
import es.mascotapp.service.entity.Mascota;
import es.mascotapp.service.service.interfaces.DesparasitacionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controladora que expone el servicio para la entidad Desparasitacion
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@RestController
@RequestMapping("/api/desparasitaciones")
public class DesparasitacionController {

	/**
	 * Inyección de objeto DesparasitacionService
	 */
	@Autowired
	private DesparasitacionService despService;

	/**
	 * Inyección de objeto MascotaDAO
	 */
	@Autowired
	private MascotaDAO mascDAO;

	/**
	 * Crear nueva Desparasitacion para la Mascota cuyo id entra por parámetro
	 * 
	 * @param desp Desparasitacion
	 * @return 400 si no se ha podido crear, 201 y la Desparasitacion si se ha
	 *         creado
	 */
	@ApiOperation(value = "Crear nueva Desparasitacion", notes = "Provee la operación para crear una nueva Desparasitacion a partir de la Desparasitacion que entra por parámetro"
			+ "para la Mascota cuyo id viene también por parámetro, realiza una búsqueda de la Mascota y si existe devuelve el objeto creado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Desparasitacion.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/{id}")
	public ResponseEntity<?> create(
			@ApiParam(value = "Datos de la nueva Desparasitacion", type = "Desparasitacion.class") @RequestBody Desparasitacion desp,
			@ApiParam(value = "ID de la Mascota", required = true, type = "long") @PathVariable(value = "id") Long mascId) {

		Mascota masc = mascDAO.findById(mascId).get();

		desp.addMascota(masc);
		masc.addDesparasitacion(desp);

		return ResponseEntity.status(HttpStatus.CREATED).body(despService.save(desp));
	}

	/**
	 * Se obtiene una Desparasitacion por su ID
	 * 
	 * @param despId
	 * @return 404 si no encuentra la Desparasitacion, 200 y la Desparasitacion si
	 *         la encuentra
	 */
	@ApiOperation(value = "Se obtiene una Desparasitacion por su ID", notes = "Provee un mecanismo para obtener todos los datos de una Desparasitacion por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Desparasitacion.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> read(
			@ApiParam(value = "ID de la Desparasitacion", required = true, type = "long") @PathVariable(value = "id") Long despId) {
		Optional<Desparasitacion> oDesp = despService.findById(despId);

		if (!oDesp.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oDesp);
	}

	/**
	 * Actualiza los datos de una Desparasitacion
	 * 
	 * @param despDetalles nuevos datos de desparasitacion
	 * @param despId       de Desparasitacion a actualizar
	 * @return 200 Ok si la actualización ha sido correcta, 404 si no se encuentra
	 *         la Desparasitacion
	 */
	@ApiOperation(value = "Actualizar los datos de una Desparasitacion", notes = "Provee la operación para actualizar una nueva Desparasitacion buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Desparasitacion.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@ApiParam(value = "Nuevos datos para la Desparasitacion", type = "Desparasitacion.class") @RequestBody Desparasitacion despDetalles,
			@ApiParam(value = "ID de la Desparasitacion", required = true, type = "long") @PathVariable(value = "id") Long despId) {
		Optional<Desparasitacion> desp = despService.findById(despId);

		if (!desp.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		desp.get().setTipo(despDetalles.getTipo());

		Calendar fecha = despDetalles.getFecha();
		fecha.add(Calendar.DATE, 1);
		desp.get().setFecha(fecha);

		desp.get().setProximaFecha(despDetalles.getProximaFecha());
		desp.get().setObservaciones(despDetalles.getObservaciones());

		return ResponseEntity.status(HttpStatus.CREATED).body(despService.save(desp.get()));
	}

	/**
	 * Borra una Desparasitacion
	 * 
	 * @param despId
	 * @return 200 Ok si ha borrado correctamente, 404 si no se encuentra la
	 *         Desparasitacion
	 */
	@ApiOperation(value = "Borrar una Desparasitacion", notes = "Provee la operación para borrar una Desparasitacion buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Desparasitacion.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@ApiParam(value = "ID de la Desparasitacion", required = true, type = "long") @PathVariable(value = "id") Long despId) {

		if (!despService.findById(despId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		despService.deleteById(despId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtener todas las desparasitaciones
	 * 
	 * @return lista
	 */
	@ApiOperation(value = "Obtener la lista de desparasitaciones", notes = "Provee un mecanismo para obtener todas las desparasitaciones")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public List<Desparasitacion> readAll() {
		List<Desparasitacion> desparasitaciones = StreamSupport.stream(despService.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return desparasitaciones;
	}

	/**
	 * Obtener lista de desparasitaciones por el ID de Mascota
	 * 
	 * @param id Mascota
	 * @return lista de desparasitaciones
	 */
	@ApiOperation(value = "Obtener lista de desparasitaciones por el ID de Mascota", notes = "Provee un mecanismo para obtener la lista de desparasitaciones de una Mascota")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = "mascota_id")
	public List<Desparasitacion> findByMascotaId(
			@ApiParam(value = "ID de la mascota", required = true, type = "long") @RequestParam(value = "mascota_id") Long id) {
		List<Desparasitacion> desparasitaciones = StreamSupport
				.stream(despService.findByMascotaId(id).spliterator(), false).collect(Collectors.toList());

		return desparasitaciones;
	}
}
