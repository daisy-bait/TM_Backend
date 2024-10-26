
package co.edu.usco.TM.controller.dto;

import co.edu.usco.TM.persistence.entity.administration.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserDTO {
    
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
}
