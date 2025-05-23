package co.edu.usco.TM.service.veterinary;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import co.edu.usco.TM.persistence.entity.user.UserEntity;
import co.edu.usco.TM.persistence.entity.veterinary.Contact;
import co.edu.usco.TM.persistence.entity.user.Veterinarian;
import co.edu.usco.TM.persistence.repository.ContactRepository;
import co.edu.usco.TM.persistence.repository.UserRepository;
import co.edu.usco.TM.persistence.repository.VeterinarianRepository;
import co.edu.usco.TM.service.toImpl.IContactService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <h1>ContactService</h1>
 * Servicio encargado de manejar la lógica de negocio relacionada con las relaciones de contacto
 * entre dueños de mascotas y veterinarios en la aplicación.
 *
 * <p>Implementa la interfaz {@link IContactService} para proporcionar operaciones CRUD y consultas
 * especializadas relacionadas con los contactos, como la creación de nuevas relaciones, la obtención de
 * contactos con filtros y la actualización de su estado.</p>
 *
 * <h2>Características principales:</h2>
 * <ul>
 *     <li><b>Creación de contactos:</b> Permite crear relaciones entre dueños y veterinarios.</li>
 *     <li><b>Obtención de contactos filtrados:</b> Admite la búsqueda con filtros avanzados como estado,
 *     nombre, especialidad y datos asociados tanto al dueño como al veterinario.</li>
 *     <li><b>Actualización de contactos:</b> Modifica el estado o atributos específicos de las relaciones existentes.</li>
 * </ul>
 *
 * <h2>Colaboraciones:</h2>
 * Esta clase depende de los siguientes componentes:
 * <ul>
 *     <li>{@link ContactRepository}: Para realizar operaciones de acceso a la base de datos sobre la entidad Contact.</li>
 *     <li>{@link UserRepository} y {@link VeterinarianRepository}: Para validar y recuperar entidades relacionadas.</li>
 *     <li>{@link ModelMapper}: Para mapear entidades a DTOs y viceversa, facilitando la transferencia de datos entre capas.</li>
 * </ul>
 *
 * <h2>Uso típico:</h2>
 * Esta clase es utilizada principalmente por controladores para procesar solicitudes relacionadas con los relaciones
 * establecidas entre los usuarios de la aplicacion ya registrados en la base de datos, ciertas operacioens no requieren
 * de comprobar en sí mismas si el usuario existe por el ID que se obtiene como parámetro, puesto que este será proporcionado
 * por el servicio de autenticación {@link co.edu.usco.TM.service.auth.UserDetailsService}, el cual se encarga de verificar
 * inmediatamente la existencia del usuario mediante el contexto de Spring Security ({@link org.springframework.security.core.context.SecurityContextHolder}).
 * Proporciona métodos que encapsulan la lógica de negocio, dejando la manipulación directa de datos al repositorio.
 *
 * <h2>Anotaciones importantes:</h2>
 * <ul>
 *     <li><b>@Service:</b> Indica que esta clase es un componente de servicio dentro del contexto de Spring,
 *     gestionado automáticamente como un bean.</li>
 * </ul>
 *
 * @see IContactService
 * @see ContactRepository
 * @see UserRepository
 * @see VeterinarianRepository
 */
@Service
public class ContactService implements IContactService {

