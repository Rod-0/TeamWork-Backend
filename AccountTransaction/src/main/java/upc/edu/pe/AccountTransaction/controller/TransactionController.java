package upc.edu.pe.AccountTransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.AccountTransaction.dto.AccountDto;
import upc.edu.pe.AccountTransaction.dto.TransactionDto;
import upc.edu.pe.AccountTransaction.exception.ValidationException;
import upc.edu.pe.AccountTransaction.model.Account;
import upc.edu.pe.AccountTransaction.model.Transaction;
import upc.edu.pe.AccountTransaction.repository.TransactionRepository;
import upc.edu.pe.AccountTransaction.service.AccountService;
import upc.edu.pe.AccountTransaction.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bank/v1")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;
    private final AccountService accountService;


    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService, AccountService accountService){

        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    //URL: http://localhost:8080/api/bank/v1/1/transactions
    //Method: POST
    @Transactional
    @PostMapping("/accounts/{id}/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable(value = "id") Long accountId, @RequestBody Transaction transaction){

        AccountDto accountDto = accountService.getAccountById(accountId);
        validation(transaction);
        existsAccountById(accountDto);
        transactiontype(transaction);
        transaction.setCreateDate(LocalDate.now());


        return new ResponseEntity<TransactionDto>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/bank/v1/transactions/filterByNameCustomer
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/transactions/filterByNameCustomer")
    public ResponseEntity<List<TransactionDto>> getTransactionByNameCustomer(@RequestParam(name = "nameCustomer") String nameCustomer){
        return new ResponseEntity<List<TransactionDto>>(transactionService.getTransactionByNameCustomer(nameCustomer), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/bank/v1/transactions/filterByCreateDateRange
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/transactions/filterByCreateDateRange")
    public ResponseEntity<List<TransactionDto>> getTransactionByCreateDateRange(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate){
        return new ResponseEntity<List<TransactionDto>>(transactionService.getTransactionByDateRange(startDate, endDate), HttpStatus.OK);
    }

    private void validation(Transaction transaction) {
        if (transaction.getType() == null || transaction.getType().isEmpty()) {
            throw new ValidationException("El tipo de transacción bancaria debe ser obligatorio");
        }
        if (transaction.getAmount() <= 0.0){
            throw new ValidationException("El monto en una transacción bancaria debe ser mayor de S/.0.0");
        }



    }

    private void existsAccountById(AccountDto accountDto) {
        if (accountService.getAccountById(accountDto.id()) == null) {
            throw new ValidationException("No se puede registrar la transacción porque no existe la cuenta");
        }
    }

    private void transactiontype(Transaction transaction) {
        if (transaction.getType().equals("Deposito")){
            transaction.setBalance(transaction.getAmount() + transaction.getBalance());
        }
        if (transaction.getType().equals("Retiro")){
            if (transaction.getAmount() > transaction.getBalance()){
                throw new ValidationException("En una transacción bancaria tipo retiro el monto no puede ser mayor al saldo");
            }else {
                transaction.setBalance(transaction.getBalance() - transaction.getAmount());
            }

        }
    }
}
