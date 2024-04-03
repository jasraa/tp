package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.commons.Expense;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.exception.BudgetBuddyException;
import org.junit.jupiter.api.Disabled;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


import java.util.ArrayList;

public class ExpenseListTest {

    private static final Logger LOGGER = Logger.getLogger(ExpenseListTest.class.getName());

    @Test
    public void calculateTotalExpenses_addingIntegers_success() throws BudgetBuddyException {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");
        expenseList.addExpense("Housing", "30", "BTO");

        assertEquals(80, expenseList.calculateTotalExpenses());
    }

    @Test
    public void addExpense_addingExpense_success() throws BudgetBuddyException {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");

        assertEquals(1, expenseList.getExpenses().size());
        assertEquals("Transport", expenseList.getExpenses().get(0).getCategory());
        assertEquals(50.0, expenseList.getExpenses().get(0).getAmount(), 0.01); 
        assertEquals("Bus Fare", expenseList.getExpenses().get(0).getDescription());
    }

    @Test @Disabled
    public void addExpense_addingNegativeExpense_exceptionThrown() {
        ExpenseList expenseList = new ExpenseList();
        try {
            expenseList.addExpense("Transport", "-50", "Bus Fare");
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.Exception: Expenses should not be negative", e.getMessage());
        }
    }

    //@@ jasraa
    @Test
    public void editExpense_validInput_success() throws BudgetBuddyException {
        //Create an ExpenseList and add two expenses
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");
        expenseList.addExpense("Housing", "30", "Lunch");

        //Edit the first expense
        expenseList.editExpense("Transport", 1, 70.0, "Updated Bus Fare");

        // Assert: Check if the edited expense details are correct
        assertEquals(2, expenseList.getExpenses().size());
        assertEquals("Transport", expenseList.getExpenses().get(0).getCategory());
        assertEquals(70.0, expenseList.getExpenses().get(0).getAmount(), 0.01); // using delta for double comparison
        assertEquals("Updated Bus Fare", expenseList.getExpenses().get(0).getDescription());
    }

    @Test
    public void addSaving_addingSaving_success() throws BudgetBuddyException {
        SavingList savingList = new SavingList();
        savingList.addSaving("Salary", "1000");
        assertEquals(1, savingList.getSavings().size());
    }

    //@@ jasraa
    @Test @Disabled
    public void editExpense_invalidCategoryOrIndex_failure() throws BudgetBuddyException {
        // Create an ExpenseList and add two expenses
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");
        expenseList.addExpense("Housing", "30", "Lunch");

        // Edit an expense with an invalid category
        expenseList.editExpense("InvalidCategory", 1, 70.0, "Updated Bus Fare");

        // Assert: Check if the expense list remains unchanged
        assertEquals(2, expenseList.getExpenses().size()); // Should not change size
        // Check if the expense details remain unchanged
        assertEquals("Transport", expenseList.getExpenses().get(0).getCategory());
        assertEquals(50.0, expenseList.getExpenses().get(0).getAmount(), 0.01);
        assertEquals("Bus Fare", expenseList.getExpenses().get(0).getDescription());
    }

    @Test
    public void testDeleteExpense_indexOutOfBounds() {
        ExpenseList expenseList = new ExpenseList();

        int initialSize = expenseList.getExpenses().size();
        expenseList.deleteExpense(initialSize + 1); // Trying to delete with index out of bounds
        assertEquals(initialSize, expenseList.getExpenses().size()); // Size should remain the same
    }

    @Test
    public void filterExpenses_filterByDescription_returnsTwoMatches() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "100", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses("apple"
                , null, null);

        assertEquals(2, filteredExpenses.size());

    }

    @Test
    public void filterExpenses_filterByMinAmount_returnsThreeMatches() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "100", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses(""
                , 50.00, null);

        assertEquals(3, filteredExpenses.size());

    }

    @Test
    public void filterExpenses_filterByMaxAmount_returnsOneMatches() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "100", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses(""
                , null, 75.00);

        assertEquals(1, filteredExpenses.size());

    }

    @Test
    public void filterExpenses_filterByRange_returnsTwoMatches() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "100", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses(""
                , 49.00, 76.00);

        assertEquals(2, filteredExpenses.size());

    }

    @Test
    public void filterExpenses_filterByDescriptionAndMinAmount_returnsOneMatch() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "20", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses("apple"
                , 50.00, null);

        assertEquals(1, filteredExpenses.size());

    }

    @Test
    public void filterExpenses_filterByNull_returnsAllMatch() throws BudgetBuddyException {
        ExpenseList expenses = new ExpenseList();
        expenses.addExpense("Groceries", "20", "Apples");
        expenses.addExpense("Transport", "50", "Bus fare");
        expenses.addExpense("Entertainment", "75", "Movie");
        expenses.addExpense("Groceries", "100", "apple");
        ArrayList<Expense> filteredExpenses = expenses.filterExpenses(""
                , null, null);

        assertEquals(4, filteredExpenses.size());

    }

    @Test
    public void testGetExpenseInsights() {
        // Set up the ExpenseList with sample expenses
        ExpenseList expenseList = new ExpenseList();
        try {
            expenseList.addExpense("Transport", "50", "Bus fare");
            expenseList.addExpense("Groceries", "30", "Weekly groceries");
            expenseList.addExpense("Entertainment", "20", "Movie ticket");
        } catch (BudgetBuddyException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while adding an expense", e);
        }

        // Redirect standard output to capture the output of getExpenseInsights
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Invoke the method
        expenseList.getExpenseInsights();

        // Capture and assert the output
        String output = outContent.toString();
        assertTrue(output.contains("Highest Expense Category:"));
        assertTrue(output.contains("Lowest Expense Category:"));
        assertTrue(output.contains("Categories with no expenses added:"));

        // Restore the original standard output
        System.setOut(originalOut);
    }
}
