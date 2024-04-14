package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringExpenseCommandTest {

    @Test
    public void execute_addRecCommand_addsTwoExpensesToExpenses() throws BudgetBuddyException {
        ExpenseList overallExpenses = new ExpenseList();
        overallExpenses.addExpense("Entertainment", "200", "first");
        overallExpenses.addExpense("Entertainment", "200", "second");

        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("list1");

        RecurringExpenseCommand add = new RecurringExpenseCommand(1, recurringExpenseLists
                ,"Entertainment", 200.00,"description", "newexpense" );

        RecurringExpenseCommand secondAdd = new RecurringExpenseCommand(1, recurringExpenseLists
               ,"Entertainment", 500.00,"description", "newexpense" );

        add.execute();
        secondAdd.execute();

        RecurringExpenseCommand command = new RecurringExpenseCommand(1, recurringExpenseLists
                , overallExpenses,"addrec");

        command.execute();

        assertEquals(4, overallExpenses.getExpenses().size());


    }

    @Test
    public void execute_addRecCommandwithOutOfBoundsIndex_overallExpensesSizeUnchanged() throws BudgetBuddyException {
        ExpenseList overallExpenses = new ExpenseList();
        overallExpenses.addExpense("Entertainment", "200", "first");
        overallExpenses.addExpense("Entertainment", "200", "second");

        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("list1");

        RecurringExpenseCommand add = new RecurringExpenseCommand(1, recurringExpenseLists
                ,"Entertainment", 200.00,"description", "newexpense" );

        RecurringExpenseCommand secondAdd = new RecurringExpenseCommand(1, recurringExpenseLists
                ,"Entertainment", 500.00,"description", "newexpense" );

        add.execute();
        secondAdd.execute();

        RecurringExpenseCommand command = new RecurringExpenseCommand(2, recurringExpenseLists
                , overallExpenses,"addrec");

        command.execute();

        assertEquals(2, overallExpenses.getExpenses().size());

    }

    @Test
    public void execute_viewExpensesWithOutOfBoundsIndex_printsErrorMessageNoExceptionThrown() {

        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("list1");


        RecurringExpenseCommand command = new RecurringExpenseCommand(2, recurringExpenseLists
                , "viewexpenses");

        command.execute();
    }

    @Test
    public void execute_viewExpensesWithValidIndex_printsOutputWithNoExceptionThrown() {

        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("list1");


        RecurringExpenseCommand command = new RecurringExpenseCommand(1, recurringExpenseLists
                , "viewexpenses");

        command.execute();
    }
    @Test
    public void execute_viewEmptyListOfRecurringExpensesList_printsListWithoutExceptions() {
        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(recurringExpenseLists
                , "viewlists");

        recurringExpenseCommand.execute();
    }

    @Test
    public void execute_viewNonEmptyListOfRecurringExpensesList_printsListWithoutExceptions() {
        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("listOne");
        recurringExpenseLists.addNewRecurringList("listTwo");
        recurringExpenseLists.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(recurringExpenseLists
                , "viewlists");

        recurringExpenseCommand.execute();
    }

    @Test
    public void execute_addNewList_sizeOfOverallListIncreasedByOne() {
        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("listOne");
        recurringExpenseLists.addNewRecurringList("listTwo");
        recurringExpenseLists.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand("listFour"
                , recurringExpenseLists, "newlist");

        recurringExpenseCommand.execute();

        assertEquals(4, recurringExpenseLists.getSize());

    }
    @Test
    public void execute_removeList_sizeOfOverallListReducedByOne(){
        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("listOne");
        recurringExpenseLists.addNewRecurringList("listTwo");
        recurringExpenseLists.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(2,
                recurringExpenseLists, "removelist" );

        recurringExpenseCommand.execute();

        assertEquals(2, recurringExpenseLists.getSize());
    }

    @Test
    public void execute_removeListWithOutOfBoundsListNumber_sizeOfOverallListStaysSame(){
        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("listOne");
        recurringExpenseLists.addNewRecurringList("listTwo");
        recurringExpenseLists.addNewRecurringList("listThree");

        RecurringExpenseCommand recurringExpenseCommand = new RecurringExpenseCommand(4,
                recurringExpenseLists, "removelist" );

        recurringExpenseCommand.execute();

        assertEquals(3, recurringExpenseLists.getSize());
    }


    @Test
    public void execute_newExpenseCommand_sizeOfRecurringExpenseListIncreaseByOne() {

        RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();
        recurringExpenseLists.addNewRecurringList("list1");

        RecurringExpenseCommand newExpenseCommand = new RecurringExpenseCommand(1, recurringExpenseLists
                ,"Entertainment", 500.00,"description", "newexpense" );


        newExpenseCommand.execute();

        assertEquals(1, recurringExpenseLists.getExpenseListAtListNumber(1).getExpenses().size());

    }
}
