package lk.ijse.dep11.security.api;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lk.ijse.dep11.security.entity.User;
import lk.ijse.dep11.security.repostiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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

    @PostMapping(value= "/login", consumes = "application/json", produces = "application/json")
    public String login(@RequestBody Map<String, String> credentials){

        // (2)
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = repository.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (!user.getPassword().matches(password)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // (3)
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuer("dep-11");
        jwtBuilder.issuedAt(new Date());
        LocalDateTime tokenExpTime = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);
        Date expTime = Date.
                from(tokenExpTime.atZone(ZoneId.systemDefault()).toInstant());
        jwtBuilder.expiration(expTime);
        jwtBuilder.subject(username);

        jwtBuilder.signWith(secretKey);

        String jwt = jwtBuilder.compact();

        // (4)
        return jwt;

    }
}
