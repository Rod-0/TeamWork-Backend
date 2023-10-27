package upc.edu.pe.AccountTransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.AccountTransaction.dto.AccountDto;
import upc.edu.pe.AccountTransaction.exception.ValidationException;
import upc.edu.pe.AccountTransaction.model.Account;
import upc.edu.pe.AccountTransaction.repository.AccountRepository;
import upc.edu.pe.AccountTransaction.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/bank/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository){
        this.accountRepository = accountRepository;

    }

    //URL: http://localhost:8080/api/bank/v1/accounts
    //Method: GET
    @GetMapping("/accounts")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return new ResponseEntity<List<AccountDto>>( accountService.getAllAccounts(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/bank/v1/accounts/
    //Method: POST
    @PostMapping("/accounts")
    @Transactional
    public ResponseEntity<AccountDto> createAccount(@RequestBody Account account){
        validation(account);
        existsAccountByNameCustomerAndNumberAccount(account);
        return new ResponseEntity<AccountDto>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    private void validation(Account account) {
        if (account.getNameCustomer() == null || account.getNameCustomer().isEmpty()) {
            throw new ValidationException("El nombre del cliente debe ser obligatorio");
        }
        if (account.getNameCustomer().length() >= 30) {
            throw new ValidationException("El nombre del cliente no debe exceder los 30 caracteres");
        }
        if (account.getNumberAccount() == null || account.getNumberAccount().isEmpty()) {
            throw new ValidationException("El número de cuenta debe ser obligatorio");
        }
        if (account.getNumberAccount().length() != 13) {
            throw new ValidationException("Number Account must have at least 5 characters");
        }
    }

    private void existsAccountByNameCustomerAndNumberAccount(Account account) {
        if (accountRepository.existsByNumberAccountAndNameCustomer(account.getNumberAccount(), account.getNameCustomer())) {
            throw new ValidationException("No se puede registrar la cuenta porque ya existe uno con estos datos");
        }


    }





}
