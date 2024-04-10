# Chan Jun Rong - Project Portfolio Page

## Overview
The product created is a finance-tracking application named BudgetBuddy.It is a product for users who wish 
to handle and track any current/future expenses on a singular platform. BudgetBuddy provides a faster 
and more efficient way to track and calculate expenses and provides the ability to deal with 
finances on a singular platform with ease as long as you can type fast.

### Summary of Contributions
Given below are my contributions to the project

#### New Feature : Add the Ability to view Menu Commands 
Pull Requests [#91](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/91), [#8](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/8),
1. What it does : Allows user to view the respective commands of a certain item displayed in the menu.
2. Justification : This features improves the overall usability of the product. Allowing users to utilize the
application without have to refer to a user guide to know what commands are there to use

#### New Feature : Add the Ability to Find expenses of a specific name/amount
Pull Requests [#41](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/41), [#90](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/90), [#55](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/55)
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
Pull Requests [#68](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/68), [#92](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/92)
1. What it does : Allows user to create an empty list of expenses of their desired name
2. Justification : The feature is a small part of a larger feature called `Recurring Expenses`. This feature itself allows
the user to be able to differentiate and create different lists of expenses, all with a specific purpose.

#### New Feature : Add the Ability to remove a named list of recurring expenses
Pull Requests [#68](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/68), [#92](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/92)
1. What it does : Allows user to remove a list of expenses from the overall lists of Recurring Expenses
2. Justification : This feature improve the product significantly because a user can make typos in the naming
of their list, which can be reversed by removing the list, and creating it again. It also allows the user to remove
any unneeded list of expenses that they would not use anymore.

#### New Feature : Add the ability to view all names of lists of recurring expenses
Pull Requests [#68](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/68), [#92](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/92)

#### New Feature : Add the ability to add an expense to a specific list of recurring expenses
Pull Requests [#68](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/68), [#92](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/92)
1. What is does : Allows user to add an expense to a specified list in the overall list of recurring expenses
2. Credits : yyangdaa for his `addExpense` method which aiding in adding the expense into the specified list

#### New Feature : Add the ability to add all expenses in a specific list to the overall expenses
Pull Requests [#68](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/68), [#92](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/92)
1. What it does : Allows user to add all expenses in a specified list to the overall expenses
2. Credits : yyangdaa for his `addExpense` method which aids in adding each expense into the overall expense list

#### New Feature : Add the ability for recurring expenses to work with the currency converter
Pull Requests [#132](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/132)
1. What it does : Allows all expenses in recurring expenses to be updated to the new currency upon a convert currency command.
2. Justification : This feature improves the product significantly, as it helps to make the app more cohesive in terms of its features

#### New Feature : Add saving and loading from a file for recurring expenses
Pull Requests [#105](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/105)
1. What it does : Allows all expenses / lists in the recurring expenses to be saved and loaded from a file
2. Justification : This feature improves the product significantly because a user does not have to retype their recurring expenses
everytime they leave/reopen the application

#### Code Contributed
[RepoSenseLink](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=itsmejr257&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

#### Project Management
1. Managed releases v1.0-v2.0 (2 releases) on Github
2. Integrated features to work as a cohesive application in v1.0 (Pull Request [#34](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/34))

#### Enhancements to existing features:
1. Wrote JUnit tests for the Recurring Expense, Find and Menu Command feature, leading to a coverage of the following (Pull Request [#106](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/106))
 
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

2. Implemented Bug Fixes for `RecurringBill`, `Find` and `Menu` features. (Pull Request [#192](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/192), [#190](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/190), [#189](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/189), [#133](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/133),)

#### Documentation
Developer Guide : Pull Requests [#108](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/108),[#77](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/77), [#76](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/76), [#67](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/76)
  1. Added diagrams and documentation for the features `Recurring Expenses`, `Menu` and `Find` under `Implementation` Section
  2. Added diagrams and documentation for the `Introduction`, `Setup Guide` and `Design` sections


User Guide : Pull Requests [#140](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/140),[#136](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/136), [#109](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/109), [#61](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/61)
  1. Added documentation for the features `Recurring Expenses`, `Menu` and `Find`
  2. Added documentation for the `Introduction` and `Quick Start`

#### Community
1. PRs reviewed : [#195](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/195), [#111](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/111), [#107](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/107), [#100](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/100), [#99](https://github.com/AY2324S2-CS2113-T12-3/tp/pull/99)
2. Provided support and help to teammates in bug-fixing and ideation of features. (Communication was done through text-messages and weekly-meeting calls, may contact group-mates to ascertain this)
3. Provided DG Peer Review Comments for another team. [CS2113-F12-2 LifeTrack](https://github.com/nus-cs2113-AY2324S2/tp/pull/56)
4. Reported bugs for another team during PE-D. [CS2113T-T09-1 MediTracker](https://github.com/AY2324S2-CS2113T-T09-1/tp/)



