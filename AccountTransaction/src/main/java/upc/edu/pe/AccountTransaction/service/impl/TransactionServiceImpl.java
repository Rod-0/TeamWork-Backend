package upc.edu.pe.AccountTransaction.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upc.edu.pe.AccountTransaction.dto.TransactionDto;
import upc.edu.pe.AccountTransaction.dto.request.TransactionRequestDto;
import upc.edu.pe.AccountTransaction.dto.response.TransactionResponseDto;
import upc.edu.pe.AccountTransaction.exception.ResourceNotFoundException;

import upc.edu.pe.AccountTransaction.model.Transaction;

import upc.edu.pe.AccountTransaction.repository.TransactionRepository;
import upc.edu.pe.AccountTransaction.service.TransactionService;

import java.time.LocalDate;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper){
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) {

        var newTransaction = modelMapper.map(transactionRequestDto, Transaction.class);
        return modelMapper.map(transactionRepository.save(newTransaction), TransactionResponseDto.class);

    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for ID: " + id));

        return modelMapper.map(transaction, TransactionResponseDto.class);
    }

    @Override
    public List<TransactionResponseDto> getTransactionByNameCustomer(String nameCustomer) {
        List<Transaction> transactions = transactionRepository.findByAccountNameCustomer(nameCustomer);

        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionResponseDto.class))
                .toList();
    }

    @Override
    public List<TransactionResponseDto> getTransactionByDateRange(String dateStart, String dateEnd) {
        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dateEnd);

        List<Transaction> transactions = transactionRepository.findByCreateDateBetween(startDate, endDate);


        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionResponseDto.class))
                .toList();
    }
}
