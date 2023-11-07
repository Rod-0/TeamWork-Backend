package upc.edu.pe.AccountTransaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AccountRequestDto {
    private Long id;
    private String nameCustomer;
    private String numberAccount;
}
