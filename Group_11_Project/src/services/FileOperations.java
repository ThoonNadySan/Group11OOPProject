package services;

import java.io.IOException;


/**
 * Interface FileOperations:
 * - **Interface Concept**: Defines common file operations for FinanceManager
 * - Provides abstraction (FinanceManager implements these methods)
 */
public interface FileOperations {
    void saveToFile(String filename) throws IOException;
    void loadFromFile(String filename) throws IOException, ClassNotFoundException;
}
