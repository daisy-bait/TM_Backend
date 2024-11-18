
package co.edu.usco.TM.security;

import co.edu.usco.TM.security.filter.JwtTokenValidator;
import co.edu.usco.TM.service.auth.UserDetailsService;
import co.edu.usco.TM.security.jwt.JwtUtil;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(request -> {
                    // Configure Public Endpoints
                    request.requestMatchers(HttpMethod.POST,
                            "/api/owner/create",
                            "/api/vet/create").permitAll();
                    request.requestMatchers(HttpMethod.GET,
                            "/api/user/find",
                            "/api/owner/find",
                            "/api/vet/find",
                            "/api/owner/find/{id}",
                            "/api/vet/find/{id}").permitAll();
                    request.requestMatchers(
                            "/api/auth/**").permitAll();
                    // Swagger
                    request.requestMatchers(
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml",
                            "/v3/api-docs/swagger-config").permitAll();

                    //Configure Private Endpoints
                    request.requestMatchers(HttpMethod.GET,
                            "/api/owner/details",
                            "/api/owner/contact/list").hasRole("OWNER");
                    request.requestMatchers(HttpMethod.PUT,
                            "/api/owner/update",
                            "/api/owner/pet/save").hasRole("OWNER");
                    request.requestMatchers(HttpMethod.POST,
                            "/api/owner/pet/save",
                            "/api/owner/contact/add/{id}").hasRole("OWNER");
                    request.requestMatchers(HttpMethod.GET,
                            "/api/vet/details",
                            "/api/vet/contact/list").hasRole("VET");
                    request.requestMatchers(HttpMethod.PUT,
                            "/api/vet/update").hasRole("VET");
                    request.requestMatchers(HttpMethod.POST,
                            "/api/vet/contact/add/{id}").hasRole("VET");
                    
                    // Configure No Specified Endpoints
                    request.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtil), BasicAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8082"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
