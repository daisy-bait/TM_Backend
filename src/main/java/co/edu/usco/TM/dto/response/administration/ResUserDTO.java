
package co.edu.usco.TM.dto.response.administration;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResUserDTO {
    
    private Long id;
    private String username;
    private String name;
    private String email;
    private String imgURL;
}