
package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.persistence.entity.commerce.Maker;
import java.util.List;
import java.util.Optional;

public interface IMakerService {
    
    List<Maker> findAll();
    
    Optional<Maker> findById(Long id);
    
    void save(Maker maker);
    
    void deleteById(Long id);
}
