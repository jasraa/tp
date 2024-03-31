# User Guide

## Introduction
BudgetBuddy is a product for users who wish to handle and track any current/future expenses on a singular platform. 
BudgetBuddy provides a faster and more efficient way to track and calculate expenses and provides the ability 
to deal with finances on a singular platform with ease as long as you can type fast.


## Quick Start
1. Ensure that you have Java 11 installed.
2. Down the latest version of `BudgetBuddy` from [here](https://github.com/AY2324S2-CS2113-T12-3/tp/releases/tag/BudgetBuddy-MVP).


## Features
1. Display Commands
2. Add
3. Edit Savings
4. Edit Expense
5. Reduce Savings
6. Delete Expense
7. List Savings
8. List Expense
9. Find Expense
10. Change Currency
11. Recurring Bills

### Display Commands : `menu`
Displays the corresponding features of BudgetBuddy

Format: `menu [INDEX]`

* The `INDEX` refers to the number associated with each menu option. If `INDEX` is not provided, the overall 
menu list will be displayed
* `INDEX` must be either be empty OR a positive integer and a valid index in the menu list

Example of usage:

`menu` : Displays all menu list items

`menu 1` : Displays commands related to feature associated to menu list item 1

### Add Expense
Adds new expense

Format: `add expense c/CATEGORY a/AMOUNT d/DESCRIPTION`

* Increments expense of the specified CATEGORY by AMOUNT given.
* The `CATEGORY` must be one of the following pre-defined categories: "Housing",
  "Groceries", "Utility", "Transport", "Entertainment" or "Others".
* The `AMOUNT` must be a positive integer.
* The `DESCRIPTION` can be any string.

Example of Usage:

`add expense c/Entertainment a/167 d/Bruno Mars`


### Add Savings
Increase savings by specified amount to the savings list

Format:  `add savings c/CATEGORY a/AMOUNT`

* Increments savings of the specified CATEGORY by AMOUNT given.
* The `CATEGORY` must be one of the following pre-defined categories: "Salary",
  "Investments", "Gifts" or "Others".
* The `AMOUNT` must be a positive integer.
* The `DESCRIPTION` can be any string.

### Add Split Expenses
Add expenses that are meant for splitting among friends or colleague

Format: `split expenses a/AMOUNT n/NUMBER_OF_PEOPLE d/DESCRIPTION`

* Increments split expenses 
* The `AMOUNT` must be a positive number
* The `NUMER_OF_PEOPLE` must be a positive integer.
* The `DESCRIPTION` can be any string

Example of usage:

`add savings c/Salary a/10000`

### Edit Savings: `edit savings`
Edit Savings that have been added previously.

Format: `edit savings c/CATEGORY i/INDEX a/AMOUNT`

* The `CATEGORY` must be one of the following pre-defined categories: "Salary",
  "Investments", "Gifts" or "Others".
* The `INDEX` must be a positive integer and a valid index in the menu list.
* The `AMOUNT` must be a positive integer.

Example of usage:

`edit savings c/Entertainment i/3 a/300`

### Edit Expenses: `edit expense`
Edit expenses that have been added previously.

Format: `edit expense c/CATEGORY i/INDEX a/AMOUNT d/DESCRIPTION`

* The `CATEGORY` must be one of the following pre-defined categories: "Housing",
  "Groceries", "Utility", "Transport", "Entertainment" or "Others".
* The `INDEX` must be a positive integer and a valid index in the menu list.
* The `AMOUNT` must be a positive integer.
* The `DESCRIPTION` can be any string.

Example of usage:

`edit expense c/Utility i/2 a/180 d/Household Electricity`

### Reduce Savings: `reduce savings`

Reduces the amount saved in a particular category

Format: `reduce savings i/INDEX a/AMOUNT`

* The `INDEX` must be a positive integer and a valid index in the menu list.
* The `AMOUNT` to be reduced must be a positive integer

Example of usage:

`reduce savings i/4 a/10`
Reduces the savings of category of index 4 listed in the savings tracker by $10


### Delete Expense: `delete expense`

Deletes expenses that have been added wrongly or are no longer relevant.

Format: `delete expense i/INDEX`

* The `INDEX` must be a positive integer and a valid index in the menu list.

Example of usage:

`delete expense i/4` 
Deletes the expense of at index 4 listed in the expenditure tracker.

**Note:**
- Once an expense is deleted, it cannot be recovered.


### Listing Savings: `list savings`

Lists savings

Format: `list savings CATEGORY`

* The `CATEGORY` is optional and can be left blank.
* The `CATEGORY` must be a pre-existing category if inputted.
* Similar to listing expenses, users can view their savings with optional category filtering.
* Savings are listed along with their respective categories and amounts.
* Total savings are displayed at the end of the list, after deducting relevant expenditures.

Example Usage:

`list savings`
`list savings Salary`
`list savings Investment`


### Listing Expenses: `list expense`

Lists expenses

Format: `list expense CATEGORY`

* The `CATEGORY` is optional and can be left blank.
* The `CATEGORY` must be a pre-existing category if inputted.
* When listing expenses, users have the option to filter expenses based on categories.
* Users can specify a category to view expenses related to that category only.
* If no category is specified, the system will list all expenses.
* The listed expenses include details such as the date of the expense, category, amount, and description.
* Total expenses are displayed at the end of the list.

Example Usage:

`list expenses`
`list expenses Transport`
`list expenses Housing`

### Finding expenses : `find expenses`

Finds expenses based on their description or amount

Format : `find expenses [d/DESCRIPTION] [morethan/MINAMOUNT] [lessthan/MAXAMOUNT]`

* `DESCRIPTION`, `MINAMOUNT`, `MAXAMOUNT` can be used in any order and combination
* `DESCRIPTION` is the description associated with the expenses the user wishes to find
* `MINAMOUNT` is the filter for expenses with amounts higher than specified value
* `MAXAMOUNT` is the filter for expenses with amounts lower than specified value
* At least one filter must be provided

Examples of usage :

`find expenses d/coffee` : Finds all expenses with the word "coffee" in the description
`find expenses d/coffee morethan/200` : Finds all expenses with the word "coffee" and amount higher than $200

### Changing Currencies : `change currency [CURRENCY_CODE]`

Converts current currency to targeted currency

Format : `change currency [CURRENCY_CODE]`

* Default currency is 'SGD'.
* `CURRENCY_CODE` consists of the following currencies: 'SGD', 'USD', 'EUR', 'MYR', 'JPY', 'KRW', 'CNY', 'HKD'
* `CURRENCY_CODE` cannot be null. 
* Conversion of Currency is interchangeable (e.g. SGD -> USD -> JPY)

Examples of usage:

`change currency USD` : Converts current currency into USD

### Add Recurring Bill : `rec newlist`

Adds a new recurring Bill.

Format : `rec newlist LISTNAME`

* `LISTNAME` refers to the name you wish to associate the recurring Bill with
* `LISTNAME` cannot be empty
* This command is **space sensitive**, in particular the space between `rec`, `newlist` and `LISTNAME`
should be **exactly** one space apart for the command to be recognised

Examples of usage :

`rec newlist Subscriptions` : Creates a new empty recurring bill named `Subscriptions`

### List all Recurring Bills : `rec viewlists`

Lists all recurring bill names, along with their associated list number

Format : `rec viewlists`

* This command is **space sensitive**, in particular, the space between `rec` and `viewlists` must be
**exactly** one space apart for the command to be recognised 

Examples of Output : 

When there are already added recurring bills :

![Output of viewlists when there are recurring bills added](userguideimages/rec_viewlists_exampleOutput.jpg)

When there are no added recurring bills

![Output of viewlists where there are no recurring bills](userguideimages/rec_viewlists_emptyOutput.png)

### Remove Recurring Bill : `rec removelist`

Removes a recurring bill

Format : `rec removelist LISTNUMBER`

* `LISTNUMBER` refers to the associated list number of recurring bill when doing a `rec viewlists`
* `LISTNUMBER` must be a **valid** integer, and should be a **valid** list number

Examples of usage :

`rec removelist 2` : Removes the 2nd recurring bill in the list of recurring bills

### Add an expense to a recurring bill : `rec newexpense`

Adds an expense to a specified recurring bill

Format : `rec newexpense to/LISTNUMBER c/CATEGORY a/AMOUNT d/DESCRIPTION`

* `LISTNUMBER` refers to the associated list number of recurring bill when doing a `rec viewlists`
* `CATEGORY` refers to the category of the expense you wish to add
* `AMOUNT` refers to the amount value of the expense you wish to add
* `DESCRIPTION` refers to the description of the expense you wish to add
* `LISTNUMBER` must be a **valid** integer, and should be a **valid** list number
* `CATEGORY`, `AMOUNT` and `DESCRIPTION` follows the same constraints as if you were to add a normal expense

Examples of usage :
`rec newexpense to/1 c/Entertainment a/200 d/movies` : Adds a new expense to the 1st recurring bill 
, with category as Entertainment, amount as 200 and description as Movies

### View expenses in a recurring bill : `rec viewexpenses`

Views all expenses in a specified recurring bill

Format : `rec viewexpenses LISTNUMBER`

* `LISTNUMBER` refers to the associated list number of the recurring bill when doing a `rec viewlists`
* `LISTNUMBER` must be a **valid** integer, and should be a **valid** list number
* This command is **space sensitive**, in particular, the space between `rec` and `viewlists` must be
  **exactly** one space apart for the command to be recognised

Examples of usage :
`rec viewexpenses 1` : Prints all expenses in the 1st recurring bill

### Add expenses in a recurring bill to overall expenses : `rec addrec`

Adds all expenses in a specified recurring bill to the overall expenses

Format : `rec addrec LISTNUMBER`

* `LISTNUMBER` refers to the associated list number of the recurring bill when doing a `rec viewlists`
* `LISTNUMBER` must be a **valid** integer, and should be a **valid** list number
*  You may wish to perform a `list expenses` to view the newly added expenses being added to the
overall list of expenses

Examples of usage :
`rec addrec 1` : Adds all expenses in the 1st recurring bill into the overall expenses


## Command Summary
* Display Commands : `menu INDEX`
* Add Savings: `add savings c/CATEGORY a/AMOUNT`
* Add Expense: `add expense c/CATEGORY a/AMOUNT d/DESCRIPTION`
* Edit Expenses `edit expense c/CATEGORY i/INDEX a/AMOUNT d/DESCRIPTION`
* Edit Savings `edit savings c/CATEGORY i/INDEX a/AMOUNT`
* List Expenses: `list expenses CATEGORY`
* List Savings: `list savings CATEGORY`
* Find Expenses: `find expenses [d/DESCRIPTION] [morethan/MINAMOUNT] [lessthan/MAXAMOUNT]`
* Change Currency: `change currency [CURRENCY_CODE]`
* Add Recurring Bill: `rec newlist LISTNAME`
* List All Recurring Bills: `rec viewlists`
* Remove Recurring Bill : `rec removelist LISTNUMBER`
* Add Expense to Recurring Bill : `rec newexpense to/LISTNUMBER c/CATEGORY a/AMOUNT d/DESCRIPTION`
* View Expenses in Recurring Bill : `rec viewexpenses LISTNUMBER`
* Add Expenses in Recurring Bill to Overall Expenses : `rec addrec LISTNUMBER`

