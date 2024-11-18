
package co.edu.usco.TM.dto.request.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReqUserDTO {

    private String username;
    private String name;
    private String email;
    private String password;
    private String address;
    private String zipCode;
    private String phone;
}
