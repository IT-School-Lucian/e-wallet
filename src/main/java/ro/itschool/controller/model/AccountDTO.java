package ro.itschool.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.itschool.enums.Currency;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Currency currency;
    private Double amount;
    private String iban;
}
