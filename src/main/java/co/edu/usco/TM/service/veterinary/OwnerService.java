
package co.edu.usco.TM.service.veterinary;

import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import co.edu.usco.TM.persistence.repository.OwnerRepository;
import co.edu.usco.TM.persistence.repository.RoleRepository;
import co.edu.usco.TM.service.noImpl.IOwnerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService implements IOwnerService {
    
    @Autowired
    OwnerRepository ownerRepo;
    
    @Autowired
    RoleRepository roleRepo;

    @Override
    public List<Owner> findAll() {
        return ownerRepo.findAll();
    }

    @Override
    public Optional<Owner> findById(Long id) {
        return ownerRepo.findById(id);
    }

    @Override
    public void save(Owner owner) {
        owner.setRole(roleRepo.findByName("OWNER"));
        ownerRepo.save(owner);
    }

    @Override
    public void delete(Long id) {
        ownerRepo.deleteById(id);
    }
    
}
