
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.veterinary.Veterinarian;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    @Query(value = "SELECT v FROM Veterinarian v " +
            "WHERE (:name IS NULL OR v.name ILIKE %:name%) " +
            "AND (:username IS NULL OR v.username ILIKE %:username%) " +
            "AND (:email IS NULL OR v.email ILIKE %:email%) " +
            "AND (:veterinary IS NULL OR v.veterinary ILIKE %:veterinary%) " +
            "AND (:specialty IS NULL OR v.specialty ILIKE %:specialty%)",
            nativeQuery = false)
    public Page<Veterinarian> findAllVeterinarians(
            @Param("name") String name,
            @Param("username") String username,
            @Param("veterinary") String veterinary,
            @Param("specialty") String specialty,
            @Param("email") String email,
            Pageable pageable);

}
