package seedu.budgetbuddy.commons;

//@author sweijie24
public class Saving extends Transaction{

    public Saving(String category, double amount) {
        super(category, amount);
    }

    @Override
    public String toString() {
        return "Category: " + category + " Amount: " + amount;
    }

    public double getAmount() {
        return amount;
    }

}
