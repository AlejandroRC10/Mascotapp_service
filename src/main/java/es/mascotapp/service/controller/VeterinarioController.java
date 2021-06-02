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
import org.springframework.web.bind.annotation.RestController;

import es.mascotapp.service.entity.Veterinario;
import es.mascotapp.service.service.interfaces.VeterinarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controladora que expone el servicio para la entidad Veterinario
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 *
 */
@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

	/**
	 * Inyección de objeto VeterinarioService
	 */
	@Autowired
	private VeterinarioService veterinarioService;

	/**
	 * Crear nuevo Veterinario
	 * 
	 * @param veterinario
	 * @return 400 si no se ha podido crear, 201 y el veterinario si se ha creado
	 */
	@ApiOperation(value = "Crear un nuevo Veterinario", notes = "Provee la operación para crear un nuevo Veterinario a partir del Veterinario que entra por parámetro y devuelve el objeto creado")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Veterinario.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping
	public ResponseEntity<?> create(
			@ApiParam(value = "Datos del nuevo veterinario", type = "Veterinario.class") @RequestBody Veterinario veterinario) {
		Optional<Veterinario> oVet = veterinarioService.findByUsuario(veterinario.getUsuario());
		Optional<Veterinario> oVet2 = veterinarioService.findByNumColegiado(veterinario.getNum_colegiado());

		if (oVet.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}

		if (oVet2.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.save(veterinario));
	}

	/**
	 * Se obtiene un veterinario por su ID
	 * 
	 * @param id
	 * @return 404 si no encuentra el veterinario, 200 y el veterinario si lo
	 *         encuentra
	 */
	@ApiOperation(value = "Obtener un veterinario por su ID", notes = "Provee un mecanismo para obtener todos los datos de un veterinario por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Veterinario.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> read(
			@ApiParam(value = "ID del veterinario", required = true, type = "long") @PathVariable(value = "id") Long vetId) {
		Optional<Veterinario> oVet = veterinarioService.findById(vetId);

		if (!oVet.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oVet);
	}

	/**
	 * Actualizar los datos de un Veterinario
	 * 
	 * @param veterinario
	 * @param id
	 * @return 200 Ok si la actualización ha sido correcta, 404 si no se encuentra
	 *         el veterinario
	 */
	@ApiOperation(value = "Actualizar los datos de un veterinario", notes = "Provee la operación para actualizar un nuevo Veterinario buscándolo por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Veterinario.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@ApiParam(value = "Nuevos datos para el veterinario", type = "Veterinario.class") @RequestBody Veterinario vetDetalles,
			@ApiParam(value = "ID del veterinario", required = true, type = "long") @PathVariable(value = "id") Long vetId) {
		Optional<Veterinario> veterinario = veterinarioService.findById(vetId);

		if (!veterinario.isPresent()) {
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

	/**
	 * Borrar un Veterinario
	 * 
	 * @param id
	 * @return 200 Ok si ha borrado correctamente, 404 si no se encuentra el
	 *         veterinario
	 */
	@ApiOperation(value = "Borrar un veterinario", notes = "Provee la operación para borrar un Veterinario buscándolo por su ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Veterinario.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@ApiParam(value = "ID del veterinario", required = true, type = "long") @PathVariable(value = "id") Long vetId) {

		if (!veterinarioService.findById(vetId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		veterinarioService.deleteById(vetId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtener todos los veterinarios
	 * 
	 * @return lista
	 */
	@ApiOperation(value = "Obtener la lista de veterinarios", notes = "Provee un mecanismo para obtener todos los veterinarios")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public List<Veterinario> readAll() {
		List<Veterinario> veterinarios = StreamSupport.stream(veterinarioService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return veterinarios;
	}

	/**
	 * Petición de inicio de sesión en aplicación cliente para Veterinario
	 * 
	 * @param login HashMap con las credenciales de acceso
	 * @return 404 si no es correcto, 200 y los datos del Veterinario con esas
	 *         credenciales
	 */
	@ApiOperation(value = "Petición de inicio de sesión en aplicación cliente para Veterinario", notes = "Provee la operación para comprobar que las credenciales que entran por parámetro son correctas y devolver los datos del Veterinario")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Veterinario.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(path = "/login")
	public ResponseEntity<?> login(
			@ApiParam(value = "Credenciales del veterinario", required = true, type = "HashMap") @RequestBody Map<String, String> login) {
		String usuario = login.get("usuario");
		String password = login.get("password");

		Optional<Veterinario> oVet = veterinarioService.findByUsuarioAndPassword(usuario, password);

		if (!oVet.isPresent()) {
			System.out.println("usuario--------->> " + usuario);
			System.out.println("usuario--------->> " + password);
			System.out.println("log--------->> " + oVet.toString());
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oVet);
	}

}
