
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.commerce.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long>{
    
}
