
package co.edu.usco.TM.persistence.entity.veterinary.appointment;
import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import co.edu.usco.TM.persistence.entity.veterinary.Pet;
import co.edu.usco.TM.persistence.entity.veterinary.Veterinarian;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long id;
    
    @Column(name = "app_reason")
    private String reason;
    
    @Column(name = "app_description")
    private String description;
    
    @Column(name = "app_date")
    private LocalDateTime date;
    
    @Column(name ="app_status")
    @Enumerated(EnumType.STRING)
    private AppointmentEnum status;
    
    @ManyToOne
    @JoinColumn(name = "own_id", nullable = false)
    @JsonIgnore
    private Owner owner;
    
    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    @JsonIgnore
    private Veterinarian vet;
    
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnore
    private Pet pet;
}
