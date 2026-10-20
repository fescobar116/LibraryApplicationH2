package edu.unac.domain.exception;

public class BookNotBorrowedByUserException extends Exception {
    public BookNotBorrowedByUserException(String message) {
        super(message);
    }
}
