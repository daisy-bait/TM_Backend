
package co.edu.usco.TM.service.users;

import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.persistence.entity.user.UserEntity;
import co.edu.usco.TM.persistence.repository.PetRepository;
import co.edu.usco.TM.persistence.repository.RoleRepository;
import co.edu.usco.TM.persistence.repository.UserRepository;
import co.edu.usco.TM.service.toImpl.IOwnerService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import co.edu.usco.TM.util.FileUploader;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OwnerService implements IOwnerService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PetRepository petRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    FileUploader uploader;

    @Override
    public ResUserDTO save(ReqUserDTO ownerDTO, MultipartFile image, Long ownerID) throws IOException {

        UserEntity owner = ownerID != null ? userRepo.findOwnerById(ownerID)
                .orElseThrow(() -> new EntityNotFoundException()) : new UserEntity();

        owner = modelMapper.map(ownerDTO, UserEntity.class);

        if (owner.getId() == null) {
            owner.getRoles().add(roleRepo.findByName("OWNER"));
        }

        owner.setId(ownerID);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        uploader.uploadImage(owner, image);
        userRepo.save(owner);

        return modelMapper.map(owner, ResUserDTO.class);
    }

    @Override
    public ResUserDTO findById(Long id) {

        ResUserDTO ownerDetails = modelMapper.map(
                userRepo.findOwnerById(id)
                        .orElseThrow(() -> new EntityNotFoundException()),
                ResUserDTO.class);

        ownerDetails.setPets(getLastThreePets(id));

        return ownerDetails;
    }

    @Override
    public ResUserDTO disableOwner(Long id) {

        UserEntity owner = userRepo.findOwnerById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        owner.setEnabled(false);
        userRepo.save(owner);

        return modelMapper.map(owner, ResUserDTO.class);
    }

    public List<ResPetDTO> getLastThreePets(Long ownerID) {

        return petRepo.findByOwnerIdOrderByIdDesc(ownerID)
                .stream()
                .limit(3)
                .map(pet -> modelMapper.map(pet, ResPetDTO.class))
                .collect(Collectors.toList());
    }

}
