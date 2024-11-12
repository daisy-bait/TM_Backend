package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.security.jwt.JwtUtil;
import co.edu.usco.TM.service.noImpl.IVetService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/vet")
@Tag(name = "Veterinarians", description = "Endpoints para los usuarios que sean o quieran ser Veterinarios")
public class VetController {

    @Autowired
    private IVetService vetService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Crear un veterinario", description = "Ingresa datos válidos para los campos requeridos")
    private ResponseEntity<?> save(
            @Parameter(description = "Información básica para crear un veterinario")
            @Valid @RequestPart("vet") ReqVetDTO vetDTO,
            @Parameter(description = "Posible imágen de perfil")
            @RequestPart(name = "image", value = "image", required = false) MultipartFile image,
            @Parameter(description = "Diploma de Grado que da constancia de sus conocimientos")
            @RequestPart(name = "degree", value = "degree", required = false) MultipartFile degree) throws IOException {

        ResVetDTO response;

        response = vetService.save(vetDTO, image, degree, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/details")
    @Operation(summary = "Obtener Detalles de un Veterinario", description = "Obtiene los detalles de un Veterinario Logeado en base a su token.")
    private ResponseEntity<ResVetDTO> getVetDetails(@RequestHeader("Authorization") String token) {
        try {

            Long vetID = jwtUtil.getUserID(token);
            ResVetDTO vetDetails = vetService.findById(vetID);

            if (vetDetails == null) {
                return ResponseEntity.ok(vetDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vetDetails);
            }
        } catch(JWTVerificationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Actualizar datos de un Veterinario", description = "Obtiene el Token del veterinario logeado," +
            "así como sus nuevos datos a actualizar. El token es para extraer su ID e insertarselo al nuevo modelo " +
            "de Veterinario que se solicita actualizar")
    private ResponseEntity<ResVetDTO> updateVetDetails(
            @Parameter(description = "Token necesario para validar la sesión del usuario, crea uno en /login")
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Información para actualizar datos del Veterinario.")
            @RequestPart("vet") ReqVetDTO vetDTO,
            @Parameter(description = "Posible Imágen a actualizar")
            @RequestPart(name = "image", value = "image", required = false) MultipartFile image,
            @Parameter(description = "Posible diploma a actualizar") MultipartFile degree) throws IOException {

        Long vetID = jwtUtil.getUserID(token);
        ResVetDTO response = vetService.save(vetDTO, image, degree, vetID);

        return ResponseEntity.ok(response);

    }
}
