package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.BankAccount;
import ro.itschool.exception.AmountNotEmptyException;
import ro.itschool.exception.NotEnoughMoneyInAccount;
import ro.itschool.model.TransferMoneyRequest;
import ro.itschool.service.AccountService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    //--------------SAVE DATA FROM MODAL (NEW BANK ACCOUNT)------------------
    @GetMapping("/modals/add-bank-account")
    public String addAccount(Model model) {
        BankAccount bankAccount = new BankAccount();
        model.addAttribute("account", bankAccount);
        model.addAttribute("iban", bankAccount.getIban());
        return "add-account-modal";
    }

    @PostMapping("/modals/add-bank-account")
    public String addAccount(@ModelAttribute BankAccount account) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        account.setUser(userService.findUserByUserName(auth.getName()));
        accountService.save(account);
        return Constants.REDIRECT_TO_INDEX;
    }

    //----------------------------------------------------------------------------------

    //--------------TRANSFER MONEY (MODAL)------------------------------
    @GetMapping("/modals/transfer-money")
    public String transferMoney(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Long userId = userService.findUserByUserName(auth.getName()).getId();
        model.addAttribute("accounts", accountService.getAllAccountsByUserId(userId));
        model.addAttribute("transferMoneyRequest", new TransferMoneyRequest());
        return "transfer-money-modal";
    }

    @PostMapping("/modals/transfer-money")
    public String transferMoney(@ModelAttribute TransferMoneyRequest transferMoneyRequest) {
        try {
            accountService.transferMoney(transferMoneyRequest);
        } catch (NotEnoughMoneyInAccount e) {
            return "not-enough-money";
        }
        return Constants.REDIRECT_TO_INDEX;
    }

    //----------------------------------------------------------------------------------


    //-------------------DELETE ACCOUNT BY ID-------------------------------------------

    @RequestMapping(value = "/account/delete/{iban}")
    public String deleteAccount(@PathVariable String iban) {
        try {
            accountService.deleteByIban(iban);
        } catch (AmountNotEmptyException e) {
            return "amount-not-empty";
        }
        return Constants.REDIRECT_TO_INDEX;
    }

    //----------------------------------------------------------------------

}
