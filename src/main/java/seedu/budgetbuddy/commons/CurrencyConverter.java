package seedu.budgetbuddy.commons;

import seedu.budgetbuddy.Ui;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//@author sweijie24
public class CurrencyConverter {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Map<Currency, Double> exchangeRates;
    private Ui ui = new Ui();

    public CurrencyConverter() {
        this.exchangeRates = new HashMap<>();
        // Initialize exchange rates with default values
        exchangeRates.put(Currency.getInstance("SGD"), 1.0);
        exchangeRates.put(Currency.getInstance("USD"), 0.75);
        exchangeRates.put(Currency.getInstance("EUR"), 0.68);
        exchangeRates.put(Currency.getInstance("JPY"), 112.25);
        exchangeRates.put(Currency.getInstance("KRW"), 996.85);
        exchangeRates.put(Currency.getInstance("MYR"), 3.51);
        exchangeRates.put(Currency.getInstance("CNY"), 5.36);
        exchangeRates.put(Currency.getInstance("HKD"), 5.80);
    }


    /**
     * Converts an amount from one currency to another using exchange rates.
     *
     * @author sweijie24
     * @param amount      The amount to be converted.
     * @param fromCurrency The currency of the original amount.
     * @param toCurrency  The currency to which the amount is to be converted.
     * @return The converted amount in the target currency.
     * @throws IllegalArgumentException If exchange rates are not available for one or both currencies,
     *                                  or if exchange rates are not positive numbers.
     */
    public double convertAmount(double amount, Currency fromCurrency, Currency toCurrency) {
        if (!exchangeRates.containsKey(fromCurrency) || !exchangeRates.containsKey(toCurrency)) {
            LOGGER.warning("Exchange rates not available for one or more currencies");
            throw new IllegalArgumentException("Exchange rates not available for one or more currencies");
        }
        assert exchangeRates.containsKey(fromCurrency) : "Exchange rates not available for fromCurrency: "
                + fromCurrency;
        assert exchangeRates.containsKey(toCurrency) : "Exchange rates not available for toCurrency: " + toCurrency;


        double fromRate = exchangeRates.get(fromCurrency);
        double toRate = exchangeRates.get(toCurrency);

        if (fromRate <= 0 || toRate <= 0) {
            LOGGER.warning("Exchange rates must be positive numbers");
            throw new IllegalArgumentException("Exchange rates must be positive numbers");
        }
        assert fromRate > 0 : "Exchange rate for fromCurrency must be a positive number: " + fromRate;
        assert toRate > 0 : "Exchange rate for toCurrency must be a positive number: " + toRate;

        LOGGER.info("Converting " + amount + " " + fromCurrency + " to " + toCurrency);

        if (!fromCurrency.equals(toCurrency)) {
            double amountInSGD = amount / fromRate;
            double convertedAmount = amountInSGD * toRate;
            LOGGER.info("Conversion successful. Result: " + convertedAmount + " " + toCurrency);
            return convertedAmount;
        } else {
            LOGGER.info("Same currency. No conversion needed.");
            return amount;
        }
    }

    /**
     * Converts the currency of expenses in the given ExpenseList to the specified new currency.
     * No conversion necessary if trying to convert to the same currency.
     *
     * @author sweijie24
     * @param newCurrency The new currency to convert expenses to.
     * @param expenses    The ExpenseList containing the expenses to be converted.
     * @throws IllegalArgumentException If the ExpenseList is null.
     */
    public void convertExpenseCurrency(Currency newCurrency, ExpenseList expenses) {
        if (expenses == null) {
            throw new IllegalArgumentException("ExpenseList cannot be null");
        }
        assert expenses != null : "ExpenseList cannot be null";

        if (DefaultCurrency.getDefaultCurrency() == newCurrency) {
            System.out.println("Same currency for Expenses. No conversion needed");
        } else {
            for (Expense expense : expenses.getExpenses()) {
                if (expense == null) {
                    LOGGER.warning("Skipping null expense");
                    System.out.println("Skipping null expense");
                    continue;
                }

                try {
                    double convertedAmount = convertAmount(expense.getAmount(), expense.getCurrency(), newCurrency);
                    expense.setAmount(convertedAmount);
                    expense.setCurrency(newCurrency);
                } catch (IllegalArgumentException e) {
                    LOGGER.severe("Error converting amount for expense: " + e.getMessage());
                    System.out.println("Error converting amount for expense: " + e.getMessage());
                }
            }
            System.out.println("Default currency for Expenses changed to " + newCurrency);
        }
    }

