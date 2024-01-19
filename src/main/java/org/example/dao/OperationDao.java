package org.example.dao;

import org.example.model.Account;
import org.example.model.Operation;
import org.example.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OperationDao {
    Operation add(Operation operation);

    void update(Operation operation);

    void delete(Operation operation);

    Optional<Operation> findById(Long id);

    List<Operation> findOperationsByAccount(Account account);

    List<Operation> findOperationsByUserAndPeriod(User user,
                                                  LocalDate fromDate, LocalDate toDate);

}
