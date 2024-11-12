
package co.edu.usco.TM.security.jwt;

import co.edu.usco.TM.persistence.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    @Value("${jwtKeys.privateKey}")
    private String privateKey;
    
    @Value("${jwtUser.generator}")
    private String userGenerator;
    
    @Autowired
    UserRepository userRepo;
    
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        
        String username = authentication.getPrincipal().toString();
        Long userId = userRepo.findUser(username).get().getId();
        String roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("user_id", userId) // Add User ID as Claim
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 60 * 1000)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
        
        return jwtToken;
        
    } 
    
    public DecodedJWT validateToken(String token) {
        try {
            
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
            
        } catch (JWTVerificationException ex) {
        
            throw new JWTVerificationException("Token invalid, not Authorized");
            
        }
    }

    public Long getUserID(String token) {

        //Validate Token
        DecodedJWT decodedJWT = this.validateToken(token.replace("Bearer ", ""));

        //Get specific Claim, User ID
        return this.getSpecificClaim(decodedJWT, "user_id").asLong();
    }

    public String extractUsername (DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }
    
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }
    
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
    
}
