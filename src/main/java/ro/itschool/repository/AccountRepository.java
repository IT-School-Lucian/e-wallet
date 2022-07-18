package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.BankAccount;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<BankAccount, UUID> {
}
