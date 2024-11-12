
package co.edu.usco.TM.dto.response.veterinary;

import co.edu.usco.TM.dto.base.AppointmentDTO;
import co.edu.usco.TM.dto.response.administration.ResUserDTO;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResVetDTO extends ResUserDTO{

    private String address;
    private String zipCode;
    private String phone;
    private String specialty;
    private String veterinary;
    private String degreeURL;

    private List<AppointmentDTO> appointmentList;
}
