package seedu.budgetbuddy.commons;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SplitExpense extends Transaction {
    protected String description;
    private int numberOfPeople;
    private LocalDate dateAdded;

    public SplitExpense(LocalDate dateAdded, double totalAmount, int numberOfPeople, String description) {
        super("Shared Bill", calculateAmountPerPerson(totalAmount, numberOfPeople));
        this.dateAdded = dateAdded;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
    }

    public SplitExpense(double totalAmount, int numberOfPeople, String description) {
        super("Shared Bill", calculateAmountPerPerson(totalAmount, numberOfPeople));
        this.numberOfPeople = numberOfPeople;
        this.description = description;
        this.dateAdded = LocalDate.now();
    }

    private static double calculateAmountPerPerson(double totalAmount, int numberOfPeople) {
        BigDecimal amount = BigDecimal.valueOf(totalAmount);
        return amount.divide(BigDecimal.valueOf(numberOfPeople), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
    
    public String getDescription() {
        return description;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }
    
    public Boolean isExpenseSettled() {
        return false;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Number of People: " + numberOfPeople + " Amount per person: " + amount + " Description: " +
                description + " Total Amount: " + getTotalAmount();
    }
    
    public double getTotalAmount() {
        return amount * numberOfPeople;
    }

}
