package upc.edu.pe.AccountTransaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.AccountTransaction.dto.AccountDto;
import upc.edu.pe.AccountTransaction.exception.ResourceNotFoundException;
import upc.edu.pe.AccountTransaction.model.Account;
import upc.edu.pe.AccountTransaction.repository.AccountRepository;
import upc.edu.pe.AccountTransaction.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public AccountDto createAccount(Account account) {
        Account savedAccount = accountRepository.save(account);

        return new AccountDto(savedAccount.getId(), savedAccount.getNameCustomer(), savedAccount.getNumberAccount());
    }


    @Override
    public AccountDto getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The account that could not be found is : " + id));

        return new AccountDto(account.getId(), account.getNameCustomer(), account.getNumberAccount());

    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDto(account.getId(), account.getNameCustomer(), account.getNumberAccount()))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountByNumberAndName(String accountNumber, String nameCustomer) {
        Account account = accountRepository.findByNumberAccountAndNameCustomer(accountNumber, nameCustomer);
        return new AccountDto(account.getId(), account.getNameCustomer(), account.getNumberAccount());

    }



}
