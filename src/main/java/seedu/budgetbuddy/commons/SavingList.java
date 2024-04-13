package seedu.budgetbuddy.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Collections;

import seedu.budgetbuddy.Ui;
import seedu.budgetbuddy.exception.BudgetBuddyException;

public class SavingList {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final double MAX_AMOUNT = 1_000_000_000_000.0;

    protected ArrayList<Saving> savings;
    protected ArrayList<String> categories;
    protected double initialAmount;
    Ui ui = new Ui();



    public SavingList() {
        this.savings = new ArrayList<>();
        this.categories = new ArrayList<>(Arrays.asList("Salary",
                "Investments", "Gifts", "Others"));
        this.initialAmount = 0;
    }

    public int size() {
        return savings.size();
    }

    public double getInitialAmount() {
        return this.initialAmount;
    }

    public ArrayList<Saving> getSavings() {
        return savings;
    }

    /**
     * Calculates the total savings amount by summing up the amounts of all savings.
     * Sets the initial amount to the calculated total savings.
     * Logs a severe error if an AssertionError occurs during the calculation.
     */
    public void findTotalSavings() {
        try {
            assert savings != null : "Savings list should not be null";

            double totalSavings = 0;
            for (int i = 0; i < savings.size(); i++) {
                Saving saving = savings.get(i);
                assert saving != null : "Saving object at index " + i + " is null";
                totalSavings += saving.getAmount();
            }

            this.initialAmount = totalSavings;
        } catch (AssertionError e) {
            LOGGER.log(Level.SEVERE, "Error occurred while calculating total savings", e);
        }
    }

