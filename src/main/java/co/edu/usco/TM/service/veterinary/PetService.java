
package co.edu.usco.TM.service.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.persistence.entity.veterinary.Pet;
import co.edu.usco.TM.persistence.repository.OwnerRepository;
import co.edu.usco.TM.persistence.repository.PetRepository;
import co.edu.usco.TM.s3.S3Service;
import co.edu.usco.TM.service.noImpl.IPetService;
import co.edu.usco.TM.util.AgeCalculator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PetService implements IPetService {

    @Autowired
    PetRepository petRepo;

    @Autowired
    OwnerRepository ownerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    S3Service s3Service;

    @Autowired
    AgeCalculator ageCalculator;

    @Override
    public ResPetDTO save(ReqPetDTO petDTO, Long ownerID, MultipartFile image) throws IOException {

        Pet pet = modelMapper.map(petDTO, Pet.class);
        pet.setOwner(ownerRepo.findById(ownerID).get());

        if (pet.getBirthDate() != null) {
            pet.setMonths(ageCalculator.calculateMonths(pet.getBirthDate()));
        }

        if (image != null && !image.isEmpty()) {
            pet.setImgURL(s3Service.uploadFile(image));
        }

        petRepo.save(pet);

        return modelMapper.map(pet, ResPetDTO.class);
    }
}
