package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.exception.BudgetBuddyException;


public class AddExpenseCommand extends Command{
    private ExpenseList expenses;
    private final String category;
    private final String amount;
    private final String description;


    public AddExpenseCommand (ExpenseList expenses,String category, String amount, String description) {
        this.expenses = expenses;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public void execute() {
        try {
            expenses.addExpense(this.category,this.amount,this.description);
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
        }
    }
}
