package lk.ijse.dep11.security.security;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // (1)
        String authorization = request.getHeader("Authorization");
        if (authorization != null){

            // (2)
            if (authorization.toUpperCase().startsWith("BASIC")){

                // (3)
                String encodedText = authorization.substring(6);

                // (4)
                byte[] decodedTextBytes = Base64.getDecoder().decode(encodedText);
                String decodedText = new String(decodedTextBytes);

                // (5)
                String[] credentials = decodedText.split(":");
                if (credentials.length == 2){

                    // (6)
                    String username = credentials[0];
                    String password = credentials[1];

                    if (username.equals("ijse") && password.equals("dep11")){
                        return true;
                    }
                }
            }
        }
        response.sendError(401);
        return false;
    }

}
