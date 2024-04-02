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
                double categorySpent = expenseList.getExpenses().stream()
                        .filter(expense -> expense.getCategory().equalsIgnoreCase(category))
                        .mapToDouble(Expense::getAmount)
                        .sum();
                String remaining = categorySpent > budgetAmount ? "Exceeded" :
                                    String.format("$%.2f", budgetAmount - categorySpent);
                double percentSpent = (categorySpent / budgetAmount) * 100;

                System.out.printf("%-20s | $%-14.2f | $%-14.2f | %-14s | %-13.2f%%%n",
                        category, budgetAmount, categorySpent, remaining, percentSpent);
                System.out.println(String.join("",
                        java.util.Collections.nCopies(88, "-"))); // Creates a line
            }
        }

        System.out.println("\nCategories above budget:");
        System.out.printf("%-20s | %-15s%n", "Category", "Exceeded by");
        System.out.println(String.join("", java.util.Collections.nCopies(44, "-")));

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
        System.out.println(String.join("", java.util.Collections.nCopies(44, "-")));
    }
}
