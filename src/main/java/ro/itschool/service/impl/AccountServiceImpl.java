package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.BankAccount;
import ro.itschool.entity.MyUser;
import ro.itschool.exception.AmountNotEmptyException;
import ro.itschool.exception.NotEnoughMoneyInAccount;
import ro.itschool.model.Currency;
import ro.itschool.model.TransferMoneyRequest;
import ro.itschool.repository.AccountRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.AccountService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public void deleteByIban(String iban) throws AmountNotEmptyException {
        BankAccount bankAccount = accountRepository.findByIban(iban);
        if (bankAccount != null && bankAccount.getAmount().intValue() > 0)
            throw new AmountNotEmptyException("Please transfer all your money before account deletion");
        accountRepository.deleteByIban(iban);
    }

    @Override
    public void save(BankAccount bankAccount) {
        accountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public BankAccount findByIban(String iban) {
        return accountRepository.findByIban(iban);
    }

    @Override
    public List<BankAccount> getAllAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Transactional
    private void saveTransactional(BankAccount fromAccount, BankAccount toAccount) {
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Override
    public void transferMoney(TransferMoneyRequest transferMoneyRequest) throws NotEnoughMoneyInAccount {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final MyUser myUser = userRepository.findByUsernameIgnoreCase(auth.getName());
        BankAccount fromAccount = getBankAccountFromString(transferMoneyRequest.getFromIban());
        fromAccount.setUser(myUser);
        BankAccount toAccount = getBankAccountFromString(transferMoneyRequest.getToIban());
        toAccount.setUser(myUser);

        if (transferMoneyRequest.getAmount() > fromAccount.getAmount()) {
            throw new NotEnoughMoneyInAccount("Not enough money in account");
        }
        fromAccount.setAmount(fromAccount.getAmount() - transferMoneyRequest.getAmount());
        toAccount.setAmount(toAccount.getAmount() + transferMoneyRequest.getAmount());

        saveTransactional(fromAccount, toAccount);
    }

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
