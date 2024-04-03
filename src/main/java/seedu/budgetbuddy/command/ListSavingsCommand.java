package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.SavingList;

public class ListSavingsCommand extends Command {
    private SavingList savings;
    private ExpenseList expenses;
    private String filterCategory;

    /**
     * Creates a ListSavingsCommand object.
     * 
     * @param savings The list of savings.
     * @param expenses The list of expenses.
     */
    public ListSavingsCommand(SavingList savings, ExpenseList expenses) {
        this.savings = savings;
        this.expenses = expenses;
    }

    /**
     * Creates a ListSavingsCommand object.
     * 
     * @param savings The list of savings.
     * @param expenses The list of expenses.
     * @param filterCategory The category to filter the savings by.
     */
    public ListSavingsCommand(SavingList savings, ExpenseList expenses, String filterCategory) {
        this.savings = savings;
        this.expenses = expenses;
        this.filterCategory = filterCategory;
    }

    /**
     * Lists all the savings.
     */

    @Override
    public void execute() {
        savings.listSavings(this.filterCategory, this.expenses);
    }
}

