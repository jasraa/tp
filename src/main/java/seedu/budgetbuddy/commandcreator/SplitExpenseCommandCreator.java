package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.SplitExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.SplitExpenseCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

public class SplitExpenseCommandCreator extends CommandCreator {

    private SplitExpenseList splitexpenses;
    private String input;

    public SplitExpenseCommandCreator(SplitExpenseList splitexpenses, String input) {
        this.splitexpenses = splitexpenses;
        this.input = input;
    }

    public Command handleSplitExpenseCommand(SplitExpenseList splitexpenses, String input) {
        if (input == null || !input.contains("a/") || !input.contains("n/") || !input.contains("d/")) {
            System.out.println("Invalid command format.");
            return null;
        }

        String amount = extractDetail(input, "a/", "n/");
        String numberOfPeople = extractDetail(input, "n/", "d/");
        String description = extractDetail(input, "d/", null); // Description is last, so no nextPrefix

        if (amount.isEmpty()|| numberOfPeople.isEmpty() || description.isEmpty()) {
            System.out.println("Missing details.");
            return null;
        }

        double amountValue;
        try {
            amountValue = Double.parseDouble(amount);
            if (amountValue <= 0 || amountValue > 1_000_000_000_000D) {
                throw new BudgetBuddyException(amount + " is not a valid amount. Amount must be positive and" +
                                                " less than or equal" + 
                                                " to 1,000,000,000,000.");
            }
            if (!amount.matches("^\\d+(\\.\\d{1,2})?$")) {
                throw new BudgetBuddyException("Amount must be a number with up to 2 decimal places.");
            }
        } catch (NumberFormatException | BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }

        int numberValue;
        try {
            numberValue = Integer.parseInt(numberOfPeople);
            if (numberValue <= 0) {
                throw new BudgetBuddyException(numberOfPeople + " is not a valid number.");
            }
        } catch (NumberFormatException | BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return new SplitExpenseCommand(splitexpenses, amount, numberOfPeople, description);
    }

    private String extractDetail(String input, String prefix, String nextPrefix) {
        try {
            int startIndex = input.indexOf(prefix) + prefix.length();
            int endIndex = nextPrefix != null ? input.indexOf(nextPrefix, startIndex) : input.length();
            if (endIndex == -1) {
                endIndex = input.length();
            }
            String detail = input.substring(startIndex, endIndex).trim(); 
            return detail.isEmpty() ? "" : detail;
        } catch (Exception e) {
            return ""; 
        }
    }

    @Override
    public Command createCommand() {
        return handleSplitExpenseCommand(splitexpenses, input);
    }
}
