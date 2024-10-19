
package co.edu.usco.TM.controller.dto;

import co.edu.usco.TM.entity.Maker;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    
    private Long id;
    private String name;
    private BigDecimal price;
    private Maker maker;
}
