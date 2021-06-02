package es.mascotapp.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Clase que permite la generación de documentación de la aplicación con Swagger
 * 
 * @author Alejandro Rodríguez Campiñez
 * @version 2021/05/30
 */
@Configuration 
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * Selecciona el paquete que se va a documentar en Swagger y construye la documentación
	 * @return documentación
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(
						RequestHandlerSelectors
						.basePackage("es.mascotapp.service.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	/**
	 * Crea la información de cabecera de la documentación
	 * @return documentación
	 */
	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API REST Mascotapp")
				.description("Api que permite llevar a cabo la gestión de citas, vacunaciones, historias y desparasitaciones de mascotas, por parte del veterinario."
						+ "\nRealiza operaciones CRUD a una base de datos MySql, con las entidades:\tVETERINARIO - PROPIETARIO - MASCOTA - VACUNA - CITA - DESPARASITACION"
						+ "\nSe pueden realizar peticiones a este servicio a través de los métodos POST - PUT - GET - DELETE del protocolo HTTP"
						+ "\n\nHaz click en el -controller- de cada entidad para conocer todas las operaciones que se pueden realizar")
				.version("1.0")
				.contact(new Contact("Alejandro Rodríguez Campiñez", "arodriguezc21@iesalbarregas.com",""))
				.build();
	}

}
