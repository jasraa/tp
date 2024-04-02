package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import seedu.budgetbuddy.command.MenuCommand;

public class MenuCommandTest {

    @Test
    public void execute_validMenuCommandWithInput0_printsAndNoExceptions() {
        MenuCommand menuCommand = new MenuCommand(0);

        menuCommand.execute();
    }

    @Test
    public void execute_validMenuCommandWithInput1_printsAndNoExceptions() {
        MenuCommand menuCommand = new MenuCommand(1);
        menuCommand.execute();
    }
}
