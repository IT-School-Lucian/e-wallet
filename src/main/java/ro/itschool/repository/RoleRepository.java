package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Set<Role> findByUsers(MyUser user);
}
