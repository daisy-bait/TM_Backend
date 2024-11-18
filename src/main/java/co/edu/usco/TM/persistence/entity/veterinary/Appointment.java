
package co.edu.usco.TM.persistence.entity.veterinary;
import co.edu.usco.TM.persistence.entity.user.UserEntity;
import co.edu.usco.TM.persistence.entity.user.Veterinarian;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "app_reason")
    private String reason;
    
    @Column(name = "app_description")
    private String description;

    @NotNull
    @Column(name = "app_datetime")
    private LocalDateTime datetime;

    @NotNull
    @Column(name = "app_duration")
    private Integer duration;
    
    @Column(name ="app_status")
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "own_id", nullable = false)
    @JsonIgnore
    private UserEntity owner;
    
    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    @JsonIgnore
    private Veterinarian vet;
    
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnore
    private Pet pet;
}
