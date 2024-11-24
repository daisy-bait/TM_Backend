
package co.edu.usco.TM.persistence.entity.user;

import co.edu.usco.TM.persistence.entity.veterinary.Appointment;
import co.edu.usco.TM.persistence.entity.veterinary.Contact;
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
    
    @Column(name = "vet_specialty")
    private String specialty;
    
    @Column(name = "vet_veterinary")
    private String veterinary;

    @Column(name = "vet_degree_url")
    private String degreeURL;

    @Column(name = "vet_enabled")
    private boolean vetEnabled;

    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Appointment> vetAppointments = new ArrayList<>();

    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> vetContacts = new ArrayList<>();
}
