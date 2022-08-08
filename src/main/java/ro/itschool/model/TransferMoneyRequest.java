package ro.itschool.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransferMoneyRequest {

    private String fromIban;
    private String toIban;
    private String userIban;
    private Double amount;


    public TransferMoneyRequest(String fromIban, Double amount) {
        this.fromIban = fromIban;
        this.amount = amount;
    }

    public TransferMoneyRequest(String fromIban, String userIban, Double amount) {
        this.fromIban = fromIban;
        this.userIban = userIban;
        this.amount = amount;
    }
}

