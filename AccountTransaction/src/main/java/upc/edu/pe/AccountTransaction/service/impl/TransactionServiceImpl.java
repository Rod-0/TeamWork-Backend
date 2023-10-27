package upc.edu.pe.AccountTransaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upc.edu.pe.AccountTransaction.dto.TransactionDto;
import upc.edu.pe.AccountTransaction.exception.ResourceNotFoundException;

import upc.edu.pe.AccountTransaction.model.Transaction;

import upc.edu.pe.AccountTransaction.repository.TransactionRepository;
import upc.edu.pe.AccountTransaction.service.TransactionService;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public TransactionDto createTransaction(Transaction transaction) {

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDto(savedTransaction.getId(), savedTransaction.getType(), savedTransaction.getAmount(), savedTransaction.getBalance(),savedTransaction.getCreateDate());

    }

    @Override
    public TransactionDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for ID: " + id));

        return new TransactionDto(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getCreateDate()

        );
    }

    @Override
    public List<TransactionDto> getTransactionByNameCustomer(String nameCustomer) {
        List<Transaction> transactions = transactionRepository.findByAccountNameCustomer(nameCustomer);

        return transactions.stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getBalance(),
                        transaction.getCreateDate()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionByDateRange(String dateStart, String dateEnd) {
        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dateEnd);

        List<Transaction> transactions = transactionRepository.findByCreateDateBetween(startDate, endDate);


        return transactions.stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getBalance(),
                        transaction.getCreateDate()

                ))
                .collect(Collectors.toList());
    }
}
