
package co.edu.usco.TM.security.filter;

import co.edu.usco.TM.security.jwt.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    
    public JwtTokenValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (jwtToken != null) {
            
            //Eliminamos el Bearer
            jwtToken = jwtToken.substring(7);
            
            DecodedJWT decodedJWT = jwtUtil.validateToken(jwtToken);
            
            String username = jwtUtil.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtil.getSpecificClaim(decodedJWT, "roles").asString();
            
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
            
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authentication);
            
            SecurityContextHolder.setContext(context);
            
        }
        
        filterChain.doFilter(request, response);
        
    }

}
