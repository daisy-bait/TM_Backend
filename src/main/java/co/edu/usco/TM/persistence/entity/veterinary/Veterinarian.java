
package co.edu.usco.TM.persistence.entity.veterinary;

import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "veterinarians")
public class Veterinarian extends UserEntity{
    
    @Column(name = "ovet_fist_name")
    private String firstName;
    
    @Column(name = "vet_last_name")
    private String lastName;
    
    @Column(name = "vet_address")
    private String address;
    
    @Column(name = "vet_phone")
    private String phone;
    
    @Column(name = "vet_specialty")
    private String specialty;
    
    @Column(name = "vet_veterinary")
    private String veterinary;
}
