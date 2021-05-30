package it.unicam.cs.gp.inmytable.exception;

public class UnknownUsernameException extends Exception{
    private static final long serialVersionUID = -4238976321140958242L;
    public static final String MESSAGE = "Unknown Username: ";

    public UnknownUsernameException(String name){
        super(MESSAGE + name);
    }
}
