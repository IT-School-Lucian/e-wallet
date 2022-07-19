package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.entity.BankAccount;
import ro.itschool.repository.AccountRepository;
import ro.itschool.service.UserService;

@Controller
@RequestMapping("/modals")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;


    //--------------SAVE DATA FROM MODAL (NEW BANK ACCOUNT)------------------
    @GetMapping("/addBankAccount")
    public String modal1(Model model) {
        model.addAttribute("account", new BankAccount());
        return "addAccountModal1";
    }

    @PostMapping("/addBankAccount")
    public String submit(@ModelAttribute BankAccount account) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        account.setUser(userService.findUserByUserName(auth.getName()));
        accountRepository.save(account);
        return "redirect:/index";
    }

    //----------------------------------------------------------------------------------

}
