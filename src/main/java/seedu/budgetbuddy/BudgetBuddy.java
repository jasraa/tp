package seedu.budgetbuddy;

import seedu.budgetbuddy.command.Command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class BudgetBuddy {
    private Ui ui;
    private Parser parser;
    private ExpenseList expenses;
    private SavingList savings;
    private SplitExpenseList splitexpenses;
    private RecurringExpensesList expensesList;

    private Storage expensesStorage;
    private Storage savingsStorage;
    private Storage defaultCurrency;



    public BudgetBuddy() {
        ui = new Ui();
        parser = new Parser();
        expenses = new ExpenseList();
        savings = new SavingList();
        expensesList = new RecurringExpensesList();
        splitexpenses = new SplitExpenseList();
        expensesStorage = new Storage("src/main/java/seedu/budgetbuddy/data/ExpenseFile.txt");
        savingsStorage = new Storage("src/main/java/seedu/budgetbuddy/data/SavingsFile.txt");
        defaultCurrency = new Storage("src/main/java/seedu/budgetbuddy/data/DefaultCurrency.txt");

    }

    public void handleCommands(String input) {
        Command command = parser.parseCommand(expenses, savings, splitexpenses, expensesList, input);


        if (command != null) {
            command.execute();
        } else {
            System.out.println("(Invalid command)");
        }

        try {
            expensesStorage.saveExpenses(expenses.getExpenses());
            savingsStorage.saveSavings(savings.getSavings());

            // Save Currency
            defaultCurrency.saveCurrency(expenses.getExpenses(), savings.getSavings());
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        try {
            this.expenses.getExpenses().addAll(expensesStorage.loadExpenses());
            this.savings.getSavings().addAll(savingsStorage.loadSavings());

            // Load Currency
            defaultCurrency.loadCurrency(expenses.getExpenses(), savings.getSavings());

        } catch (FileNotFoundException e) {
            System.out.println("No existing files found. Starting fresh.");
        }

        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            String input = scanner.nextLine();

            if (parser.isExitCommand(input)) {
                isExit = true;
            } else {
                handleCommands(input);
            }
        }

        ui.showGoodbye();
        scanner.close();
    }

    public static void main(String[] args) {
        new BudgetBuddy().run();
    }
}
