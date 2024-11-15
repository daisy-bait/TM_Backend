package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.veterinary.Contact;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "SELECT c FROM Contact c " +
            "JOIN c.vet v " +
            "WHERE (c.owner.id = :ownerID) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:name IS NULL OR v.name ILIKE %:name%) " +
            "AND (:username IS NULL OR v.username ILIKE %:username%) " +
            "AND (:veterinary IS NULL OR v.veterinary ILIKE %:veterinary%) " +
            "AND (:specialty IS NULL OR v.specialty ILIKE %:specialty%)",
            nativeQuery = false)
    Page<Contact> findOwnerContacs(
            @Param("ownerID") Long ownerID,
            @Param("status") String status,
            @Param("name") String name,
            @Param("username") String username,
            @Param("veterinary") String veterinary,
            @Param("specialty") String specialty,
            Pageable pageable);

    @Query(value = "SELECT c FROM Contact c " +
            "JOIN c.owner o " +
            "WHERE (c.vet.id = :vetID) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:name IS NULL OR o.name ILIKE %:name%) " +
            "AND (:username IS NULL OR o.username ILIKE %:username%)",
            nativeQuery = false)
    Page<Contact> findVetContacts(
            @Param("vetID") Long vetID,
            @Param("status") String status,
            @Param("name") String name,
            @Param("username") String username,
            Pageable pageable
    );

}
