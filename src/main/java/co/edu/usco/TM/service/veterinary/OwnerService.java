
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
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OwnerService implements IOwnerService {

    private static final Logger logger = LoggerFactory.getLogger(OwnerService.class);

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
    public ResOwnerDTO save(ReqOwnerDTO ownerDTO, MultipartFile image, Long ownerID) throws IOException {

        Owner owner = modelMapper.map(ownerDTO, Owner.class);
        owner.setId(ownerID);
        owner.getRoles().add(roleRepo.findByName("OWNER"));
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));

        boolean haveImgToUpload = (image != null && !image.isEmpty());
        boolean haveImg = (owner.getImgURL() != null);
        logger.info(owner.getImgURL());

        if (!haveImg && haveImgToUpload) { // Crear Imágen
            owner.setImgURL(s3Service.uploadFile(image));

        } else if (haveImg && haveImgToUpload) { // Actualizar Imágen
            s3Service.deleteFile(owner.getImgURL());
            owner.setImgURL(s3Service.uploadFile(image));

        } else if (haveImg && !haveImgToUpload) { // Eliminar Imágen
            s3Service.deleteFile(owner.getImgURL());
            owner.setImgURL(null);
        }

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
