package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.EditSavingCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

public class EditSavingsCommandCreator extends CommandCreator {
    private SavingList savings;
    private String input;
    public EditSavingsCommandCreator (String input, SavingList savings) {
        this.input = input;
        this.savings = savings;
    }

    /**
     * Parses the input command to extract the parameters for editing a saving entry and then
     * initiates the edit operation. The command string is expected to contain indicators
     * followed by the values for category (c/) and amount (a/).
     *
     * @param savings The SavingList object that contains the list of savings.
     * @param input   The input command string containing the parameters to edit a saving entry.
     * @return A Command object to execute the edit operation or null if the input is invalid.
     */
    //@@author jasraa
    public Command handleEditSavingCommand(SavingList savings, String input) {
        try {
            checkForInvalidInputs(input);
            checkForValidCategory(input);
            checkForInvalidAmount(input);
        } catch (IllegalArgumentException | BudgetBuddyException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Format : edit savings c/CATEGORY a/AMOUNT");
            return null;
        }

        String category = null;
        double amount = -1;

        if (input.contains("c/") && input.contains("a/")) {
            category = input.substring(input.indexOf("c/") + 2, input.indexOf("a/")).trim();
            amount = Double.parseDouble(input.substring(input.indexOf("a/") + 2).trim());
        }

        if (category != null && amount != -1) {
            return new EditSavingCommand(savings, category, amount);
        } else {
            // Handle incomplete command
            return null;
        }
    }

    public static void checkForInvalidInputs (String input) throws BudgetBuddyException {
        final String categoryPrefix = "c/";
        final String amountPrefix = "a/";

        if (input.contains("!") || input.contains("|")) {
            throw new BudgetBuddyException("Please do not include a ! or | in your input");
        }
        if (!input.contains("c/") || !input.contains("a/")) {
            throw new IllegalArgumentException("Please Ensure that you include c/ and a/");
        }

        String [] parameters = {categoryPrefix, amountPrefix};

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

        if (category == null || !(category.equalsIgnoreCase("Salary") ||
                category.equalsIgnoreCase("Investments") || category.equalsIgnoreCase("Gifts")
                || category.equalsIgnoreCase("Others"))) {
            throw new BudgetBuddyException("Please enter a valid category: Salary, Investments, Gifts and Others");
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
        return handleEditSavingCommand(savings, input);
    }

}
