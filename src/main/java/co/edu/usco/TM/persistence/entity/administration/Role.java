
package co.edu.usco.TM.persistence.entity.administration;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long id;
    
    @Column(name = "rol_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    
}
