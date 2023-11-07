package upc.edu.pe.AccountTransaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.AccountTransaction.dto.AccountDto;
import upc.edu.pe.AccountTransaction.dto.request.AccountRequestDto;
import upc.edu.pe.AccountTransaction.dto.request.TransactionRequestDto;
import upc.edu.pe.AccountTransaction.dto.response.TransactionResponseDto;
import upc.edu.pe.AccountTransaction.exception.ValidationException;
import upc.edu.pe.AccountTransaction.model.Transaction;
import upc.edu.pe.AccountTransaction.repository.TransactionRepository;
import upc.edu.pe.AccountTransaction.service.AccountService;
import upc.edu.pe.AccountTransaction.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para la gestion de transacciones de la API
 * Proporciona los metodos para gestionar las transacciones
 * @version 1.0, 06/11/2023
 * @author Rodrigo Pozo and Andrea O'Higgins
 */
@Tag(name = "Transaction", description = "API of transactions")
@RestController
@RequestMapping("/api/bank/v1")
public class TransactionController {

    //Inyeccion de dependencias para el servicio de transacciones

    private final TransactionService transactionService;

    //Inyeccion de dependencias para el servicio de cuentas
    private final AccountService accountService;


    /**
     * Constructor de la clasw
     * @param transactionRepository El repositorio para operaciones relacionales con la entidad Transaction
     * @param transactionService El servicio para operaciones relacionales con la entidad Transaction
     * @param accountService El servicio para operaciones relacionales con la entidad Account
     */
    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService, AccountService accountService){

        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    /**
     * Metodo para crear una transaccion
      * @param accountId El id de la cuenta
     * @param transactionRequestDto La transaccion a crear
     * @return Una entidad de respuesta HTTP con la transaccion creada y el estado de la peticion
     */
    //URL: http://localhost:8080/api/bank/v1/1/transactions
    //Method: POST
    @Operation(summary = "Create a transaction")
    @ApiResponse(responseCode = "201", description = "OK",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Transaction.class)))
    @Transactional
    @PostMapping("/accounts/{id}/transactions")
    public ResponseEntity<TransactionResponseDto> createTransaction(@PathVariable(value = "id") Long accountId, @RequestBody TransactionRequestDto transactionRequestDto){

        var accountRequestDto = accountService.getAccountById(accountId);
        validation(transactionRequestDto);
        existsAccountById(accountDto);
        transactiontype(transactionRequestDto);
        transactionRequestDto.setCreateDate(LocalDate.now());


        return new ResponseEntity<>(transactionService.createTransaction(transactionRequestDto), HttpStatus.CREATED);
    }

    /**
     * Metodo que obtiene una transaccion por su nombre de cliente
     * @param nameCustomer El nombre del cliente
     * @return Una entidad de respuesta HTTP con una lista de transacciones por nombre de cliente y el estado de la peticion
     */
    //URL: http://localhost:8080/api/bank/v1/transactions/filterByNameCustomer
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/transactions/filterByNameCustomer")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionByNameCustomer(@RequestParam(name = "nameCustomer") String nameCustomer){
        var res = transactionService.getTransactionByNameCustomer(nameCustomer);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Metodo que obtiene una transaccion por su rango de fecha de creacion
     * @param startDate La fecha de inicio
     * @param endDate La fecha de fin
     * @return Una entidad de respuesta HTTP con una lista de transacciones por rango de fecha de creacion y el estado de la peticion
     */
    //URL: http://localhost:8080/api/bank/v1/transactions/filterByCreateDateRange
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/transactions/filterByCreateDateRange")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionByCreateDateRange(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate){
        var res = transactionService.getTransactionByDateRange(startDate, endDate);
        return new ResponseEntity<>(transactionService.getTransactionByDateRange(startDate, endDate), HttpStatus.OK);
    }

    /**
     * Validacion de los datos de la transaccion
     * @param transaction La transaccion a validar
     * @throws ValidationException Si los datos de la transaccion no son validos
     */
    private void validation(TransactionRequestDto transaction) {
        if (transaction.getType() == null || transaction.getType().isEmpty()) {
            throw new ValidationException("El tipo de transacción bancaria debe ser obligatorio");
        }
        if (transaction.getAmount() <= 0.0){
            throw new ValidationException("El monto en una transacción bancaria debe ser mayor de S/.0.0");
        }



    }

    /**
     * Metodo que verifica si existe una cuenta por su id
     * @param accountDto La cuenta a verificar
     * @throws ValidationException Si la cuenta no existe
     */
    private void existsAccountById(AccountDto accountDto) {
        if (accountService.getAccountById(accountDto.id()) == null) {
            throw new ValidationException("No se puede registrar la transacción porque no existe la cuenta");
        }
    }

    /**
     * Tipo de transaccion
     * @param transaction La transaccion a validar
     * Si es un deposito se suma el monto al saldo de la cuenta y si es un retiro se resta el monto al saldo de la cuenta
     * @throws ValidationException Si el monto es mayor al saldo de la cuenta
     */
    private void transactiontype(TransactionRequestDto transaction) {
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
