package seedu.budgetbuddy.commons;

public class SplitExpense extends Transaction{
    private final String amount;
    private final String description;
    private final String numberOfPeople;

    public SplitExpense(String amount, String numberOfPeople, String description) {
        super("Split Expense", Double.parseDouble(amount));
        this.amount = amount;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }
    
    public String getDescription() {
        return description;
    }

    public double calculateAmountPerPerson() {
        double amountValue = Double.parseDouble(amount);
        double numberOfPeopleValue = Double.parseDouble(numberOfPeople);
    
        double rawAmountPerPerson = amountValue / numberOfPeopleValue;
        
        double roundedAmountPerPerson = Math.round(rawAmountPerPerson * 100) / 100.0;
        
        return roundedAmountPerPerson;
    }
    
    public Boolean isExpenseSettled() {
        return false;
    }

    @Override
    public String toString() {
        return "Number of People: " + numberOfPeople + " Amount: " + amount + " Description: " +
                description + " Amount per person: " + calculateAmountPerPerson();
    }

}
