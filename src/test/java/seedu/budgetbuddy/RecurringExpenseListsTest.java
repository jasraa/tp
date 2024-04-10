package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecurringExpenseListsTest {

    RecurringExpenseLists recurringExpenseLists = new RecurringExpenseLists();

    @Test
    void addNewRecurringList_addValidNewList_success() {
        recurringExpenseLists.addNewRecurringList("Entertainment");
        assertEquals(1, recurringExpenseLists.getSize());
    }

    @Test
    void removeList_removeValidListNumber_success() {
        recurringExpenseLists.addNewRecurringList("Entertainment");
        recurringExpenseLists.addNewRecurringList("Housing");
        recurringExpenseLists.addNewRecurringList("Utilities");
        recurringExpenseLists.removeList(2);

        assertEquals(2, recurringExpenseLists.getSize());
    }

    @Test
    void getSize_addThreeLists_sizeReturnedCorrect() {
        recurringExpenseLists.addNewRecurringList("Entertainment");
        recurringExpenseLists.addNewRecurringList("Housing");
        recurringExpenseLists.addNewRecurringList("Utilities");

        int expectedSize = 3;

        int obtainedSize = recurringExpenseLists.getSize();

        assertEquals(expectedSize, obtainedSize);
    }

    @Test
    void getExpenseListAtListNumber_validListNumber_returnsCorrectList() {
        recurringExpenseLists.addNewRecurringList("Entertainment");
        recurringExpenseLists.addNewRecurringList("Utilities");
        recurringExpenseLists.addNewRecurringList("Housing");
        ExpenseList obtainedList = recurringExpenseLists.getExpenseListAtListNumber(2);


        assertNotNull(obtainedList);
        assertEquals("Utilities", obtainedList.getName());
    }

    @Test
    void printAllRecurringLists_nonEmptyList_noExceptionsThrown() {
        recurringExpenseLists.addNewRecurringList("Entertainment");
        recurringExpenseLists.addNewRecurringList("Utilities");

        recurringExpenseLists.printAllRecurringLists();
    }

    @Test
    void printAllRecurringLists_emptyList_noExceptionsThrown() {
        recurringExpenseLists.printAllRecurringLists();
    }
}
