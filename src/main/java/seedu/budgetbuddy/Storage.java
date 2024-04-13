package seedu.budgetbuddy;

import seedu.budgetbuddy.commons.Saving;
import seedu.budgetbuddy.commons.SplitExpense;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.Expense;
import seedu.budgetbuddy.commons.RecurringExpenseLists;
import seedu.budgetbuddy.commons.RecurringExpenseList;
import seedu.budgetbuddy.commons.DefaultCurrency;

import seedu.budgetbuddy.exception.BudgetBuddyException;
import seedu.budgetbuddy.exception.InvalidRecurringExpensesFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Storage {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String filePath;

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

    /**
     * Loads a list of expenses from a file.
     * If an exception occurs during the loading process (e.g., if the file is corrupted),
     * the expenses list file will be reset.
     *
     * @return A list of {@link Expense} objects loaded from the file.
     * @throws IOException If an error occurs when accessing the file.
     */
    public List<Expense> loadExpenses() throws IOException {
        File file = new File(filePath);
        List<Expense> expenses = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0].trim());
                String category = parts[1].trim();
                double amount = Double.parseDouble(parts[2].trim());
                String description = parts[3].trim();
                Expense expense = new Expense(date, category, amount, description);
                expenses.add(expense);
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Exception successfully caught. Error has been handled");
            System.out.println(e.getMessage());
            System.out.println("Your Expenses File is corrupted, resetting the file....");
            resetExpenseListFile();
            return expenses;
        } finally {
            scanner.close();
        }
        return expenses;
    }

    /**
     * Saves a list of expenses to a file.
     * If an IOException occurs, the expenses list file will be reset.
     *
     * @param expenses A list of {@link Expense} objects to save to the file.
     * @throws IOException If an error occurs during writing to the file.
     */
    public void saveExpenses(List<Expense> expenses) throws IOException {
        ensureDirectoryExists(); // Ensure directory and file exist before writing
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, false);
            for (Expense expense : expenses) {
                writer.write(String.format("%s | %s | %.2f | %s\n",
                        expense.getDateAdded(), expense.getCategory(), expense.getAmount(), expense.getDescription()));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException occurred while saving expenses. " +
                    "Resetting expense list file.", e);
            resetExpenseListFile(); // Reset the expense list file if an exception occurs
            throw e; // Re-throw the exception to indicate that saving was not successful
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Resets the expense list file. If the file exists, it is deleted and a new empty file is created.
     *
     * @throws IOException If deleting the existing file or creating a new file fails.
     */
    public void resetExpenseListFile() throws IOException {
        File file = new File(filePath);
        file.delete();
        file.createNewFile();
        FileWriter writer = new FileWriter(filePath, false);
        writer.write("");
        writer.close();
    }

    /**
     * Loads a list of savings from the specified file.
     * If an exception occurs during the loading process (e.g., if the file is corrupted),
     * the savings list file will be reset.
     *
     * @return A list of {@link Saving} objects representing the savings loaded from the file.
     * @throws IOException If there is an issue with file access that prevents the method from reading the savings.
     */
    public List<Saving> loadSavings() throws IOException {
        List<Saving> savings = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String category = parts[0].trim();
                double amount = Double.parseDouble(parts[1].trim());
                Saving saving = new Saving(category, amount);
                savings.add(saving);
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Exception caught while loading savings. Resetting savings list file.", e);
            System.out.println(e.getMessage());
            System.out.println("Your Savings File is corrupted, resetting the file....");
            resetSavingsListFile();
            return savings;
        } finally {
            scanner.close();
        }
        return savings;
    }

    /**
     * Saves the list of savings to the specified file.
     * If an IOException occurs, the savings list file will be reset.
     *
     * @param savings A list of {@link Saving} objects that represent the savings to save to the file.
     * @throws IOException If an IOException occurs during file writing, indicating the savings could not be saved.
     */
    public void saveSavings(List<Saving> savings) throws IOException {
        ensureDirectoryExists(); // Ensure directory and file exist before writing
        FileWriter writer = null;
        try  {
            writer = new FileWriter(filePath, false);
            for (Saving saving : savings) {
                writer.write(String.format("%s | %.2f\n",
                        saving.getCategory(), saving.getAmount()));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException occurred while saving savings. Resetting savings list file.", e);
            resetSavingsListFile();
            throw e;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Resets the savings list file. If the file exists, it is deleted, and a new empty file is created.
     * This method is typically called when the file is found to be corrupted or
     * when an issue arises during file operations.
     *
     * @throws IOException If there is an issue with file access that prevents the method
     *                     from deleting or creating the file.
     */
    public void resetSavingsListFile() throws IOException {
        File file = new File(filePath);
        file.delete();
        file.createNewFile();
        FileWriter writer = new FileWriter(filePath, false);
        writer.write("");
        writer.close();
    }

    public void resetRecurringExpensesListFile() throws IOException {
        File file = new File(filePath);
        file.delete();
        file.createNewFile();
        FileWriter writer = new FileWriter(filePath, false);
        writer.write("");
        writer.close();
    }

    public void parseRecurringExpensesFile(ArrayList<ExpenseList> recurringExpenses, String line)
            throws BudgetBuddyException{

        if (line.startsWith("!!!")) {
            int indexOfStartExclamation = line.indexOf("!!!", 0);
            int indexOfStartOfListName = indexOfStartExclamation + 3;

            int indexOfEndExclamation = line.indexOf("!!!", 4);
            int indexOfEndOfListName = indexOfEndExclamation;

            String name = line.substring(indexOfStartOfListName, indexOfEndOfListName).trim();
            ExpenseList expenses = new RecurringExpenseList(name, new ArrayList<>());

            recurringExpenses.add(expenses);
        } else {
            String[] parts = line.split("\\|");

            if (parts.length > 5) {
                LOGGER.log(Level.WARNING, "Invalid RecurringExpensesFile detected, throwing Exception");
                throw new BudgetBuddyException("Invalid Format of Line : There should only be 4 Dividers");
            }

            int listNumber = Integer.parseInt(parts[0].trim());
            LocalDate dateAdded = LocalDate.parse(parts[1].trim());
            String category = parts[2].trim();
            double amount = Double.parseDouble(parts[3].trim());
            String description = parts[4].trim();
            Expense expense = new Expense(dateAdded, category, amount, description);

            int listNumberAsArrayIndex = listNumber - 1;
            ExpenseList expenses = recurringExpenses.get(listNumberAsArrayIndex);
            expenses.getExpenses().add(expense);
        }

    }
    public RecurringExpenseLists loadRecurringExpensesList() throws IOException{
        File file = new File(filePath);
        ArrayList<ExpenseList> recurringExpenses = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    continue;
                }
                parseRecurringExpensesFile(recurringExpenses, line);
            }

            scanner.close();

            RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists(recurringExpenses);
            return recurringExpenseLists;
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Exception successfully caught. Error has been handled");
            System.out.println(e.getMessage());
            System.out.println("You Recurring Expenses File is corrupted, resetting the file....");
            resetRecurringExpensesListFile();
            return new RecurringExpenseLists();
        }

    }

    public void saveRecurringExpenses(RecurringExpenseLists recurringExpenseLists)
            throws InvalidRecurringExpensesFileException, IOException {

        ensureDirectoryExists();

        try {
            FileWriter writer = new FileWriter(filePath, false);
            int numberOfRecurringExpenseList = recurringExpenseLists.getSize();

            for (int i = 0; i < numberOfRecurringExpenseList; i++) {
                int listNumber = i + 1;
                ExpenseList expenseList = recurringExpenseLists.getExpenseListAtListNumber(listNumber);
                ArrayList<Expense> expenses = expenseList.getExpenses();
                String listName = expenseList.getName();

                writer.write(String.format("!!! %s !!!\n", listName));

                for (Expense expense : expenses) {
                    writer.write(String.format("%d | %s | %s | %.2f | %s\n"
                            , listNumber
                            , expense.getDateAdded(), expense.getCategory()
                            , expense.getAmount(), expense.getDescription()));
                }

            }
            writer.close();

        } catch (IOException e) {
            resetRecurringExpensesListFile();
            throw new InvalidRecurringExpensesFileException("The RecurringExpensesFile seems to not be valid" +
                    ", file has been reinitialized. Run a command to save your recurringexpenses");
        }

    }

    /**
     * Saves the default currency to the specified file path.
     *
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void saveCurrency() throws IOException {
        assert filePath != null : "File path should not be null";

        ensureDirectoryExists();

        FileWriter writer = new FileWriter(filePath, false);

        try {
            Currency currentCurrency = DefaultCurrency.getDefaultCurrency();
            writer.write("Default Currency: " + currentCurrency);
            writer.close();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Problem saving currency code", e);
        }
    }


    public List<SplitExpense> loadSplitExpenses() throws FileNotFoundException {
        File file = new File(filePath);
        List<SplitExpense> splitExpenses = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
            // Assuming the order is Date|Amount|Number of People|Description
            String amount = parts[1].trim();
            String numberOfPeople = parts[2].trim();
            String description = parts[3].trim();
            SplitExpense splitExpense = new SplitExpense(amount, numberOfPeople, description);
            splitExpenses.add(splitExpense);
        }
        scanner.close();
        return splitExpenses;
    }

    public void saveSplitExpenses(List<SplitExpense> splitExpenses) throws IOException {

        ensureDirectoryExists(); 
        
        FileWriter writer = new FileWriter(filePath, false); 
        for (SplitExpense splitExpense : splitExpenses) {
            writer.write(String.format("%s | %s | %s\n",
                    splitExpense.getAmount(), splitExpense.getNumberOfPeople(), splitExpense.getDescription()));
        }
        writer.close();
    }


    /**
     * Loads currency data from the specified file path and sets the default currency accordingly.
     *
     * @throws FileNotFoundException if the specified file path does not exist
     */
    public void loadCurrency() throws FileNotFoundException {

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

            DefaultCurrency.setDefaultCurrency(currency);
        }
        scanner.close();
    }
}
