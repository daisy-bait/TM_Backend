package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.dto.response.administration.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    public Page<ResUserDTO> findAllUsers(String name, String username, String email, Pageable pageable);

    public Page<ResOwnerDTO> findAllOwners(String name, String username, String email, Pageable pageable);

    public Page<ResVetDTO> findAllVeterinarians(String name, String username, String email, String specialty, String veteirnary, Pageable pageable);

}
