package seedu.budgetbuddy.command;

import seedu.budgetbuddy.CurrencyConverter;
import seedu.budgetbuddy.DefaultCurrency;
import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.SavingList;

import java.util.Currency;

public class ChangeCurrencyCommand extends Command {

    private Currency newCurrency;
    private SavingList savings;
    private ExpenseList expenses;
    private CurrencyConverter currencyConverter;

    public ChangeCurrencyCommand(Currency newCurrency, SavingList savings, ExpenseList expenses,
                                 CurrencyConverter currencyConverter) {
        this.newCurrency = newCurrency;
        this.savings = savings;
        this.expenses = expenses;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute() {
        currencyConverter.convertSavingCurrency(newCurrency, savings);
        currencyConverter.convertExpenseCurrency(newCurrency, expenses);
        DefaultCurrency.setDefaultCurrency(newCurrency);
    }
}
