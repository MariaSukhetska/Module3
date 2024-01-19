package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Report {
    private User user;
    private LocalDate fromDate;
    private LocalDate toDate;
    private List<Operation> operations;

    public Report(User user, LocalDate fromDate, LocalDate toDate, List<Operation> operations) {
        this.user = user;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.operations = operations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "Report{"
                + "user=" + user
                + ", fromDate=" + fromDate
                + ", toDate=" + toDate
                + ", operations=" + operations
                + '}';
    }
}
