package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.ExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.ListBudgetCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ListBudgetCommandCreator extends CommandCreator {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ExpenseList expenses;

    public ListBudgetCommandCreator(ExpenseList expenses){
        this.expenses = expenses;
    }

    @Override
    public Command createCommand() {
        LOGGER.log(Level.INFO, "Creating ListBudgetCommand");
        return new ListBudgetCommand(expenses);
    }
}
