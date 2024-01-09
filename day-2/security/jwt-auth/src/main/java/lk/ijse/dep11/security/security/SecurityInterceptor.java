package lk.ijse.dep11.security.security;

import io.jsonwebtoken.*;
import lk.ijse.dep11.security.repostiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final SecretKey secretKey;
    private final UserRepository repository;

    public SecurityInterceptor(SecretKey secretKey, UserRepository repository) {
        this.secretKey = secretKey;
        this.repository = repository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /* Publicly authorize endpoints */
        String endpoint = request.getServletPath();
        if (endpoint.equalsIgnoreCase("/api/v1/users/login")) return true;
        if (endpoint.equalsIgnoreCase("/error")) return true;

        // (1) Check for the authorization header
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            // (2) Check for the bearer token
            if (authorization.toUpperCase().startsWith("BEARER")) {
                // (3) Extract the token
                String token = authorization.substring(7);
                // (4) Parse the token
                JwtParserBuilder jwtParserBuilder = Jwts.parser().requireIssuer("dep-11");
                try {
                    Jws<Claims> jwt = jwtParserBuilder.verifyWith(secretKey).build().parseSignedClaims(token);
                    // (5) Obtain user information
                    String username = jwt.getPayload().getSubject();
                    // (6) Verify user
                    if (repository.existsById(username)) return true;
                } catch (JwtException exp) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
