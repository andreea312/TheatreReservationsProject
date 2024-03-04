package org.example.repo.exceptions;


/**
 * org.example.repo.Repository exception class
 */
public class RepositoryException extends Exception{
    /**
     * RepoException constructor with no argument
     */
    public RepositoryException() {
    }

    /**
     * RepoException constructor with message argument
     * @param message String - the message that should be displayed when exception is thrown
     */
    public RepositoryException(String message) {
        super(message);
    }

    /**
     * RepoException constructor with message and cause arguments
     * @param message String - the message that should be displayed when exception is thrown
     * @param cause Throwable
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * RepoException constructor with cause argument
     * @param cause Throwable
     */
    public RepositoryException(Throwable cause) {
        super(cause);
    }

    /**
     * ValidationException constructor with more arguments
     * @param message String - the message that should be displayed when exception is thrown
     * @param cause Throwable
     * @param enableSuppression boolean
     * @param writableStackTrace writableStackTrace
     */
    public RepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
