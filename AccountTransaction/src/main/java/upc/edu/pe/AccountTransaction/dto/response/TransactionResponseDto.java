package upc.edu.pe.AccountTransaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import upc.edu.pe.AccountTransaction.model.Account;

import java.time.LocalDate;

@Data
public class TransactionResponseDto {
    private Long id;
    private String type;
    private LocalDate createDate;
    private Double amount;
    private Double balance;
    private Account account;
}
