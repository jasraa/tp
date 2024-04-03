package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.ChangeCurrencyCommand;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.commandcreator.ChangeCurrencyCommandCreator;
import seedu.budgetbuddy.commons.CurrencyConverter;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpensesList;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ChangeCurrencyCommandCreatorTest {


    @Test
    public void handleChangeCurrencyCommand_changeCurrencyToUSD_success() throws BudgetBuddyException {
        SavingList savingList = new SavingList();
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        CurrencyConverter currencyConverter = new CurrencyConverter();

        savingList.addSaving("Salary", "1000");

        String input = "change currency USD";

        ChangeCurrencyCommandCreator changeCurrencyCommandCreator = new ChangeCurrencyCommandCreator(input, savingList,
                expenseList, recurringExpensesList, currencyConverter);

        Command command = changeCurrencyCommandCreator.handleChangeCurrencyCommand(input, savingList,
                expenseList, recurringExpensesList, currencyConverter);

        assertEquals(ChangeCurrencyCommand.class, command.getClass());
    }

    @Test
    public void handleChangeCurrencyCommand_changeCurrency_invalidCurrencyCode() throws BudgetBuddyException {
        SavingList savingList = new SavingList();
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        CurrencyConverter currencyConverter = new CurrencyConverter();

        savingList.addSaving("Salary", "1000");

        String input = "change currency abc";
        ChangeCurrencyCommandCreator changeCurrencyCommandCreator = new ChangeCurrencyCommandCreator(input, savingList,
                expenseList, recurringExpensesList, currencyConverter);

        Command command = changeCurrencyCommandCreator.handleChangeCurrencyCommand(input, savingList,
                expenseList, recurringExpensesList, currencyConverter);
        assertNull(command);
    }

    @Test
    public void handleChangeCurrencyCommand_changeCurrency_invalidCommandFormat() throws BudgetBuddyException {
        SavingList savingList = new SavingList();
        ExpenseList expenseList = new ExpenseList();
        RecurringExpensesList recurringExpensesList = new RecurringExpensesList();
        CurrencyConverter currencyConverter = new CurrencyConverter();

        savingList.addSaving("Salary", "1000");

        String input = "change currency abc asd";
        ChangeCurrencyCommandCreator changeCurrencyCommandCreator = new ChangeCurrencyCommandCreator(input, savingList,
                expenseList, recurringExpensesList, currencyConverter);

        Command command = changeCurrencyCommandCreator.handleChangeCurrencyCommand(input, savingList,
                expenseList, recurringExpensesList, currencyConverter);
        assertNull(command);
    }
}
