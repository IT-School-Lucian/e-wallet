package ro.itschool.startup;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.BankAccount;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;
import ro.itschool.model.Currency;
import ro.itschool.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class RunAtStartup {

    @Autowired
    private UserService userService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        MyUser myUser = new MyUser();
        myUser.setUsername("user0");
        myUser.setPassword("password");
        myUser.setRandomToken("randomToken");
        final Role roleUser = new Role("ROLE_USER");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("user@gmail.com");
        myUser.setFullName("Userescu Userila");
        myUser.setPasswordConfirm("password");
        myUser.setRandomTokenEmail("randomToken");


        Set<BankAccount> accounts = new HashSet<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmount(12000D);
        bankAccount.setCurrency(Currency.EUR);
        bankAccount.setIsCredit(true);
        Iban iban = Iban.random(CountryCode.RO);
        bankAccount.setUser(myUser);
        bankAccount.setIban(iban.toString());
        bankAccount.setCreatedAt(LocalDateTime.now());

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAmount(11000D);
        bankAccount2.setCurrency(Currency.CHF);
        bankAccount2.setIsCredit(false);
        Iban iban2 = Iban.random(CountryCode.RO);
        bankAccount2.setUser(myUser);
        bankAccount2.setIban(iban2.toString());
        bankAccount2.setCreatedAt(LocalDateTime.now());

        accounts.add(bankAccount);
        accounts.add(bankAccount2);
        myUser.setAccounts(accounts);

        userService.saveUser(myUser);

    }

}
