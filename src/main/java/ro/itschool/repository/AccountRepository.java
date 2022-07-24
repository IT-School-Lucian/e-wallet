package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.BankAccount;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<BankAccount, UUID> {

    @Transactional
    void deleteByIban(String iban);

    BankAccount findByIban(String iban);
}
