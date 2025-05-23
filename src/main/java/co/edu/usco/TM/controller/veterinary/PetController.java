package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.service.auth.UserDetailsService;
import co.edu.usco.TM.service.toImpl.IPetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    IPetService petService;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Registrar una mascota", description = "Ingresa datos válidos para los campos requeridos")
    private ResponseEntity<?> save(
            @Parameter(description = "Información para registrarla mascota")
            @Valid @RequestPart("pet") ReqPetDTO petDTO,
            @Parameter(description = "Imágen ilustrativa de la mascota")
            @RequestPart(name = "file", value = "file", required = false) MultipartFile image) throws IOException {

        Long ownerID = userDetailsService.getAuthenticatedUserID();
        ResPetDTO response = petService.save(petDTO, ownerID, image, false, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find")
    private ResponseEntity<PageResponse<ResPetDTO>> findOwnerPets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specie,
            @RequestParam(required = false) Integer months,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Long ownerID = userDetailsService.getAuthenticatedUserID();
        Page<ResPetDTO> petsPage = petService.findFilteredPets(ownerID, name, specie, months, pageable);
        PageResponse<ResPetDTO> pageResponse = new PageResponse<>(petsPage);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/find/{id}")
    private ResponseEntity<ResPetDTO> find(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }

    @PutMapping(value = "/update/{id}")
    private ResponseEntity<ResPetDTO> update(
            @Valid @RequestPart("pet") ReqPetDTO petDTO,
            @PathVariable Long id,
            @RequestPart(name = "file", value = "file", required = false) MultipartFile image,
            @Parameter(description = "Decide si quieres eliminar la imagen de tu usuario o no, por defecto no se hará nada")
            @RequestParam(name = "deleteImage", required = false) boolean deleteImg) throws IOException {

        Long authenticatedUserID = userDetailsService.getAuthenticatedUserID();
        verifyOwnerID(authenticatedUserID, petService.findById(id).getOwner().getId());

        ResPetDTO response = petService.save(petDTO, authenticatedUserID, image, deleteImg, id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/disable/{id}")
    private ResponseEntity<ResPetDTO> disable(@PathVariable Long id) {
        Long authenticatedUserID = userDetailsService.getAuthenticatedUserID();
        verifyOwnerID(authenticatedUserID, petService.findById(id).getOwner().getId());

        return ResponseEntity.ok(petService.disablePet(id));
    }

    private void verifyOwnerID(Long authenticatedUserID, Long ownerID) {

        if (!authenticatedUserID.equals(ownerID)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

}
