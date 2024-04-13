package seedu.budgetbuddy.command;

import seedu.budgetbuddy.commons.Expense;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;
import seedu.budgetbuddy.Ui;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecurringExpenseCommand extends Command{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private RecurringExpenseLists recurringExpenseLists;

    private ExpenseList overallExpenses;
    private String initialListName;
    private String commandType;
    private int listNumber;
    private String category;
    private Double amount;
    private String description;

    private final Ui ui = new Ui();


    /**
     * Constructs a RecurringExpenseCommand for operations that only require the recurringExpensesList.
     * This constructor is used when the commandType is `viewlists`
     *
     * @param recurringExpenseLists The overall recurringExpensesList containing the list of ExpenseList
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(RecurringExpenseLists recurringExpenseLists, String commandType) {
        this.commandType = commandType;
        this.recurringExpenseLists = recurringExpenseLists;
    }

    /**
     * Constructs a RecurringExpenseCommand for operations that require the name of the ExpenseList
     * , the recurringExpensesList
     * This constructor is used when the commandType is `newlist`
     *
     * @param initialListName The name of the new RecurringExpenseList to create
     * @param recurringExpenseLists The overall recurringExpensesList containing the list of ExpenseList
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(String initialListName,
                                   RecurringExpenseLists recurringExpenseLists, String commandType) {
        this.initialListName = initialListName;
        this.commandType = commandType;
        this.recurringExpenseLists = recurringExpenseLists;
    }

    /**
     * Constructs a RecurringExpenseCommand for operations that require listNumber, recurringExpensesList
     * This constructor is used when the commandType is either `viewexpenses` or `removelist`
     *
     * @param listNumber The listNumber associated to the listName printed during a `viewlists` command
     * @param recurringExpenseLists The overall recurringExpensesList containing the list of ExpenseList
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(int listNumber,
                                   RecurringExpenseLists recurringExpenseLists, String commandType) {
        this.listNumber = listNumber;
        this.commandType = commandType;
        this.recurringExpenseLists = recurringExpenseLists;
    }


    /**
     * Constructs a RecurringExpenseCommand for operations that require listNumber, recurringExpensesList
     * , overallExpenses.
     * This constructor is used when the commandType is `addrec`
     *
     * @param listNumber The listNumber associated to the listName printed during a `viewlists` command
     * @param recurringExpenseLists The overall recurringExpensesList containing the list of ExpenseList
     * @param overallExpenses The overall ExpenseList containing all the user's expenses
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(int listNumber, RecurringExpenseLists recurringExpenseLists,
                                   ExpenseList overallExpenses, String commandType) {

        this.recurringExpenseLists = recurringExpenseLists;
        this.overallExpenses = overallExpenses;
        this.listNumber = listNumber;
        this.commandType = commandType;
    }

    /**
     * Constructs a RecurringExpenseCommand for operations that require listNumber, recurringExpensesList,
     * category, amount, description.
     * This constructor is used when the commandType is `newexpense`
     *
     * @param listNumber The listNumber associated to the listName printed during a `viewlists` command
     * @param recurringExpenseLists The overall recurringExpensesList containing the list of ExpenseList
     * @param category The category of the new expense user wishes to add
     * @param amount The amount of the new expense user wishes to add
     * @param description The description of the new expense user wishes to add
     * @param commandType The commandType of the RecurringExpenseCommand
     */
    public RecurringExpenseCommand(int listNumber, RecurringExpenseLists recurringExpenseLists, String category,
                                   Double amount, String description, String commandType) {

        this.recurringExpenseLists = recurringExpenseLists;
        this.listNumber = listNumber;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.commandType = commandType;
    }

    /**
     * Adds a new list with the name `listName` to the recurringExpensesList
     *
     * @param listName The name of the new list
     */
    private void addNewList(String listName) {
        recurringExpenseLists.addNewRecurringList(listName);
    }

    /**
     * Removes the ExpenseList located at listNumber in the overall recurringExpensesList
     *
     * @param listNumber The list position of the ExpenseList to remove
     */
    private void removeList(int listNumber) {

        if (listNumber <= 0 || listNumber > recurringExpenseLists.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpenseLists.getSize());
            return;
        }

        recurringExpenseLists.removeList(listNumber);
    }


    /**
     * Adds an Expense with the provided category, amount and description to the ExpenseList located
     * at the provided listNumber in the overall recurringExpensesList
     *
     * @param listNumber The list position of the ExpenseList to add the Expense in
     * @param category The category of the Expense to add
     * @param amount The amount of the Expense to add
     * @param description The description of the Expense to add
     */
    private void addExpenseToList(int listNumber, String category, Double amount, String description) {

        if (listNumber <= 0 || listNumber > recurringExpenseLists.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpenseLists.getSize());
            return;
        }

        ExpenseList expenses = recurringExpenseLists.getExpenseListAtListNumber(listNumber);

        try {
            expenses.addExpense(category, amount.toString(), description);

            ui.printDivider();
            System.out.println("Successfully Added Expense to " + expenses.getName());
            System.out.println("|Details of Expense");
            System.out.println("--------------------");
            System.out.println("|Category : " + category);
            System.out.println("|Amount : " + amount);
            System.out.println("|Description : " + description);
            ui.printDivider();

        } catch (BudgetBuddyException e) {
            LOGGER.log(Level.WARNING
                    , "An attempt to add an Invalid Expense was created. Error Captured Successfully");
            System.out.println(e.getMessage());
        }

    }
    
    /**
     * Adds all Expenses in the ExpenseList located at the provided listNumber in the `recurringExpensesList`,
     * into the provided `overallExpenses`
     *
     * @param listNumber The list position of the ExpenseList in recurringExpensesList
     * @param recurringExpenseLists The overall recurringExpensesList
     * @param overallExpenses The overall expenses
     */
    private void addRecurringExpensesToExpenses(int listNumber, RecurringExpenseLists recurringExpenseLists
            , ExpenseList overallExpenses) {

        if (listNumber <= 0 || listNumber > recurringExpenseLists.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpenseLists.getSize());
            return;
        }

        ExpenseList expenseList = recurringExpenseLists.getExpenseListAtListNumber(listNumber);
        ArrayList<Expense> expenses = expenseList.getExpenses();

        if (expenses.isEmpty()) {
            ui.printDivider();
            System.out.println("There are no expenses present in \"" + expenseList.getName() +
                    "\". Nothing has been added to the overall expenses");
            ui.printDivider();
            return;
        }
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            Double amount = expense.getAmount();
            String description = expense.getDescription();

            Command addExpenseCommand = new AddExpenseCommand(overallExpenses, category,
                    amount.toString(), description);

            addExpenseCommand.execute();
        }

        ui.printDivider();
        System.out.println("Your Recurring Expenses in " + expenseList.getName() +
                " has been added to your overall Expenses");
        ui.printDivider();

    }

    /**
     * Prints all expenses in the ExpenseList located at the provided `listNumber` in the overall
     * `recurringExpensesList`
     *
     * @param listNumber The list position of the ExpenseList in recurringExpensesList
     * @param recurringExpenseLists The recurringExpensesList to obtain ExpenseList from
     */
    private void printExpensesAtIndex(int listNumber, RecurringExpenseLists recurringExpenseLists) {

        if (listNumber <= 0 || listNumber > recurringExpenseLists.getSize()) {
            System.out.println("Invalid List Number. Choose a List Number from 1 onwards");
            System.out.println("Number of Lists you have currently : " + recurringExpenseLists.getSize());
            return;
        }

        ExpenseList expenseList = recurringExpenseLists.getExpenseListAtListNumber(listNumber);

        expenseList.listExpenses(null);
    }


    /**
     * Prints the names of all ExpenseList in the recurringExpensesList
     */
    private void printList() {
        recurringExpenseLists.printAllRecurringLists();
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
            removeList(this.listNumber);
            break;
        case "newexpense":
            addExpenseToList(this.listNumber, this.category, this.amount, this.description);
            break;
        case "addrec":
            addRecurringExpensesToExpenses(this.listNumber, this.recurringExpenseLists, this.overallExpenses);
            break;
        case "viewexpenses":
            printExpensesAtIndex(this.listNumber, this.recurringExpenseLists);
            break;
        default:
            break;
        }
    }

}
