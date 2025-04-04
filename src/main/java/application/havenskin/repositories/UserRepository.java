package application.havenskin.repositories;

import application.havenskin.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    List<Users> findByRole(byte role);
    Optional<Users> findByEmail(String email);
    List<Users> findByRoleIn(List<Byte> roles);
    List<Users> findByRole(Byte role);
    List<Users> findByLastName(String name);
}
