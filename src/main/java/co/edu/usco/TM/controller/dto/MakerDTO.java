package co.edu.usco.TM.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class MakerDTO extends UserDTO{

    private String name;
    private String address;
    private String phone;
}
