package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import java.util.Optional;

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
    public String getAllUsers(Model model) throws Exception {

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll().stream().map(Role::getName).toList());
        model.addAttribute("adminRole", roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equals("ROLE_ADMIN"))
                .findAny()
                .orElseThrow(() -> new Exception("User with admin roles not found")));

        return "all-users";
    }

    //---------DELETE a user by id for ADMINs only ------------------------------
    @RequestMapping(path = "/delete/{id}")
    public String deleteUserById(Model model, @PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/index";
    }

    //----------ADD ADMIN ROLE TO USER----------------------------------------------
    @RequestMapping("/add-admin-role/{id}")
    public String addAdminRoleToUser(@PathVariable("id") Long id) {
        final Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            final Role role = roleRepository.findByName("ROLE_ADMIN");
            user.get().getRoles().add(role);
            userService.updateUser(user.get());
            return "redirect:/users";
        }
        return ("USER not found for this id : " + id);
    }
    //------------------------------------------------------------------------------

    //----------REMOVE ADMIN ROLE FROM USER-----------------------------------------
    @RequestMapping("/remove-admin-role/{id}")
    public String removeAdminRoleFromUser(@PathVariable("id") Long id) {
        final Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            final Role role = roleRepository.findByName("ROLE_ADMIN");
            user.get().getRoles().remove(role);
            userService.updateUser(user.get());
            String username = getCurrentUserDetails();
            //Check is logged user is the same as selected user
            if (username.equals(user.get().getUsername()))
                return "redirect:/logout";
            else
                return "redirect:/users";
        }
        return ("USER not found for this id : " + id);
    }

    //------------------------------------------------------------------------------


    //----------------------PRIVATE METHODS-----------------------------------------
    private String getCurrentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
