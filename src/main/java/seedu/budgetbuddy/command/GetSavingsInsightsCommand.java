package seedu.budgetbuddy.command;

import seedu.budgetbuddy.SavingList;

public class GetSavingsInsightsCommand extends Command {

    private SavingList savingList;

    public GetSavingsInsightsCommand(SavingList savingList) {
        this.savingList = savingList;
    }

    @Override
    public void execute() {
        savingList.getSavingsInsights();
    }
}
