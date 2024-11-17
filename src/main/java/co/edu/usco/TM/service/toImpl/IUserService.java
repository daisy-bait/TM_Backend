package co.edu.usco.TM.service.toImpl;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    public Page<ResUserDTO> findAllUsers(
            String name,
            String username,
            String email,
            String role,
            Pageable pageable,
            String specialty,
            String veterinary
    );
}
