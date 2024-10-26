
package co.edu.usco.TM.controller.dto.request;

import co.edu.usco.TM.persistence.entity.veterinary.Owner;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PetDTO {
    
    private Long id;
    private String name;
    private Double weight;
    private int years;
    private int months;
    private Owner owner;
}
