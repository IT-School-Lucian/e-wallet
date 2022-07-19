package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import ro.itschool.model.Currency;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class BankAccount {
    //UUID (Universally Unique Identifier), also known as GUID (Globally Unique Identifier) represents
// a 128-bit long value that is unique for all practical purposes.
// The standard representation of the UUID uses hex digits (octets):
    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    private Currency currency;

    private Double amount;

    private Boolean isCredit;

    private String iban;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    public BankAccount() {
        this.iban = Iban.random(CountryCode.RO).toString();
        this.createdAt = LocalDateTime.now();
        this.amount = 0D;
    }
}
