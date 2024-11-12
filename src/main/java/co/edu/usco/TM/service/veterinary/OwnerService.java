
package co.edu.usco.TM.service.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import co.edu.usco.TM.persistence.repository.OwnerRepository;
import co.edu.usco.TM.persistence.repository.PetRepository;
import co.edu.usco.TM.persistence.repository.RoleRepository;
import co.edu.usco.TM.s3.S3Service;
import co.edu.usco.TM.service.noImpl.IOwnerService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OwnerService implements IOwnerService {
    
    @Autowired
    private OwnerRepository ownerRepo;
    
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PetRepository petRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    S3Service s3Service;

    @Override
    public List<ResOwnerDTO> findAll() {
        return ownerRepo.findAll().stream()
                .map(owner -> modelMapper.map(owner, ResOwnerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResOwnerDTO findById(Long id) {
        
        ResOwnerDTO ownerDetails = modelMapper.map(ownerRepo.findById(id).get(), ResOwnerDTO.class);
        
        return ownerDetails;
    }

    @Override
    public ResOwnerDTO save(ReqOwnerDTO ownerDTO, Long ownerID) {
        
        Owner owner = modelMapper.map(ownerDTO, Owner.class);
        owner.setId(ownerID);
        owner.getRoles().add(roleRepo.findByName("OWNER"));
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        
        ownerRepo.save(owner);
        
        return modelMapper.map(owner, ResOwnerDTO.class);
    }
    
    @Override
    public ResOwnerDTO uploadWithImage(
            ReqOwnerDTO ownerDTO,
            MultipartFile file,
            Long ownerID) throws IOException {
        
        Owner owner = modelMapper.map(ownerDTO, Owner.class);
        owner.getRoles().add(roleRepo.findByName("OWNER"));
        owner.setId(ownerID);
        owner.setImgURL(s3Service.uploadFile(file));
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        
        ownerRepo.save(owner);
        
        return modelMapper.map(owner, ResOwnerDTO.class);
    }

    @Override
    public ResOwnerDTO basicDetails(Long id) {

        ResOwnerDTO ownerDetails = modelMapper.map(ownerRepo.findById(id).get(), ResOwnerDTO.class);
        ownerDetails.setPetList(this.getLastThreePets(id));

        return ownerDetails;
    }

    @Override
    public void delete(Long id) {
        ownerRepo.deleteById(id);
    }

    public List<ResPetDTO> getLastThreePets(Long ownerID) {

        return petRepo.findByOwnerIdOrderByIdDesc(ownerID)
                .stream()
                .limit(3)
                .map(pet -> modelMapper.map(pet, ResPetDTO.class))
                .collect(Collectors.toList());
    }

}
