package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.GetExpenseInsightsCommand;
import seedu.budgetbuddy.ExpenseList;

public class GetExpenseInsightsCommandCreator extends CommandCreator {

    private ExpenseList expenseList;

    public GetExpenseInsightsCommandCreator(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public Command createCommand() {
        return new GetExpenseInsightsCommand(expenseList);
    }
}
