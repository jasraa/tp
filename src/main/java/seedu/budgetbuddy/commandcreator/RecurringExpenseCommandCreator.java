package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpenseLists;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecurringExpenseCommandCreator extends CommandCreator{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final double MAX_AMOUNT = 1_000_000_000_000.00;
    private static final String LISTNUMBER_PREFIX = "to/";
    private static final String CATEGORY_PREFIX = "c/";
    private static final String AMOUNT_PREFIX = "a/";
    private static final String DESCRIPTION_PREFIX = "d/";
    private String input;
    private RecurringExpenseLists recurringExpenseLists;
    private ExpenseList expenses;

    private ArrayList<String> expenseCategories = new ArrayList<>(Arrays.asList("Housing"
            , "Groceries", "Utility", "Transport", "Entertainment", "Others"));


    /**
     * Constructs a RecurringExpenseCommandCreator with the provided input, recurringExpensesList and expenses
     * @param input The user input
     * @param recurringExpenseLists The RecurringExpensesList containing a list of ExpenseList
     * @param expenses The ExpenseList containing user's overall expenses
     */
    public RecurringExpenseCommandCreator(String input, RecurringExpenseLists recurringExpenseLists
            , ExpenseList expenses) {

        this. input = input;
        this.recurringExpenseLists = recurringExpenseLists;
        this.expenses = expenses;
    }

    /**
     * Checks the input for the presence of `|` and `!`. Throws the BudgetBuddyException if detected in the user input
     *
     * @param input The user input
     * @throws IllegalArgumentException if any of the required prefixes are not found
     */
    private void checkForInvalidCharacters(String input) throws BudgetBuddyException{
        if (input.contains("|") || input.contains("!")) {
            LOGGER.log(Level.WARNING, "An attempt of including a | and ! in input has been detected." +
                    "Attempting to handle error");
            throw new BudgetBuddyException("Please do not include a | or ! in your input");
        }
    }

    /**
     * Creates a RecurringExpenseCommand to view all expenses in a specific ExpenseList in recurringExpensesList
     * This method obtains the listNumber from the provided commandParts.
     *
     * @param commandParts The split parts of the input command string
     * @return RecurringExpenseCommand if list number is valid, returns null if list number is invalid or empty
     */
    public Command createViewExpensesCommand(String[] commandParts) {
        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpenseLists, "viewexpenses");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "An invalid integer has been detected");
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec viewexpenses [List Number]");
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "Value at commandParts[2] does not exist");
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec viewexpenses [List Number]");
            return null;
        }
    }

    /**
     * Creates a RecurringExpenseCommand to add the expenses in a specific ExpenseList in recurringExpensesList into
     * the overall ExpenseList.
     * This method obtains the listNumber from the provided commandParts.
     *
     * @param commandParts The split parts of the input command string
     * @return RecurringExpenseCommand if the list number is valid, returns null if list number is invalid or empty
     */
    public Command createAddListToOverallExpensesCommand(String[] commandParts) {

        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpenseLists, expenses, "addrec");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "An invalid integer has been detected");
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec addrec [List Number]");
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "Value at commandParts[2] does not exist");
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec addrec [List Number]");
            return null;
        }

    }


    /**
     * Checks the input for the presence of all the required to/ , d/, a/ and c/ prefixes
     *
     * @param input The user input
     * @throws IllegalArgumentException if any of the required prefixes are not found
     */
    private static void checkForInvalidParameters(String input) {
        if (!input.contains("to/") || !input.contains("d/") || !input.contains("a/") || !input.contains("c/")) {
            throw new IllegalArgumentException("Please Ensure that you include to/, c/, a/ and d/");
        }
    }

    /**
     * Parses the description from the input string
     *
     * @param input The user input
     * @return The extracted description from the d/ prefix
     * @throws BudgetBuddyException if the description is empty
     */
    private String parseDescription(String input) throws BudgetBuddyException {
        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int startIndexOfDescription = indexOfDescriptionPrefix + DESCRIPTION_PREFIX.length();

        int endIndexOfDescription = input.length();

        String description = input.substring(startIndexOfDescription,endIndexOfDescription).trim();

        if(description.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Empty Description Detected, throwing BudgetBuddyException");
            throw new BudgetBuddyException("Please Ensure Description is NOT empty");
        }

        return description;
    }

    /**
     * Parses the amount from the input string
     *
     * @param input The user input
     * @return The extracted amount from the a/ prefix
     * @throws NumberFormatException If the extracted amount is not a valid double
     * @throws BudgetBuddyException If the extracted amount is empty
     */
    private Double parseAmount(String input) throws NumberFormatException, BudgetBuddyException{
        int indexOfAmountPrefix = input.indexOf(AMOUNT_PREFIX);
        int startIndexOfAmount = indexOfAmountPrefix + AMOUNT_PREFIX.length();

        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int endIndexOfAmount = indexOfDescriptionPrefix;

        String amountAsString = input.substring(startIndexOfAmount, endIndexOfAmount);

        if(amountAsString.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Empty Amount Detected, throwing BudgetBuddyException");
            throw new BudgetBuddyException("Please Ensure Amount is NOT empty");
        }

        Double amount = Double.parseDouble(amountAsString);

        if(amount > MAX_AMOUNT) {
            throw new BudgetBuddyException("Please Ensure that Amount is Less than " + "1,000,000,000,000");
        }

        return amount;
    }


    /**
     * Returns a case-insensitive match to the provided `category`
     * @param category The category to be found
     * @return The case-insensitive match to the category to be found
     * @throws BudgetBuddyException if no matches are found
     */
    private String getCategory(String category) throws BudgetBuddyException{
        for (String validCategory : expenseCategories) {
            if (validCategory.equalsIgnoreCase(category)) {
                return validCategory;
            }
        }

        throw new BudgetBuddyException("Please ensure the category is a valid category\n" +
                "Valid Categories : Entertainment, Housing, Groceries, Utility, Transport, Other");
    }
    /**
     * Parses the category from the input string
     *
     * @param input The user input
     * @return The extracted category from the c/ prefix
     * @throws BudgetBuddyException If the category is empty
     */
    private String parseCategory(String input) throws BudgetBuddyException{
        int indexOfCategoryPrefix = input.indexOf(CATEGORY_PREFIX);
        int startIndexOfCategory = indexOfCategoryPrefix + CATEGORY_PREFIX.length();

        int indexOfAmountPrefix = input.indexOf(AMOUNT_PREFIX);
        int endIndexOfCategory = indexOfAmountPrefix;

        String categoryToObtain = input.substring(startIndexOfCategory, endIndexOfCategory).trim();

        if(categoryToObtain.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Empty Category Detected, throwing BudgetBuddyException");
            throw new BudgetBuddyException("Please Ensure Category is NOT empty");
        }

        String category = getCategory(categoryToObtain);
        return category;
    }

    /**
     * Parses the list number from the input string
     *
     * @param input The user input
     * @return The extracted list number from the `to/` prefix
     * @throws NumberFormatException if the list number is not a valid number
     * @throws BudgetBuddyException if the list number is empty
     */
    private int parseListNumber(String input) throws NumberFormatException, BudgetBuddyException{
        int indexOfListNumberPrefix = input.indexOf(LISTNUMBER_PREFIX);
        int startIndexOfListNumber = indexOfListNumberPrefix + LISTNUMBER_PREFIX.length();

        int indexOfCategoryPrefix = input.indexOf(CATEGORY_PREFIX);
        int endIndexOfListNumber = indexOfCategoryPrefix;

        String listNumberAsString = input.substring(startIndexOfListNumber, endIndexOfListNumber).trim();

        if(listNumberAsString.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Empty ListNumber Detected, throwing BudgetBuddyException");
            throw new BudgetBuddyException("Please Ensure List Number is NOT empty");
        }

        int listNumber = Integer.parseInt(listNumberAsString);

        return listNumber;
    }

    private void checkForOutOfOrderParameters(String input) throws BudgetBuddyException{
        assert (input.contains(LISTNUMBER_PREFIX) && input.contains(CATEGORY_PREFIX)
                && input.contains(AMOUNT_PREFIX) && input.contains(DESCRIPTION_PREFIX))
                : "Input has all required prefixes";

        int indexOfListNumberPrefix = input.indexOf(LISTNUMBER_PREFIX);
        int indexOfCategoryPrefix = input.indexOf(CATEGORY_PREFIX);
        int indexOfAmountPrefix = input.indexOf(AMOUNT_PREFIX);
        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);

        if (indexOfListNumberPrefix > indexOfCategoryPrefix) {
            throw new BudgetBuddyException("Please Ensure your prefixes are in the right order");
        }

        if (indexOfCategoryPrefix > indexOfAmountPrefix) {
            throw new BudgetBuddyException("Please Ensure your prefixes are in the right order");
        }

        if (indexOfAmountPrefix > indexOfDescriptionPrefix) {
            throw new BudgetBuddyException("Please Ensure your prefixes are in the right order");
        }
    }

    private void checkForDuplicateParameters(String input) throws BudgetBuddyException {

        assert (input.contains(LISTNUMBER_PREFIX) && input.contains(CATEGORY_PREFIX)
                && input.contains(AMOUNT_PREFIX) && input.contains(DESCRIPTION_PREFIX))
                : "Input has all required prefixes";

        String[] parameters = {LISTNUMBER_PREFIX, CATEGORY_PREFIX, AMOUNT_PREFIX, DESCRIPTION_PREFIX};

        for (String parameter : parameters) {
            if (input.indexOf(parameter) != input.lastIndexOf(parameter)) {
                LOGGER.log(Level.WARNING, "Duplicate Parameters Detected, throwing BudgetBuddyException");
                throw new BudgetBuddyException("Please ensure that you do not have any duplicate parameters");
            }
        }
    }

    /**
     * Creates a RecurringExpenseCommand to add an expense into a specific ExpenseList in recurringExpensesList
     *
     * @param input The user input
     * @return RecurringExpenseCommand if user input is valid, returns null if any of the user input is invalid
     */
    public Command createAddExpenseToListCommand(String input) {
        try {
            checkForInvalidParameters(input);
            checkForDuplicateParameters(input);
            checkForInvalidCharacters(input);
            checkForOutOfOrderParameters(input);
        } catch (IllegalArgumentException | BudgetBuddyException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        }

        try {
            int listNumber = parseListNumber(input);
            String category = parseCategory(input);
            Double amount = parseAmount(input);
            String description = parseDescription(input);

            return new RecurringExpenseCommand(listNumber, recurringExpenseLists, category,
                    amount, description, "newexpense");
        } catch (BudgetBuddyException e) {
            LOGGER.log(Level.INFO, "Successfully caught BudgetBuddy Exception. Handling Error");
            System.out.println(e.getMessage());
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, "Successfully caught NumberFormatException. Handling Error");
            System.out.println("Please ensure that listNumber and Amount are valid Numbers/Monetary amounts");
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        }

    }

    /**
     * Creates a RecurringExpenseCommand to remove a specified ExpenseList in the recurringExpensesList
     * This method uses the provided commandParts to obtain the list Number of the ExpenseList to remove
     *
     * @param commandParts The split parts of the user input
     * @return RecurringExpenseCommand if user input is valid, returns null if listNumber is empty or invalid
     */
    public Command createRemoveListCommand(String[] commandParts) {
        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpenseLists, "removelist");
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "Successfully caught Exception. Handling Error");
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec removelist [List Number]");
            return null;
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, "Successfully caught Exception. Handling Error");
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec removelist [List Number]");
            return null;
        }
    }


    /**
     * Creates a RecurringExpenseCommand to print all the names of the ExpenseLists present in recurringExpensesList
     *
     * @return A RecurringExpenseCommand
     */
    public Command createViewListCommand() {
        return new RecurringExpenseCommand(recurringExpenseLists, "viewlists");
    }

    /**
     * Creates a RecurringExpenseCommand to add a new RecurringExpenseList into recurringExpensesList
     *
     * @param input The user input
     * @return RecurringExpenseCommand if listName is valid, returns null if the listName extracted is empty
     */
    public Command createNewListCommand(String input) {
        assert (input.startsWith("rec newlist")) : "Input must start with rec newlist ";

        try {
            checkForInvalidCharacters(input);
            int indexOfNewListCommandType = input.indexOf("newlist");
            int indexOfListName = indexOfNewListCommandType + "newlist".length();
            int endIndexOfListName = input.length();

            String listName = input.substring(indexOfListName, endIndexOfListName).trim();

            if (listName.isEmpty()) {
                System.out.println("Please Ensure the LISTNAME is not empty");
                return null;
            }

            return new RecurringExpenseCommand(listName, this.recurringExpenseLists, "newlist");

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Something went wrong");
            return null;
        } catch (BudgetBuddyException e) {
            LOGGER.log(Level.INFO, "BudgetBuddyException has been caught and handled");
            System.out.println(e.getMessage());
            return null;
        }

    }

    /**
     * Handles the creation of the various types of RecurringExpenseCommand based on the extracted commandType
     * This method extracts the commandType from the user input, and calls methods based on the commandType
     *
     * @param input The user input
     * @return RecurringExpenseCommand if commandType extracted is a valid commandType,
     *         returns null if commandType is not valid
     */
    public Command handleRecCommand(String input){

        try {
            String[] commandParts = input.split(" ");
            String commandType = commandParts[1];
            commandType = commandType.trim();

            switch(commandType) {
            case "newlist":
                return createNewListCommand(this.input);
            case "viewlists":
                return createViewListCommand();
            case "removelist":
                return createRemoveListCommand(commandParts);
            case "newexpense":
                return createAddExpenseToListCommand(input);
            case "addrec":
                return createAddListToOverallExpensesCommand(commandParts);
            case "viewexpenses":
                return createViewExpensesCommand(commandParts);
            default:
                System.out.println("This Command Type does not exist for \"rec\"");
                return null;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please do not leave the command type empty");
            return null;
        }

    }

    @Override
    public Command createCommand() {
        return handleRecCommand(input);
    }
}
