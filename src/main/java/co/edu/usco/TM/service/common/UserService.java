package co.edu.usco.TM.service.common;

import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import co.edu.usco.TM.persistence.entity.veterinary.Veterinarian;
import co.edu.usco.TM.persistence.repository.OwnerRepository;
import co.edu.usco.TM.persistence.repository.UserRepository;
import co.edu.usco.TM.persistence.repository.VeterinarianRepository;
import co.edu.usco.TM.service.noImpl.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    OwnerRepository ownerRepo;

    @Autowired
    VeterinarianRepository vetRepo;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public Page<UserEntity> findAllUsers(String name, String username, String email, Pageable pageable) {
        return userRepo.findAllUsers(name, username, email, pageable);
    }

    @Override
    public Page<Owner> findAllOwners(String name, String username, String email, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Veterinarian> findAllVeterinarians(String name, String username, String email, String specialty, String veteirnary, Pageable pageable) {
        return null;
    }
}
