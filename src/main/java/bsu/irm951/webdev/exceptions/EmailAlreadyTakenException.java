package bsu.irm951.webdev.exceptions;

public class EmailAlreadyTakenException extends Throwable{

    public EmailAlreadyTakenException(){

    }

    public EmailAlreadyTakenException(String message){
        super(message);
    }

}
