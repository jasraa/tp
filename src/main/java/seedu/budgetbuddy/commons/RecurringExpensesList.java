package seedu.budgetbuddy.commons;

import seedu.budgetbuddy.Ui;

import java.util.ArrayList;

public class RecurringExpensesList {
    protected ArrayList<ExpenseList> recurringExpenses;

    Ui ui = new Ui();

    public RecurringExpensesList(ArrayList<ExpenseList> recurringExpenses) {
        this.recurringExpenses = recurringExpenses;
    }
    public RecurringExpensesList() {
        this.recurringExpenses = new ArrayList<>();
    }
    public void addNewRecurringList(String listName) {

        assert !listName.contains("!") && !listName.contains("|") : "List Name should not contain a ! or |";
        assert !listName.isEmpty() : "List Name should not be empty";
        ExpenseList expenses = new RecurringExpenseList(listName, new ArrayList<>());

        recurringExpenses.add(expenses);

        ui.printDivider();
        System.out.println("New List Created with name : " + expenses.getName());
        ui.printDivider();
    }

    public void removeList(int listNumber) {
        int listNumberAsArrayPosition = listNumber - 1;
        recurringExpenses.remove(listNumberAsArrayPosition);

        ui.printDivider();
        System.out.println("List Successfully Removed");
        ui.printDivider();

    }

    public void printAllRecurringLists() {

        int counter = 1;

        if (recurringExpenses.isEmpty()) {
            ui.printDivider();
            System.out.println("You currently have no Recurring Expenses");
            ui.printDivider();
            return;
        }

        ui.printDivider();
        System.out.println("These are your lists of Recurring Expenses");

        for (ExpenseList expenses : recurringExpenses) {
            String listName = expenses.getName();
            System.out.println(counter + ". " + listName);
            counter += 1;
        }

        ui.printDivider();
    }

    public int getSize() {
        return recurringExpenses.size();
    }

    public ExpenseList getExpenseListAtListNumber(int listNumber) {

        int listNumberAsArrayPosition = listNumber - 1;
        return recurringExpenses.get(listNumberAsArrayPosition);
    }



}
