package seedu.budgetbuddy.commons;


import java.util.ArrayList;
import java.util.List;

import seedu.budgetbuddy.Ui;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SplitExpenseList {

    Ui ui = new Ui();

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    protected ArrayList <SplitExpense> splitexpenses;
    public SplitExpenseList(ArrayList<SplitExpense> splitexpenses){
        this.splitexpenses = splitexpenses;
    }

    public SplitExpenseList() {
        this.splitexpenses = new ArrayList<>();
    }

    public int getSize() {
        return splitexpenses.size();
    }

    public List<SplitExpense> getSplitExpenses() {
        return splitexpenses;
    }

    public SplitExpense getSplitExpenseListAtListNumber(int listNumber) {
        int listNumberAsArrayPosition = listNumber - 1;
        return splitexpenses.get(listNumberAsArrayPosition);
    }

    /**
     * Lists all the expenses in the list
     * @return void
     * @throws BudgetBuddyException if there is an error while listing expenses
     * @param void
     * @return void
     * @throws BudgetBuddyException if there is an error while listing expenses
     */

    public void listSplitExpenses() {
        LOGGER.info("Listing splitexpenses...");

        try {
            ui.printDivider();
            System.out.println(String.format("Current Currency: %s\n", DefaultCurrency.getDefaultCurrency()));

            System.out.println("Shared Bills: ");
            for (int i = 0; i < splitexpenses.size(); i++) {
                SplitExpense splitexpense = splitexpenses.get(i);

                if (splitexpense == null) {
                    LOGGER.warning("Expense object at index " + i + " is null");
                    continue;
                }
                System.out.print(i+1 + " | ");
                System.out.print(" Description: " + splitexpense.getDescription());
                System.out.print(" Number of People: " + splitexpense.getNumberOfPeople());
                System.out.print(" Amount: $" + String.format("%.2f", splitexpense.getAmount()) + "\n");
            }
            ui.printDivider();
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while listing expenses.", e);
        }
    }

    public void addSplitExpense(String amount, String numberOfPeople, String description ) throws BudgetBuddyException {
        assert amount != null : "Amount should not be null";
        assert description != null : "Description should not be null";
        LOGGER.info("Adding split expense...");

        double amountDouble;
        int numberOfPeopleInt;
        try{
            amountDouble = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            throw new BudgetBuddyException("Invalid amount format. Amount should be a number.");
        }

        if (amountDouble < 0){
            throw new BudgetBuddyException("Expenses should not be negative.");
        }

        try {
            numberOfPeopleInt = Integer.parseInt(numberOfPeople);
            if (Integer.parseInt(numberOfPeople) < 0) {
                throw new BudgetBuddyException("Number of people should be a positive number");
            }
        } catch (NumberFormatException e) {
            throw new BudgetBuddyException("Number of people should be a number");
        }

        SplitExpense splitexpense = new SplitExpense(amountDouble, numberOfPeopleInt, description);
        splitexpenses.add(splitexpense);
    }

    public void settleSplitExpenses(int index) {
        LOGGER.info("Settling split expenses...");
        assert index >= 0 : "Index should be a positive integer";
        assert index < splitexpenses.size() : "Index should be within the range of the list";

        splitexpenses.remove(index);
    }
}
