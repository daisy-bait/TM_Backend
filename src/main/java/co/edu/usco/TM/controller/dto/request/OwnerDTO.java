
package co.edu.usco.TM.controller.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class OwnerDTO extends UserDTO{
    
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}
