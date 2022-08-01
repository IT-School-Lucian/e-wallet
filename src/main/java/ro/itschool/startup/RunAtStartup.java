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
import ro.itschool.util.Constants;

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
        myUser.setPassword("user0");
        myUser.setRandomToken("randomToken");
        final Role roleUser = new Role(Constants.ROLE_USER);
        final Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("user@gmail.com");
        myUser.setFullName("Userescu Userila");
        myUser.setPasswordConfirm("user0");
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

        saveAdminUser();
        saveAnotherUser();

    }

    private void saveAdminUser() {

        MyUser myUser = new MyUser();
        myUser.setUsername("admin");
        myUser.setPassword("admin");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(new Role(Constants.ROLE_USER));
        roles.add(new Role(Constants.ROLE_ADMIN));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("user2@gmail.com");
        myUser.setFullName("Userescu2 Userila2");
        myUser.setPasswordConfirm("admin");
        myUser.setRandomTokenEmail("randomToken");


        Set<BankAccount> accounts = new HashSet<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmount(200D);
        bankAccount.setCurrency(Currency.EUR);
        bankAccount.setIsCredit(true);
        Iban iban = Iban.random(CountryCode.RO);
        bankAccount.setUser(myUser);
        bankAccount.setIban(iban.toString());
        bankAccount.setCreatedAt(LocalDateTime.now());

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAmount(1D);
        bankAccount2.setCurrency(Currency.RON);
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

    private void saveAnotherUser() {

        MyUser myUser = new MyUser();
        myUser.setUsername("user2");
        myUser.setPassword("user2");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(new Role(Constants.ROLE_USER));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("user22@gmail.com");
        myUser.setFullName("Mihai Constantin");
        myUser.setPasswordConfirm("user2");
        myUser.setRandomTokenEmail("randomToken");


        Set<BankAccount> accounts = new HashSet<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmount(10000D);
        bankAccount.setCurrency(Currency.EUR);
        bankAccount.setIsCredit(true);
        Iban iban = Iban.random(CountryCode.RO);
        bankAccount.setUser(myUser);
        bankAccount.setIban(iban.toString());
        bankAccount.setCreatedAt(LocalDateTime.now());

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAmount(24000D);
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
