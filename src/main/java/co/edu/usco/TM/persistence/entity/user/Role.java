
package co.edu.usco.TM.persistence.entity.user;

import jakarta.persistence.*;
import lombok.*;

@ToString
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
    private String name;
    
    @Column(name = "rol_enabled")
    private boolean enabled;
    
}
