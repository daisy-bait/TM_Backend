package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.persistence.entity.veterinary.Contact;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContactService {

    public Contact createContact(Long ownerID, Long vetID) throws EntityNotFoundException;

    public Page<ResVetDTO> getOwnerContacts(
            Long ownerID,
            String status,
            String name,
            String username,
            String veterinary,
            String specialty,
            Pageable pageable);

    public Page<ResOwnerDTO> getVetContacts(
            Long vetID,
            String status,
            String name,
            String username,
            Pageable pageable);

    public Contact updateContactStatus(Long contactID, String newStatus) throws EntityNotFoundException;

    public String deleteContact(Long contactID) throws EntityNotFoundException;

}
