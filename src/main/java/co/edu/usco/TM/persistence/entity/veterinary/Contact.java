
package co.edu.usco.TM.persistence.entity.veterinary;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "con_own_id", nullable = false)
    private Owner owner;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "con_vet_id", nullable = false)
    private Veterinarian vet;

    @Column(name = "con_status")
    private String status;

    @Column(name = "con_created_at")
    private LocalDateTime created_at = LocalDateTime.now();

    
}
