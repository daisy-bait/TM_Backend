
package co.edu.usco.TM.service.toImpl;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPetService {

    ResPetDTO save(ReqPetDTO petDTO, Long ownerID, MultipartFile image, boolean deleteImg, Long petID) throws IOException;

    ResPetDTO findById(Long id);

    ResPetDTO disablePet(Long id);

}
