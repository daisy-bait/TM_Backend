
package co.edu.usco.TM.persistence.entity.veterinary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
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
    
    @Column(name = "app_datetime")
    private LocalDateTime datetime;
    
    @Column(name ="app_status")
    private String status;
    
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
