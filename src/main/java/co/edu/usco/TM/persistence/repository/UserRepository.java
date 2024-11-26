
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.persistence.entity.user.UserEntity;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    @Query(value = "SELECT u FROM UserEntity u " +
            "WHERE (u.username = :username " +
            "OR u.email = :username)", nativeQuery = false)
    Optional<UserEntity> findUser(@Param("username") String username);

    @Query(value = "SELECT u FROM UserEntity u " +
            "JOIN u.roles r " +
            "LEFT JOIN Veterinarian v ON u.id = v.id " +
            "WHERE (:name IS NULL OR u.name ILIKE %:name%) " +
            "AND (:username IS NULL OR u.username ILIKE %:username%) " +
            "AND (:email IS NULL OR u.email ILIKE %:email%) " +
            "AND (:role IS NULL OR r.name = :role) " +
            "AND u.enabled = true " +
            "AND (:role != 'VET' " +
            "OR ((:specialty IS NULL OR v.specialty ILIKE %:specialty%) " +
            "AND (:veterinary IS NULL OR v.veterinary ILIKE %:veterinary%)))",
            nativeQuery = false)
    public Page<UserEntity> findFilteredUsers(
            @Param("name") String name,
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") String role,
            @Param("specialty") String specialty,
            @Param("veterinary") String veterinary,
            Pageable pageable);

    @Query(value = "SELECT u FROM UserEntity u " +
            "JOIN u.roles r " +
            "LEFT JOIN Veterinarian v ON u.id = v.id " +
            "WHERE (:name IS NULL OR u.name ILIKE %:name%) " +
            "AND (:username IS NULL OR u.username ILIKE %:username%) " +
            "AND (:email IS NULL OR u.email ILIKE %:email%) " +
            "AND (:role IS NULL OR r.name = :role) " +
            "AND u.enabled = true",
            nativeQuery = false)
    public Page<UserEntity> findFilteredOwners(
            @Param("name") String name,
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") String role,
            Pageable pageable);

    @Query(value = "SELECT u FROM UserEntity u " +
            "JOIN u.roles r " +
            "WHERE u.id = :id " +
            "AND r.name = 'OWNER' " +
            "AND u.enabled = true")
    public Optional<UserEntity> findOwnerById(@Param("id") Long id);
}
