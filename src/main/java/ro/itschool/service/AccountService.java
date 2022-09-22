package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.BankAccount;
import ro.itschool.exception.AmountNotEmptyException;
import ro.itschool.exception.NotEnoughMoneyInAccount;
import ro.itschool.controller.model.TransferMoneyRequest;

import java.util.List;

@Service
public interface AccountService {

    void deleteByIban(String iban) throws AmountNotEmptyException;

    void save(BankAccount bankAccount);

    List<BankAccount> getAllAccounts();

    BankAccount findByIban(String iban);

    List<BankAccount> getAllAccountsByUserId(Long userId);

    void transferMoney(TransferMoneyRequest transferMoneyRequest) throws NotEnoughMoneyInAccount;

    void payABill (TransferMoneyRequest transferMoneyRequest) throws NotEnoughMoneyInAccount;

    void transferMoneyToUserIban(TransferMoneyRequest transferMoneyRequest) throws NotEnoughMoneyInAccount;
}
