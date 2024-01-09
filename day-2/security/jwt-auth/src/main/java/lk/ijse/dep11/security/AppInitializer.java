package lk.ijse.dep11.security;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.SecretKey;

@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class);
    }

    @Bean
    public SecretKey secretKey() {
        return Jwts.SIG.HS256.key().build();
    }
}
