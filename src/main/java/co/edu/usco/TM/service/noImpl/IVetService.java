
package co.edu.usco.TM.service.noImpl;

import co.edu.usco.TM.persistence.entity.veterinary.Veterinarian;
import java.util.List;
import java.util.Optional;

public interface IVetService {

    List<Veterinarian> findAll();
    
    Optional<Veterinarian> findById(Long id);
    
    void save(Veterinarian vet);
    
    void delete(Long id);
    
}
