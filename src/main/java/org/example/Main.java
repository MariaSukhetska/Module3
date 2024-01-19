package org.example;

import org.example.libruary.Injector;
import org.example.model.*;
import org.example.service.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final Injector injector =
                Injector.getInstance("org.example");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user = new User();
        user.setName("Maria Zhdanova");
        userService.add(user);

        AccountService accountService = (AccountService) injector.getInstance(AccountService.class);
        Account account = new Account();
        account.setName("Savings");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setUser(user);
        accountService.add(account);

        OperationService operationService = (OperationService) injector.getInstance(OperationService.class);
        Operation incomeOperation = new Operation();
        incomeOperation.setName("Salary");
        incomeOperation.setCategory(Category.INCOME);
        incomeOperation.setAmount(2000);
        incomeOperation.setCreatedAt(LocalDateTime.now());
        incomeOperation.setAccount(account);
        operationService.add(incomeOperation);

        Operation expenseOperation = new Operation();
        expenseOperation.setName("Shopping");
        expenseOperation.setCategory(Category.EXPENSE);
        expenseOperation.setAmount(50);
        expenseOperation.setCreatedAt(LocalDateTime.now());
        expenseOperation.setAccount(account);
        operationService.add(expenseOperation);


        List<Operation> accountOperations = operationService.findOperationsByAccount(account);
        System.out.println("Transactions for the account '" + account.getName() + "':");
       for (Operation op : accountOperations) {
           System.out.println(op);
        }


        ReportService reportService = (ReportService) injector.getInstance(ReportService.class);
        LocalDate fromDate = LocalDate.now().minusDays(7);
        LocalDate toDate = LocalDate.now();
        Report report = reportService.generateFinancialReport(user, fromDate, toDate);
        reportService.exportReport(report, "financial_report.csv");
    }
}
