package co.edu.usco.TM.service.users;

import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.persistence.entity.user.Veterinarian;
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
    public ResVetDTO save(ReqVetDTO vetDTO, MultipartFile image, MultipartFile degree, boolean deleteImg, Long vetID) throws IOException {

        Veterinarian vet;

        if (vetID == null) {
            vet = new Veterinarian();
            vet.getRoles().add(roleRepo.findByName("VET"));
            vet.setEnabled(true);
        } else {
            vet = vetRepo.findById(vetID)
                    .orElseThrow(() -> new EntityNotFoundException());
        }

        modelMapper.map(vetDTO, vet);
        uploader.uploadImage(vet, image, deleteImg);
        uploader.uploadDegree(vet, degree);
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));
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
