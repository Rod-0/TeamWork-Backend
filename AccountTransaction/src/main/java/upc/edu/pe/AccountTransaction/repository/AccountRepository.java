package upc.edu.pe.AccountTransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upc.edu.pe.AccountTransaction.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Boolean existsByNumberAccountAndNameCustomer(String numberAccount, String nameCustomer);

    //getAccountByNumberAndName
    Account findByNumberAccountAndNameCustomer(String accountNumber, String nameCustomer);


}
