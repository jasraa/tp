package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.SavingList;
import seedu.budgetbuddy.GetSavingsInsightsCommand;
import seedu.budgetbuddy.command.Command;

public class GetSavingsInsightsCommandCreator extends CommandCreator {

    private SavingList savingList;

    public GetSavingsInsightsCommandCreator(SavingList savingList) {
        this.savingList = savingList;
    }

    @Override
    public Command createCommand() {
        return new GetSavingsInsightsCommand(savingList);
    }
}
