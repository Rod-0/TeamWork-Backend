package upc.edu.pe.AccountTransaction.service;

import upc.edu.pe.AccountTransaction.dto.TransactionDto;
import upc.edu.pe.AccountTransaction.model.Transaction;

import java.util.List;

public interface TransactionService {
    public abstract TransactionDto createTransaction(Transaction transaction);

    public abstract TransactionDto getTransactionById(Long id);

    public abstract List<TransactionDto> getTransactionByNameCustomer(String nameCustomer);

    //getByDateRange
    public abstract List<TransactionDto> getTransactionByDateRange(String dateStart, String dateEnd);
}
