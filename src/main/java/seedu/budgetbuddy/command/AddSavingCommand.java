package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.exception.BudgetBuddyException;

public class AddSavingCommand extends Command {

    private SavingList savings;
    private final String category;
    private final String amount;

    public AddSavingCommand(SavingList savings, String category, String amount) {
        this.category = category;
        this.amount = amount;
        this.savings = savings;
    }

    @Override
    public void execute(){
        try {

            savings.addSaving(this.category, this.amount);
            System.out.println("Savings Added to: " + category + " of $" + amount);
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
        }
    }
}
