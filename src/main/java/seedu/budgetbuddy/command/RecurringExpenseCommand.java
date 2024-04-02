package seedu.budgetbuddy.command;

import seedu.budgetbuddy.Expense;
import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.RecurringExpensesList;
import seedu.budgetbuddy.Ui;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.ArrayList;

public class RecurringExpenseCommand extends Command{

    private RecurringExpensesList recurringExpensesList;

    private ExpenseList overallExpenses;
    private String initialListName;
    private String commandType;
    private int listNumber;

    private String category;
    private Double amount;
    private String description;

    private final Ui ui = new Ui();


    public RecurringExpenseCommand(RecurringExpensesList recurringExpensesList, String commandType) {
        this.commandType = commandType;
        this.recurringExpensesList = recurringExpensesList;
    }

    public RecurringExpenseCommand(String initialListName,
                                   RecurringExpensesList recurringExpensesList, String commandType) {
        this.initialListName = initialListName;
        this.commandType = commandType;
        this.recurringExpensesList = recurringExpensesList;
    }

    public RecurringExpenseCommand(int listNumber,
                                   RecurringExpensesList recurringExpensesList, String commandType) {
        this.listNumber = listNumber;
        this.commandType = commandType;
        this.recurringExpensesList = recurringExpensesList;
    }

    public RecurringExpenseCommand(int listNumber, RecurringExpensesList recurringExpensesList,
                                   ExpenseList overallExpenses, String commandType) {

        this.recurringExpensesList = recurringExpensesList;
        this.overallExpenses = overallExpenses;
        this.listNumber = listNumber;
        this.commandType = commandType;
    }

    public RecurringExpenseCommand(int listNumber, RecurringExpensesList recurringExpensesList, String category,
                                   Double amount, String description, String commandType) {

        this.recurringExpensesList = recurringExpensesList;
        this.listNumber = listNumber;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.commandType = commandType;
    }


    private void addNewList(String listName) {
        recurringExpensesList.addNewRecurringList(listName);
    }

    private void removeList() {

        if (listNumber == 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        recurringExpensesList.removeList(listNumber);
    }

    private void addExpenseToList() {

        if (listNumber <= 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        ExpenseList expenses = recurringExpensesList.getExpenseListAtListNumber(listNumber);

        try {
            expenses.addExpense(category, amount.toString(), description);

            ui.printDivider();
            System.out.println("Successfully Added Expense to " + expenses.getName());
            ui.printDivider();

        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
        }

    }

    private void addRecurringExpensesToExpenses() {

        if (listNumber <= 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        ExpenseList expenseList = recurringExpensesList.getExpenseListAtListNumber(listNumber);
        ArrayList<Expense> expenses = expenseList.getExpenses();

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            Double amount = expense.getAmount();
            String description = expense.getDescription();

            Command addExpenseCommand = new AddExpenseCommand(overallExpenses, category,
                    amount.toString(), description);

            addExpenseCommand.execute();
        }

        ui.printDivider();
        System.out.println("You Recurring Expenses in " + expenseList.getName() +
                " has been added to your overall Expenses");

        ui.printDivider();

    }

    private void printExpensesAtIndex() {

        if (listNumber <= 0 || listNumber > recurringExpensesList.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpensesList.getSize());
            return;
        }

        ExpenseList expenseList = recurringExpensesList.getExpenseListAtListNumber(listNumber);

        expenseList.listExpenses(null);
    }

    private void printList() {
        recurringExpensesList.printAllRecurringLists();
    }
    public void execute(){

        switch(commandType) {
        case "newlist":
            addNewList(initialListName);
            break;

        case "viewlists":
            printList();
            break;

        case "removelist":
            removeList();
            break;

        case "newexpense":
            addExpenseToList();
            break;
        case "addrec":
            addRecurringExpensesToExpenses();
            break;

        case "viewexpenses":
            printExpensesAtIndex();
            break;

        default:
            break;
        }
    }

}
