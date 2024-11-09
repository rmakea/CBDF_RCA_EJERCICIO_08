package com.upiiz.relaciones.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Torneo",
                description = "Esta API proporciona acceso a los recursos del Torneo",
                version = "1.0.0",
                contact = @Contact(
                        name = "Raul Cardoso Acevedo",
                        email = "rcardosoa2100@alumno.ipn.mx",
                        url = "http://localhost:8080/contacto"
                ),
                license = @License(),
                termsOfService = "Solo se permiten 400 solicitudes"
        ),
        servers = {
                @Server(
                        description = "Servidor de pruebas",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de Produccion",
                        url = "https://cbdf-rca-ejercicio-08.onrender.com"//Poner el link del render
                )
        },
        tags = {
                @Tag(
                        name = "Equipo",
                        description = "EndPoint de los recursos"
                ),
                @Tag(
                        name = "Entrenador",
                        description = "EndPoint para los recursos de Entrenador"
                ),
                @Tag(
                        name = "Competencia",
                        description = "EndPoint para los recursos de Competencia"
                ),
                @Tag(
                        name = "Jugador",
                        description = "EndPoint para los recursos de Jugador"
                ),
                @Tag(
                        name = "Liga",
                        description = "EndPoint para los recursos de Liga"
                )
        }
)
public class OpenApiConfiguration {
}