
package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPetService {

    ResPetDTO save(ReqPetDTO petDTO, Long ownerID, MultipartFile image) throws IOException;

}
