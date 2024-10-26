
package co.edu.usco.TM.web.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "productClient", url = "http://localhost:8080/api/product")
public class ProductClient {
    
}
