
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    @Query(value = "SELECT r FROM Role r WHERE r.name = :name", nativeQuery = false)
    Role findByName(String name);
    
}
