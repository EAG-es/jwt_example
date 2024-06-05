package inser.spring.restful.jwt_example.security;

import static inser.spring.restful.jwt_example.security.Jwt_utils.get_claims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author emilio
 */
public class Jwt_authorizationFilter extends OncePerRequestFilter {
    public Jwt_utils jwt_util;
    
    public Jwt_authorizationFilter(Jwt_utils _k_webSecurity_names) {
        jwt_util = _k_webSecurity_names;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (check_jwt_token(request, response)) {
                Claims claims = validate_token(request);
                if (claims.get(jwt_util.k_claim_authorities) != null) {
                    setup_spring_authentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    public Claims validate_token(HttpServletRequest request) {
        String jwtToken = request.getHeader(jwt_util.k_header);
        return get_claims(jwtToken, jwt_util);
    }

    /**
     * Authentication method in Spring flow
     *
     * @param claims
     */
    public void setup_spring_authentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get(jwt_util.k_claim_authorities);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject()
          , null
          , authorities
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    public boolean check_jwt_token(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(jwt_util.k_header);
        if (authenticationHeader == null 
          || authenticationHeader.startsWith(jwt_util.k_prefix) == false) {
            return false;
        }
        return true;
    }
        
}
