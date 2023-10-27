package upc.edu.pe.AccountTransaction.dto;

import java.time.LocalDate;

public record TransactionDto(
        Long id,
        String type,
        Double amount,
        Double balance,
        LocalDate createDate

) {
}
