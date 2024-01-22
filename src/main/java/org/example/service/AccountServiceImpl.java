package org.example.service;

import org.example.dao.AccountDao;
import org.example.library.Inject;
import org.example.library.Service;
import org.example.model.Account;
import org.example.model.Operation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Inject
    private AccountDao accountDao;

    @Override
    public Account add(Account account) {
        return accountDao.add(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Account account) {
        accountDao.delete(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public List<Operation> findOperationsInPeriod(Account account,
                                                  LocalDate fromDate, LocalDate toDate) {
        return accountDao.findOperationsInPeriod(account, fromDate, toDate);
    }
}
