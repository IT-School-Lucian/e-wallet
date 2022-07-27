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
import ro.itschool.model.Currency;
import ro.itschool.model.TransferMoneyRequest;
import ro.itschool.service.AccountService;
import ro.itschool.service.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

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
        return "redirect:/index";
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
    public String transferMoney(@ModelAttribute TransferMoneyRequest transferMoneyRequest) throws NotEnoughMoneyInAccount {
        BankAccount fromAccount = accountService.findByIban(transferMoneyRequest.getFromIban());

        Integer amountToTransfer = transferMoneyRequest.getAmount();

        BankAccount toAccount = accountService.findByIban(transferMoneyRequest.getToIban());

        if (amountToTransfer > fromAccount.getAmount()) {
            throw new NotEnoughMoneyInAccount("Not enough money in account");
        }
        fromAccount.setAmount(fromAccount.getAmount() - amountToTransfer);
        toAccount.setAmount(toAccount.getAmount() + amountToTransfer);

        accountService.saveTransactional(fromAccount, toAccount);

        return "redirect:/index";
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
        return "redirect:/index";
    }

    //----------------------------------------------------------------------


    private BankAccount getBankAccountFromString(String str) {
        BankAccount bankAccount = new BankAccount();
        String[] ibanStringArray = str.split(",");

        bankAccount.setId(UUID.fromString(ibanStringArray[0].substring(15)));
        bankAccount.setCurrency(Currency.valueOf(ibanStringArray[1].substring(10)));
        bankAccount.setAmount(Double.valueOf(ibanStringArray[2].substring(8)));
        bankAccount.setIsCredit(Boolean.valueOf(ibanStringArray[3].substring(10)));
        bankAccount.setIban(ibanStringArray[4].substring(6));

        LocalDateTime dateTime = LocalDateTime.parse(ibanStringArray[5].substring(11, ibanStringArray[5].length() - 1));
        bankAccount.setCreatedAt(dateTime);

        return bankAccount;
    }
}
