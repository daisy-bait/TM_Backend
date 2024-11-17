
package co.edu.usco.TM.dto.request.veterinary;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReqPetDTO {

    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotNull
    private String specie;
    @Positive
    private Double weight;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String imgURL;

}
