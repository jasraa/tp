package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.commandcreator.CommandCreator;
import seedu.budgetbuddy.commandcreator.RecurringExpenseCommandCreator;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecurringExpenseCommandCreatorTest {

    @Test
    public void handleRecCommand_invalidCommandType_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec invalid Entertainment";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }
    @Test
    public void handleRecCommand_newListCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec newlist Entertainment";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNotNull(command);
        assertInstanceOf(RecurringExpenseCommand.class, command);
    }

    @Test
    public void handleRecCommand_newListCommandWithInvalidInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec newlist";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_removeListCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
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
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec removelist string";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_removeListCommandWithEmptyInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec removelist";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_viewListsCommand_returnsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec viewlists";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNotNull(command);
        assertInstanceOf(RecurringExpenseCommand.class, command);
    }

    @Test
    public void handleRecCommand_addRecCommandWithEmptyInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec addrec";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_addRecCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
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
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec addrec sdefwre";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_viewExpensesCommandWithEmptyInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec viewexpenses";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_viewExpensesCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
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
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec viewexpenses fdgder";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }


    @Test
    public void handleRecCommand_newExpenseCommandWithValidInput_createsRecurringExpenseCommand() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
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
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        expensesList.addNewRecurringList("Entertainment");
        String input = "rec newexpense to/1 c/Entertainment a/sdsdfsdf d/Movies";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_newExpenseToListCommandWithInvalidInput_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec newexpense to/ ";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_newExpenseToListCommandWithEmptyCategory_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec newexpense to/1 c/ a/200 d/ description";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_newExpenseToListCommandWithEmptyAmount_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec newexpense to/1 c/Entertainment a/ d/ description";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_newExpenseToListCommandWithEmptyDescription_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec newexpense to/1 c/Entertainment a/200 d/";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

    @Test
    public void handleRecCommand_newExpenseToListCommandWithEmptyListNumber_returnsNull() {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        String input = "rec newexpense to/ c/Entertainment a/200 d/";

        CommandCreator commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenseList);
        Command command = commandCreator.createCommand();

        assertNull(command);
    }

}
