package upc.edu.pe.AccountTransaction.dto.request;

import lombok.Data;
import upc.edu.pe.AccountTransaction.model.Account;

import java.time.LocalDate;

@Data
public class TransactionRequestDto {
    private String type;
    private LocalDate createDate;
    private Double amount;
    private Double balance;
    private Account account;
}
