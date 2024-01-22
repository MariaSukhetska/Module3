package org.example.service;

import org.example.library.Inject;
import org.example.library.Service;
import org.example.model.Operation;
import org.example.model.Report;
import org.example.model.User;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Inject
    private OperationService operationService;

    @Override
    public Report generateFinancialReport(User user, LocalDate fromDate, LocalDate toDate) {
        List<Operation> operations = operationService
                .findOperationsByUserAndPeriod(user, fromDate, toDate);
        return new Report(user, fromDate, toDate, operations);
    }

    @Override
    public void exportReport(Report report, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID, Account ID, Name, Category, Amount, Created At");
            for (Operation operation : report.getOperations()) {
                writer.println(
                        operation.getId() + ", " +
                                operation.getAccount().getId() + ", " +
                                operation.getName() + ", " +
                                operation.getCategory() + ", " +
                                operation.getAmount() + ", " +
                                operation.getCreatedAt());
            }

            System.out.println("Report exported successfully to: " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting report to CSV: " + e.getMessage());
        }
    }
}
