
package co.edu.usco.TM.dto.request.veterinary;

import co.edu.usco.TM.dto.shared.PetSpecie;
import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReqVetDTO extends ReqUserDTO{
    
    private String zipCode;
    private String address;
    private String phone;
    @NotNull
    private PetSpecie specialty;
    @NotEmpty
    @NotNull
    private String veterinary;
}
