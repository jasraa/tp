package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.FindExpensesCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindExpensesCommandCreator extends CommandCreator {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String DESCRIPTION_PREFIX = "d/";
    private static final String MINAMOUNT_PREFIX = "morethan/";
    private static final String MAXAMOUNT_PREFIX = "lessthan/";


    private ExpenseList expenses;
    private String input;

    public FindExpensesCommandCreator(String input, ExpenseList expenses) {
        this.input = input;
        this.expenses = expenses;
    }


    /**
     * Checks the order of parameters in the provided input.
     *
     * @param input The user input
     * @throws BudgetBuddyException If the parameters are not in the order of d/, morethan/ , lessthan/.
     */
    private void checkForOutOfOrderParameters(String input) throws BudgetBuddyException {
        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int indexOfMinAmountPrefix = input.indexOf(MINAMOUNT_PREFIX);
        int indexOfMaxAmountPrefix = input.indexOf(MAXAMOUNT_PREFIX);

        if (indexOfDescriptionPrefix > indexOfMinAmountPrefix) {
            throw new BudgetBuddyException("Please ensure that your parameters are in the right order.");
        }

        if (indexOfMinAmountPrefix > indexOfMaxAmountPrefix) {
            throw new BudgetBuddyException("Please ensure that your parameters are in the right order.");
        }

    }

    /**
     * Checks for the absence of the required parameters `d/`, `morethan/` and `lessthan/`
     *
     * @param input The user input
     * @throws IllegalArgumentException If any of the three required parameters are missing
     */
    private static void checkForInvalidParameters(String input) throws IllegalArgumentException {
        if (!input.contains("d/") || !input.contains("morethan/") || !input.contains("lessthan/")) {
            throw new IllegalArgumentException("Please Ensure that you include d/, morethan/ and lessthan/");
        }
    }

    /**
     * Parses and returns the maximum amount from the `lessthan/` prefix in the input string
     *
     * @param input The user input
     * @return The extracted maximum amount, or null if amount is not specified
     * @throws NumberFormatException If the maximum amount obtained is not a valid double
     */
    private Double parseMaxAmount(String input) throws NumberFormatException{
        int indexOfMaxAmountPrefix = input.indexOf(MAXAMOUNT_PREFIX);
        int startIndexOfMaxAmount = indexOfMaxAmountPrefix + MAXAMOUNT_PREFIX.length();

        int endIndexOfMaxAmount = input.length();

        String maxAmountAsString = input.substring(startIndexOfMaxAmount, endIndexOfMaxAmount).trim();

        if (maxAmountAsString.trim().isEmpty()) {
            return null;
        }

        Double maxAmount = Double.parseDouble(maxAmountAsString);

        return maxAmount;
    }

    /**
     * Parses and returns the minimum amount from the `morethan/` prefix in the input string
     *
     * @param input The user input
     * @return The extracted minimum amount, or null if amount is not specified
     * @throws NumberFormatException If the minimum amount obtained is not a valid double
     */
    private Double parseMinAmount(String input) throws NumberFormatException {
        int indexOfMinAmountPrefix = input.indexOf(MINAMOUNT_PREFIX);
        int startIndexOfMinAmount = indexOfMinAmountPrefix + MINAMOUNT_PREFIX.length();

        int indexOfMaxAmountPrefix = input.indexOf(MAXAMOUNT_PREFIX);
        int endIndexOfMinAmount = indexOfMaxAmountPrefix;

        String minAmountAsString = input.substring(startIndexOfMinAmount, endIndexOfMinAmount).trim();

        if (minAmountAsString.trim().isEmpty()) {
            return null;
        }

        Double minAmount = Double.parseDouble(minAmountAsString);

        return minAmount;
    }

    /**
     * Parses and returns the description from the `d/` prefix in the input string
     *
     * @param input The user input
     * @return The obtained description, or null if the description is empty
     */
    private String parseDescription(String input) {

        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int startIndexOfDescription = indexOfDescriptionPrefix + DESCRIPTION_PREFIX.length();

        int indexOfMinAmountPrefix = input.indexOf(MINAMOUNT_PREFIX);
        int endIndexOfDescription = indexOfMinAmountPrefix;

        String description = input.substring(startIndexOfDescription, endIndexOfDescription).trim();

        if (description.isEmpty()) {
            return null;
        }

        return description;
    }

    /**
     * Checks for duplicate occurrences of a prefix in the input string
     *
     * @param input The user input
     * @param parameter The parameter to check for duplicates
     * @throws IllegalArgumentException If the parameter appears more than once
     */
    private static void checkForDuplicateParameters(String input, String parameter) throws IllegalArgumentException{

        int count = 0;

        Pattern pattern = Pattern.compile(parameter);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            count++;
        }

        if (count > 1) {
            throw new IllegalArgumentException("The parameter '" + parameter + "' can only be used once.");
        }

    }

    /**
     * Compares the minimum and maximum amounts and throws an exception if the minimum amount is larger than the maximum amount
     *
     * @param minAmount The minimum amount
     * @param maxAmount The maximum amount
     * @throws BudgetBuddyException If the minimum amount > maximum amount
     */
    private static void compareMinAndMaxAmount(Double minAmount, Double maxAmount) throws BudgetBuddyException{

        if (minAmount != null && maxAmount != null) {
            if (minAmount > maxAmount) {
                throw new BudgetBuddyException("Ensure minimum amount is smaller than maximum amount");
            }
        }

    }

    /**
     * Parses the "find expenses" command, allowing for optional and combinable parameters.
     *
     * @param input The full user input string.
     * @param expenses The ExpenseList to search within.
     * @return A Command for executing the search, or null if the input is invalid.
     */
    private Command handleFindExpensesCommand(String input, ExpenseList expenses) {
        assert input != null : "Input cannot be null";
        assert !input.isEmpty() : "Input cannot be empty";
        assert input.startsWith("find expenses") : "Input must be a find expenses command";

        LOGGER.log(Level.INFO, "Begin parsing parameters in find expenses command");

        try {
            checkForInvalidParameters(input);
            checkForOutOfOrderParameters(input);
            checkForDuplicateParameters(input, "d/");
            checkForDuplicateParameters(input, "morethan/");
            checkForDuplicateParameters(input, "lessthan/");

        } catch (IllegalArgumentException | BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }

        try {
            String description = parseDescription(input);
            Double minAmount = parseMinAmount(input);
            Double maxAmount = parseMaxAmount(input);

            compareMinAndMaxAmount(minAmount, maxAmount);

            return new FindExpensesCommand(expenses, description, minAmount, maxAmount);

        } catch (NumberFormatException e) {
            System.out.println("Please input a valid amount.");
            return null;
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Command createCommand() {
        return handleFindExpensesCommand(input, expenses);
    }
}
