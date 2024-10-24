package co.edu.usco.TM.persistence.entity.veterinary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    @Column(name = "pet_name")
    private String name;

    @Column(name = "pet_weight")
    private Double weight;

    @Column(name = "pet_years")
    private int years;

    @Column(name = "pet_months")
    private int months;

    @ManyToOne
    @JoinColumn(name = "pet_own_id", nullable = false)
    @JsonIgnore
    private Owner owner;

}
