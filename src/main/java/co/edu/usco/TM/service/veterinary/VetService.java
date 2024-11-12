package co.edu.usco.TM.service.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.persistence.entity.veterinary.Veterinarian;
import co.edu.usco.TM.persistence.repository.RoleRepository;
import co.edu.usco.TM.persistence.repository.VeterinarianRepository;
import co.edu.usco.TM.s3.S3Service;
import co.edu.usco.TM.service.noImpl.IVetService;

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
    S3Service s3Service;

    @Override
    public List<ResVetDTO> findAll() {
        return vetRepo.findAll().stream()
                .map(vet -> modelMapper.map(vet, ResVetDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResVetDTO findById(Long id) {

        ResVetDTO vetDetails = modelMapper.map(vetRepo.findById(id).get(), ResVetDTO.class);

        return vetDetails;
    }

    @Override
    public ResVetDTO save(ReqVetDTO vetDTO, MultipartFile image, MultipartFile degree, Long vetID) throws IOException {

        Veterinarian vet = modelMapper.map(vetDTO, Veterinarian.class);
        vet.setId(vetID);
        vet.getRoles().add(roleRepo.findByName("VET"));
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));

        if (image != null && !image.isEmpty()) {
            vet.setImgURL(s3Service.uploadFile(image));
        }

        if (degree != null && !degree.isEmpty()) {
            vet.setDegreeURL(s3Service.uploadFile(degree));
        }

        vetRepo.save(vet);

        return modelMapper.map(vet, ResVetDTO.class);
    }

    @Override
    public void delete(Long id) {
        vetRepo.deleteById(id);
    }
}
