package co.edu.usco.TM.sql;

import co.edu.usco.TM.persistence.entity.administration.Role;
import co.edu.usco.TM.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepo.count() == 0) {
            roleRepo.save(new Role(null, "ADMIN", true));
            roleRepo.save(new Role(null, "OWNER", true));
            roleRepo.save(new Role(null, "VET", true));
        }
    }

}
