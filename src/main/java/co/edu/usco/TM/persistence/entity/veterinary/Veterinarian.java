
package co.edu.usco.TM.persistence.entity.veterinary;

import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "veterinarians")
public class Veterinarian extends UserEntity{
    
    @Column(name = "vet_address")
    private String address;
    
    @Column(name = "vet_zip_code")
    private String zipCode;
    
    @Column(name = "vet_phone")
    private String phone;
    
    @Column(name = "vet_specialty")
    private String specialty;
    
    @Column(name = "vet_veterinary")
    private String veterinary;

    @Column(name = "vet_degree")
    private String degreeURL;
    
    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Appointment> appointmentList = new ArrayList<>();
}
