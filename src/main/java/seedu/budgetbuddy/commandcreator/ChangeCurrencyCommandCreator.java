package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.CurrencyConverter;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.commons.SplitExpenseList;
import seedu.budgetbuddy.command.ChangeCurrencyCommand;
import seedu.budgetbuddy.command.Command;

import java.util.Currency;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeCurrencyCommandCreator extends CommandCreator {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ExpenseList expenses;
    private SavingList savings;
    private SplitExpenseList splitExpenses;
    private RecurringExpenseLists recurringExpenseLists;
    private String input;
    private CurrencyConverter newCurrency;

    public ChangeCurrencyCommandCreator(String input, SavingList savings, ExpenseList expenses, SplitExpenseList splitExpenses, 
        RecurringExpenseLists recurringExpenseLists, CurrencyConverter newCurrency) {

        this.input = input;
        this.savings = savings;
        this.expenses = expenses;
        this.splitExpenses = splitExpenses;
        this.recurringExpenseLists = recurringExpenseLists;
        this.newCurrency = newCurrency;

    }

    /**
     * Parses the user input to create a ChangeCurrencyCommand for changing the default currency.
     * If the input is valid, a ChangeCurrencyCommand is returned with the specified new currency.
     *
     * @param input             The user input to be parsed.
     * @param savingList        The SavingList containing savings data.
     * @param expenseList       The ExpenseList containing expenses data.
     * @param currencyConverter The CurrencyConverter object for currency conversion.
     * @return A ChangeCurrencyCommand if the input is valid; otherwise, null.
     */
    public Command handleChangeCurrencyCommand(String input, SavingList savingList, ExpenseList expenseList,
                                               SplitExpenseList splitExpenses,
                                               RecurringExpenseLists recurringExpenseLists,
                                               CurrencyConverter currencyConverter) {
        if (input.toLowerCase().startsWith("change currency")) {
            String[] parts = input.split(" ");
            assert parts.length > 1 : "Input should contain currency code";

            if (parts.length == 3) {
                String currencyCode = parts[2];
                assert !currencyCode.isEmpty() : "Currency code should not be empty";

                try {
                    Currency newCurrency = Currency.getInstance(currencyCode.toUpperCase());
                    assert newCurrency != null : "Currency code should be valid";
                    LOGGER.log(Level.INFO, "Default currency changed to " + newCurrency);
                    return new ChangeCurrencyCommand(newCurrency, savingList, expenseList, splitExpenses, recurringExpenseLists 
                            , currencyConverter);
                } catch (IllegalArgumentException e) {
                    LOGGER.log(Level.WARNING, "Invalid currency code: " + currencyCode);
                    System.out.println("Invalid currency code.");
                    return null;
                }
            } else {
                LOGGER.log(Level.WARNING, "Invalid command format. Use 'change currency <currency_code>'.");
                System.out.println("Invalid command format. Use 'change currency <currency_code>'.");
                return null;
            }
        }
        return null;
    }
    @Override
    public Command createCommand() {
        return handleChangeCurrencyCommand(input, savings, expenses, splitExpenses, recurringExpenseLists, newCurrency);
    }
}
