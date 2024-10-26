
package co.edu.usco.TM.web.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ownerClient", url = "http://localhost:8080/api/owner")
public interface OwnerClient {
    
    
}
