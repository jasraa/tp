package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.SavingList;

public class ReduceSavingCommand extends Command {
    private String category;
    private double amount;
    private SavingList savings;


    public ReduceSavingCommand(SavingList savings, String category, double amount) {
        this.savings = savings;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void execute() {
        if (savings != null) {
            savings.reduceSavingsByCategory(category, amount);
        } else {
            System.out.println("Savings list not initialized.");
        }
    }
}


