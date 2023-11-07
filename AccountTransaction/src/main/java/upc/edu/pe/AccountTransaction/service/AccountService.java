package upc.edu.pe.AccountTransaction.service;

import upc.edu.pe.AccountTransaction.dto.request.AccountRequestDto;
import upc.edu.pe.AccountTransaction.dto.response.AccountResponseDto;

import java.util.List;

public interface AccountService {

    //CreateAccount
    public abstract AccountResponseDto createAccount(AccountRequestDto accountRequestDto);

    public abstract AccountResponseDto getAccountById(Long id);

    public abstract List<AccountResponseDto> getAllAccounts();

    public abstract AccountResponseDto getAccountByNumberAndName(String accountNumber, String nameCustomer);

}
