
package co.edu.usco.TM.dto.request.administration;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReqUserDTO {

    private String username;
    private String name;
    private String email;
    private String imgURL;
    private String password;
}
