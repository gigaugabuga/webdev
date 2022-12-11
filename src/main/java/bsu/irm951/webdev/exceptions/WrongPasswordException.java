package bsu.irm951.webdev.exceptions;

public class WrongPasswordException extends Throwable{
    public WrongPasswordException() {

    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
