package bsu.irm951.webdev.exceptions;

public class InvalidEmailException extends Throwable{

    public InvalidEmailException(){

    }

    public InvalidEmailException(String message){
        super(message);
    }
}
