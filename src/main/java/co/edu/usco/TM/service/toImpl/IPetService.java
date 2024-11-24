
package co.edu.usco.TM.service.toImpl;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPetService {

    ResPetDTO save(ReqPetDTO petDTO, Long ownerID, MultipartFile image, boolean deleteImg, Long petID) throws IOException;

    Page<ResPetDTO> findFilteredPets(Long ownerID, String name, String specie, Integer months, Pageable pageable);

    ResPetDTO findById(Long id);

    ResPetDTO disablePet(Long id);

}
