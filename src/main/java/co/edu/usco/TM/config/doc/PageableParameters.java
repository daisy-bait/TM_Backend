package co.edu.usco.TM.config.doc;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

@Parameters({
        @Parameter(name = "page", description = "Número de página para la consulta", example = "0"),
        @Parameter(name = "size", description = "Tamaño de página para la consulta", example = "10")
})
public @interface PageableParameters {
}
