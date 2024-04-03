package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.SplitExpenseList;

public class ListSplitExpenseCommand extends Command{
    private SplitExpenseList splitexpenses;

    /**
     * Creates a ListSplitExpenseCommand object.
     * 
     * @param splitexpenses The list of split expenses.
     */

    public ListSplitExpenseCommand(SplitExpenseList splitexpenses) {
        this.splitexpenses = splitexpenses;
    }

    /**
     * Lists all the split expenses.
     */
    @Override
    public void execute() {
        splitexpenses.listSplitExpenses();
    }
}
