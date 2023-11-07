package upc.edu.pe.AccountTransaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.AccountTransaction.dto.request.AccountRequestDto;
import upc.edu.pe.AccountTransaction.dto.response.AccountResponseDto;
import upc.edu.pe.AccountTransaction.shared.exception.ValidationException;
import upc.edu.pe.AccountTransaction.model.Account;
import upc.edu.pe.AccountTransaction.repository.AccountRepository;
import upc.edu.pe.AccountTransaction.service.AccountService;

import java.util.List;

/**
 * Controlador REST para la gestion de libros de la API
 * Proporciona los metodos para gestionar los libros
 * @version 1.0, 06/11/2023
 * @author Rodrigo Pozo and Andrea O'Higgins
 */
@Tag(name = "Account", description = "API of accounts")
@RestController
@RequestMapping("/api/bank/v1")
public class AccountController {

    //Inyeccion de dependencias para el servicio de cuentas
    private final AccountService accountService;

    //Inyeccion de dependencias para el repositorio de cuentas
    private final AccountRepository accountRepository;


    /**
     * Constructor de la clase
     * @param accountRepository El repositorio para operaciones relacionales con la entidad Account
     */

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    /**
     * Metodo que obtiene una lista de todas las cuentas
     * @return Una entidad de respuesta HTTP con una lista de cuentas y el estado de la peticion
     */

    //URL: http://localhost:8080/api/bank/v1/accounts
    //Method: GET
    @Operation(summary = "Get a list of all accounts")
    @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Account.class)))
    @GetMapping("/accounts")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(){
        return new ResponseEntity<>( accountService.getAllAccounts(), HttpStatus.OK);
    }


    //URL: http://localhost:8080/api/bank/v1/accounts/
    //Method: POST
    /**
     * Metodo que crea una nueva cuenta
     * @param accountRequestDto La cuenta a crear
     * @return Una entidad de respuesta HTTP con la cuenta creada y el estado de la peticion
     */
    @Operation(summary = "Create a new account")
    @ApiResponse(responseCode = "201", description = "CREATED",
            content =  @Content(mediaType = "application/json",
            schema = @Schema(implementation = Account.class)))
    @PostMapping("/accounts")
    @Transactional
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto accountRequestDto){
        validation(accountRequestDto);
        existsAccountByNameCustomerAndNumberAccount(accountRequestDto);
        return new ResponseEntity<>(accountService.createAccount(accountRequestDto), HttpStatus.CREATED);
    }

    /**
     * Validacion de los datos de la cuenta
     * @param accountRequestDto La cuenta a validar
     * @throws ValidationException Si los datos de la cuenta no son validos
     */
    private void validation(AccountRequestDto accountRequestDto) {
        if (accountRequestDto.getNameCustomer() == null || accountRequestDto.getNameCustomer().isEmpty()) {
            throw new ValidationException("El nombre del cliente debe ser obligatorio");
        }
        if (accountRequestDto.getNameCustomer().length() >= 30) {
            throw new ValidationException("El nombre del cliente no debe exceder los 30 caracteres");
        }
        if (accountRequestDto.getNumberAccount() == null || accountRequestDto.getNumberAccount().isEmpty()) {
            throw new ValidationException("El número de cuenta debe ser obligatorio");
        }
        if (accountRequestDto.getNumberAccount().length() != 13) {
            throw new ValidationException("Number Account must have at least 5 characters");
        }
    }

    /**
     * Metodo que verifica si existe una cuenta por su nombre de cliente y numero de cuenta
     * @param accountRequestDto La cuenta a verificar
     */
    private void existsAccountByNameCustomerAndNumberAccount(AccountRequestDto accountRequestDto) {
        if (accountRepository.existsByNumberAccountAndNameCustomer(accountRequestDto.getNumberAccount(), accountRequestDto.getNameCustomer())) {
            throw new ValidationException("No se puede registrar la cuenta porque ya existe uno con estos datos");
        }
    }





}
