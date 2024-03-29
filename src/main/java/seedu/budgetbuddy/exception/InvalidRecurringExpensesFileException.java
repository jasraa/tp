package seedu.budgetbuddy.exception;

public class InvalidRecurringExpensesFileException extends Exception{
    public InvalidRecurringExpensesFileException(String errorMessage) {
        super(errorMessage);
    }
}
