package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.ExpenseList;

public class ListExpenseCommand extends Command {
    private ExpenseList expenses;
    private String filterCategory;

    /**
     * Creates a ListExpenseCommand object.
     * 
     * @param expenses The list of expenses.
     */
    public ListExpenseCommand(ExpenseList expenses) {
        this.expenses = expenses;
    }

    /**
     * Creates a ListExpenseCommand object.
     * 
     * @param expenses The list of expenses.
     * @param filterCategory The category to filter the expenses by.
     */

    public ListExpenseCommand(ExpenseList expenses, String filterCategory) {
        this.expenses = expenses;
        this.filterCategory = filterCategory;
    }

    /**
     * Lists all the expenses.
     */
    @Override
    public void execute() {
        expenses.listExpenses(filterCategory);
    }
}
