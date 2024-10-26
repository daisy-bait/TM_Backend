
package co.edu.usco.TM.service.commerce;

import co.edu.usco.TM.persistence.entity.commerce.Product;
import co.edu.usco.TM.service.noImpl.IProductService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.usco.TM.persistence.repository.ProductRepository;

@Service
public class ProductService implements IProductService{

    @Autowired
    ProductRepository productRepo;
    
    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }
    
    @Override
    public List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepo.findProductByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public void save(Product product) {
        productRepo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }
    
}
