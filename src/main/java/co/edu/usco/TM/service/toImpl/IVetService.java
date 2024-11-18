
package co.edu.usco.TM.service.toImpl;

import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface IVetService {
    
    ResVetDTO findById(Long id);
    
    ResVetDTO save(ReqVetDTO vetDTO, MultipartFile image, MultipartFile degree, boolean deleteImg, Long vetID) throws IOException;
    
    ResVetDTO disableVet(Long id);
    
}
