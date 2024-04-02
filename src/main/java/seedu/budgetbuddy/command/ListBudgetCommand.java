package seedu.budgetbuddy.command;

import seedu.budgetbuddy.Budget;
import seedu.budgetbuddy.Expense;
import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.Ui;

public class ListBudgetCommand extends Command{
    private ExpenseList expenseList;
    public ListBudgetCommand(ExpenseList expenseList){
        this.expenseList = expenseList;
    }

    @Override
    public void execute() {
        Ui ui = new Ui();

        // Print all budgets in a table format
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n", "Category", "Budget", "Spent",
                "Remaining", "% Spent");
        System.out.println(String.join("", java.util.Collections.nCopies(88, "-"))); // Creates a line

        if (expenseList.getBudgets().isEmpty()) {
            System.out.println("No budgets set.");
        } else {
            for (Budget budget : expenseList.getBudgets()) {
                String category = budget.getCategory();
                double budgetAmount = budget.getBudget();
                double totalSpent = expenseList.getExpenses().stream()
                        .filter(expense -> expense.getCategory().equalsIgnoreCase(category))
                        .mapToDouble(Expense::getAmount)
                        .sum();
                double remaining = budgetAmount - totalSpent;
                double percentSpent = (totalSpent / budgetAmount) * 100;

                // Print budget with total spent and remaining for each category
                System.out.printf("%-20s | $%-14.2f | $%-14.2f | $%-14.2f | %-13.2f%%%n",
                        category, budgetAmount, totalSpent, remaining, percentSpent);
                System.out.println(String.join("",
                        java.util.Collections.nCopies(88, "-"))); // Creates a line
            }
        }

        System.out.println("\nCategories above budget:");
        System.out.printf("%-20s | %-15s%n", "Category", "Exceeded by");
        System.out.println(String.join("", java.util.Collections.nCopies(44, "-"))); // Creates a line
        boolean found = false;

        for (String category : expenseList.getCategories()) {
            double totalSpent = expenseList.getExpenses().stream()
                    .filter(expense -> expense.getCategory().equalsIgnoreCase(category))
                    .mapToDouble(Expense::getAmount)
                    .sum();

            Budget budgetForCategory = expenseList.getBudgets().stream()
                    .filter(budget -> budget.getCategory().equalsIgnoreCase(category))
                    .findFirst()
                    .orElse(null);

            if (budgetForCategory != null && totalSpent > budgetForCategory.getBudget()) {
                double exceededBy = totalSpent - budgetForCategory.getBudget();
                System.out.printf("%-20s | $%-14.2f%n", category, exceededBy);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No categories are above budget.");
        }
        ui.printDivider();
    }
}