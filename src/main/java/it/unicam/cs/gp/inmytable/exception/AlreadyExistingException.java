package it.unicam.cs.gp.inmytable.exception;

public class AlreadyExistingException extends Exception{
    private static final long serialVersionUID = -4238970143123958242L;
    public static final String MESSAGE = "Already Exist in the System or is Wrong: ";

    public AlreadyExistingException(String msg){
        super(MESSAGE + msg);
    }
}
