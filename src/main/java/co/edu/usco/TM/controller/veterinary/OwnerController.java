package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import co.edu.usco.TM.service.auth.UserDetailsService;
import co.edu.usco.TM.service.toImpl.IContactService;
import co.edu.usco.TM.service.toImpl.IOwnerService;
import co.edu.usco.TM.service.toImpl.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;

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

@RestController
@RequestMapping("/api/owner")
@Tag(name = "Owners", description = "Endpoints para los usuarios que sean o quieran ser Dueños")
public class OwnerController {

    @Autowired
    IOwnerService ownerService;

    @Autowired
    IUserService userService;

    @Autowired
    IContactService contactService;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Crear un dueño", description = "Ingresa datos válidos para los campos requeridos")
    private ResponseEntity<?> save(
            @Parameter(description = "Información básica para crear un dueño")
            @Valid @RequestPart("owner") ReqUserDTO ownerDTO,
            @Parameter(description = "Posible imágen de perfil")
            @RequestPart(name = "image", required = false) MultipartFile file) throws IOException {

        ResUserDTO response;
        response = ownerService.save(ownerDTO, file, false, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find")
    private ResponseEntity<PageResponse<ResUserDTO>> findFilteredOwners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ResUserDTO> ownersPage = userService.findAllUsers(name, username, email, "OWNER", pageable, null, null);
        PageResponse<ResUserDTO> pageResponse = new PageResponse<>(ownersPage);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<ResUserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.findById(id));
    }

    @GetMapping("/details")
    @Operation(summary = "Obtener Detalles de un Dueño", description = "Obtiene los detalles de un Dueño Logeado en base a su token.")
    private ResponseEntity<ResUserDTO> getOwnerDetails() {

        Long ownerID = userDetailsService.getAuthenticatedUserID();
        ResUserDTO ownerDetails = ownerService.findById(ownerID);

        return ResponseEntity.ok(ownerDetails);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Actualizar datos de un Dueño", description = "Obtiene el Token del dueño logeado," +
            "así como sus nuevos datos a actualizar. El token es para extraer su ID e insertarselo al nuevo modelo " +
            "de Dueño que se solicita actualizar")
    private ResponseEntity<ResUserDTO> updateOwnerDetails(
            @Parameter(description = "Información para actualizar datos del Dueño.")
            @RequestPart("owner") ReqUserDTO ownerDTO,
            @Parameter(description = "Posible Imágen a actualizar")
            @RequestPart(name = "image", required = false) MultipartFile image,
            @Parameter(description = "Decide si quieres eliminar la imagen de tu usuario o no, por defecto no se hará nada")
            @RequestParam(name = "deleteImage", required = false) boolean deleteImg) throws IOException {

        Long ownerID = userDetailsService.getAuthenticatedUserID();
        ResUserDTO response = ownerService.save(ownerDTO, image, deleteImg, ownerID);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/disable")
    private ResponseEntity<ResUserDTO> disableOwner() {
        Long ownerID = userDetailsService.getAuthenticatedUserID();
        return ResponseEntity.ok(ownerService.disableOwner(ownerID));
    }

    @PostMapping("/contact/add/{id}")
    private ResponseEntity<ResContactDTO> addContact(@PathVariable("id") Long vetID) {
        Long ownerID = userDetailsService.getAuthenticatedUserID();
        return ResponseEntity.ok(contactService.createContact(ownerID, ownerID, vetID));
    }

    @GetMapping("/contact/list")
    private ResponseEntity<PageResponse<ResContactDTO>> listContacts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String veterinary,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Long ownerID = userDetailsService.getAuthenticatedUserID();

        Page<ResContactDTO> vetsPage = contactService.getOwnerContacts(ownerID, status, name, username, email, veterinary, specialty, pageable);
        PageResponse<ResContactDTO> pageResponse = new PageResponse<>(vetsPage);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/contact/list/all")
    private ResponseEntity<List<ResVetDTO>> listAllContacts() {

        Long ownerID = userDetailsService.getAuthenticatedUserID();

        return ResponseEntity.ok(contactService.getAllOwnerContacts(ownerID));
    }

    @DeleteMapping("/contact/remove/{id}")
    private ResponseEntity<ResContactDTO> removeContact(@PathVariable("id") Long vetID) {
        Long ownerID = userDetailsService.getAuthenticatedUserID();
        return ResponseEntity.ok(contactService.deleteContact(ownerID, vetID));
    }

}