    @Autowired
    ContactRepository contactRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    VeterinarianRepository vetRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * <h3>Crear Contacto</h3>
     * Operación encargada de verificar la existencia de usuarios a relacionar así como de instanciar dicha relación
     * mediante una instancia de la entidad {@link Contact}
     *
     * <p><b>Uso Esperado:</b></p> Mediante {@link co.edu.usco.TM.service.auth.UserDetailsService} se verifica el ID del
     * usuario logueado y se envía como parámetro del método en el servicio instanciado del controlador, la posición del
     * ID puede variar entre el primer y segundo parámetro, por lo que es necesario verificar que ambos usuarios existan
     * y lanzar la excepción esperada en caso de no hacerlo.
     *
     * @param ownerID ID del Dueño que quiere iniciar iniciar una relación o al que se quiere enviar una solicitud
     * @param vetID   ID del Veterinario que quiere iniciar iniciar una relación o al que se quiere enviar una solicitud
     * @return Contact Entity para visualizar los atributos de la entidad recién creada e insertada en la base de datos
     * @throws EntityNotFoundException Si el usuario con el que se quiere crear una relación no existe como entidad
     */
    @Override
    public ResContactDTO createContact(Long originID, Long ownerID, Long vetID) throws EntityNotFoundException {

        Contact contact = new Contact();

        UserEntity owner = userRepo.findOwnerById(ownerID)
                .orElseThrow(() -> new EntityNotFoundException());
        Veterinarian vet = vetRepo.findById(vetID)
                .orElseThrow(() -> new EntityNotFoundException());

        if(ownerID.equals(vetID)) throw new IllegalStateException("contact.sameID.exception");

        Optional<Contact> optContact = contactRepo.verifyContact(ownerID, vetID);

        if (optContact.isPresent()) {
            contact = optContact.get();
            if (contact.getStatus().equals("ACCEPTED")) {
                throw new IllegalStateException("contact.exist.exception");
            } else if (contact.getOrigin().equals(originID)) {
                throw new IllegalStateException("contact.sent.exception");
            } else {
                contact.setStatus("ACCEPTED");
            }
        } else {
            contact = Contact.builder().origin(originID).owner(owner).vet(vet)
                    .status("PENDING").created_at(LocalDateTime.now()).build();
        }

        contactRepo.save(contact);

        return modelMapper.map(contact, ResContactDTO.class);
    }

    /**
     * <h3>Obtener contactos de un Dueño</h3>
     * Obtiene una lista paginada de veterinarios relacionados con un dueño de mascotas específico,
     * aplicando filtros opcionales como estado de relación, nombre, nombre de usuario, veterinaria y especialidad.
     *
     * <p><b>Proceso de transformación:</b></p>
     * <ul>
     *     <li><b>ownerContactsPage.stream():</b> Convierte la página de contactos devuelta por {@code findOwnerContacts}
     *     en un flujo para procesar cada instancia de {@code Contact}.</li>
     *
     *     <li><b>Mapeo de Contact a ResVetDTO:</b> Cada objeto {@code Contact} se transforma en su veterinario relacionado
     *     ({@code Vet}), que posteriormente se mapea a un objeto {@code ResVetDTO} mediante {@code modelMapper}.</li>
     *
     *     <li><b>PageImpl<>:</b> Para conservar la estructura de paginación, se crea una nueva instancia de {@code PageImpl}
     *     que contiene los objetos transformados ({@code ResVetDTO}) y conserva los metadatos de la paginación original.</li>
     * </ul>
     *
     * @param ownerID    ID del dueño de mascotas cuyos contactos se quieren recuperar.
     * @param status     (Opcional) Estado del contacto para filtrar (por ejemplo, "accepted" o "pending").
     * @param name       (Opcional) Filtro por nombre del veterinario.
     * @param username   (Opcional) Filtro por nombre de usuario del veterinario.
     * @param veterinary (Opcional) Filtro por nombre de la veterinaria asociada.
     * @param specialty  (Opcional) Filtro por especialidad del veterinario.
     * @param pageable   Objeto de paginación que define el tamaño y la página actual del resultado.
     * @return Una instancia de {@link Page} que contiene los veterinarios relacionados con el dueño en forma de {@code ResVetDTO}.
     */
    @Override
    public Page<ResContactDTO> getOwnerContacts(
            Long ownerID,
            String status,
            String name,
            String username,
            String email,
            String veterinary,
            String specialty,
            Pageable pageable) {

        Page<Contact> ownerContacts = contactRepo.findUserContacts(ownerID, null, status, name, username, email, specialty, veterinary, pageable);

        List<ResContactDTO> referencedVets = ownerContacts
                .stream()
                .map(contact -> {
                    ResContactDTO dto = new ResContactDTO();
                    dto.setOriginID(contact.getOrigin());
                    dto.setStatus(contact.getStatus());
                    dto.setUser(modelMapper.map(contact.getVet(), ResVetDTO.class));
                    return dto;
                }).collect(Collectors.toList());

        return new PageImpl<>(referencedVets, pageable, ownerContacts.getTotalElements());

    }

