package upc.edu.pe.AccountTransaction.service;

import upc.edu.pe.AccountTransaction.dto.request.TransactionRequestDto;
import upc.edu.pe.AccountTransaction.dto.response.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    public abstract TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto);

    public abstract TransactionResponseDto getTransactionById(Long id);

    public abstract List<TransactionResponseDto> getTransactionByNameCustomer(String nameCustomer);

    //getByDateRange
    public abstract List<TransactionResponseDto> getTransactionByDateRange(String dateStart, String dateEnd);
}
