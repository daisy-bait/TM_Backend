
package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IVetService {

    List<ResVetDTO> findAll();
    
    ResVetDTO findById(Long id);
    
    ResVetDTO save(ReqVetDTO vetDTO, MultipartFile image, MultipartFile degree, Long vetID) throws IOException;
    
    void delete(Long id);
    
}
