package co.edu.usco.TM.service.users;

import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.persistence.entity.veterinary.Veterinarian;
import co.edu.usco.TM.persistence.repository.RoleRepository;
import co.edu.usco.TM.persistence.repository.VeterinarianRepository;
import co.edu.usco.TM.service.toImpl.IVetService;

import java.io.IOException;

import co.edu.usco.TM.util.FileUploader;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VetService implements IVetService {

    @Autowired
    VeterinarianRepository vetRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    FileUploader uploader;

    @Override
    public ResVetDTO save(ReqVetDTO vetDTO, MultipartFile image, MultipartFile degree, Long vetID) throws IOException {

        Veterinarian vet = vetID != null ? vetRepo.findById(vetID)
                .orElseThrow(() -> new EntityNotFoundException()) : new Veterinarian();

        if (vet.getId() != null) {
            vet.getRoles().add(roleRepo.findByName("VET"));
        }

        vet = modelMapper.map(vetDTO, Veterinarian.class);
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));
        uploader.uploadImage(vet, image);
        uploader.uploadDegree(vet, degree);
        vetRepo.save(vet);

        return modelMapper.map(vet, ResVetDTO.class);
    }

    @Override
    public ResVetDTO findById(Long vetID) throws EntityNotFoundException {

        ResVetDTO vetDetails = modelMapper.map(
                vetRepo.findById(vetID)
                        .orElseThrow(() -> new EntityNotFoundException()),
                ResVetDTO.class);

        return vetDetails;
    }

    @Override
    public ResVetDTO disableVet(Long id) {

        Veterinarian vet = vetRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        vet.setEnabled(false);
        vetRepo.save(vet);

        return modelMapper.map(vet, ResVetDTO.class);
    }
}
