
package co.edu.usco.TM.persistence.repository;

import co.edu.usco.TM.persistence.entity.commerce.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
}
