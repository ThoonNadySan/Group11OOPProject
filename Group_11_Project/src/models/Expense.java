package models;

/**
 * Class Expense:
 * - **Inheritance**: Extends Transaction class
 * - **Polymorphism**: Overrides `getType()` method
 */

public class Expense extends Transaction {
    public Expense(String description, double amount, String category) {
        super(description, amount, category);
    }

    @Override
    public String getType() {
        return "Expense";
    }
}
