package org.example.service;

import org.example.model.Report;
import org.example.model.User;

import java.time.LocalDate;

public interface ReportService {

    Report generateFinancialReport(User user, LocalDate fromDate, LocalDate toDate);

    void exportReport(Report report, String filePath);
}
