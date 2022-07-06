package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;


    //--------- GET all users for ADMINs only ------------------------------
    @GetMapping("/users")
    public String getAllUsers(Model model) {

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll().stream().map(Role::getName).toList());
        model.addAttribute("adminRole", roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equals("ROLE_ADMIN"))
                .findFirst()
                .get());

        return "all-users";
    }

    //---------DELETE a user by id for ADMINs only ------------------------------
    @RequestMapping(path = "/delete/{id}")
    public String deleteUserById(Model model, @PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/index";
    }

    //----------UPDATE USER ROLES----------------------------------------------
    @GetMapping("/admin-role")
    public String updateUserRoles(@PathVariable Long id, Model model) {
        if (userRepository.findById(id).isPresent()) {
            final MyUser user = userRepository.findById(id).get();
            final Role role = roleRepository.findByName("ROLE_ADMIN");
            user.getRoles().add(role);
            userService.saveUser(user);
            return "redirect:/users";
        }
        return ("USER not found for this id : " + id);
    }
}
