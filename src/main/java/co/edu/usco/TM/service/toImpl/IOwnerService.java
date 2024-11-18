
package co.edu.usco.TM.service.toImpl;

import java.io.IOException;

import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IOwnerService {
    
    ResUserDTO findById(Long id);
    
    ResUserDTO save(ReqUserDTO ownerDTO, MultipartFile image, boolean deleteImg, Long ownerID) throws IOException;
    
    ResUserDTO disableOwner(Long id);
    
}
