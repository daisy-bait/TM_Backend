
package co.edu.usco.TM.service.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.persistence.entity.veterinary.Pet;
import co.edu.usco.TM.persistence.repository.PetRepository;
import co.edu.usco.TM.persistence.repository.UserRepository;
import co.edu.usco.TM.s3.S3Service;
import co.edu.usco.TM.service.toImpl.IPetService;
import co.edu.usco.TM.util.AgeCalculator;
import co.edu.usco.TM.util.FileUploader;
import jakarta.persistence.EntityNotFoundException;
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
    UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FileUploader uploader;

    @Autowired
    AgeCalculator ageCalculator;

    @Override
    public ResPetDTO save(ReqPetDTO petDTO, Long ownerID, MultipartFile image, Long petID) throws IOException {

        Pet pet = petID != null ? petRepo.findById(petID).
                orElseThrow(() -> new EntityNotFoundException()) : new Pet();

        if (pet.getId() == null) {
            pet.setOwner(userRepo.findOwnerById(ownerID)
                    .orElseThrow(() -> new EntityNotFoundException()));
        }

        pet = modelMapper.map(petDTO, Pet.class);

        if (pet.getBirthDate() != null) {
            pet.setMonths(ageCalculator.calculateMonths(pet.getBirthDate()));
        }

        uploader.uploadImage(pet, image);
        petRepo.save(pet);

        return modelMapper.map(pet, ResPetDTO.class);
    }

    @Override
    public ResPetDTO findById(Long petID) {

        ResPetDTO petDetails = modelMapper.map(
                petRepo.findById(petID)
                        .orElseThrow(() -> new EntityNotFoundException()),
                ResPetDTO.class);

        return petDetails;
    }

    @Override
    public ResPetDTO disablePet(Long petID) {

        Pet pet = petRepo.findById(petID).orElseThrow(() -> new EntityNotFoundException());

        pet.setEnabled(false);
        petRepo.save(pet);

        return modelMapper.map(pet, ResPetDTO.class);
    }
}
