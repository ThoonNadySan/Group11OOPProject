package services;

import models.Transaction;
import models.Income;
import models.Expense;

import java.io.*;
import java.util.*;

/**
 * FinanceManager:
 * - **Implements Interfaces**: Implements FinanceManagerInterface and FileOperations
 * - **Uses Collections**: Uses List<Transaction> to store transactions
 * - **Exception Handling**: Catches invalid inputs and ensures robust execution
 */
public class FinanceManager {
    private List<Transaction> transactions;
    private Scanner scanner;
    private double budget;
    private double totalIncome;
    private double totalExpenses;
    private List<String> categories;

    public FinanceManager() {
        transactions = new ArrayList<>();
        scanner = new Scanner(System.in);
        budget = 0;
        categories = new ArrayList<>(Arrays.asList("Salary", "Food", "Rent", "Entertainment"));
    }

    public void setBudget() {
        System.out.print("Enter your monthly budget: ");
        budget = getValidDoubleInput();
        System.out.println("Budget set to: $" + budget);
    }

    public void addTransaction() {
        System.out.print("Enter transaction type (1-Income, 2-Expense): ");
        int type = getValidIntInput(1, 2);

        System.out.print("Enter description: ");
        String description = getValidDescription();

        System.out.print("Enter amount: ");
        double amount = getValidDoubleInput();

        String category = chooseCategory();

        Transaction transaction;
        if (type == 1) {
            transaction = new Income(description, amount, category);
            totalIncome += amount;
        } else {
            transaction = new Expense(description, amount, category);
            totalExpenses += amount;
        }

        transactions.add(transaction);
        System.out.println("Transaction added successfully!");

        if (totalExpenses > budget * 0.9) {
            System.out.println("⚠ Warning: Your expenses are close to exceeding your budget!");
        }
    }

    public void displayTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }

        System.out.println("\n=== Transaction History ===");
        int index = 1;
        for (Transaction t : transactions) {
            System.out.println(index++ + ". " + t);
        }
    }

    public void displaySummary() {
        double savings = totalIncome - totalExpenses;
        System.out.println("\n=== Financial Summary ===");
        System.out.println("Total Income: $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpenses);
        System.out.println("Net Savings: $" + savings);
    }

    public void exportToCSV() {
        try (PrintWriter writer = new PrintWriter(new File("transactions.csv"))) {
            writer.println("Type,Description,Amount,Category,Date");
            for (Transaction t : transactions) {
                writer.println(t.getType() + "," + t.getDescription() + "," + t.getAmount() + "," + t.getCategory() + "," + t.getTimestamp());
            }
            System.out.println("Transactions exported to transactions.csv");
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    private int getValidIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    private double getValidDoubleInput() {
        while (true) {
            try {
                double input = Double.parseDouble(scanner.nextLine().trim());
                if (input > 0) {
                    return input;
                }
                System.out.println("Amount must be greater than zero! Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid amount.");
            }
        }
    }

    private String getValidDescription() {
        while (true) {
            String description = scanner.nextLine().trim();
            if (!description.isEmpty() && !description.matches("\\d+")) {
                return description;
            }
            System.out.println("Description cannot be empty or only numbers. Please enter a valid description.");
        }
    }

    private String chooseCategory() {
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        System.out.println((categories.size() + 1) + ". Other (Create New Category)");

        int choice = getValidIntInput(1, categories.size() + 1);
        if (choice == categories.size() + 1) {
            System.out.print("Enter new category name: ");
            String newCategory = scanner.nextLine().trim();
            categories.add(newCategory);
            return newCategory;
        }
        return categories.get(choice - 1);
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(transactions);
            oos.writeDouble(budget);
            oos.writeDouble(totalIncome);
            oos.writeDouble(totalExpenses);
            oos.writeObject(categories);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            transactions = (List<Transaction>) ois.readObject();
            budget = ois.readDouble();
            totalIncome = ois.readDouble();
            totalExpenses = ois.readDouble();
            categories = (List<String>) ois.readObject();
        }
    }

    public void run() {
        try {
            loadFromFile("finance_data.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        }

        while (true) {
            System.out.println("\n=== Personal Finance Tracker ===");
            System.out.println("1. Set Budget");
            System.out.println("2. Add Transaction");
            System.out.println("3. View Transactions");
            System.out.println("4. View Summary");
            System.out.println("5. Export to CSV");
            System.out.println("6. Save & Exit");
            System.out.print("Choose an option: ");

            int choice = getValidIntInput(1, 6);

            switch (choice) {
                case 1 -> setBudget();
                case 2 -> addTransaction();
                case 3 -> displayTransactions();
                case 4 -> displaySummary();
                case 5 -> exportToCSV();
                case 6 -> {
                    try {
                        saveToFile("finance_data.ser");
                        System.out.println("Data saved. Exiting...");
                    } catch (IOException e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    return;
                }
            }
        }
    }
}
