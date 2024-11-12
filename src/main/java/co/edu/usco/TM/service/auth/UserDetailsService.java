package co.edu.usco.TM.service.auth;

import co.edu.usco.TM.dto.auth.AuthLoginRequest;
import co.edu.usco.TM.dto.auth.AuthResponse;
import co.edu.usco.TM.persistence.entity.administration.Role;
import co.edu.usco.TM.persistence.entity.administration.UserEntity;
import co.edu.usco.TM.persistence.repository.UserRepository;
import co.edu.usco.TM.security.jwt.JwtUtil;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        return userRepo.findUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.getUsername();
        String password = authLoginRequest.getPassword();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtil.createToken(authentication);
        AuthResponse authResponse = new AuthResponse(username, "User loged succesfully", accessToken, true);
        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    public Long getAuthenticatedUserID() {
        Object username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Object principal = userRepo.findUser(username.toString()).get();

        logger.info("Clase principal: {}", principal.getClass().getName());

        if (principal instanceof UserDetails) {

            logger.info("SI ES INSTANCIA DE USERDETAILS!!");

            UserEntity user = (UserEntity) principal;
            return user.getId();
        }

        throw new IllegalArgumentException("Usuario no autenticado o no existe");

    }

}
