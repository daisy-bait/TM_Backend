
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.veterinary.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    
}
