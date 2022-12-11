package bsu.irm951.webdev.exceptions;

public class AccountAlreadyConfirmedException extends Throwable{
    public AccountAlreadyConfirmedException() {

    }

    public AccountAlreadyConfirmedException(String message) {
        super(message);
    }
}
