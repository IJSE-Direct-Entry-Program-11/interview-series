package lk.ijse.dep11.security;

import io.jsonwebtoken.Jwts;
import lk.ijse.dep11.security.security.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.SecretKey;

@SpringBootApplication
public class AppInitializer  {

    @Bean
    public SecretKey secretKey(){
        return Jwts.SIG.HS256.key().build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class);
    }
}
