
package co.edu.usco.TM.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequest {

    @NotBlank
    @NotEmpty
    private String username;
    @NotBlank
    @NotEmpty
    private String password;
    
}
