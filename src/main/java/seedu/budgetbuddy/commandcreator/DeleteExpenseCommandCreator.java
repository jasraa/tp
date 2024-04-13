package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.DeleteExpenseCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteExpenseCommandCreator extends CommandCreator{
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ExpenseList expenseList;
    private String input;

    public DeleteExpenseCommandCreator(ExpenseList expenseList, String input){
        this.expenseList = expenseList;
        this.input = input;
    }

    public Command handleDeleteExpenseCommand(ExpenseList expenses, String input) {
        LOGGER.log(Level.INFO, "Processing handleDeleteExpenseCommand");

        assert expenses != null : "Expense list cannot be null";
        assert input != null : "Input string cannot be null";

        String[] parts = input.split("i/", 2);
        if (parts.length < 2) {
            // Log and notify the user about the incorrect format without returning null.
            LOGGER.log(Level.WARNING, "Invalid command format. Expected format: delete expense i/<index>." );
            System.out.println("Invalid command format. Expected format: delete expense i/<index>.");
            return null; // Return null to indicate no command should be executed; assuming your loop can handle this.
        }

        try {
            int index = Integer.parseInt(parts[1].trim()) - 1;
            if (index < 0 || index >= expenses.size()) {
                LOGGER.log(Level.WARNING, "Index is out of bounds. Please try again.");
                System.out.println("Index is out of bounds. Please try again.");
                return null;
            }
            LOGGER.log(Level.INFO, "Successfully processed DeleteExpenseCommand");
            return new DeleteExpenseCommand(expenses, index);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Index is not a valid number. Please try again.");
            System.out.println("Index is not a valid number. Please try again.");
            return null;
        }
    }

    @Override
    public Command createCommand(){
        return handleDeleteExpenseCommand(expenseList, input);
    }
}
