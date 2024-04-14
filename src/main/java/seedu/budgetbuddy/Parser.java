package seedu.budgetbuddy;

import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.commandcreator.ListBudgetCommandCreator;
import seedu.budgetbuddy.commandcreator.CommandCreator;
import seedu.budgetbuddy.commandcreator.AddExpenseCommandCreator;
import seedu.budgetbuddy.commandcreator.AddSavingCommandCreator;
import seedu.budgetbuddy.commandcreator.ChangeCurrencyCommandCreator;
import seedu.budgetbuddy.commandcreator.DeleteExpenseCommandCreator;
import seedu.budgetbuddy.commandcreator.EditExpenseCommandCreator;
import seedu.budgetbuddy.commandcreator.EditSavingsCommandCreator;
import seedu.budgetbuddy.commandcreator.FindExpensesCommandCreator;
import seedu.budgetbuddy.commandcreator.GetExpenseInsightsCommandCreator;
import seedu.budgetbuddy.commandcreator.GetSavingsInsightsCommandCreator;
import seedu.budgetbuddy.commandcreator.ListCommandCreator;
import seedu.budgetbuddy.commandcreator.ListSplittedExpenseCommandCreator;
import seedu.budgetbuddy.commandcreator.MenuCommandCreator;
import seedu.budgetbuddy.commandcreator.RecurringExpenseCommandCreator;
import seedu.budgetbuddy.commandcreator.ReduceSavingCommandCreator;
import seedu.budgetbuddy.commandcreator.SetBudgetCommandCreator;
import seedu.budgetbuddy.commandcreator.SettleSplitExpenseCommandCreator;
import seedu.budgetbuddy.commandcreator.SplitExpenseCommandCreator;
import seedu.budgetbuddy.commandcreator.GetBudgetCommandCreator;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;
import seedu.budgetbuddy.commons.SplitExpenseList;
import seedu.budgetbuddy.commons.CurrencyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Parser {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    protected ArrayList<String> expenseCategories;
    protected ArrayList<String> savingsCategories;

    public Parser() {
        this.expenseCategories = new ArrayList<>(Arrays.asList("Housing",
                "Groceries", "Utility", "Transport", "Entertainment", "Others"));
        this.savingsCategories = new ArrayList<>(Arrays.asList("Salary",
                "Investments", "Gifts", "Others"));
    }

    public Boolean isRecCommand(String input) {
        return input.startsWith("rec ");
    }
  
    public Boolean isFindExpensesCommand(String input) {
        return input.startsWith("find expenses");
    }

    /**
     * Checks if the provided input starts with the word "list" .
     *
     * @param input The user input string
     * @return true if user input starts with "list", else returns false
     */
    public Boolean isListCommand(String input) {
        LOGGER.log(Level.INFO, "Checking if input is a List Command");
        return input.startsWith("list");
    }

    /**
     * Checks if the provided input starts with the word "menu" .
     *
     * @param input The user input string
     * @return true if user input starts with "menu", else returns false
     */
    public Boolean isMenuCommand(String input) {
        LOGGER.log(Level.INFO, "Checking if Input is a Menu Command");
        return input.startsWith("menu");
    }

    /**
     * Checks if the provided input starts with the word "bye" .
     *
     * @param input The user input string
     * @return true if user input starts with "bye", else returns false
     */
    public Boolean isExitCommand(String input) {
        return input.startsWith("bye");
    }

    /**
     * Checks if the provided input starts with the word "add expense" .
     *
     * @param input The user input string
     * @return true if user input starts with "add expense", else returns false
     */
    public Boolean isAddExpenseCommand(String input) {
        return input.startsWith("add expense");
    }

    public Boolean isAddSavingCommand(String input) {
        return input.startsWith("add savings");
    }

    public Boolean isEditExpenseCommand(String input) {
        return input.startsWith("edit expense");
    }

    public Boolean isEditSavingCommand(String input) {
        return input.startsWith("edit savings");
    }

    public Boolean isDeleteExpenseCommand(String input) {
        return input.startsWith("delete expense");
    }

    public Boolean isReduceSavingCommand(String input) {
        return input.startsWith("reduce savings");
    }

    /**
     * Checks if the provided input starts with the phrase "change currency".
     *
     * @param input The user input string
     * @return true if user input starts with "change currency", else returns false
     */
    public Boolean isConvertCurrencyCommand(String input) {
        LOGGER.log(Level.INFO, "Checking if input is Change Currency Command");
        return input.startsWith("change currency");
    }

    public Boolean isSplitExpenseCommand(String input) {
        return input.startsWith("add shared bill");
    }

    public Boolean isListSplitExpenseCommand(String input) {
        return input.contentEquals("check split bills");
    }

    public Boolean isSetBudgetCommand(String input){
        return input.startsWith("set budget");
    }

    public Boolean isGetBudgetCommand(String input) {
        return input.startsWith("get budget");
    }

    public boolean isListBudgetCommand(String input){
        return input.startsWith("print budget");
    }

    public Boolean isSettleSplitExpenseCommand(String input) {
        return input.startsWith("settle bill");
    }

    public Boolean isGetExpensesInsightsCommand(String input) {
        return input.equalsIgnoreCase("get expenses insights");
    }

    public Boolean isGetSavingsInsightsCommand(String input) {
        return input.equalsIgnoreCase("get savings insights");
    }



    /**
     * Parses a string input into a Command object and returns the associated
     * command to handle the user input
     *
     * @param input The user input string.
     * @return A Command object corresponding to the user input, or null if the
     *         input is invalid.
     */
    public Command parseCommand(ExpenseList expenses, SavingList savings, SplitExpenseList
            splitexpenses, RecurringExpenseLists expensesList, String input) {

        CommandCreator commandCreator = null;

        if(isMenuCommand(input)) {
            LOGGER.log(Level.INFO, "Confirmed that input is a menu command");
            commandCreator = new MenuCommandCreator(input);
        }
        if (isAddExpenseCommand(input)) {
            commandCreator = new AddExpenseCommandCreator(expenses, input);
        }
        if (isAddSavingCommand(input)) {
            commandCreator = new AddSavingCommandCreator(savings, input);
        }
        if (isEditExpenseCommand(input)) {
            commandCreator = new EditExpenseCommandCreator(input, expenses);
        }
        if (isEditSavingCommand(input)) {
            commandCreator = new EditSavingsCommandCreator(input, savings);
        }
        if (isDeleteExpenseCommand(input)) {
            commandCreator = new DeleteExpenseCommandCreator(expenses, input);
        }
        if (isReduceSavingCommand(input)) {
            commandCreator = new ReduceSavingCommandCreator(savings, input);
        }
        if (isListCommand(input.toLowerCase())) {
            commandCreator = new ListCommandCreator(expenses, savings, input);
        }
        if (isListSplitExpenseCommand(input)) {
            commandCreator = new ListSplittedExpenseCommandCreator(input, splitexpenses);
        }
        if (isFindExpensesCommand(input)) {
            commandCreator = new FindExpensesCommandCreator(input, expenses);
        }
        if (isRecCommand(input)) {
            commandCreator = new RecurringExpenseCommandCreator(input, expensesList, expenses);
        }
        if (isConvertCurrencyCommand(input.toLowerCase())) {
            commandCreator = new ChangeCurrencyCommandCreator(input, savings, expenses, splitexpenses,
                    expensesList, new CurrencyConverter());
        }
        if (isSplitExpenseCommand(input)) {
            commandCreator = new SplitExpenseCommandCreator(splitexpenses, input);
        }
        if (isSettleSplitExpenseCommand(input)) {
            commandCreator = new SettleSplitExpenseCommandCreator(input, splitexpenses);
        }
        if (isSetBudgetCommand(input)) {
            commandCreator = new SetBudgetCommandCreator(expenses, input);
        }
        if (isGetBudgetCommand(input)) {
            commandCreator = new GetBudgetCommandCreator(expenses, input);
        }
        if (isListBudgetCommand(input)){
            commandCreator = new ListBudgetCommandCreator(expenses);
        }
        if (isGetExpensesInsightsCommand(input)) {
            commandCreator = new GetExpenseInsightsCommandCreator(expenses);
        }
        if (isGetSavingsInsightsCommand(input)) {
            commandCreator = new GetSavingsInsightsCommandCreator(savings);
        }

        if (commandCreator == null) {
            return null;
        }

        return commandCreator.createCommand();
    }
}
