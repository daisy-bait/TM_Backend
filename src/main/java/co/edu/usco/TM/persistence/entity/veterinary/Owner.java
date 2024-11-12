
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
@Table(name = "owners")
public class Owner extends UserEntity {
    
    @Column(name = "own_address")
    private String address;
    
    @Column(name = "own_zip_code")
    private String zipCode;
    
    @Column(name = "own_phone")
    private String phone;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Pet> petList = new ArrayList<>();
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointmentList = new ArrayList<>();
    
}
