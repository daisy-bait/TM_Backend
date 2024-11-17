
package co.edu.usco.TM.dto.response.veterinary;

import co.edu.usco.TM.dto.shared.appointment.AppointmentDTO;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResVetDTO extends ResUserDTO{

    private String specialty;
    private String veterinary;
    private String degreeURL;

    private List<AppointmentDTO> vetAppointments;
}
