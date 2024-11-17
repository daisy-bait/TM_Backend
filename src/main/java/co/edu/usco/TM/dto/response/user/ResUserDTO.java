
package co.edu.usco.TM.dto.response.user;

import co.edu.usco.TM.dto.shared.appointment.AppointmentDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResUserDTO {
    
    private Long id;
    private String username;
    private String name;
    private String email;
    private String address;
    private String zipCode;
    private String phone;
    private String imgURL;

    private List<ResPetDTO> pets;

    private List<AppointmentDTO> userAppointments;
}
