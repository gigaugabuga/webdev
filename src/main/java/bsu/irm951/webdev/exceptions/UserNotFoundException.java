package bsu.irm951.webdev.exceptions;

public class UserNotFoundException extends Throwable{
    public UserNotFoundException() {

    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
