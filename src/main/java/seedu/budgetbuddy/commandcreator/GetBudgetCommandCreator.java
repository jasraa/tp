package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.GetBudgetCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetBudgetCommandCreator extends CommandCreator {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private ExpenseList expenses;
    private String input;
    private ArrayList<String> expenseCategories;

    public GetBudgetCommandCreator(ExpenseList expenses, String input) {
        this.expenses = expenses;
        this.input = input;
        this.expenseCategories = new ArrayList<>(Arrays.asList("Housing", "Groceries", "Utility", "Transport",
                "Entertainment", "Others"));
    }

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

    public Command createCommand() {
        LOGGER.log(Level.INFO, "Entering createCommand with input: " + input);
        String[] parts = input.split(" ");
        String category = null;

        for (String part : parts) {
            if (part.startsWith("c/")) {
                category = part.substring(2);
                LOGGER.log(Level.INFO, "Category extracted: " + category);
            }
        }

        if (category == null) {
            LOGGER.log(Level.WARNING, "Invalid command format or missing value for category");
            System.out.println("Invalid command format.");
            System.out.println("Expected format: get budget c/<category>");
            return null;
        }

        boolean isValidCategory = isValidExpenseCategory(category);
        if (!isValidCategory) {
            LOGGER.log(Level.WARNING, "Invalid category: " + category);
            System.out.println("Invalid category: " + category);
            System.out.println("Valid categories: Housing, Groceries, Utility, Transport, Entertainment, Others");
            return null;
        }

        LOGGER.log(Level.INFO, "Exiting createCommand. Command ready for execution.");
        return new GetBudgetCommand(expenses, category);
    }
}
