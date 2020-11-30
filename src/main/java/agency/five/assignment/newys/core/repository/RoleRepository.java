package agency.five.assignment.newys.core.repository;

import agency.five.assignment.newys.core.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.name = 'READER'")
    Role findReaderRole();

    @Query("select r from Role r where r.name = 'AUTHOR'")
    Role findAuthorRole();
}
