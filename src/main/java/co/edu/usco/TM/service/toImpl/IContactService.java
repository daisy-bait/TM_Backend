package co.edu.usco.TM.service.toImpl;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ContactDTO;
import co.edu.usco.TM.persistence.entity.veterinary.Contact;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContactService {

    public ContactDTO createContact(Long originID, Long ownerID, Long vetID) throws EntityNotFoundException;

    public Page<ResVetDTO> getOwnerContacts(
            Long ownerID,
            String status,
            String name,
            String username,
            String email,
            String veterinary,
            String specialty,
            Pageable pageable);

    public Page<ResUserDTO> getVetContacts(
            Long vetID,
            String status,
            String name,
            String username,
            String email,
            Pageable pageable);

    public ContactDTO deleteContact(Long ownerID, Long vetID) throws EntityNotFoundException;

}
