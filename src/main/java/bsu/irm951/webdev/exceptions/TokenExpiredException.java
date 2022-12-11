package bsu.irm951.webdev.exceptions;

public class TokenExpiredException extends Throwable{
    public TokenExpiredException() {

    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
