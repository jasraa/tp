package seedu.budgetbuddy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.ListBudgetCommand;
import seedu.budgetbuddy.command.GetBudgetCommand;
import seedu.budgetbuddy.commandcreator.GetBudgetCommandCreator;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.exception.BudgetBuddyException;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BudgetTest {
    private ExpenseList expenseList;

    @BeforeEach
    public void setUp() throws BudgetBuddyException {
        expenseList = new ExpenseList();
        expenseList.setBudget("Transport", 300);
        expenseList.setBudget("Groceries", 150);
        expenseList.addExpense("Transport", "50", "Bus ticket");
        expenseList.addExpense("Transport", "100", "Flight ticket");
        expenseList.addExpense("Groceries", "100", "Groceries");
    }

    @Test
    public void listBudgetCommand_allBudgets_success() {
        Command command = new ListBudgetCommand(expenseList);
        assertInstanceOf(ListBudgetCommand.class, command);
    }

    @Test
    public void getBudgetCommand_specificCategory_success() {
        String category = "Transport";
        GetBudgetCommandCreator creator = new GetBudgetCommandCreator(expenseList, "get budget c/" + category);
        Command command = creator.createCommand();

        assertInstanceOf(GetBudgetCommand.class, command);
        // Execute command and verify the output
        command.execute();
    }

    @Test
    public void getBudgetCommand_specificCategory_withExpensesListed() {
        String category = "Transport";
        GetBudgetCommandCreator creator = new GetBudgetCommandCreator(expenseList, "get budget c/" + category);
        GetBudgetCommand command = (GetBudgetCommand) creator.createCommand();

        // Execute command
        command.execute();
    }
}
