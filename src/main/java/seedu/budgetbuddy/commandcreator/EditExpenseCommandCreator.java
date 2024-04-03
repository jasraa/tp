package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.EditExpenseCommand;

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
        String[] parts = input.split(" ");
        String category = null;
        int index = -1;
        double amount = -1;
        String description = null;

        for (String part : parts) {
            if (part.startsWith("c/")) {
                category = part.substring(2);
            } else if (part.startsWith("i/")) {
                try {
                    index = Integer.parseInt(part.substring(2));
                } catch (NumberFormatException e) {
                    // Handle invalid index format
                    return null;
                }
            } else if (part.startsWith("a/")) {
                try {
                    amount = Double.parseDouble(part.substring(2));
                } catch (NumberFormatException e) {
                    // Handle invalid amount format
                    System.out.println("Invalid Amount. Amount should be a numerical value.");
                    return null;
                }
            } else if (part.startsWith("d/")) {
                description = part.substring(2);
            }
        }

        // Validate required fields
        if (category != null && index != -1 && amount != -1 && description != null) {
            return new EditExpenseCommand(expenses, category, index, amount, description);
        } else {
            // Handle incomplete command
            return null;
        }
    }

    @Override
    public Command createCommand() {
        return handleEditExpenseCommand(expenses, input);
    }
}
