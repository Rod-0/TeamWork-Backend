package upc.edu.pe.AccountTransaction.service;

import upc.edu.pe.AccountTransaction.dto.AccountDto;
import upc.edu.pe.AccountTransaction.model.Account;

import java.util.List;

public interface AccountService {

    //CreateAccount
    public abstract AccountDto createAccount(Account account);

    public abstract AccountDto getAccountById(Long id);

    public abstract List<AccountDto> getAllAccounts();

    public abstract AccountDto getAccountByNumberAndName(String accountNumber, String nameCustomer);

}
