package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.MyUser;

import java.util.List;

@Service
public interface UserService {

    MyUser findUserByEmail(String email);

    MyUser findUserByUserName(String username);

    MyUser findUserByRandomToken(String randomToken);

    boolean findUserByUserNameAndPassword(String userName, String password);

    List<MyUser> findAll();

    void deleteById(long id);

    MyUser saveUser(MyUser u);

    void updateUser(MyUser user);

    List<MyUser> searchUser(String keyword);

}
