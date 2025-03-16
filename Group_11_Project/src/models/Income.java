package models;


/**
 * Class Income:
 * - **Inheritance**: Extends Transaction class
 * - **Polymorphism**: Overrides `getType()` method
 */

public class Income extends Transaction {
    public Income(String description, double amount, String category) {
        super(description, amount, category);
    }

    @Override
    public String getType() {
        return "Income";
    }
}
