package upc.edu.pe.AccountTransaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AccountResponseDto {
    private Long id;
    private String nameCustomer;
    private String numberAccount;
}
