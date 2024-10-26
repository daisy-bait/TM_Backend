
package co.edu.usco.TM.controller.dto.request;

import co.edu.usco.TM.persistence.entity.administration.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDTO {
    
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
}
