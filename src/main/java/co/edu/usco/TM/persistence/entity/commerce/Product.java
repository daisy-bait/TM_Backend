
package co.edu.usco.TM.persistence.entity.commerce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Long id;
    
    @Column(name = "pro_name")
    private String name;
    
    @Column(name = "pro_description")
    private String description;
    
    @Column(name = "pro_price")
    private BigDecimal price;
    
    @Column(name = "pro_stock")
    private int stock;
    
    @Column(name= "pro_type")
    private ProductEnum type;
    
    @Column(name = "pro_created_at")
    private LocalDateTime date;
    
    @ManyToOne
    @JoinColumn(name = "mak_id", nullable = false)
    @JsonIgnore
    private Maker maker;
}
