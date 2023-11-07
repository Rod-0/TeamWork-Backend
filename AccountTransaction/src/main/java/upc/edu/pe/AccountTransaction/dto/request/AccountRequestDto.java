package upc.edu.pe.AccountTransaction.dto.request;

import lombok.Data;

@Data
public class AccountRequestDto {
    private Long id;
    private String nameCustomer;
    private String numberAccount;
}
