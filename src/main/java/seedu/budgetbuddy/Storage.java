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
import java.util.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Storage {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String filePath;

    private ArrayList<String> expenseCategories = new ArrayList<>(Arrays.asList("Housing"
            , "Groceries", "Utility", "Transport", "Entertainment", "Others"));

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


    private void checkValidAmount(Double amount) throws BudgetBuddyException{
        if (amount <= 0) {
            throw new BudgetBuddyException("Invalid Amount detected. Possible Corrupted File");
        }
    }

    private void checkValidCategory(String category) throws BudgetBuddyException {
        if (!expenseCategories.contains(category)) {
            throw new BudgetBuddyException("Invalid Category detected. Possible Corrupted File");
        }
    }

    private void checkValidDescription(String description) throws BudgetBuddyException {
        if (description.contains("|") || description.contains("!") || description.isEmpty()) {
            throw new BudgetBuddyException("Invalid description detected. Possible Corrupted File");
        }
    }

    private void checkValidTitle(String line) throws BudgetBuddyException {
        int indexOfEndExclamation = line.indexOf("!!!", 4);
        int endIndexOfEndExclamation = indexOfEndExclamation + "!!!".length();

        if (endIndexOfEndExclamation != line.length() || line.contains("|")) {
            throw new BudgetBuddyException("Invalid ListName title detected. Possible Corrupted File");
        }
    }

    private void checkValidListName(String listName) throws BudgetBuddyException {
        if (listName.contains("!") || listName.contains("|") || listName.isEmpty()) {
            throw new BudgetBuddyException("Invalid listName detected. Possible Corrupted File");
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
            LocalDate date = LocalDate.parse(parts[0].trim());
            String category = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            String description = parts[3].trim();
            Expense expense = new Expense(date, category, amount, description);
            expenses.add(expense);
        }
        scanner.close();
        return expenses;
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
            checkValidTitle(line);
            int indexOfStartExclamation = line.indexOf("!!!", 0);
            int indexOfStartOfListName = indexOfStartExclamation + 3;

            int indexOfEndExclamation = line.indexOf("!!!", 4);
            int indexOfEndOfListName = indexOfEndExclamation;

            String name = line.substring(indexOfStartOfListName, indexOfEndOfListName).trim();
            checkValidListName(name);

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
            checkValidCategory(category);

            double amount = Double.parseDouble(parts[3].trim());
            checkValidAmount(amount);

            String description = parts[4].trim();
            checkValidDescription(description);

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
            System.out.println("Error Detected : " + e.getMessage());
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
