
package co.edu.usco.TM.persistence.entity.administration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;



@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;
    
    @NotNull
    @Column(name = "usr_username")
    private String username;
    
    @NotNull
    @Column(name = "usr_email")
    private String email;
    
    @NotNull
    @Column(name = "usr_password")
    private String password;
    
    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_id")
    @JsonIgnore
    private Role role;
    
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
}
