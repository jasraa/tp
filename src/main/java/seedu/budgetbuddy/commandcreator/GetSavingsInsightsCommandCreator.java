package seedu.budgetbuddy.commandcreator;
import seedu.budgetbuddy.commons.SavingList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.GetSavingsInsightsCommand;

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
