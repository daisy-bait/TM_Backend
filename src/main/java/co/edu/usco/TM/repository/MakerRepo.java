
package co.edu.usco.TM.repository;

import co.edu.usco.TM.entity.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepo extends JpaRepository<Maker, Long>{
    
}
