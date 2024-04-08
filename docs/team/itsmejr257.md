# Chan Jun Rong - Project Portfolio Page

## Overview
The product created is a finance-tracking application named BudgetBuddy.It is a product for users who wish 
to handle and track any current/future expenses on a singular platform. BudgetBuddy provides a faster 
and more efficient way to track and calculate expenses and provides the ability to deal with 
finances on a singular platform with ease as long as you can type fast.

### Summary of Contributions
Given below are my contributions to the project

#### New Feature : Add the Ability to view Menu Commands

1. What it does : Allows user to view the respective commands of a certain item displayed in the menu.
2. Justification : This features improves the overall usability of the product. Allowing users to utilize the
application without have to refer to a user guide to know what commands are there to use

#### New Feature : Add the Ability to Find expenses of a specific name/amount
1. What it does : Allows user to view all expenses that match a user's provided description, minimum amount and maximum
amount
2. Justification : This feature improves the product by allowing the user quickly look for specific expenses, especially
when there are many different expenses added at different times. It also allows the user to identify large/small expenses
with a single search.
3. Highlights : This enhancement capitalizes on already implemented commands within the application. To ensure that all methods
not have any overlapping functionality , the implementation of displaying the found expenses was made slightly more challenging as
it required additions to the ExpenseList class, in this case, a new constructor for it.
4. Credits : sweijie24 for his `listExpenses()` method which aided in displaying the found expenses.

#### New Feature : Add the Ability to create a named list of recurring expenses
1. What it does : Allows user to create an empty list of expenses of their desired name
2. Justification : The feature is a small part of a larger feature called `Recurring Expenses`. This feature itself allows
the user to be able to differentiate and create different lists of expenses, all with a specific purpose.

#### New Feature : Add the Ability to remove a named list of recurring expenses
1. What it does : Allows user to remove a list of expenses from the overall lists of Recurring Expenses
2. Justification : This feature improve the product significantly because a user can make typos in the naming
of their list, which can be reversed by removing the list, and creating it again. It also allows the user to remove
any unneeded list of expenses that they would not use anymore.

#### New Feature : Add the ability to view all names of lists of recurring expenses

#### New Feature : Add the ability to add an expense to a specific list of recurring expenses
1. What is does : Allows user to add an expense to a specified list in the overall list of recurring expenses
2. Credits : yyangdaa for his `addExpense` method which aiding in adding the expense into the specified list

#### New Feature : Add the ability to add all expenses in a specific list to the overall expenses
1. What it does : Allows user to add all expenses in a specified list to the overall expenses
2. Credits : yyangdaa for his `addExpense` method which aids in adding each expense into the overall expense list

#### Code Contributed
[RepoSenseLink](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=itsmejr257&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

#### Enhancements to existing features:
1. Wrote JUnit tests for the Recurring Expense, Find and Menu Command feature, leading to a coverage of the following
 
| Class Name                     | Coverage                 |
|--------------------------------|--------------------------|
| RecurringExpenseList           | 100%                     |
| RecurringExpenseLists          | 100% methods, 94% lines  |
| RecurringExpenseCommand        | 100% methods, 97% lines  |
| RecurringExpenseCommandCreator | 100% methods, 93% lines  |
| FindExpensesCommandCreator     | 100% methods, 100% lines |
| FindExpensesCommand            | 100% methods, 93% lines  |
| MenuCommandCreator             | 100% methods, 75% lines  |
| MenuCommand                    | 100%                     |



