package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.FindExpensesCommand;
import seedu.budgetbuddy.command.MenuCommand;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.commons.SplitExpenseList;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;



public class ParserTest {


    @Test 
    public void parseCommand_invalidCommand_returnsNull() {
        Parser parser = new Parser();
        ExpenseList expenses = new ExpenseList();
        SavingList savings = new SavingList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        SplitExpenseList splitExpenseList = new SplitExpenseList();
        Command invalidCommand = parser.parseCommand(expenses, savings, splitExpenseList,
                expensesList, "NotaCommand");

        assertNull(invalidCommand);
    }

    @Test
    public void parseCommand_menuCommand_returnsMenuCommand() {
        Parser parser = new Parser();
        ExpenseList expenses = new ExpenseList();
        SavingList savings = new SavingList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        SplitExpenseList splitExpenseList = new SplitExpenseList();

        String input = "menu 1";

        Command command = parser.parseCommand(expenses, savings, splitExpenseList, expensesList, input);

        assertInstanceOf(MenuCommand.class, command);
    }

    @Test
    public void parseCommand_findExpensesCommand_returnsFindExpensesCommand() {
        Parser parser = new Parser();
        ExpenseList expenses = new ExpenseList();
        SavingList savings = new SavingList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        SplitExpenseList splitExpenseList = new SplitExpenseList();

        String input = "find expenses d/hello morethan/200 lessthan/300";

        Command command = parser.parseCommand(expenses, savings, splitExpenseList, expensesList, input);

        assertInstanceOf(FindExpensesCommand.class, command);
    }

    @Test
    public void parseCommand_recCommand_returnsRecurringExpenseCommand() {
        Parser parser = new Parser();
        ExpenseList expenses = new ExpenseList();
        SavingList savings = new SavingList();
        RecurringExpenseLists expensesList = new RecurringExpenseLists();
        SplitExpenseList splitExpenseList = new SplitExpenseList();

        String input = "rec newlist listname";

        Command command = parser.parseCommand(expenses, savings, splitExpenseList, expensesList, input);

        assertInstanceOf(RecurringExpenseCommand.class, command);
    }


}
