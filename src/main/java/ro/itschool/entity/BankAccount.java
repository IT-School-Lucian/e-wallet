package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.itschool.model.Currency;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private MyUser user;

}
