
package co.edu.usco.TM.repository;

import co.edu.usco.TM.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    
    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
}
