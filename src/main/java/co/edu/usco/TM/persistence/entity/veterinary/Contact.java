
package co.edu.usco.TM.persistence.entity.veterinary;

import co.edu.usco.TM.persistence.entity.user.UserEntity;
import co.edu.usco.TM.persistence.entity.user.Veterinarian;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "con_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "con_own_id", nullable = false)
    @JsonIgnoreProperties("contacts")
    private UserEntity owner;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "con_vet_id", nullable = false)
    @JsonIgnoreProperties("contacts")
    private Veterinarian vet;

    @Column(name = "con_origin_id", nullable = false)
    private Long origin;

    @Column(name = "con_status")
    private String status;

    @Column(name = "con_created_at")
    private LocalDateTime created_at = LocalDateTime.now();

    
}
