package co.edu.usco.TM.dto.shared.appointment;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {

    private ResUserDTO owner;
    private ResVetDTO vet;
    private Long origin;
    private String status;


}
