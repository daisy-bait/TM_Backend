
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    @Query(value = "SELECT u FROM UserEntity u WHERE u.username = :username", nativeQuery = false)
    Optional<UserEntity> findUser(@Param("username") String username);

    @Query(value = "SELECT u FROM UserEntity u " +
            "WHERE (:name IS NULL OR u.name ILIKE %:name%) " +
            "AND (:username IS NULL OR u.username ILIKE %:username%) " +
            "AND (:email IS NULL OR u.email ILIKE %:email%)",
            nativeQuery = false)
    public Page<UserEntity> findAllUsers(
            @Param("name") String name,
            @Param("username") String username,
            @Param("email") String email,
            Pageable pageable);
}
