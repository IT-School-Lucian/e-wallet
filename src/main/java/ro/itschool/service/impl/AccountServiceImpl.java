package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.BankAccount;
import ro.itschool.exception.AmountNotEmptyException;
import ro.itschool.repository.AccountRepository;
import ro.itschool.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

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

    @Override
    @Transactional
    public void saveTransactional(BankAccount fromAccount, BankAccount toAccount) {
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
