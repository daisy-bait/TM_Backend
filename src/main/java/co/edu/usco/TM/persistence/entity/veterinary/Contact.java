
package co.edu.usco.TM.persistence.entity.veterinary;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "own_id")
    private Owner owner;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vet_id")
    private Veterinarian vet;
    
}
