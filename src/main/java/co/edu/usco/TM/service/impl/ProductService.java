
package co.edu.usco.TM.service.impl;

import co.edu.usco.TM.entity.Product;
import co.edu.usco.TM.repository.ProductRepo;
import co.edu.usco.TM.service.IProductService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductService implements IProductService{

    @Autowired
    ProductRepo productRepo;
    
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
