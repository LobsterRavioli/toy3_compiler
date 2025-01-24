package main.visitors.utils;

public class DuplicateException extends RuntimeException {
    // Constructors
    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

}
