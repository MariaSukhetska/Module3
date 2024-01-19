package org.example.service;

import org.example.model.Account;
import org.example.model.Operation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account add(Account account);

    void update(Account account);

    void delete(Account account);

    Optional<Account> findById(Long id);

    List<Operation> findOperationsInPeriod(Account account, LocalDate fromDate, LocalDate toDate);
}
