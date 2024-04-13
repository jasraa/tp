package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.FindExpensesCommand;
import seedu.budgetbuddy.commandcreator.CommandCreator;
import seedu.budgetbuddy.commandcreator.FindExpensesCommandCreator;
import seedu.budgetbuddy.commons.ExpenseList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FindExpensesCommandCreatorTest {
    private FindExpensesCommandCreator initializeFindExpensesCommandCreator(String input) {
        ExpenseList expenses = new ExpenseList();
        return new FindExpensesCommandCreator(input, expenses);
    }

    @Test
    public void createCommand_invalidParametersGiven_returnsNull(){
        String inputWithoutDescription = "find expenses morethan/ lessthan/";
        String inputWithoutMax = "find expenses d/ morethan/";
        String inputWithoutMin = "find expenses d/ lessthan/";
        String inputWithoutMinAndMax = "find expenses d/Hello";
        String inputWithoutDescriptionAndMax = "find expenses morethan/test";
        String inputWithoutDescriptionAndMin = "find expenses lessthan/test";
        String inputWithoutParameters = "find expenses ";

        assertNull(initializeFindExpensesCommandCreator(inputWithoutMax).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithoutDescription).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithoutMin).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithoutMinAndMax).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithoutDescriptionAndMax).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithoutDescriptionAndMin).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithoutParameters).createCommand());
    }

    @Test
    public void createCommand_duplicateParameters_returnsNull() {
        String inputWithDuplicateDescription = "find expenses d/23 morethan/200 lessthan/400 d/50";
        String inputWithDuplicateMinAmount = "find expenses d/23 morethan/200 lessthan/400 morethan/20";
        String inputWithDuplicateMaxAmount = "find expenses d/23 morethan/200 lessthan/400 lessthan/20";

        assertNull(initializeFindExpensesCommandCreator(inputWithDuplicateDescription).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithDuplicateMinAmount).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithDuplicateMaxAmount).createCommand());
    }

    @Test
    public void createCommand_outOfOrderParameters_returnsNull() {
        String inputWithOutOfOrderAmountParameters = "find expenses d/23 lessthan/40 morethan/";
        String inputWithOutOfOrderDescriptionParameter = "find expenses morethan/ d/23 lessthan/40";
        assertNull(initializeFindExpensesCommandCreator(inputWithOutOfOrderAmountParameters).createCommand());
        assertNull(initializeFindExpensesCommandCreator(inputWithOutOfOrderDescriptionParameter).createCommand());
    }
    @Test
    public void createCommand_invalidMinAmount_returnsNull() {
        String validInputWithEmptyDescription = "find expenses d/hello morethan/dsfefew lessthan/20";

        Command command = initializeFindExpensesCommandCreator(validInputWithEmptyDescription).createCommand();
        assertNull(command);
    }

    @Test
    public void createCommand_invalidMaxAmount_returnsNull() {
        String validInputWithEmptyDescription = "find expenses d/hello morethan/20 lessthan/sdsdasdasd";

        Command command = initializeFindExpensesCommandCreator(validInputWithEmptyDescription).createCommand();
        assertNull(command);
    }
    @Test
    public void createCommand_invalidMaxAndMinValues_returnsNull() {

        String input = "find expenses d/Bruno Mars morethan/400 lessthan/300";
        CommandCreator commandCreator = initializeFindExpensesCommandCreator(input);
        Command command = commandCreator.createCommand();
        assertNull(command);

    }

    @Test
    public void createCommand_maxAndMinValuesAsLetters_returnsNull() {
        ExpenseList expenses = new ExpenseList();

        String input = "find expenses d/Bruno Mars morethan/hello lessthan/hello";
        CommandCreator commandCreator = new FindExpensesCommandCreator(input, expenses);
        Command command = commandCreator.createCommand();
        assertNull(command);
    }

    @Test
    public void createCommand_emptyDescription_returnsFindExpensesCommand() {
        String validInputWithEmptyDescription = "find expenses d/ morethan/200 lessthan/400";

        Command command = initializeFindExpensesCommandCreator(validInputWithEmptyDescription).createCommand();
        assertInstanceOf(FindExpensesCommand.class, command);
    }

    @Test
    public void createCommand_emptyMinAmount_returnsFindExpensesCommand() {
        String validInputWithEmptyDescription = "find expenses d/hello morethan/ lessthan/200";

        Command command = initializeFindExpensesCommandCreator(validInputWithEmptyDescription).createCommand();
        assertInstanceOf(FindExpensesCommand.class, command);
    }

    @Test
    public void createCommand_emptyMaxAmount_returnsFindExpensesCommand() {
        String validInputWithEmptyDescription = "find expenses d/hello morethan/200 lessthan/";

        Command command = initializeFindExpensesCommandCreator(validInputWithEmptyDescription).createCommand();
        assertInstanceOf(FindExpensesCommand.class, command);
    }

    @Test
    public void createCommand_validInput_returnsFindExpenseCommand() {
        String input = "find expenses d/Bruno Mars morethan/400 lessthan/500";
        CommandCreator commandCreator = initializeFindExpensesCommandCreator(input);
        Command command = commandCreator.createCommand();
        assertInstanceOf(FindExpensesCommand.class, command);

    }
}
