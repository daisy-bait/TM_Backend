package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import co.edu.usco.TM.service.auth.UserDetailsService;
import co.edu.usco.TM.service.noImpl.IOwnerService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/owner")
@Tag(name = "Owners", description = "Endpoints para los usuarios que sean o quieran ser Dueños")
public class OwnerController {

    private static final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    @Autowired
    IOwnerService ownerService;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Crear un dueño", description = "Ingresa datos válidos para los campos requeridos")
    private ResponseEntity<?> save(
            @Parameter(description = "Información básica para crear un dueño")
            @Valid @RequestPart("owner") ReqOwnerDTO ownerDTO,
            @Parameter(description = "Posible imágen de perfil")
            @RequestPart(name = "file", value = "file", required = false) MultipartFile file) throws IOException {
        ResOwnerDTO response;

        if (file != null && !file.isEmpty()) {
            response = ownerService.uploadWithImage(ownerDTO, file, null);
        } else {
            response = ownerService.save(ownerDTO, null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find")
    private ResponseEntity<List<ResOwnerDTO>> findAll() {

        return ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/details")
    @Operation(summary = "Obtener Detalles de un Dueño", description = "Obtiene los detalles de un Dueño Logeado en base a su token.")
    private ResponseEntity<ResOwnerDTO> getOwnerDetails() {
        try {

            // Extract Owner ID from the Decoded Token
            Long ownerID = userDetailsService.getAuthenticatedUserID();

            // Obtain the specific Owner Data
            ResOwnerDTO ownerDetails = ownerService.basicDetails(ownerID);

            if (ownerDetails != null) {
                return ResponseEntity.ok(ownerDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (JWTVerificationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Actualizar datos de un Dueño", description = "Obtiene el Token del dueño logeado," +
            "así como sus nuevos datos a actualizar. El token es para extraer su ID e insertarselo al nuevo modelo " +
            "de Dueño que se solicita actualizar")
    private ResponseEntity<ResOwnerDTO> updateOwnerDetails(
            @Parameter(description = "Token necesario para validar la sesión del usuario, crea uno en /login")
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Información para actualizar datos del Dueño.")
            @RequestPart("owner") ReqOwnerDTO ownerDTO,
            @Parameter(description = "Posible Imágen a actualizar")
            @RequestPart(name = "file", value = "file", required = false) MultipartFile file) throws IOException {

        Long ownerID = userDetailsService.getAuthenticatedUserID();

        logger.info("URL IMAGEN: ", ownerDTO.getImgURL());

        ResOwnerDTO response;

        if (file != null && !file.isEmpty()) {
            response = ownerService.uploadWithImage(ownerDTO, file, ownerID);
        } else {
            response = ownerService.save(ownerDTO, ownerID);
        }

        return ResponseEntity.ok(response);
    }

}
