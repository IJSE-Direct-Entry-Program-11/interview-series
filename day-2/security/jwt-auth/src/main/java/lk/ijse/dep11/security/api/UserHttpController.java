package lk.ijse.dep11.security.api;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lk.ijse.dep11.security.entity.User;
import lk.ijse.dep11.security.repostiory.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserHttpController {

    private final UserRepository repository;
    private final SecretKey secretKey;

    public UserHttpController(UserRepository repository, SecretKey secretKey) {
        this.repository = repository;
        this.secretKey = secretKey;
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public String login(@RequestBody Map<String, String> credentials) {

        // (1) Verify the user
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = repository.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (!user.getPassword().matches(password)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // (2) Generate the token
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuer("dep-11");
        jwtBuilder.issuedAt(new Date());
        LocalDateTime tokenExpTime = LocalDateTime.now().plusMinutes(10);
        Date expTime = Date.from(tokenExpTime.atZone(ZoneId.systemDefault()).toInstant());
        jwtBuilder.expiration(expTime);
        jwtBuilder.subject(username);

        jwtBuilder.signWith(secretKey);

        // (3) Return the signed JWT (JWS)
        return jwtBuilder.compact();

    }
}
