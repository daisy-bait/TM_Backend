package co.edu.usco;

import co.edu.usco.TM.persistence.entity.administration.Role;
import co.edu.usco.TM.persistence.entity.administration.RoleEnum;
import co.edu.usco.TM.persistence.entity.commerce.Maker;
import co.edu.usco.TM.persistence.repository.MakerRepository;
import co.edu.usco.TM.persistence.repository.RoleRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TMRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TMRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(MakerRepository makerRepo, RoleRepository roleRepo) {
        return args -> {
            
                // Roles
                Role adminRole = Role.builder()
                        .name(RoleEnum.ADMIN)
                        .build();
                
                Role makerRole = Role.builder()
                        .name(RoleEnum.MAKER)
                        .build();
                
                Role ownerRole = Role.builder()
                        .name(RoleEnum.OWNER)
                        .build();
                
                Role vetRole = Role.builder()
                        .name(RoleEnum.VETERINARIAN)
                        .build();
                
                // Makers
                Maker pepitoMaker = Maker.builder()
                        .username("pepito")
                        .email("pepito@gmail.com")
                        .password("1234")
                        .role(makerRole)
                        .name("Tienda Pepito")
                        .address("Cll 45 #56-7")
                        .phone("3157889507")
                        .build();
                
                makerRepo.saveAll(List.of(pepitoMaker));
                roleRepo.save(ownerRole);
        };
    }
}
