package topg.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import topg.user_service.model.User;


import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
