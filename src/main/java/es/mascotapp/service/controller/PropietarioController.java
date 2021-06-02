package es.mascotapp.service.controller;

import java.util.List;
import java.util.Map;
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
import es.mascotapp.service.entity.Veterinario;
import es.mascotapp.service.service.interfaces.PropietarioService;
import es.mascotapp.service.service.interfaces.VeterinarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controladora que expone el servicio para la entidad Propietario
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

	/**
	 * Inyección de objeto PropietarioService
	 */
	@Autowired
	private PropietarioService propietarioService;

	/**
	 * Inyección de objeto VeterinarioService
	 */
	@Autowired
	private VeterinarioService vetService;

	/**
	 * Crear nuevo Propietario para un Veterinario
	 * 
	 * @param propietario
	 * @param vetId
	 * @return 400 si no se ha podido crear, 201 y el propietario si se ha creado
	 */
	@ApiOperation(value = "Crear un nuevo Propietario", notes = "Provee la operación para crear un nuevo Propietario a partir del Propietario y el ID de Veterinario que entran por parámetro, devuelve el objeto creado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Propietario.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/{id}")
	public ResponseEntity<?> create(
			@ApiParam(value = "Datos del nuevo propietario", type = "Propietario.class") @RequestBody Propietario propietario,
			@ApiParam(value = "ID del veterinario", required = true, type = "long") @PathVariable(value = "id") Long vetId) {
		Optional<Veterinario> vet = vetService.findById(vetId);

		// Busca el Propietario por Email y Dni y si ya existe envía una Bad Request
		Optional<Propietario> p = propietarioService.findByEmail(propietario.getEmail());
		Optional<Propietario> p2 = propietarioService.findByDni(propietario.getDni());

		if (!vet.isPresent() || p.isPresent() || p2.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		// Añade el propietario a la lista de propietarios del veterinario y el
		// veterinario al propietario mediante los métodos helper del modelo
		propietario.addVeterinario(vet.get());
		vet.get().addPropietario(propietario);

		return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.save(propietario));
	}

	/**
	 * Se obtiene un propietario por su ID
	 * 
	 * @param propietarioId
	 * @return 404 si no encuentra el propietario, 200 y el propietario si lo
	 *         encuentra
	 */
	@ApiOperation(value = "Obtener un propietario por su ID", notes = "Provee un mecanismo para obtener todos los datos de un propietario por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Propietario.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> read(
			@ApiParam(value = "ID del propietario", required = true, type = "long") @PathVariable(value = "id") Long propietarioId) {
		Optional<Propietario> oProp = propietarioService.findById(propietarioId);

		if (!oProp.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oProp);
	}

	/**
	 * Actualizar los datos de un Propietario
	 * 
	 * @param propDetalles  nuevos datos del Propietario
	 * @param propietarioId
	 * @return 200 Ok si la actualización ha sido correcta, 404 si no se encuentra
	 *         el propietario
	 */
	@ApiOperation(value = "Actualizar los datos de un propietario", notes = "Provee la operación para actualizar un nuevo Propietario buscándolo por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Propietario.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@ApiParam(value = "Nuevos datos para el propietario", type = "Propietario.class") @RequestBody Propietario propDetalles,
			@ApiParam(value = "ID del propietario", required = true, type = "long") @PathVariable(value = "id") Long propietarioId) {
		Optional<Propietario> propietario = propietarioService.findById(propietarioId);

		if (!propietario.isPresent()) {
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

	/**
	 * Borrar un Propietario
	 * 
	 * @param propietarioId
	 * @return 200 Ok si ha borrado correctamente, 404 si no se encuentra el
	 *         propietario
	 */
	@ApiOperation(value = "Borrar un propietario", notes = "Provee la operación para borrar un Propietario buscándolo por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Propietario.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@ApiParam(value = "ID del propietario", required = true, type = "long") @PathVariable(value = "id") Long propietarioId) {

		if (!propietarioService.findById(propietarioId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		propietarioService.deleteById(propietarioId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtener todos los propietarios
	 * 
	 * @return lista
	 */
	@ApiOperation(value = "Obtener la lista de propietarios", notes = "Provee un mecanismo para obtener todos los propietarios")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public List<Propietario> readAll() {
		List<Propietario> propietarios = StreamSupport.stream(propietarioService.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return propietarios;
	}

	/**
	 * Se obtiene lista de propietarios por ID de Veterinario
	 * 
	 * @param id
	 * @return 404 si no encuentra el Veterinario, 200 y la lista de propietarios si
	 *         lo encuentra
	 */
	@ApiOperation(value = "Obtiene lista de propietarios por ID de Veterinario", notes = "Provee un mecanismo para obtener todos los propietarios de un Veterinario por el ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(params = "vet_id")
	public List<Propietario> findByVeterinario(
			@ApiParam(value = "ID del veterinario", required = true, type = "long") @RequestParam(value = "vet_id") Long id) {
		List<Propietario> propietarios = StreamSupport
				.stream(propietarioService.findByVeterinarioId(id).spliterator(), false).collect(Collectors.toList());

		return propietarios;
	}

	/**
	 * Obtiene un propietario por su DNI y el ID de su Veterinario
	 * 
	 * @param propietarioDni
	 * @param vet_id
	 * @return 404 si no encuentra el Propietario, 200 y los datos del Propietario
	 *         si lo encuentra
	 */
	@GetMapping(params = { "dni", "vet_id" })
	public ResponseEntity<?> readByDni(@RequestParam(value = "dni") String propietarioDni,
			@RequestParam(value = "vet_id") Long vet_id) {
		Optional<Propietario> oProp = propietarioService.findByDniAndVeterinarioId(propietarioDni, vet_id);

		if (!oProp.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oProp);
	}

	/**
	 * Petición de inicio de sesión en aplicación cliente para Propietario
	 * 
	 * @param login HashMap con las credenciales de acceso
	 * @return 404 si no es correcto, 200 y los datos del Propietario con esas
	 *         credenciales
	 */
	@ApiOperation(value = "Petición de inicio de sesión en aplicación cliente para Propietario", notes = "Provee la operación para comprobar que las credenciales que entran por parámetro son correctas y devolver los datos del Propietario")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Propietario.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(path = "/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> login) {
		String correo = login.get("email");
		String password = login.get("password");

		Optional<Propietario> oProp = propietarioService.findByEmailAndPassword(correo, password);

		if (!oProp.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(oProp);
		}
	}

}
