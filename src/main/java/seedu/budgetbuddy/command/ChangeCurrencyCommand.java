package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.CurrencyConverter;
import seedu.budgetbuddy.commons.DefaultCurrency;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.commons.SplitExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;

import java.util.Currency;

/**
 * @@author sweijie24
 */
public class ChangeCurrencyCommand extends Command {

    private Currency newCurrency;
    private SavingList savings;
    private ExpenseList expenses;
    private SplitExpenseList splitExpenses;
    private RecurringExpenseLists recurringExpenseLists;
    private CurrencyConverter currencyConverter;

    public ChangeCurrencyCommand(Currency newCurrency, SavingList savings, ExpenseList expenses, SplitExpenseList 
                                 splitExpenses, RecurringExpenseLists recurringExpenseLists, 
                                 CurrencyConverter currencyConverter) {
        this.newCurrency = newCurrency;
        this.savings = savings;
        this.expenses = expenses;
        this.splitExpenses = splitExpenses;
        this.recurringExpenseLists = recurringExpenseLists;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute() {
        currencyConverter.convertSavingCurrency(newCurrency, savings);
        currencyConverter.convertExpenseCurrency(newCurrency, expenses);
        currencyConverter.convertSplitExpenseCurrency(newCurrency, splitExpenses);
        currencyConverter.convertRecurringExpensesCurrency(newCurrency, recurringExpenseLists);
        currencyConverter.convertBudgetCurrency(newCurrency, expenses);
        DefaultCurrency.setDefaultCurrency(newCurrency);
    }
}
