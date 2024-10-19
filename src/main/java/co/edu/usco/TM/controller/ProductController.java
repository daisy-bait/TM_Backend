package co.edu.usco.TM.controller;

import co.edu.usco.TM.controller.dto.ProductDTO;
import co.edu.usco.TM.entity.Product;
import co.edu.usco.TM.service.IProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/find")
    public ResponseEntity<?> findAll() {

        List<ProductDTO> productsDTO = productService.findAll()
                .stream().map(product -> ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .maker(product.getMaker())
                .build()
                ).toList();

        return ResponseEntity.ok(productsDTO);
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(Long id) {
        
        Optional<Product> productOptional = productService.findById(id);
        
        if (productOptional.isPresent()) {
            
        }
    }
}
