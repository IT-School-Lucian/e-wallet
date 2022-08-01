package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import ro.itschool.entity.BankAccount;
import ro.itschool.entity.MyUser;

import java.util.List;

public interface UserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByUsernameIgnoreCase(String username);

    MyUser findByEmail(String username);

    MyUser findByRandomToken(String randomToken);

    @Query(
            value = "SELECT * FROM my_user u WHERE u.username LIKE %:keyword% OR u.full_name LIKE %:keyword% OR u.email LIKE %:keyword%",
            nativeQuery = true)
    List<MyUser> searchUser (@Param("keyword") String keyword);

}
