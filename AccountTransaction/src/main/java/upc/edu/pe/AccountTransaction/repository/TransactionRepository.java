package upc.edu.pe.AccountTransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.AccountTransaction.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByAccount_NumberAccount(String accountNumber);

    boolean existsByAccount_NumberAccountAndType(String accountNumber, String transactionType);

    List<Transaction> findByAccount_NumberAccount(String accountNumber);

    //findByDateBetween
    List<Transaction> findByCreateDateBetween(LocalDate dateStart, LocalDate dateEnd);

    //findByAccountNameCustomer
    List<Transaction> findByAccountNameCustomer(String nameCustomer);

}
