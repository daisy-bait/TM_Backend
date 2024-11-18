package co.edu.usco.TM.service.users;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.persistence.entity.user.UserEntity;
import co.edu.usco.TM.persistence.repository.UserRepository;
import co.edu.usco.TM.service.toImpl.IUserService;
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
    ModelMapper modelMapper;

    @Override
    public Page<ResUserDTO> findAllUsers(String name, String username, String email, String role, Pageable pageable, String specialty, String veterinary) {

        Page<UserEntity> usersPage = userRepo.findFilteredOwners(name, username, email, role, pageable);

        return new PageImpl<>(
                usersPage.stream().map(user -> mapUserToDTO(user, role)).collect(Collectors.toList()),
                pageable,
                usersPage.getTotalElements());

    }

    public ResUserDTO mapUserToDTO(UserEntity userEntity, String role) {

        if (role == "VET") {
            return modelMapper.map(userEntity, ResVetDTO.class);
        } else {
            return modelMapper.map(userEntity, ResUserDTO.class);
        }
    }
}
