package bsu.irm951.webdev.exceptions;

public class WrongTokenException extends Throwable{
    public WrongTokenException() {

    }

    public WrongTokenException(String message) {
        super(message);
    }
}
