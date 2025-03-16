package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract class Transaction: 
 * - Implements **Polymorphism** (Different transactions behave differently)
 * - Implements **Serializable** (Allows saving to file)
 */

public abstract class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String description;
    protected double amount;
    protected String category;
    protected LocalDateTime timestamp;

    public Transaction(String description, double amount, String category) {
        if (description.matches("\\d+")) {
            throw new IllegalArgumentException("Description cannot be only numbers!");
        }
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.timestamp = LocalDateTime.now();
    }

    public abstract String getType();

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getType() + ": " + description + " | Amount: $" + amount + " | Category: " + category + " | Date: " + getTimestamp();
    }
}
