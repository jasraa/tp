package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.commons.CurrencyConverter;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.commons.Saving;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.Expense;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.Currency;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author sweijie24
public class CurrencyConverterTest {

    @Test
    public void convertAmount_convertSameDefaultCurrency_success() {
        CurrencyConverter converter = new CurrencyConverter();
        double amount = 100.0;
        Currency currency = Currency.getInstance("SGD");
        double convertedAmount = converter.convertAmount(amount, currency, currency);
        assertEquals(amount, convertedAmount);
    }

    @Test
    public void convertAmount_convertDifferentCurrency_success() {
        CurrencyConverter converter = new CurrencyConverter();
        double amount = 100.0;
        Currency currency = Currency.getInstance("SGD");
        Currency newCurrency = Currency.getInstance("USD");
        double convertedAmount = converter.convertAmount(amount, currency, newCurrency);
        assertEquals(75.0, convertedAmount);
    }

    @Test
    public void convertAmount_convertDifferentCurrencies_success() {
        CurrencyConverter converter = new CurrencyConverter();
        double amount = 100.0;
        Currency currency = Currency.getInstance("USD");
        Currency newCurrency = Currency.getInstance("JPY");
        double convertedAmount = converter.convertAmount(amount, currency, newCurrency);
        assertEquals("14966.67", String.format("%.2f", convertedAmount));
    }

    @Test
    public void convertSavingCurrency_convertCurrenciesInSavingList_success() throws BudgetBuddyException {
        CurrencyConverter converter = new CurrencyConverter();
        SavingList savings = new SavingList();
        savings.addSaving("Salary", "1000");
        savings.addSaving("Investments", "200");
        Currency newCurrency = Currency.getInstance("USD");

        converter.convertSavingCurrency(newCurrency, savings);

        for (Saving saving : savings.getSavings()) {
            assertEquals(newCurrency, saving.getCurrency());
        }
    }

    @Test
    public void convertExpenseCurrency_convertCurrenciesInExpenseList_success() throws BudgetBuddyException {
        CurrencyConverter converter = new CurrencyConverter();
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Transport", "1000", "MRT");
        expenses.addExpense("Housing", "200", "BTO");
        Currency newCurrency = Currency.getInstance("USD");

        converter.convertExpenseCurrency(newCurrency, expenses);

        for (Expense expense : expenses.getExpenses()) {
            assertEquals(newCurrency, expense.getCurrency());
        }
    }
}
