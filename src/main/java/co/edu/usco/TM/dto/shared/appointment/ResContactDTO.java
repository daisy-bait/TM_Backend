package co.edu.usco.TM.dto.shared.appointment;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResContactDTO {

    @JsonIgnoreProperties({"pets", "vetAppointments", "userAppointments"})
    private ResUserDTO user;
    private Long originID;
    private String status;


}
