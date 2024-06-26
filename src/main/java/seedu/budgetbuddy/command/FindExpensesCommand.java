package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.Expense;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.Ui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command that finds and lists expenses based on a provided criteria.
 * Criteria can include description, minimum and maximum amounts
 */
public class FindExpensesCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ExpenseList expenses;
    private String description;
    private Double minAmount;
    private Double maxAmount;
    private Ui ui;

    /**
     * Constructs a FindExpenseCommand with the specified expense list, description, minimum amount and maximum amount
     *
     *
     * @param expenses The expenseList to filter the expenses
     * @param description The description to be filtered, can be null or empty
     * @param minAmount The minimum amount of expense to be filtered, can be null
     * @param maxAmount The maximum amount of expense to be filtered, can be null
     */
    public FindExpensesCommand(ExpenseList expenses, String description, Double minAmount, Double maxAmount) {
        if (minAmount != null && maxAmount != null) {
            assert minAmount < maxAmount : "Minimum amount cannot be larger than Maximum Amount";
        }

        ui = new Ui();
        this.expenses = expenses;

        if(description == null || description.isEmpty()) {
            this.description = "";
        } else {
            this.description = description;
        }
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    /**
     * Prints an initialization message that informs user of the parameters used when filtering. Diplays
     * an N.A. for filters which would not be used
     */
    private void printInitializationMessage() {
        ui.printDivider();
        System.out.println("Looking for Expenses with the following parameters : ");

        System.out.println("Description : ");
        if (description == null || description.isEmpty()) {
            System.out.println("N.A");
        } else {
            System.out.println(description);
        }

        System.out.println("Minimum Amount : ");
        if (minAmount == null) {
            System.out.println("N.A");
        } else {
            System.out.println(minAmount);
        }

        System.out.println("Maximum Amount : ");
        if (maxAmount == null) {
            System.out.println("N.A");
        } else {
            System.out.println(maxAmount);
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void execute() {

        LOGGER.log(Level.INFO, "Start processing of Find Command");

        assert minAmount == null || maxAmount == null || minAmount <= maxAmount
                : "Minimum amount cannot be larger than Maximum Amount";

        LOGGER.log(Level.INFO, "Creating filteredExpenses");

        printInitializationMessage();
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses(description, minAmount, maxAmount);
        ExpenseList filteredExpenseList = new ExpenseList(filteredExpenses);

        if (filteredExpenses.isEmpty()) {
            LOGGER.log(Level.INFO, "filtered expenses is empty, returning no expenses found");

            ui.printDivider();
            System.out.println("No matching expenses found.");
            ui.printDivider();
        } else {
            LOGGER.log(Level.INFO, "Filtered expenses contains items, returning matching expenses");

            ui.printDivider();
            System.out.println("Here are the matching expenses : ");
            filteredExpenseList.listExpenses(null);
            ui.printDivider();
        }
    }
}
