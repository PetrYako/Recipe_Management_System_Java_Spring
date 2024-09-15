package recipes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
