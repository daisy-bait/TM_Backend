package co.edu.usco.TM.persistence.entity.veterinary;

import co.edu.usco.TM.dto.shared.PetSpecie;
import co.edu.usco.TM.persistence.entity.shared.ImageEntity;
import co.edu.usco.TM.persistence.entity.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pets")
public class Pet implements ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    @Column(name = "pet_name", nullable = false)
    private String name;
    
    @Column(name = "pet_specie", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetSpecie specie;

    @Column(name = "pet_weight")
    private Double weight;

    @Column(name = "pet_age_months")
    private int months;

    @Column(name = "pet_birth_date")
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;
    
    @Column(name = "pet_image_url")
    private String imgURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_own_id", nullable = false)
    @JsonIgnore
    private UserEntity owner;

    @Column(name = "pet_enabled")
    private boolean enabled;

}
