package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.CurrencyConverter;
import seedu.budgetbuddy.commons.DefaultCurrency;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;

import java.util.Currency;

public class ChangeCurrencyCommand extends Command {

    private Currency newCurrency;
    private SavingList savings;
    private ExpenseList expenses;
    private RecurringExpenseLists recurringExpenseLists;
    private CurrencyConverter currencyConverter;

    public ChangeCurrencyCommand(Currency newCurrency, SavingList savings, ExpenseList expenses,
                                 RecurringExpenseLists recurringExpenseLists, CurrencyConverter currencyConverter) {
        this.newCurrency = newCurrency;
        this.savings = savings;
        this.expenses = expenses;
        this.recurringExpenseLists = recurringExpenseLists;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute() {
        currencyConverter.convertSavingCurrency(newCurrency, savings);
        currencyConverter.convertExpenseCurrency(newCurrency, expenses);
        currencyConverter.convertRecurringExpensesCurrency(newCurrency, recurringExpenseLists);
        DefaultCurrency.setDefaultCurrency(newCurrency);
    }
}
