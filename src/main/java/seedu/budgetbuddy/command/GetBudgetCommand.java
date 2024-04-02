package seedu.budgetbuddy.command;

import seedu.budgetbuddy.ExpenseList;

public class GetBudgetCommand extends Command {
    private ExpenseList expenseList;
    private String category;

    public GetBudgetCommand(ExpenseList expenseList, String category) {
        this.expenseList = expenseList;
        this.category = category;
    }

    @Override
    public void execute() {
        expenseList.getBudgetAndListExpensesForCategory(category);
    }
}
