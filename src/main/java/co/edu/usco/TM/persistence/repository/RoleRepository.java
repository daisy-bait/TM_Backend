
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.administration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
