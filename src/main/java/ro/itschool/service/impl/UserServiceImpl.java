package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.itschool.controller.mapper.MyUserMapper;
import ro.itschool.controller.model.MyUserDTO;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;
import ro.itschool.repository.AccountRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;
import ro.itschool.service.email.EmailBodyService;
import ro.itschool.service.email.EmailSender;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    EmailBodyService emailBodyService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public MyUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public MyUser findUserByUserName(String userName) {
        return userRepository.findByUsernameIgnoreCase(userName);
    }

    public MyUser findUserByRandomToken(String randomToken) {
        return userRepository.findByRandomToken(randomToken);
    }

    public boolean findUserByUserNameAndPassword(String userName, String password) {
        final Optional<MyUser> myUser = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(userName));
        return myUser.filter(user -> BCrypt.checkpw(password, user.getPassword())).isPresent();
    }

    public List<MyUserDTO> findAll() {
        return userRepository.findAll().stream().map(MyUserMapper::convertToDTO).toList();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public MyUser saveUser(MyUser receivedUser) {
        MyUser myUser = new MyUser(receivedUser);
        myUser.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
        myUser.setRandomToken(UUID.randomUUID().toString());
        emailSender.sendEmail(myUser.getEmail(), "Activate your Account", emailBodyService.emailBody(myUser));
        receivedUser.getRoles().forEach(role -> {
            final Role roleByName = roleRepository.findByName(role.getName());
            if (roleByName == null)
                myUser.getRoles().add(roleRepository.save(role));
            else {
                role.setId(roleByName.getId());
            }
        });
        final MyUser user = userRepository.save(myUser);
        if (receivedUser.getAccounts() != null)
            receivedUser.getAccounts()
                    .forEach(acc -> {
                        acc.setUser(user);
                        accountRepository.save(acc);
                    });
        return user;
    }

    public void updateUser(MyUser user) {
        List<GrantedAuthority> actualAuthorities = getUserAuthority(user.getRoles());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        userRepository.save(user);
    }

    @Override
    public List<MyUserDTO> searchUser(String keyword) {
        return userRepository.searchUser(keyword).stream().map(MyUserMapper::convertToDTO).toList();

    }


    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

}


