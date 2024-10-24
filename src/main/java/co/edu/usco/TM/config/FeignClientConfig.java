
package co.edu.usco.TM.config;

import java.lang.System.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "co.edu.usco.TM.web")
public class FeignClientConfig {
    
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.ALL;
    }
}
