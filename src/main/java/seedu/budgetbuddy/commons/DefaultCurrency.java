package seedu.budgetbuddy.commons;

import java.util.Currency;

public class DefaultCurrency {

    private static Currency defaultCurrency = Currency.getInstance("SGD");

    public static Currency getDefaultCurrency() {
        return defaultCurrency;
    }
    public static void setDefaultCurrency(Currency currency) {
        defaultCurrency = currency;
    }
}
