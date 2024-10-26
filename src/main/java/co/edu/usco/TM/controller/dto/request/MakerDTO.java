package co.edu.usco.TM.controller.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MakerDTO extends UserDTO{

    private String name;
    private String address;
    private String phone;
}
