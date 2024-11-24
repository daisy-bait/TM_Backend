
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.dto.shared.PetSpecie;
import co.edu.usco.TM.persistence.entity.veterinary.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwnerIdOrderByIdDesc(Long ownerId);

    @Query(value = "SELECT p FROM Pet p " +
            "WHERE (:ownerID IS NULL OR p.owner.id = :ownerID) " +
            "AND (:name IS NULL OR p.name ILIKE %:name%) " +
            "AND (:specie IS NULL OR CAST(p.specie AS string) ILIKE %:specie%  ) " +
            "AND (:months IS NULL OR p.months > :months)",
            nativeQuery = false)
    public Page<Pet> findAll(
            @Param("ownerID") Long ownerID,
            @Param("name") String name,
            @Param("specie") String specie,
            @Param("months") Integer months,
            Pageable pageable
    );

}
