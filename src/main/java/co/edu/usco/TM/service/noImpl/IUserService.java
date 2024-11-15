package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import co.edu.usco.TM.persistence.entity.veterinary.Veterinarian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    public Page<UserEntity> findAllUsers(String name, String username, String email, Pageable pageable);

    public Page<Owner> findAllOwners(String name, String username, String email, Pageable pageable);

    public Page<Veterinarian> findAllVeterinarians(String name, String username, String email, String specialty, String veteirnary, Pageable pageable);

}
