package models;

/**
 * Class Expense:
 * **Compilation Command:** (Ensure models directory is included)
 * javac -d out -cp src src/models/Expense.java
 */

 /**
  * The reasons for using inheritance
  *Instead of writing the same code multiple times, a child class inherits methods and properties from a parent class.
  *A subclass can modify the behavior of a method inherited from its superclass.
  *New features can be added without modifying existing code.
  */

public class Expense extends Transaction // **Inheritance**: Extends Transaction class
 {
    public Expense(String description, double amount, String category) {
        super(description, amount, category);
    }

    @Override
    public String getType() // - **Polymorphism**: Overrides `getType()` method
    {
        return "Expense";
    }
}
    

