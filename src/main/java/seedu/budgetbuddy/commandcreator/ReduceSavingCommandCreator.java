package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.ReduceSavingCommand;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReduceSavingCommandCreator extends CommandCreator {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private SavingList savings;
    private String input;

    public ReduceSavingCommandCreator(SavingList savings, String input) {
        this.savings = savings;
        this.input = input;
    }

    public Command handleReduceSavingCommand(SavingList savings, String input) {
        LOGGER.log(Level.INFO, "Processing handleReduceSavingCommand");

        String description = input.replace("reduce", "").trim();
        Pattern categoryPattern = Pattern.compile("c/\\s*(\\w+)\\s*");
        Pattern amountPattern = Pattern.compile("a/\\s*(-?\\d+(\\.\\d+)?)\\s*");

        Matcher categoryMatcher = categoryPattern.matcher(description);
        Matcher amountMatcher = amountPattern.matcher(description);

        if (categoryMatcher.find() && amountMatcher.find()) {
            try {
                String categoryToReduce = categoryMatcher.group(1);
                double amountToReduce = Double.parseDouble(amountMatcher.group(1));

                if (amountToReduce <= 0) {
                    LOGGER.log(Level.WARNING, "Amount must be a positive value.");
                    System.out.println("Amount must be a positive value.");
                    return null;
                }

                LOGGER.log(Level.INFO, "Successfully processed ReduceSavingCommand!");
                return new ReduceSavingCommand(savings, categoryToReduce, amountToReduce);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Amount must be a valid number. Please try again.");
                System.out.println("Amount must be a valid number.");
                return null;
            }
        } else {
            LOGGER.log(Level.WARNING, "Invalid command format. Expected format: " +
                                            "reduce savings c/<category> a/<amount>.");
            System.out.println("Invalid command format. Expected format: reduce savings c/<category> a/<amount>");
            return null;
        }
    }

    @Override
    public Command createCommand() {
        return handleReduceSavingCommand(savings, input);
    }
}
