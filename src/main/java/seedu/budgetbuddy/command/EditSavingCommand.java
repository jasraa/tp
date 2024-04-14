package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.SavingList;
public class EditSavingCommand extends Command{

    private SavingList savings;
    private String category;
    private double amount;

    public EditSavingCommand(SavingList savings, String category, double amount) {
        this.savings = savings;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void execute() {
        savings.editSaving(category, amount);
    }
}
