package upc.edu.pe.AccountTransaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {
    private String nameCustomer;
    private String numberAccount;
}