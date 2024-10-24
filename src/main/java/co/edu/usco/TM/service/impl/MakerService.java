
package co.edu.usco.TM.service.impl;

import co.edu.usco.TM.persistence.entity.commerce.Maker;
import co.edu.usco.TM.service.IMakerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usco.TM.persistence.repository.MakerRepository;
import co.edu.usco.TM.persistence.repository.RoleRepository;

@Service
public class MakerService implements IMakerService{
    
    @Autowired
    MakerRepository makerRepo;
    
    @Autowired
    RoleRepository roleRepo;
    
    @Override
    public List<Maker> findAll() {
        return makerRepo.findAll();
    }
    
    @Override
    public Optional<Maker> findById(Long id) {
        return makerRepo.findById(id);
    }
    
    @Override
    public void save(Maker maker) {
        //maker.setRole(roleRepo.);
        makerRepo.save(maker);
    }
    
    @Override
    public void deleteById(Long id) {
        makerRepo.deleteById(id);
    }
    
}
