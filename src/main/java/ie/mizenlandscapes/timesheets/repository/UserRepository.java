package ie.mizenlandscapes.timesheets.repository;

import ie.mizenlandscapes.timesheets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByActive(boolean active);
    List<User> findByFirstName(String firstName);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<Long> userIds);
}
