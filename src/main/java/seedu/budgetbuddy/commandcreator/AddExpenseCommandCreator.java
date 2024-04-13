package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.command.AddExpenseCommand;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.logging.Level;

/**
 * Creates an AddExpenseCommand object.
 */
public class AddExpenseCommandCreator extends CommandCreator {
    private ExpenseList expenses;
    private String input;

    public AddExpenseCommandCreator(ExpenseList expenses, String input) {
        this.expenses = expenses;
        this.input = input;
    }

    /**
     * Parses the input and creates a new AddExpenseCommand object.
     * 
     * @param expenses The list of expenses.
     * @param input The input string.
     * @return The AddExpenseCommand object.
     */
    public Command handleAddExpenseCommand(ExpenseList expenses, String input) {
        if (input == null || !input.contains("c/") || !input.contains("a/") || !input.contains("d/")) {
            System.out.println("Invalid command format.");
            return null;
        }
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Expense details are missing.");
            return null;
        }
        String details = parts[1];

        String category = extractDetailsForAdd(details, "c/");
        if (category.isEmpty()) {
            System.out.println("category is missing.");
            return null;
        }
        String amount = extractDetailsForAdd(details, "a/");
        if (amount.isEmpty()) {
            System.out.println("amount is missing.");
            return null;
        }
        if (input.contains("!") || input.contains("|")) {
            System.out.println("Please do not include a ! or | in your input");
            return null;
        }

        try {
            double amountValue = Double.parseDouble(amount);
            if (amountValue <= 0) {
                throw new BudgetBuddyException(amount + " is not a valid amount.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
            return null;
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }

        String description = extractDetailsForAdd(details, "d/");
        if (description.isEmpty()) {
            System.out.println("description is missing.");
            return null;
        }
        return new AddExpenseCommand(expenses, category, amount, description);
    }

    /**
     * Extracts the details for the add command.
     * 
     * @param details The details string.
     * @param prefix The prefix to search for.
     * @return The extracted details.
     */
    private String extractDetailsForAdd(String details, String prefix) {
        int startIndex = details.indexOf(prefix) + prefix.length();
        int endIndex = details.length();

        String[] nextPrefixes = { "c/", "a/", "d/" };
        for (String nextPrefix : nextPrefixes) {
            if (details.indexOf(nextPrefix, startIndex) != -1 && details.indexOf(nextPrefix, startIndex) < endIndex) {
                endIndex = details.indexOf(nextPrefix, startIndex);
            }
        }
        return details.substring(startIndex, endIndex).trim();
    }

    @Override
    public Command createCommand(){
        return handleAddExpenseCommand(expenses, input);
    }
}
