package upc.edu.pe.AccountTransaction.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionResponseDto {
    private Long id;
    private String type;
    private LocalDate createDate;
    private Double amount;
    private Double balance;
}
