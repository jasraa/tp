package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.ExpenseList;

public class GetExpenseInsightsCommand extends Command {

    private ExpenseList expenseList;

    public GetExpenseInsightsCommand(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public void execute() {
        expenseList.getExpenseInsights();
    }
}
