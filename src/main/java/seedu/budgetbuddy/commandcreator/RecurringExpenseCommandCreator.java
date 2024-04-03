package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.commons.RecurringExpensesList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

public class RecurringExpenseCommandCreator extends CommandCreator{
    private static final String LISTNUMBER_PREFIX = "to/";
    private static final String CATEGORY_PREFIX = "c/";
    private static final String AMOUNT_PREFIX = "a/";
    private static final String DESCRIPTION_PREFIX = "d/";
    private String input;
    private RecurringExpensesList recurringExpensesList;
    private ExpenseList expenses;

    public RecurringExpenseCommandCreator(String input, RecurringExpensesList recurringExpensesList
            , ExpenseList expenses) {
        this. input = input;
        this.recurringExpensesList = recurringExpensesList;
        this.expenses = expenses;
    }

    private void checkForInvalidCharacters(String input) throws BudgetBuddyException{
        if (input.contains("|") || input.contains("!")) {
            throw new BudgetBuddyException("Please do not include a | or ! in your input");
        }
    }

    public Command createViewExpensesCommand(String[] commandParts) {
        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpensesList, "viewexpenses");
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec viewexpenses [List Number]");
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec viewexpenses [List Number]");
            return null;
        }
    }
    public Command createAddListToOverallExpensesCommand(String[] commandParts) {

        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpensesList, expenses, "addrec");
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec addrec [List Number]");
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec addrec [List Number]");
            return null;
        }

    }

    private static void checkForInvalidParameters(String input) {
        if (!input.contains("to/") || !input.contains("d/") || !input.contains("a/") || !input.contains("c/")) {
            throw new IllegalArgumentException("Please Ensure that you include to/, c/, a/ and d/");
        }
    }

    private String parseDescription(String input) throws BudgetBuddyException {
        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int startIndexOfDescription = indexOfDescriptionPrefix + DESCRIPTION_PREFIX.length();

        int endIndexOfDescription = input.length();

        String description = input.substring(startIndexOfDescription,endIndexOfDescription);

        if(description.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure Description is NOT empty");
        }

        return description;
    }
    private Double parseAmount(String input) throws NumberFormatException, BudgetBuddyException{
        int indexOfAmountPrefix = input.indexOf(AMOUNT_PREFIX);
        int startIndexOfAmount = indexOfAmountPrefix + AMOUNT_PREFIX.length();

        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int endIndexOfAmount = indexOfDescriptionPrefix - 1;

        String amountAsString = input.substring(startIndexOfAmount, endIndexOfAmount);

        if(amountAsString.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure Amount is NOT empty");
        }

        Double amount = Double.parseDouble(amountAsString);

        return amount;
    }

    private String parseCategory(String input) throws BudgetBuddyException{
        int indexOfCategoryPrefix = input.indexOf(CATEGORY_PREFIX);
        int startIndexOfCategory = indexOfCategoryPrefix + CATEGORY_PREFIX.length();

        int indexOfAmountPrefix = input.indexOf(AMOUNT_PREFIX);
        int endIndexOfCategory = indexOfAmountPrefix - 1;

        String category = input.substring(startIndexOfCategory, endIndexOfCategory);

        if(category.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure Category is NOT empty");
        }

        return category;
    }
    private int parseListNumber(String input) throws NumberFormatException, BudgetBuddyException{
        int indexOfListNumberPrefix = input.indexOf(LISTNUMBER_PREFIX);
        int startIndexOfListNumber = indexOfListNumberPrefix + LISTNUMBER_PREFIX.length();

        int indexOfCategoryPrefix = input.indexOf(CATEGORY_PREFIX);
        int endIndexOfListNumber = indexOfCategoryPrefix - 1;

        String listNumberAsString = input.substring(startIndexOfListNumber, endIndexOfListNumber);

        if(listNumberAsString.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure List Number is NOT empty");
        }

        int listNumber = Integer.parseInt(listNumberAsString);

        return listNumber;
    }

    public Command createAddExpenseToListCommand(String input) {
        try {
            checkForInvalidParameters(input);
            checkForInvalidCharacters(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }

        try {
            int listNumber = parseListNumber(input);
            String category = parseCategory(input);
            Double amount = parseAmount(input);
            String description = parseDescription(input);

            return new RecurringExpenseCommand(listNumber, recurringExpensesList, category,
                    amount, description, "newexpense");
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Please ensure that listNumber and Amount are valid Numbers");
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        }

    }

    public Command createRemoveListCommand(String[] commandParts) {
        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpensesList, "removelist");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec removelist [List Number]");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec removelist [List Number]");
            return null;
        }
    }
    public Command createViewListCommand() {
        return new RecurringExpenseCommand(recurringExpensesList, "viewlists");
    }

    public Command createNewListCommand(String[] commandParts) {
        try {
            String listName = commandParts[2];
            checkForInvalidCharacters(input);
            return new RecurringExpenseCommand(listName, recurringExpensesList, "newlist");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please Input a Valid listName");
            System.out.println("Command Format : rec newlist [listName]");
            return null;
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Command handleRecCommand(String input){
        String[] commandParts = input.split(" ");
        String commandType = commandParts[1];
        commandType = commandType.trim();

        switch(commandType) {
        case "newlist":
            return createNewListCommand(commandParts);
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

    }

    @Override
    public Command createCommand() {
        return handleRecCommand(input);
    }
}
