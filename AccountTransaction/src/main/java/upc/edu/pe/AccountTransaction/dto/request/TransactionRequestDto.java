package upc.edu.pe.AccountTransaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
