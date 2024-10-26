
package co.edu.usco.TM.service.security;

import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import co.edu.usco.TM.persistence.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        
        UserEntity user = userRepo.findUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " doesn't exist"));
        
        List<SimpleGrantedAuthority> roleList = new ArrayList<>();
        
        roleList.add(new SimpleGrantedAuthority("ROLE_".concat(user.getRole().getName().name())));
        
        return new User(user.getUsername(), user.getPassword(), roleList);
    }
    
}
