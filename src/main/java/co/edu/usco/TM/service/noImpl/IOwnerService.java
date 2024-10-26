
package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import java.util.List;
import java.util.Optional;

public interface IOwnerService {
    
    List<Owner> findAll();
    
    Optional<Owner> findById(Long id);
    
    void save(Owner owner);
    
    void delete(Long id);
    
}
