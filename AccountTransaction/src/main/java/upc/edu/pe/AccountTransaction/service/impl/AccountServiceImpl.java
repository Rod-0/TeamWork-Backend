package upc.edu.pe.AccountTransaction.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import upc.edu.pe.AccountTransaction.dto.request.AccountRequestDto;
import upc.edu.pe.AccountTransaction.dto.response.AccountResponseDto;
import upc.edu.pe.AccountTransaction.exception.ResourceNotFoundException;
import upc.edu.pe.AccountTransaction.model.Account;
import upc.edu.pe.AccountTransaction.repository.AccountRepository;
import upc.edu.pe.AccountTransaction.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        if(accountRepository.existsByNumberAccountAndNameCustomer(accountRequestDto.getNumberAccount(), accountRequestDto.getNameCustomer())){
            throw new RuntimeException("The account already exists");
        }
        var newAccount = modelMapper.map(accountRequestDto, Account.class);
        return modelMapper.map(accountRepository.save(newAccount), AccountResponseDto.class);
    }


    @Override
    public AccountResponseDto getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The account that could not be found is : " + id));

        return modelMapper.map(account, AccountResponseDto.class);

    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> modelMapper.map(account, AccountResponseDto.class))
                .toList();
    }

    @Override
    public AccountResponseDto getAccountByNumberAndName(String accountNumber, String nameCustomer) {
        Account account = accountRepository.findByNumberAccountAndNameCustomer(accountNumber, nameCustomer);
        return modelMapper.map(account, AccountResponseDto.class);

    }



}
