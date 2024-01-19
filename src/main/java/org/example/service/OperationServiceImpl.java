package org.example.service;

import org.example.dao.OperationDao;
import org.example.libruary.Inject;
import org.example.libruary.Service;
import org.example.model.Account;
import org.example.model.Operation;
import org.example.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {
    @Inject
    private OperationDao operationDao;

    @Override
    public Operation add(Operation operation) {
        if (operation.getAmount() <= 0 || operation.getCategory() == null) {
            throw new RuntimeException("Error: The amount of the operation must be positive "
                    + "and operation must have a category");
        }
        return operationDao.add(operation);
    }

    @Override
    public void update(Operation operation) {
      operationDao.update(operation);
    }

    @Override
    public void delete(Operation operation) {
       operationDao.delete(operation);
    }

    @Override
    public Optional<Operation> findById(Long id) {
        return operationDao.findById(id);
    }

    @Override
    public List<Operation> findOperationsByAccount(Account account) {
        return operationDao.findOperationsByAccount(account);
    }

    @Override
    public List<Operation> findOperationsByUserAndPeriod(User user, LocalDate fromDate, LocalDate toDate) {
        return operationDao.findOperationsByUserAndPeriod(user, fromDate, toDate);
    }
}
