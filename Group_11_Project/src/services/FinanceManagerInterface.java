package services;

import java.io.IOException;

public interface FinanceManagerInterface extends FileOperations {
    void setBudget();
    void addTransaction();
    void displayTransactions();
    void displaySummary();
    //void editTransaction();
    void exportToCSV();
}
