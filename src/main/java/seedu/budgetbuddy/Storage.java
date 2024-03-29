package seedu.budgetbuddy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private final String filePath;
    private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());


    public Storage(String filePath) {
        this.filePath = filePath;
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // This will create the directory if it doesn't exist
        }
        try {
            file.createNewFile(); // This will create the file if it doesn't exist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> loadExpenses() throws FileNotFoundException {
        File file = new File(filePath);
        List<Expense> expenses = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
            // Assuming the order is Date|Category|Amount|Description
            String category = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            String description = parts[3].trim();
            Expense expense = new Expense(category, amount, description);
            expenses.add(expense);
        }
        scanner.close();
        return expenses;
    }

    public void saveExpenses(List<Expense> expenses) throws IOException {
        ensureDirectoryExists(); // Ensure directory and file exist before writing
        FileWriter writer = new FileWriter(filePath, false); // Overwrite the file
        for (Expense expense : expenses) {
            writer.write(String.format("%s | %s | %.2f | %s\n",
                    expense.getDateAdded(), expense.getCategory(), expense.getAmount(), expense.getDescription()));
        }
        writer.close();
    }

    // Inside Storage.java

    public List<Saving> loadSavings() throws FileNotFoundException {
        File file = new File(filePath);
        List<Saving> savings = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
            // Assuming the order is Category|Amount
            String category = parts[0].trim();
            double amount = Double.parseDouble(parts[1].trim());
            Saving saving = new Saving(category, amount);
            savings.add(saving);
        }
        scanner.close();
        return savings;
    }

    public void saveSavings(List<Saving> savings) throws IOException {
        ensureDirectoryExists(); // Ensure directory and file exist before writing
        FileWriter writer = new FileWriter(filePath, false); // Overwrite the file
        for (Saving saving : savings) {
            writer.write(String.format("%s | %.2f\n",
                    saving.getCategory(), saving.getAmount()));
        }
        writer.close();
    }

    /**
     * Saves currency information to the file specified in the constructor based on the provided expenses and savings.
     * If both expenses and savings are empty, the default currency is set to SGD. If either expenses or savings is
     * empty, the default currency is set to the currency of the non-empty list.
     *
     * @param expenses the list of expenses to consider for determining the default currency
     * @param savings  the list of savings to consider for determining the default currency
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void saveCurrency(List<Expense> expenses, List<Saving> savings) throws IOException {
        assert expenses != null : "Expense list should not be null";
        assert savings != null : "Saving list should not be null";
        assert filePath != null : "File path should not be null";

        ensureDirectoryExists();

        FileWriter writer = new FileWriter(filePath, false);

        try {
            if (savings.isEmpty() && expenses.isEmpty()) {
                writer.write("Default Currency: SGD");
                writer.close();
            } else if (!savings.isEmpty() && !expenses.isEmpty()) {
                assert savings.get(0).getCurrency().equals(expenses.get(0).getCurrency()) :
                        "Savings and expenses currency should be equal";
                writer.write(String.format("Default Currency: %s\n", savings.get(0).getCurrency()));
                writer.close();
            } else if (savings.isEmpty()) {
                assert !expenses.isEmpty() : "Expenses should not be empty when savings are empty";

                writer.write(String.format("Default Currency: %s\n", expenses.get(0).getCurrency()));
                writer.close();
            } else {
                assert !savings.isEmpty() : "Savings should not be empty when expenses are empty";

                writer.write(String.format("Default Currency: %s\n", savings.get(0).getCurrency()));
                writer.close();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Problem saving currency code", e);
        }
    }

    /**
     * Loads currency information from the file specified in the constructor and updates the currencies of expenses
     * and savings accordingly.
     *
     * @param expenses the list of expenses to update with the loaded currency
     * @param savings  the list of savings to update with the loaded currency
     * @throws FileNotFoundException if the specified file path does not exist
     */
    public void loadCurrency(List<Expense> expenses, List<Saving> savings) throws FileNotFoundException {

        assert expenses != null : "Expense list should not be null";
        assert savings != null : "Saving list should not be null";
        assert filePath != null : "File path should not be null";

        File file = new File(filePath);
        assert file.exists() : "Currency file does not exist";
        assert file.isFile() : "Currency file is not a regular file";

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            assert line != null : "Line should not be null";

            String[] parts = line.split(": ");
            assert parts.length == 2 : "Invalid line format";

            String currencyCode = parts[1].trim();
            assert !currencyCode.isEmpty() : "Currency code should not be empty";

            Currency currency = Currency.getInstance(currencyCode);

            for (Saving saving : savings) {
                assert saving != null : "Saving should not be null";
                saving.setCurrency(currency);
            }

            for (Expense expense : expenses) {
                assert expense != null : "Expense should not be null";
                expense.setCurrency(currency);
            }
        }
        scanner.close();
    }

}
