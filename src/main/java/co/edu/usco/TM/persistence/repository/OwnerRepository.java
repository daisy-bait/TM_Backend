
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query(value = "SELECT o FROM Owner o " +
            "WHERE (:name IS NULL OR o.name ILIKE %:name%) " +
            "AND (:username IS NULL OR o.username ILIKE %:username%) " +
            "AND (:email IS NULL OR o.email ILIKE %:email%)",
            nativeQuery = false)
    public Page<Owner> findAllOwners(
            @Param("name") String name,
            @Param("username") String useranme,
            @Param("email") String email,
            Pageable pageable);

}
