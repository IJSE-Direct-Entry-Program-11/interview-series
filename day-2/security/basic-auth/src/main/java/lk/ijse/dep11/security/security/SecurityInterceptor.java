package lk.ijse.dep11.security.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String endpoint = request.getServletPath();
        if (endpoint.equalsIgnoreCase("/error")) return true;

        // (1) Check for the authorization header
        String authorization = request.getHeader("Authorization");
        if (authorization != null){
            // (2) Check for the basic authentication
            if (authorization.toUpperCase().startsWith("BASIC")){
                // (3) Extract the encoded text
                String encodedText = authorization.substring(6);
                // (4) Decode the credentials
                byte[] decodedTextBytes = Base64.getDecoder().decode(encodedText);
                String decodedText = new String(decodedTextBytes);
                // (5) Extract username and password
                String[] credentials = decodedText.split(":");
                if (credentials.length == 2){
                    String username = credentials[0];
                    String password = credentials[1];
                    // (6) Verify user
                    if (username.equals("ijse") && password.equals("dep11")){
                        return true;
                    }
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

}
