
package co.edu.usco.TM.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import java.util.ArrayList;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
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

        )
)
@Configuration
public class OpenApiConfig {
    
    public OpenApiConfig(MappingJackson2HttpMessageConverter converter) {
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }
    
}
