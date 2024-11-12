package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.service.noImpl.IPetService;
import co.edu.usco.TM.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/owner/pet")
public class PetController {

    @Autowired
    IPetService petService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Registrar una mascota", description = "Ingresa datos válidos para los campos requeridos")
    private ResponseEntity<?> save(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Información para registrarla mascota")
            @Valid @RequestPart("pet") ReqPetDTO petDTO,
            @Parameter(description = "Imágen ilustrativa de la mascota")
            @RequestPart(name = "file", value = "file", required = false) MultipartFile file) throws IOException {

        Long ownerID = jwtUtil.getUserID(token);

        ResPetDTO response;

        if (file != null && !file.isEmpty()) {
            response = petService.save(petDTO, ownerID, file);
        } else {
            response = petService.save(petDTO, ownerID, null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
