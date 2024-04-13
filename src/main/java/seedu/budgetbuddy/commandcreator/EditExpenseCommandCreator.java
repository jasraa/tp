package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.EditExpenseCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

public class EditExpenseCommandCreator extends CommandCreator {
    private ExpenseList expenses;
    private String input;
    public EditExpenseCommandCreator(String input, ExpenseList expenses) {
        this.input = input;
        this.expenses = expenses;
    }

    /**
     * Parses the input command to extract parameters for editing an expense entry and then
     * creates a command to perform the edit operation. The input string is expected to contain
     * parts with prefixes indicating category (c/), index (i/), amount (a/), and description (d/).
     *
     * @param expenses The ExpenseList object containing the list of expenses to be edited.
     * @param input    The input command string containing the edit parameters.
     * @return An instance of EditExpenseCommand if the input is valid, or null if the input
     *         is invalid or incomplete.
     */
    public Command handleEditExpenseCommand(ExpenseList expenses, String input) {
        try {
            checkForInvalidInputs(input);
            checkForValidCategory(input);
            checkForInvalidAmount(input);
        } catch (IllegalArgumentException | BudgetBuddyException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Format : edit expense c/CATEGORY i/INDEX a/AMOUNT d/DESCRIPTION");
            return null;
        }
        String[] parts = input.split(" ");
        String category = null;
        int index = -1;
        double amount = -1;
        String description = null;

        for (String part : parts) {
            if (part.startsWith("c/")) {
                category = part.substring(2);
            } else if (part.startsWith("i/")) {
                index = Integer.parseInt(part.substring(2)); // Removed the redundant try-catch block
            } else if (part.startsWith("a/")) {
                amount = Double.parseDouble(part.substring(2));
            } else if (part.startsWith("d/")) {
                description = part.substring(2);
            }
        }

        if (category != null && index != -1 && amount != -1 && description != null) {
            return new EditExpenseCommand(expenses, category, index, amount, description);
        } else {
            // Handle incomplete command
            System.out.println("Incomplete command. Please ensure all parameters are included.");
            return null;
        }
    }


    public static void checkForInvalidInputs (String input) throws BudgetBuddyException {
        final String categoryPrefix = "c/";
        final String indexPrefix = "i/";
        final String amountPrefix = "a/";
        final String descriptionPrefix = "d/";

        if (input.contains("!") || input.contains("|")) {
            throw new BudgetBuddyException("Please do not include a ! or | in your input");
        }
        if (!input.contains("c/") || !input.contains("i/") || !input.contains("a/") || !input.contains("d/")) {
            throw new IllegalArgumentException("Please Ensure that you include c/, i/, a/ and d/");
        }

        String [] parameters = {categoryPrefix, indexPrefix, amountPrefix, descriptionPrefix};

        for (String parameter : parameters) {
            if (input.indexOf(parameter) != input.lastIndexOf(parameter)) {
                throw new BudgetBuddyException("Please ensure that you do not have duplicate parameters.");
            }
        }
    }

    public static void checkForValidCategory (String input) throws BudgetBuddyException {
        String[] parts = input.split(" ");
        String category = null;
        for (String part : parts) {
            if (part.startsWith("c/")) {
                category = part.substring(2);
                break;
            }
        }

        if (category == null || !(category.equals("Transport") || category.equals("Housing") ||
                category.equals("Groceries") || category.equals("Utility") ||
                category.equals("Entertainment") || category.equals("Others"))) {
            throw new BudgetBuddyException("Please enter a valid category: Housing, Groceries, Utility, Transport," +
                    "Entertainment or Others ");
        }

    }

    public static void checkForInvalidAmount(String input) throws BudgetBuddyException {
        String[] parts = input.split(" ");
        double amount = -1;

        for (String part : parts) {
            if (part.startsWith("a/")) {
                try {
                    amount = Double.parseDouble(part.substring(2));
                    if (amount <= 0) { // Amount must be greater than 0
                        throw new BudgetBuddyException("Invalid Amount. Amount must be greater than 0.");
                    }
                    break; // Break after finding the amount to stop checking other parts
                } catch (NumberFormatException e) {
                    throw new BudgetBuddyException("Invalid Amount. Amount should be a numerical value.");
                }
            }
        }

        if (amount == -1) {
            // If amount is still -1, it means no amount was entered
            throw new BudgetBuddyException("No amount specified. Please enter an amount using a/ prefix.");
        }
    }


    @Override
    public Command createCommand() {
        return handleEditExpenseCommand(expenses, input);
    }
}
