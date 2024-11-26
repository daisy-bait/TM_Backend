package co.edu.usco.TM.service.toImpl;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IContactService {

    public ResContactDTO createContact(Long originID, Long ownerID, Long vetID) throws EntityNotFoundException;

    public Page<ResContactDTO> getOwnerContacts(
            Long ownerID,
            String status,
            String name,
            String username,
            String email,
            String veterinary,
            String specialty,
            Pageable pageable);

    public Page<ResContactDTO> getVetContacts(
            Long vetID,
            String status,
            String name,
            String username,
            String email,
            Pageable pageable);

    public List<ResVetDTO> getAllOwnerContacts(
            Long ownerID
    );

    public List<ResUserDTO> getAllVetContacts(
            Long vetID
    );

    public ResContactDTO deleteContact(Long ownerID, Long vetID) throws EntityNotFoundException;

}
