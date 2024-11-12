package co.edu.usco.TM.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public class AuthResponse {

    private String username;
    private String message;
    private String jwt;
    private boolean status;
    
}
