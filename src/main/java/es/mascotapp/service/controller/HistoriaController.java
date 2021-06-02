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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controladora que expone el servicio para la entidad Historia
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@RestController
@RequestMapping("/api/historias")
public class HistoriaController {

	/**
	 * Inyección de objeto HistoriaService
	 */
	@Autowired
	private HistoriaService historiaService;

	/**
	 * Inyección de objeto MascotaDAO
	 */
	@Autowired
	private MascotaDAO mascDAO;

	/**
	 * Crea una nueva Historia
	 * 
	 * @param historia
	 * @param mascId
	 * @return 400 si no se ha podido crear, 201 y la Historia si se ha creado
	 */
	@ApiOperation(value = "Crear una nueva Historia", notes = "Provee la operación para crear una nueva Historia a partir de la Historia y el ID de Mascota que entran por parámetro, devuelve el objeto creado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Historia.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/{id}")
	public ResponseEntity<?> create(
			@ApiParam(value = "Datos de la nueva Historia", type = "Historia.class") @RequestBody Historia historia,
			@ApiParam(value = "ID de la mascota", required = true, type = "long") @PathVariable(value = "id") Long mascId) {
		Mascota masc = mascDAO.findById(mascId).get();

		historia.addMascota(masc);
		masc.addHistoria(historia);

		return ResponseEntity.status(HttpStatus.CREATED).body(historiaService.save(historia));
	}

	/**
	 * Obtiene una Historia por su ID
	 * 
	 * @param histId
	 * @return si no encuentra la historia, 200 y la historia si la encuentra
	 */
	@ApiOperation(value = "Obtener una Historia por su ID", notes = "Provee un mecanismo para obtener todos los datos de una Historia por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Historia.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> read(
			@ApiParam(value = "ID de la historia", required = true, type = "long") @PathVariable(value = "id") Long histId) {
		Optional<Historia> oHist = historiaService.findById(histId);

		if (!oHist.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oHist);
	}

	/**
	 * Actualizar los datos de una Historia
	 * 
	 * @param histDetalles
	 * @param histId
	 * @return 200 Ok si la actualización ha sido correcta, 404 si no se encuentra
	 *         la Historia
	 */
	@ApiOperation(value = "Actualizar los datos de una historia", notes = "Provee la operación para actualizar una nueva Historia buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Historia.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@ApiParam(value = "Nuevos datos para la historia", type = "Historia.class") @RequestBody Historia histDetalles,
			@ApiParam(value = "ID de la historia", required = true, type = "long") @PathVariable(value = "id") Long histId) {
		Optional<Historia> historia = historiaService.findById(histId);

		if (!historia.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Calendar fecha = histDetalles.getFecha();
		fecha.add(Calendar.DATE, 1);
		historia.get().setFecha(fecha);

		historia.get().setEnfermedad(histDetalles.getEnfermedad());
		historia.get().setTratamiento(histDetalles.getTratamiento());

		return ResponseEntity.status(HttpStatus.CREATED).body(historiaService.save(historia.get()));
	}

	/**
	 * Borrar una Historia
	 * 
	 * @param histId
	 * @return 200 Ok si ha borrado correctamente, 404 si no se encuentra la
	 *         historia
	 */
	@ApiOperation(value = "Borrar una Historia", notes = "Provee la operación para borrar una Historia buscándola por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Historia.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@ApiParam(value = "ID de la Historia", required = true, type = "long") @PathVariable(value = "id") Long histId) {

		if (!historiaService.findById(histId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		historiaService.deleteById(histId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtener todas las historias
	 * 
	 * @return lista
	 */
	@ApiOperation(value = "Obtener la lista de historias", notes = "Provee un mecanismo para obtener todas las historias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public List<Historia> readAll() {
		List<Historia> historias = StreamSupport.stream(historiaService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return historias;
	}

	/**
	 * Obtiene lista de historias por el ID de su Mascota
	 * 
	 * @param id
	 * @return lista
	 */
	@ApiOperation(value = "Obtiene lista de historias por el ID de su Mascota", notes = "Provee un mecanismo para obtener todas las historias de una Mascota por el ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = "mascota_id")
	public List<Historia> findByMascotaId(
			@ApiParam(value = "ID de la Mascota", required = true, type = "long") @RequestParam(value = "mascota_id") Long id) {
		List<Historia> historias = StreamSupport.stream(historiaService.findByMascotaId(id).spliterator(), false)
				.collect(Collectors.toList());
		return historias;
	}
}
