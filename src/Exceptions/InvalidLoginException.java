package Exceptions;

public class InvalidLoginException extends Exception {
    public InvalidLoginException (String str)
    {
        super(str);
    }
}