    /**
     * <h3>Obtener Contactos de un Veterinario</h3>
     * Recupera una lista paginada de dueños de mascotas que están asociados a un veterinario específico.
     * Los resultados pueden filtrarse por diferentes criterios como estado de la relación, nombre, nombre de usuario
     * o correo electrónico, y están organizados en una estructura de paginación.
     *
     * <p><b>Proceso:</b></p>
     * <ul>
     *     <li>Consulta las relaciones existentes en la base de datos entre el veterinario especificado y los dueños relacionados.</li>
     *     <li>Transforma cada contacto en una instancia de {@link ResUserDTO} usando {@code ModelMapper}.</li>
     *     <li>Devuelve una página que conserva los metadatos de paginación original.</li>
     * </ul>
     *
     * @param vetID    ID del veterinario cuyos contactos (dueños de mascotas) se desean consultar.
     * @param status   (Opcional) Filtro por estado del contacto (por ejemplo, "accepted", "pending").
     * @param name     (Opcional) Filtro por nombre del dueño de mascotas.
     * @param username (Opcional) Filtro por nombre de usuario del dueño.
     * @param email    (Opcional) Filtro por correo electrónico del dueño.
     * @param pageable Parámetros de paginación que definen la página actual y el tamaño del resultado.
     * @return Una página de objetos {@link ResUserDTO}, representando a los dueños de mascotas relacionados.
     */
    @Override
    public Page<ResContactDTO> getVetContacts(
            Long vetID,
            String status,
            String name,
            String username,
            String email,
            Pageable pageable) {

        Page<Contact> vetContacts = contactRepo.findUserContacts(null, vetID, status, name, username, email, null, null, pageable);

        List<ResContactDTO> referencedOwners = vetContacts
                .stream()
                .map(contact -> {
                    ResContactDTO dto = new ResContactDTO();
                    dto.setOriginID(contact.getOrigin());
                    dto.setStatus(contact.getStatus());
                    dto.setUser(modelMapper.map(contact.getOwner(), ResUserDTO.class));
                    return dto;
                }).collect(Collectors.toList());

        return new PageImpl<>(referencedOwners, pageable, vetContacts.getTotalElements());
    }

    @Override
    public List<ResVetDTO> getAllOwnerContacts(Long ownerID) {

        List<Contact> contacts = contactRepo.findAllOwnerContacts(ownerID);

        return contacts.stream()
                .map(contact -> modelMapper.map(contact.getVet(), ResVetDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<ResUserDTO> getAllVetContacts(Long vetID) {

        List<Contact> contacts = contactRepo.findAllVetContacts(vetID);

        return contacts.stream()
                .map(contact -> modelMapper.map(contact.getOwner(), ResUserDTO.class))
                .collect(Collectors.toList());

    }


    /**
     * <h3>Eliminar Contacto</h3>
     * Elimina una relación existente entre un dueño de mascotas y un veterinario, identificados por sus IDs.
     * Esta operación valida que los IDs proporcionados no sean iguales y que la relación exista previamente.
     *
     * <p><b>Flujo de trabajo:</b></p>
     * <ul>
     *     <li>Verifica que los IDs del dueño y el veterinario no sean iguales.</li>
     *     <li>Intenta recuperar el contacto desde la base de datos mediante {@code contactRepo.verifyContact}.</li>
     *     <li>Si el contacto existe, se elimina y se transforma en un objeto {@link ResContactDTO} para devolver como respuesta.</li>
     * </ul>
     *
     * @param ownerID ID del dueño de mascotas participante en la relación.
     * @param vetID   ID del veterinario participante en la relación.
     * @return Un {@link ResContactDTO} que representa la relación eliminada.
     * @throws EntityNotFoundException Si no existe un contacto entre el dueño y el veterinario.
     * @throws IllegalStateException   Si el ID del dueño y el veterinario son iguales.
     */
    @Override
    public ResContactDTO deleteContact(Long ownerID, Long vetID) throws EntityNotFoundException {

        if (ownerID.equals(vetID)) throw new IllegalStateException("contact.sameID.exception");

        Contact contactToDelete = contactRepo.verifyContact(ownerID, vetID).orElseThrow(() -> new EntityNotFoundException());
        contactRepo.deleteById(contactToDelete.getId());

        return modelMapper.map(contactToDelete, ResContactDTO.class);
    }

}
