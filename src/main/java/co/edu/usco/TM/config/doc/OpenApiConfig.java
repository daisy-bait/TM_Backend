
package co.edu.usco.TM.config.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import java.util.ArrayList;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@OpenAPIDefinition(
        info = @Info(
                title = "T&M RESTful API",
                description = "Esta Interfaz de programación de aplicaciones provee de un sistema de CRUD básico" +
                        " para los usuarios de T&M, desde Veterinarios, hasta Dueños, abarcando el registro de " +
                        "mascotas, relación entre dueños y como no puede faltar, un endpoint exclusivo para la " +
                        "generación de tokens para la cabecera de las peticiones HTTP (Header \"Authorization\"), " +
                        "permite un sistema de agendacmiento de citas, con validaciones, paginación mediante pageable," +
                        " sistema de filtros para peticiones GET y un endpoint para listar usuarios de forma básica y sencilla",
                version = "SNAPSHOT-0.0.1",
                contact = @Contact(
                        name = "Kaleth Daniel Narváez Paredes",
                        url = "https://github.com/kadanarpa",
                        email = "u20231213624@usco.edu.co"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        name = "Security Token",
        description = "Token de Acceso para la API, generalo en /api/auth el método POST de login",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class OpenApiConfig {
    
    public OpenApiConfig(MappingJackson2HttpMessageConverter converter) {
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }
    
}
