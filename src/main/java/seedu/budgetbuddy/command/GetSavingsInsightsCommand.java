package seedu.budgetbuddy;

import seedu.budgetbuddy.SavingList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.exception.BudgetBuddyException;

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
