package lk.ijse.dep11.security.security;

import io.jsonwebtoken.*;
import lk.ijse.dep11.security.repostiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private SecretKey secretKey;
    @Autowired
    private UserRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String endpoint = request.getServletPath();
        if (endpoint.equalsIgnoreCase("/api/v1/users/login")) return true;

        // (1)
        String authorization = request.getHeader("Authorization");
        if (authorization != null){
            // (2)
            if (authorization.toUpperCase().startsWith("BEARER")){
                // (3)
                String token = authorization.substring(7);
                // (4)
                JwtParserBuilder jwtParser = Jwts.parser();
                try {
                    Jwt<Header, Claims> jwt = (Jwt<Header, Claims>) jwtParser.verifyWith(secretKey)
                            .build().parse(token);
                    if (jwt.getPayload().getIssuer().equalsIgnoreCase("dep-11")) {
                        // (5)
                        String username = jwt.getPayload().getSubject();
                        // (6)
                        if (repository.existsById(username)) return true;
                    }
                }catch (ExpiredJwtException exp){
                    response.sendError(401);
                    return false;
                }
            }
        }

        response.sendError(401);
        return false;
    }
}
