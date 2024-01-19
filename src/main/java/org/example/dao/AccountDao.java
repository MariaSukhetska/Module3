package org.example.dao;

import org.example.model.Account;
import org.example.model.Operation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountDao {
     List<Operation> findOperationsInPeriod
            (Account account, LocalDate fromDate, LocalDate toDate);

     Account add(Account account);

     void update(Account account);

     void delete(Account account);

     Optional<Account> findById(Long id);
}