    public void convertSplitExpenseCurrency(Currency newCurrency, SplitExpenseList splitExpenses) {
        if (splitExpenses == null) {
            throw new IllegalArgumentException("SplitExpenseList cannot be null");
        }

        assert splitExpenses != null : "SplitExpenseList cannot be null";

        if (DefaultCurrency.getDefaultCurrency() == newCurrency) {
            System.out.println("Same currency for Split Expenses. No Conversion needed");
            return;
        } else { // Convert the currency of each split expense in the SplitExpenseList
            for (SplitExpense splitExpense : splitExpenses.getSplitExpenses()) {
                if (splitExpense == null) {
                    LOGGER.warning("Skipping null split expense");
                    System.out.println("Skipping null split expense");
                    continue;
                }

                try {
                    double convertedAmount = convertAmount(splitExpense.getAmount(), splitExpense.getCurrency(), 
                        newCurrency);
                    splitExpense.setAmount(convertedAmount);
                    splitExpense.setCurrency(newCurrency);
                } catch (IllegalArgumentException e) {
                    LOGGER.severe("Error converting amount for split expense: " + e.getMessage());
                    System.out.println("Error converting amount for split expense: " + e.getMessage());
                }
            }
            System.out.println("Default currency for Split Expenses changed to " + newCurrency);
        }
    }

    /**
     * Converts the currency of savings in the given SavingList to the specified new currency.
     * No conversion necessary if trying to convert to the same currency.
     *
     * @author sweijie24
     * @param newCurrency The new currency to convert savings to.
     * @param savings     The SavingList containing the savings to be converted.
     * @throws IllegalArgumentException If the SavingList is null.
     */
    public void convertSavingCurrency(Currency newCurrency, SavingList savings) {
        if (savings == null) {
            throw new IllegalArgumentException("SavingList cannot be null");
        }
        assert savings != null : "SavingList cannot be null";

        if (DefaultCurrency.getDefaultCurrency() == newCurrency) {
            System.out.println("Same currency for Savings. No conversion needed");
        } else {
            for (Saving saving : savings.getSavings()) {
                if (saving == null) {
                    LOGGER.warning("Skipping null saving");
                    System.out.println("Skipping null saving");
                    continue;
                }

                try {
                    double convertedAmount = convertAmount(saving.getAmount(), saving.getCurrency(), newCurrency);
                    saving.setAmount(convertedAmount);
                    saving.setCurrency(newCurrency);
                } catch (IllegalArgumentException e) {
                    LOGGER.severe("Error converting amount for saving: " + e.getMessage());
                    System.out.println("Error converting amount for saving: " + e.getMessage());
                }
            }
            System.out.println("Default currency for Savings changed to " + newCurrency);
        }
    }

    public void convertRecurringExpensesCurrency(Currency newCurrency, RecurringExpenseLists recurringExpenseLists) {
        assert recurringExpenseLists != null : "RecurringExpenseLists cannot be null";

        if (DefaultCurrency.getDefaultCurrency() == newCurrency) {
            System.out.println("Same currency for Recurring Expenses. No Conversion needed");
            return;
        }

        int numberOfExpenseList = recurringExpenseLists.getSize();

        ui.printDivider();
        System.out.println("Conversion for expenses in Recurring Expenses : ");

        for (int i = 0; i < numberOfExpenseList; i++) {
            int arrayIndexAsListNumber = i + 1;
            ExpenseList reccuringExpenseList = recurringExpenseLists.getExpenseListAtListNumber(arrayIndexAsListNumber);
            System.out.print("Changing the default currency for " + reccuringExpenseList.getName() + ": ");
            convertExpenseCurrency(newCurrency, reccuringExpenseList);
        }

        System.out.println("Default currency for Recurring Expenses changed to " + newCurrency);
        ui.printDivider();
    }
 
    public void convertBudgetCurrency(Currency newCurrency, ExpenseList expenseList) {
        if (expenseList == null) {
            throw new IllegalArgumentException("ExpenseList cannot be null");
        }

        // Check if the new currency is the same as the default currency to avoid unnecessary conversion
        if (DefaultCurrency.getDefaultCurrency().equals(newCurrency)) {
            System.out.println("Same currency. No conversion needed for budgets.");
            return;
        }

        // Iterate over each budget in the ExpenseList and convert its currency
        for (Budget budget : expenseList.getBudgets()) {
            // Assuming the budget amount is in the default currency
            Currency defaultCurrency = DefaultCurrency.getDefaultCurrency();
            try {
                // Convert the budget amount from the default currency to the new currency
                double convertedBudgetAmount = convertAmount(budget.getBudget(), defaultCurrency, newCurrency);
                // Update the budget amount with the converted value
                budget.setBudget(convertedBudgetAmount);
            } catch (IllegalArgumentException e) {
                // Handle any IllegalArgumentException thrown during conversion
                LOGGER.severe("Error converting budget amount for category: " + budget.getCategory()
                                    + "; " + e.getMessage());
                System.out.println("Error converting budget amount for category: " + budget.getCategory()
                                    + "; " + e.getMessage());
            }
        }

        System.out.println("Budgets successfully converted to " + newCurrency.getCurrencyCode());
    }

}
