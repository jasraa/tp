package seedu.budgetbuddy.command;

import seedu.budgetbuddy.CurrencyConverter;
import seedu.budgetbuddy.DefaultCurrency;
import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.SavingList;
import seedu.budgetbuddy.RecurringExpensesList;

import java.util.Currency;

public class ChangeCurrencyCommand extends Command {

    private Currency newCurrency;
    private SavingList savings;
    private ExpenseList expenses;
    private RecurringExpensesList recurringExpensesList;
    private CurrencyConverter currencyConverter;

    public ChangeCurrencyCommand(Currency newCurrency, SavingList savings, ExpenseList expenses,
                                 RecurringExpensesList recurringExpensesList, CurrencyConverter currencyConverter) {
        this.newCurrency = newCurrency;
        this.savings = savings;
        this.expenses = expenses;
        this.recurringExpensesList = recurringExpensesList;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute() {
        currencyConverter.convertSavingCurrency(newCurrency, savings);
        currencyConverter.convertExpenseCurrency(newCurrency, expenses);
        currencyConverter.convertRecurringExpensesCurrency(newCurrency, recurringExpensesList);
        DefaultCurrency.setDefaultCurrency(newCurrency);
    }
}
