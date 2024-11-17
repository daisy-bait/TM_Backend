
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.veterinary.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByUserIdOrderByIdDesc(Long ownerId);

}
