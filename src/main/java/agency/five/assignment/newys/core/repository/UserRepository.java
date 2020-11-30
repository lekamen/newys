package agency.five.assignment.newys.core.repository;

import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.model.enums.AuthorStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByEmail(String email);

    boolean existsByUsername(String username);

    List<UserAccount> findByAuthorStatus(AuthorStatusEnum authorStatus);
}
