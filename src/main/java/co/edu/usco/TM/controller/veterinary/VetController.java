package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import co.edu.usco.TM.service.auth.UserDetailsService;
import co.edu.usco.TM.service.toImpl.IContactService;
import co.edu.usco.TM.service.toImpl.IUserService;
import co.edu.usco.TM.service.toImpl.IVetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/vet")
@Tag(name = "Veterinarians", description = "Endpoints para los usuarios que sean o quieran ser Veterinarios")
public class VetController {

    @Autowired
    private IVetService vetService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IContactService contactService;

    @Autowired
    UserDetailsService userDetailsService;

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
        response = vetService.save(vetDTO, image, degree, false, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find")
    private ResponseEntity<PageResponse<ResUserDTO>> findFilteredVets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String veterinary,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ResUserDTO> vetsPage = userService.findAllUsers(name, username, email, "VET", pageable, specialty, veterinary);
        PageResponse<ResUserDTO> pageResponse = new PageResponse<>(vetsPage);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<ResVetDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(vetService.findById(id));
    }

    @GetMapping(value = "/details")
    @Operation(summary = "Obtener Detalles de un Veterinario", description = "Obtiene los detalles de un Veterinario Logeado en base a su token.")
    private ResponseEntity<ResVetDTO> getVetDetails() {

        Long vetID = userDetailsService.getAuthenticatedUserID();
        ResVetDTO vetDetails = vetService.findById(vetID);

        return ResponseEntity.ok(vetDetails);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Actualizar datos de un Veterinario", description = "Obtiene el Token del veterinario logeado," +
            "así como sus nuevos datos a actualizar. El token es para extraer su ID e insertarselo al nuevo modelo " +
            "de Veterinario que se solicita actualizar")
    private ResponseEntity<ResVetDTO> updateVetDetails(
            @Parameter(description = "Información para actualizar datos del Veterinario.")
            @RequestPart("vet") ReqVetDTO vetDTO,
            @Parameter(description = "Posible Imágen a actualizar")
            @RequestPart(name = "image", value = "image", required = false) MultipartFile image,
            @Parameter(description = "Posible diploma a actualizar")
            @RequestPart(name = "degree", value = "degree", required = false) MultipartFile degree,
            @Parameter(description = "Decide si quieres eliminar la imagen de tu usuario o no, por defecto no se hará nada")
            @RequestParam(name = "deleteImage", required = false) boolean deleteImg) throws IOException {

        Long vetID = userDetailsService.getAuthenticatedUserID();
        ResVetDTO response = vetService.save(vetDTO, image, degree, deleteImg, vetID);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/disable")
    private ResponseEntity<ResVetDTO> disableVet() {
        Long vetID = userDetailsService.getAuthenticatedUserID();
        return ResponseEntity.ok(vetService.disableVet(vetID));
    }

    @PostMapping("/contact/add/{id}")
    private ResponseEntity<ResContactDTO> addContact(@PathVariable("id") Long ownerID) {
        Long vetID = userDetailsService.getAuthenticatedUserID();
        return ResponseEntity.ok(contactService.createContact(vetID, ownerID, vetID));
    }

    @GetMapping("/contact/list")
    private ResponseEntity<PageResponse<ResContactDTO>> listContacts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Long vetID = userDetailsService.getAuthenticatedUserID();

        Page<ResContactDTO> ownersPage = contactService.getVetContacts(vetID, status, name, username, email, pageable);
        PageResponse<ResContactDTO> ownersResponse = new PageResponse<>(ownersPage);

        return ResponseEntity.ok(ownersResponse);
    }

    @GetMapping("/contact/list/all")
    private ResponseEntity<List<ResUserDTO>> listAllContacts() {
        Long vetID = userDetailsService.getAuthenticatedUserID();

        return ResponseEntity.ok(contactService.getAllVetContacts(vetID));
    }

    @DeleteMapping("/contact/remove/{id}")
    private ResponseEntity<ResContactDTO> removeContact(@PathVariable("id") Long ownerID) {
        Long vetID = userDetailsService.getAuthenticatedUserID();

        return ResponseEntity.ok(contactService.deleteContact(ownerID, vetID));
    }

}