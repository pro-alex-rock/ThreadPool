package app.exceptions;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class TooManyThreadsException extends RuntimeException {
    public TooManyThreadsException(String s) {
        super(s);
    }
}
