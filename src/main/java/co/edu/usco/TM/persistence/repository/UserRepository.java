
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    @Query(value = "SELECT u FROM UserEntity u WHERE u.username = :username", nativeQuery = false)
    Optional<UserEntity> findUser(@Param("username") String username);
}
