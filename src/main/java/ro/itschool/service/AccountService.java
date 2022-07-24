package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.BankAccount;
import ro.itschool.exception.AmountNotEmptyException;

import java.util.List;

@Service
public interface AccountService {

    void deleteByIban(String iban) throws AmountNotEmptyException;

    void save(BankAccount bankAccount);

    List<BankAccount> getAllAccounts();

    BankAccount findByIban(String iban);
}
