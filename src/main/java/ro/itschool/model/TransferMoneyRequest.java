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
    private Integer amount;

}
