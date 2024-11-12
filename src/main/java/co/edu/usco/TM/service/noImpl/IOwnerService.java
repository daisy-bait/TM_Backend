
package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.dto.request.veterinary.ReqOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface IOwnerService {
    
    List<ResOwnerDTO> findAll();
    
    ResOwnerDTO findById(Long id);
    
    ResOwnerDTO save(ReqOwnerDTO ownerDTO, Long ownerID);

    ResOwnerDTO basicDetails(Long id);

    ResOwnerDTO uploadWithImage(
            ReqOwnerDTO ownerDTO,
            MultipartFile file,
            Long ownerID) throws IOException;
    
    void delete(Long id);
    
}
