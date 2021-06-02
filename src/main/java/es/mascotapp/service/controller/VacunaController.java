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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controladora que expone el servicio para la entidad Vacuna
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@RestController
@RequestMapping("/api/vacunas")
public class VacunaController {

	/**
	 * Inyección de objeto VacunaService
	 */
	@Autowired
	private VacunaService vacunaService;

	/**
	 * Inyección de objeto MascotaDAO
	 */
	@Autowired
	private MascotaDAO mascDAO;

	/**
	 * Crear nueva Vacuna para la Mascota cuyo id entra por parámetro
	 * 
	 * @param vacuna
	 * @return 400 si no se ha podido crear, 201 y la vacuna si se ha creado
	 */
	@ApiOperation(value = "Crear nueva Vacuna", notes = "Provee la operación para crear una nueva Vacuna a partir de la Vacuna que entra por parámetro"
			+ "para la Mascota cuyo id viene también por parámetro, realiza una búsqueda de la Mascota y si existe devuelve el objeto creado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Vacuna.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/{id}")
	public ResponseEntity<?> create(
			@ApiParam(value = "Datos de la nueva vacuna", type = "Vacuna.class") @RequestBody Vacuna vacuna,
			@ApiParam(value = "ID de la Mascota", required = true, type = "long") @PathVariable(value = "id") Long mascId) {

		Mascota masc = mascDAO.findById(mascId).get();

		vacuna.addMascota(masc);
		masc.addVacuna(vacuna);

		return ResponseEntity.status(HttpStatus.CREATED).body(vacunaService.save(vacuna));
	}

	/**
	 * Se obtiene una vacuna por su ID
	 * 
	 * @param vacunaId
	 * @return 404 si no encuentra la vacuna, 200 y la vacuna si la encuentra
	 */
	@ApiOperation(value = "Se obtiene una vacuna por su ID", notes = "Provee un mecanismo para obtener todos los datos de una vacuna por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Vacuna.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> read(
			@ApiParam(value = "ID de la vacuna", required = true, type = "long") @PathVariable(value = "id") Long vacunaId) {
		Optional<Vacuna> oVac = vacunaService.findById(vacunaId);

		if (!oVac.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oVac);
	}

	/**
	 * Actualiza los datos de una Vacuna
	 * 
	 * @param vacunaDetalles nuevos datos de vacuna
	 * @param vacunaId       de Vacuna a actualizar
	 * @return 200 Ok si la actualización ha sido correcta, 404 si no se encuentra
	 *         la vacuna
	 */
	@ApiOperation(value = "Actualizar los datos de una vacuna", notes = "Provee la operación para actualizar una nueva Vacuna buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Vacuna.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@ApiParam(value = "Nuevos datos para la vacuna", type = "Vacuna.class") @RequestBody Vacuna vacunaDetalles,
			@ApiParam(value = "ID de la vacuna", required = true, type = "long") @PathVariable(value = "id") Long vacunaId) {
		Optional<Vacuna> vacuna = vacunaService.findById(vacunaId);

		if (!vacuna.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		vacuna.get().setEnfermedad(vacunaDetalles.getEnfermedad());

		// Suma un día a la fecha que le llega, necesario por la diferencia en franja
		// horaria
		Calendar fecha = vacunaDetalles.getFecha();
		fecha.add(Calendar.DATE, 1);
		vacuna.get().setFecha(fecha);

		Calendar fechaProx = vacunaDetalles.getProximaFecha();
		fechaProx.add(Calendar.DATE, 1);
		vacuna.get().setProximaFecha(fechaProx);

		vacuna.get().setObservaciones(vacunaDetalles.getObservaciones());

		return ResponseEntity.status(HttpStatus.CREATED).body(vacunaService.save(vacuna.get()));
	}

	/**
	 * Borra una Vacuna
	 * 
	 * @param vacunaId
	 * @return 200 Ok si ha borrado correctamente, 404 si no se encuentra la vacuna
	 */
	@ApiOperation(value = "Borrar una vacuna", notes = "Provee la operación para borrar una Vacuna buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Vacuna.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@ApiParam(value = "ID de la vacuna", required = true, type = "long") @PathVariable(value = "id") Long vacunaId) {

		if (!vacunaService.findById(vacunaId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		vacunaService.deleteById(vacunaId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtener todas las vacunas
	 * 
	 * @return lista
	 */
	@ApiOperation(value = "Obtener la lista de vacunas", notes = "Provee un mecanismo para obtener todas las vacunas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public List<Vacuna> readAll() {
		List<Vacuna> vacunas = StreamSupport.stream(vacunaService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return vacunas;
	}

	/**
	 * Obtener lista de vacunas por el ID de Mascota
	 * 
	 * @param id Mascota
	 * @return lista de vacunas
	 */
	@ApiOperation(value = "Obtener lista de vacunas por el ID de Mascota", notes = "Provee un mecanismo para obtener la lista de vacunas de una Mascota")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = "mascota_id")
	public List<Vacuna> findByMascotaId(
			@ApiParam(value = "ID de la mascota", required = true, type = "long") @RequestParam(value = "mascota_id") Long id) {
		List<Vacuna> vacunas = StreamSupport.stream(vacunaService.findByMascotaId(id).spliterator(), false)
				.collect(Collectors.toList());
		return vacunas;
	}

}
