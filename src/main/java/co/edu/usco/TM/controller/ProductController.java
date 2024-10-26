package co.edu.usco.TM.controller;

import co.edu.usco.TM.controller.dto.request.ProductDTO;
import co.edu.usco.TM.persistence.entity.commerce.Product;
import co.edu.usco.TM.service.noImpl.IProductService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/find")
    public ResponseEntity<?> findAll() {

        List<ProductDTO> productsList = productService.findAll()
                .stream().map(product -> ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .maker(product.getMaker())
                .build()
                ).toList();

        return ResponseEntity.ok(productsList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();

            return ResponseEntity.ok(productDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if (productDTO.getName().isBlank() || productDTO.getPrice() == null
                || productDTO.getMaker() == null) {
            return ResponseEntity.badRequest().build();
        }

        productService.save(Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build());

        return ResponseEntity.created(new URI("/api/product/save")).build();
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        
        Optional<Product> productOptional = productService.findById(id);
        
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setMaker(productDTO.getMaker());
            
            productService.save(product);
            
            return ResponseEntity.ok("updated product");
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        
        if (id != null) {
            productService.deleteById(id);
            
            return ResponseEntity.ok("deleted product");
        }
        
        return ResponseEntity.badRequest().build();
    }
}
