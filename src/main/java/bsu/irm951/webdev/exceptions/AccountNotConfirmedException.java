package bsu.irm951.webdev.exceptions;

public class AccountNotConfirmedException extends Throwable{
    public AccountNotConfirmedException() {

    }

    public AccountNotConfirmedException(String message) {
        super(message);
    }
}
