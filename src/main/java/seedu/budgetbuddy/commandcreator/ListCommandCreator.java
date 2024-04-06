package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.ListExpenseCommand;
import seedu.budgetbuddy.command.ListSavingsCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListCommandCreator extends CommandCreator {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    protected ArrayList<String> expenseCategories;
    protected ArrayList<String> savingsCategories;
    private ExpenseList expenses;
    private SavingList savings;
    private String input;

    public ListCommandCreator(ExpenseList expenses, SavingList savings,
                              String input) {

        this.expenses = expenses;
        this.savings = savings;
        this.input = input;

        this.expenseCategories = new ArrayList<>(Arrays.asList("Housing",
                "Groceries", "Utility", "Transport", "Entertainment", "Others"));
        this.savingsCategories = new ArrayList<>(Arrays.asList("Salary",
                "Investments", "Gifts", "Others"));
    }

    /**
     * Checks if the given category is valid for expenses.
     *
     * @param category The category to be validated.
     * @return true if the category is valid for expenses; false otherwise.
     */
    private boolean isValidExpenseCategory(String category) {

        assert category != null : "Category should not be null";
        assert !category.isEmpty() : "Category should not be empty";

        for (String validCategory : expenseCategories) {
            if (validCategory.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given category is valid for savings.
     *
     * @param category The category to be validated.
     * @return true if the category is valid for savings; false otherwise.
     */
    private boolean isValidSavingsCategory(String category) {

        assert category != null : "Category should not be null";
        assert !category.isEmpty() : "Category should not be empty";

        for (String validCategory : savingsCategories) {
            if (validCategory.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses the user input to create a ListCommand for listing expenses or savings.
     * If the input is valid, a ListCommand is returned with the specified list type and optional filter category.
     *
     * @param input        The user input to be parsed.
     * @param expenseList  The ExpenseList containing expenses data.
     * @param savingList   The SavingList containing savings data.
     * @return A ListCommand if the input is valid; otherwise, null.
     */
    public Command handleListCommand(String input, ExpenseList expenseList, SavingList savingList) {
        assert input != null : "Input should not be null";
        assert !input.isEmpty() : "Input should not be empty";

        String[] parts = input.split(" ");
        assert parts.length >= 1 : "At least one part should be present in the input";

        String action = parts[0].toLowerCase();
        assert !action.isEmpty() : "Action should not be empty";

        switch (action) {
        case "list":
            if (parts.length == 2) {
                // List expenses or savings
                String listType = parts[1];
                assert !listType.isEmpty() : "List type should not be empty";

                if (listType.equalsIgnoreCase("expenses")) {
                    return new ListExpenseCommand(expenseList);
                } else if (listType.equalsIgnoreCase("savings")) {
                    return new ListSavingsCommand(savingList, expenseList);
                }
            } else if (parts.length == 3 && parts[1].equalsIgnoreCase("expenses")) {
                String filterCategory = parts[2];
                try {
                    // Checks for valid category input
                    if (filterCategory != null) {
                        boolean isValidCategory = isValidExpenseCategory(filterCategory);
                        if (!isValidCategory) {
                            LOGGER.warning("Invalid category inputted: " + filterCategory);
                            System.out.println("Invalid category: " + filterCategory);
                            return null;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    LOGGER.log(Level.WARNING, "Invalid category inputted: " + filterCategory, e);
                }
                return new ListExpenseCommand(expenseList, filterCategory);
            } else if (parts.length == 3 && parts[1].equalsIgnoreCase("savings")) {
                String filterCategory = parts[2];
                try {
                    // Checks for valid category input
                    if (filterCategory != null) {
                        boolean isValidCategory = isValidSavingsCategory(filterCategory);
                        if (!isValidCategory) {
                            LOGGER.warning("Invalid category inputted: " + filterCategory);
                            System.out.println("Invalid category: " + filterCategory);
                            return null;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    LOGGER.log(Level.WARNING, "Invalid category inputted: " + filterCategory, e);
                }
                return new ListSavingsCommand(savingList, expenseList, filterCategory); // Pass expenseList instance
            } else {
                return null;
            }
            break;
        default:
            return null;
        }return null;
    }

    @Override
    public Command createCommand() {
        return handleListCommand(input, expenses, savings);
    }
}
