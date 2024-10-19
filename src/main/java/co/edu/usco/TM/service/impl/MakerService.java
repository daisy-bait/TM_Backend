
package co.edu.usco.TM.service.impl;

import co.edu.usco.TM.entity.Maker;
import co.edu.usco.TM.repository.MakerRepo;
import co.edu.usco.TM.service.IMakerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MakerService implements IMakerService{
    
    @Autowired
    MakerRepo makerRepo;
    
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
        makerRepo.save(maker);
    }
    
    @Override
    public void deleteById(Long id) {
        makerRepo.deleteById(id);
    }
    
}
