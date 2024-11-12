
package co.edu.usco.TM.dto.response.veterinary;

import co.edu.usco.TM.dto.base.PetSpecie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ResPetDTO {

    private String name;
    private PetSpecie specie;
    private Double weight;
    private int years;
    private int months;

    private LocalDate birthDate;

    @JsonIgnoreProperties({"petList", "appointmentList"})
    private ResOwnerDTO owner;

}
