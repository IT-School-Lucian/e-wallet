package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.entity.BankAccount;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<BankAccount, UUID> {

    @Transactional
    void deleteByIban(String iban);

    BankAccount findByIban(String iban);

    @Query(
            value = "SELECT * FROM bank_account WHERE user_id = ?",
            nativeQuery = true)
    List<BankAccount> findByUserId(Long userId);
}