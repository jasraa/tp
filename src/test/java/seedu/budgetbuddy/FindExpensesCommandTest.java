package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.FindExpensesCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindExpensesCommandTest {

    @Test
    public void findExpensesCommand_createWithNullDescription_setsDescriptionToEmptyString() {
        ExpenseList expenses = new ExpenseList();
        FindExpensesCommand findExpensesCommand = new FindExpensesCommand(expenses, null
                , null, null);

        String description = findExpensesCommand.getDescription();

        assertEquals("", description);
    }

    @Test
    public void execute_validInput_printsMatchesAndNoExceptionsThrown() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "20", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");

        FindExpensesCommand findExpensesCommand = new FindExpensesCommand(expenses
                , "apple", null, null);

        findExpensesCommand.execute();
    }

    @Test
    public void execute_validInput_printsNoMatchesAndNoExceptionsThrown() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "20", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");

        FindExpensesCommand findExpensesCommand = new FindExpensesCommand(expenses
                , "chicken", null, null);

        findExpensesCommand.execute();
    }
}
