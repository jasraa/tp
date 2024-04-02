package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringExpenseCommandTest {

    @Test
    public void execute_addRecCommand_addsTwoExpensesToExpenses() throws BudgetBuddyException {
        ExpenseList overallExpenses = new ExpenseList();
        overallExpenses.addExpense("Entertainment", "200", "first");
        overallExpenses.addExpense("Entertainment", "200", "second");

        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("list1");

        RecurringExpenseCommand add = new RecurringExpenseCommand(1,recurringExpensesList
                ,"Entertainment", 200.00,"description", "newexpense" );

        RecurringExpenseCommand secondAdd = new RecurringExpenseCommand(1,recurringExpensesList
               ,"Entertainment", 500.00,"description", "newexpense" );

        add.execute();
        secondAdd.execute();

        RecurringExpenseCommand command = new RecurringExpenseCommand(1, recurringExpensesList
                , overallExpenses,"addrec");

        command.execute();

        assertEquals(4, overallExpenses.getExpenses().size());


    }

    @Test
    public void execute_addRecCommandwithOutOfBoundsIndex_overallExpensesSizeUnchanged() throws BudgetBuddyException {
        ExpenseList overallExpenses = new ExpenseList();
        overallExpenses.addExpense("Entertainment", "200", "first");
        overallExpenses.addExpense("Entertainment", "200", "second");

        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("list1");

        RecurringExpenseCommand add = new RecurringExpenseCommand(1,recurringExpensesList
                ,"Entertainment", 200.00,"description", "newexpense" );

        RecurringExpenseCommand secondAdd = new RecurringExpenseCommand(1,recurringExpensesList
                ,"Entertainment", 500.00,"description", "newexpense" );

        add.execute();
        secondAdd.execute();

        RecurringExpenseCommand command = new RecurringExpenseCommand(2, recurringExpensesList
                , overallExpenses,"addrec");

        command.execute();

        assertEquals(2, overallExpenses.getExpenses().size());

    }

    @Test
    public void execute_viewExpensesWithOutOfBoundsIndex_printsErrorMessageNoExceptionThrown() {

        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("list1");


        RecurringExpenseCommand command = new RecurringExpenseCommand(2, recurringExpensesList
                , "viewexpenses");

        command.execute();
    }

    @Test
    public void execute_viewExpensesWithValidIndex_printsOutputWithNoExceptionThrown() {

        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("list1");


        RecurringExpenseCommand command = new RecurringExpenseCommand(1, recurringExpensesList
                , "viewexpenses");

        command.execute();
    }
    @Test
    public void execute_viewEmptyListOfRecurringExpensesList_printsListWithoutExceptions() {
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(recurringExpensesList
                , "viewlists");

        recurringExpenseCommand.execute();
    }

    @Test
    public void execute_viewNonEmptyListOfRecurringExpensesList_printsListWithoutExceptions() {
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("listOne");
        recurringExpensesList.addNewRecurringList("listTwo");
        recurringExpensesList.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(recurringExpensesList
                , "viewlists");

        recurringExpenseCommand.execute();
    }

    @Test
    public void execute_addNewList_sizeOfOverallListIncreasedByOne() {
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("listOne");
        recurringExpensesList.addNewRecurringList("listTwo");
        recurringExpensesList.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand("listFour"
                , recurringExpensesList, "newlist");

        recurringExpenseCommand.execute();

        assertEquals(4, recurringExpensesList.getSize());

    }
    @Test
    public void execute_removeList_sizeOfOverallListReducedByOne(){
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("listOne");
        recurringExpensesList.addNewRecurringList("listTwo");
        recurringExpensesList.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(2,
                recurringExpensesList, "removelist" );

        recurringExpenseCommand.execute();

        assertEquals(2, recurringExpensesList.getSize());
    }

    @Test
    public void execute_removeListWithOutOfBoundsListNumber_sizeOfOverallListStaysSame(){
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("listOne");
        recurringExpensesList.addNewRecurringList("listTwo");
        recurringExpensesList.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(4,
                recurringExpensesList, "removelist" );

        recurringExpenseCommand.execute();

        assertEquals(3, recurringExpensesList.getSize());
    }

    @Test
    public void execute_newExpenseCommandWithInvalidCategory_sizeOfRecurringExpenseListUnchanged() {

        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("list1");

        RecurringExpenseCommand newExpenseCommand = new RecurringExpenseCommand(1,recurringExpensesList
                ,"invalid", 500.00,"description", "newexpense" );


        newExpenseCommand.execute();

        assertEquals(0, recurringExpensesList.getExpenseListAtListNumber(1).getExpenses().size());

    }

    @Test
    public void execute_newExpenseCommand_sizeOfRecurringExpenseListIncreaseByOne() {

        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        recurringExpensesList.addNewRecurringList("list1");

        RecurringExpenseCommand newExpenseCommand = new RecurringExpenseCommand(1,recurringExpensesList
                ,"Entertainment", 500.00,"description", "newexpense" );


        newExpenseCommand.execute();

        assertEquals(1, recurringExpensesList.getExpenseListAtListNumber(1).getExpenses().size());

    }
}
