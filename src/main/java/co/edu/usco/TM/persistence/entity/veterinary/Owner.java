
package co.edu.usco.TM.persistence.entity.veterinary;

import co.edu.usco.TM.persistence.entity.veterinary.appointment.Appointment;
import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "owners")
public class Owner extends UserEntity {
    
    @Column(name = "own_fist_name")
    private String firstName;
    
    @Column(name = "own_last_name")
    private String lastName;
    
    @Column(name = "own_address")
    private String address;
    
    @Column(name = "own_phone")
    private String phone;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Pet> petList = new ArrayList<>();
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Appointment> appointmentList = new ArrayList<>();
    
}
