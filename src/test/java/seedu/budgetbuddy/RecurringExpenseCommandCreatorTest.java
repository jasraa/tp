package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.commandcreator.CommandCreator;
import seedu.budgetbuddy.commandcreator.RecurringExpenseCommandCreator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecurringExpenseCommandCreatorTest {
    @Test
    public void handleRecCommand_newListCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        String input = "rec newlist Entertainment";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNotNull(command);
        assertInstanceOf(RecurringExpenseCommand.class, command);
    }

    @Test
    public void handleRecCommand_newListCommandWithInvalidInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        String input = "rec newlist";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_removeListCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec removelist 1";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNotNull(command);
        assertInstanceOf(RecurringExpenseCommand.class, command);
    }

    @Test
    public void handleRecCommand_removeListCommandWithInvalidInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        String input = "rec removelist string";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_removeListCommandWithEmptyInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        String input = "rec removelist";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_newExpenseCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec newexpense to/1 c/Entertainment a/100 d/Movies";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNotNull(command);
        assertInstanceOf(RecurringExpenseCommand.class, command);
    }

    @Test
    public void handleRecCommand_newExpenseCommandWithInvalidAmount_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec newexpense to/1 c/Entertainment a/sdsdfsdf d/Movies";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_addRecCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec addrec 1";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNotNull(command);
        assertInstanceOf(RecurringExpenseCommand.class, command);
    }

    @Test
    public void handleRecCommand_addRecCommandWithInvalidInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        String input = "rec addrec sdefwre";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_viewExpensesCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec viewexpenses 1";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNotNull(command);
        assertInstanceOf(RecurringExpenseCommand.class, command);
    }

    @Test
    public void handleRecCommand_viewExpensesCommandWithInvalidInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList expensesList = new RecurringExpensesList();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec viewexpenses fdgder";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }
}
