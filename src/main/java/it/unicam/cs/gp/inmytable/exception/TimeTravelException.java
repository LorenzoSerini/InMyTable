package it.unicam.cs.gp.inmytable.exception;

public class TimeTravelException extends Exception{
    private static final long serialVersionUID = -4238970533140958242L;
    public static final String MESSAGE = "You cannot travel in time: ";

    public TimeTravelException(String msg){
        super(MESSAGE + msg);
    }

}
