package lk.ijse.dep11.security.repostiory;

import lk.ijse.dep11.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