    /**
     * Lists the savings, optionally filtered by category,
     * and calculates the remaining savings after deducting expenses.
     * Prints the initial savings amount, expenses deducted, and the remaining amount.
     *
     * @param filterCategory The category to filter savings by (optional). If null, all savings are listed.
     * @param expenseList    The ExpenseList object containing the expenses to deduct from savings.
     */
    public void listSavings(String filterCategory, ExpenseList expenseList) {
        LOGGER.info("Listing savings...");
        findTotalSavings();

        try {
            System.out.println(String.format("Current Currency: %s", DefaultCurrency.getDefaultCurrency()));
            System.out.println("Savings:");
            for (int i = 0; i < savings.size(); i++) {
                Saving saving = savings.get(i);
                if (filterCategory == null || saving.getCategory().equalsIgnoreCase(filterCategory)) {
                    System.out.print(i + 1 + " | ");
                    System.out.print("Category: " + saving.getCategory() + " | ");
                    System.out.println("Amount: $" + String.format("%.2f", saving.getAmount()) + " | ");
                }
            }
            ui.printDivider();
            System.out.println("Initial Savings Amount: $" + String.format("%.2f", initialAmount));
            System.out.println("Expenses Deducted: ");

            double totalExpenses = 0;
            for (Expense expense : expenseList.getExpenses()) {
                totalExpenses += expense.getAmount();
                System.out.println("$" + String.format("%.2f", expense.getAmount()) +
                        " spent on " + expense.getDescription() +
                        " on " + expense.getDateAdded());
            }
            ui.printDivider();

            double remainingAmount = calculateRemainingSavings(initialAmount, totalExpenses);
            if (remainingAmount < 0) {
                remainingAmount *= -1;
                System.out.println("You are currently short on savings by: $" + String.format("%.2f", remainingAmount));
            } else {
                System.out.println("Overall Remaining Amount: $" + String.format("%.2f", remainingAmount));

            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while listing savings", e);
        }
    }

    /**
     * Calculates the remaining savings amount after deducting total expenses from the initial amount.
     *
     * @param initialAmount The initial amount of savings.
     * @param totalExpenses The total amount of expenses to be deducted.
     * @return The remaining savings amount after deducting total expenses.
     */
    public double calculateRemainingSavings(double initialAmount, double totalExpenses) {
        try {
            assert initialAmount >= 0 : "Initial amount should not be negative";
            assert totalExpenses >= 0 : "Total expenses should not be negative";

            return (initialAmount - totalExpenses);
        } catch (AssertionError e) {
            LOGGER.log(Level.SEVERE, "Assertion failed while calculating remaining savings", e);
        }
        return -1;
    }

    public void addSaving(String category, String amount) throws BudgetBuddyException{
        assert category != null : "Category should not be null";
        assert amount != null : "Amount should not be null";
        LOGGER.info("Adding saving...");
    
        if (categories.stream().noneMatch(existingCategory -> existingCategory.equalsIgnoreCase(category))) {
            throw new BudgetBuddyException("The category '" + category + "' is not listed.");
        }
        
    
        if (!amount.matches("^\\d+(\\.\\d{1,2})?$")) {
            throw new BudgetBuddyException("Invalid amount format. Amount should be a positive number with up" +
                                            " to maximum two decimal places.");
        }
    
        double amountDouble;
        try {
            amountDouble = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            throw new BudgetBuddyException("Invalid amount format. Amount should be a number.");
        }
        
        if (amountDouble < 0) {
            throw new BudgetBuddyException("Savings should not be negative.");
        }
    
        if (amountDouble > MAX_AMOUNT) {
            throw new BudgetBuddyException("Amount exceeds the maximum allowed limit of " + MAX_AMOUNT);
        }
        
        boolean found = false;
        for (Saving saving : savings) {
            if (saving.getCategory().equalsIgnoreCase(category)) {
                saving.setAmount(saving.getAmount() + amountDouble);
                found = true;
                LOGGER.info("Updated existing saving for category: " + category);
                break;
            }
        }
        if (!found) {
            Saving saving = new Saving(category, amountDouble);
            savings.add(saving);
        }
    }
    
    
    

    /**
     * Edits the saving entry at the specified index. This method updates the category and amount
     * of a saving object within the savings list. If the provided category doesn't exist or the index
     * is out of the valid range, it logs a warning and prints an error message without making changes.
     *
     * @param category The new category to which the saving entry will be updated.
     * @param index    The index of the saving entry in the list to be edited.
     * @param amount   The new amount of the saving entry.
     */
    public void editSaving(String category, int index, double amount) {
        LOGGER.info(String.format("Attempting to edit saving at index %d with category '%s' " +
                "and amount %.2f", index, category, amount));

        // Assert that the provided category is not null or empty
        assert category != null && !category.isEmpty() : "Category cannot be null or empty";

        // Assert that the index is within the valid bounds of the savings list
        assert index > 0 && index <= savings.size() : "Index is out of bounds";

        // Assert that the amount is non-negative
        assert amount >= 0 : "Amount cannot be negative";

        // Check if the category exists in the list of categories
        int categoryIndex = categories.indexOf(category);
        if (categoryIndex == -1) {
            LOGGER.warning("Invalid category: " + category);
            System.out.println("Invalid category.");
            return;
        }

        // Check if the index is within valid bounds
        if (index <= 0 || index > savings.size()) {
            LOGGER.warning(String.format("Invalid index: %d. Valid index range " +
                    "is 1 to %d.", index, savings.size()));
            System.out.println("Invalid index.");
            return;
        }

        Saving savingToEdit = null;
        try {
            // Retrieve the saving to edit
            savingToEdit = savings.get(index - 1);

            // Update the saving details
            savingToEdit.setCategory(category);
            savingToEdit.setAmount(amount);

            System.out.println("Saving edited successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while editing saving at index " + index, e);
            System.out.println("An error occurred during saving edition. Please try again.");

        }
    }

    // @@author Dheekshitha2
    public void reduceSavings(int index, double amount) {

        if (index >= 0 && index < savings.size()) {
            Saving saving = savings.get(index);
            if (saving.getAmount() >= amount) {
                saving.setAmount(saving.getAmount() - amount);
                System.out.println("Savings reduced successfully!");
            } else {
                System.out.println("Insufficient savings amount.");
            }
        } else {
            System.out.println("Invalid saving index.");
        }
    }

    public double calculateTotalSavings() {
        double totalSavings = 0;
        try {
            for (Saving saving : savings) {
                if (saving.getAmount() < 0) {
                    throw new IllegalArgumentException("Savings should not be negative");
                }
                totalSavings += saving.getAmount();
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Negative savings amount detected", e);
        }

        assert totalSavings >= 0 : "Total savings should be non-negative";

        return totalSavings;
    }

    /**
     * Analyzes and displays insights into the saved amounts across different categories.
     * It prints out the highest and lowest savings categories and lists categories with no savings.
     * A bar graph representing the distribution of savings is also displayed.
     */
    public void getSavingsInsights() {
        double totalSavings = calculateTotalSavings();
        if (totalSavings == 0) {
            System.out.println("No savings to display.");
            return;
        }

        Map<String, Double> sumsByCategory = calculateSumsByCategory();

        // Calculate the highest savings
        double highestSavings = Collections.max(sumsByCategory.values());
        // Calculate the lowest savings
        double lowestSavings = sumsByCategory.values().stream()
                .filter(amount -> amount > 0)
                .min(Double::compare)
                .orElse(0.0);

        List<String> highestCategories = getSavingsCategoriesByAmount(sumsByCategory, highestSavings);
        List<String> lowestCategories = getSavingsCategoriesByAmount(sumsByCategory, lowestSavings);

        // Print the distribution graph
        ui.printDivider();
        printSavingsDistribution(sumsByCategory, totalSavings);
        ui.printDivider();

        // Print insights
        System.out.println("Highest Savings Category: " + formatCategoryList(highestCategories));
        System.out.println("Lowest Savings Category: " + formatCategoryList(lowestCategories));
        System.out.println("Categories with no savings added: " +
                formatCategoryList(getNoSavingsCategories(sumsByCategory)));
        ui.printDivider();
    }

    private List<String> getSavingsCategoriesByAmount(Map<String, Double> sumsByCategory, double amount) {
        return sumsByCategory.entrySet().stream()
                .filter(entry -> entry.getValue() == amount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<String> getNoSavingsCategories(Map<String, Double> sumsByCategory) {
        return categories.stream()
                .filter(category -> !sumsByCategory.containsKey(category) || sumsByCategory.get(category) == 0)
                .collect(Collectors.toList());
    }

    /**
     * Prints a distribution of savings as a horizontal bar graph.
     * Each category's bar length is proportional to its percentage of the total savings.
     */
    private void printSavingsDistribution(Map<String, Double> sumsByCategory, double totalSavings) {
        double maxPercentage = sumsByCategory.values().stream()
                .mapToDouble(amount -> (amount / totalSavings) * 100)
                .max()
                .orElse(100);

        for (String category : categories) {
            double percentage = (sumsByCategory.getOrDefault(category, 0.0) / totalSavings) * 100;
            int barLength = (int) (percentage / (maxPercentage / 50));
            String bar = "[" + "#".repeat(Math.max(0, barLength)) + "]";
            System.out.println(String.format("%-15s: %6.2f%% %s", category, percentage, bar));
        }
    }

    /**
     * Calculates the sum of savings for each category.
     *
     * @return A map with the category as the key and the sum of savings in that category as the value.
     */
    private Map<String, Double> calculateSumsByCategory() {
        return savings.stream().collect(
                Collectors.groupingBy(
                        Saving::getCategory,
                        Collectors.summingDouble(Saving::getAmount)
                )
        );
    }

    /**
     * Formats a list of category names into a human-readable string with categories separated by commas.
     * The word "and" is inserted before the last category if there are two or more.
     * Returns "None" if the list is empty.
     *
     * @param categories The list of categories to format.
     * @return A formatted string of category names or "None" if the list is empty.
     */
    private String formatCategoryList(List<String> categories) {
        if (categories.isEmpty()) {
            return "None";
        } else if (categories.size() == 1) {
            return categories.get(0);
        } else {
            String allButLast = String.join(", ", categories.subList(0, categories.size() - 1));
            return allButLast + " and " + categories.get(categories.size() - 1);
        }
    }

}
