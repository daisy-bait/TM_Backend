package co.edu.usco.TM.service.common;

import co.edu.usco.TM.dto.response.administration.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
    public Page<ResUserDTO> findAllUsers(String name, String username, String email, Pageable pageable) {

        Page<UserEntity> usersPage = userRepo.findAllUsers(name, username, email, pageable);

        return new PageImpl<>(
                usersPage.stream().map(user -> modelMapper.map(user, ResUserDTO.class)).collect(Collectors.toList()),
                pageable,
                usersPage.getTotalElements());

    }

    @Override
    public Page<ResOwnerDTO> findAllOwners(String name, String username, String email, Pageable pageable) {

        Page<Owner> ownersPage = ownerRepo.findAllOwners(name, username, email, pageable);

        return new PageImpl<>(
                ownersPage.stream().map(owner -> modelMapper.map(owner, ResOwnerDTO.class)).collect(Collectors.toList()),
                pageable,
                ownersPage.getTotalElements()
        );
    }

    @Override
    public Page<ResVetDTO> findAllVeterinarians(String name, String username, String email, String specialty, String veterinary, Pageable pageable) {

        Page<Veterinarian> vetsPage = vetRepo.findAllVeterinarian(name, username, veterinary, specialty, pageable);

        return new PageImpl<>(
                vetsPage.stream().map(vet -> modelMapper.map(vet, ResVetDTO.class)).collect(Collectors.toList()),
                pageable,
                vetsPage.getTotalElements());

    }
}
