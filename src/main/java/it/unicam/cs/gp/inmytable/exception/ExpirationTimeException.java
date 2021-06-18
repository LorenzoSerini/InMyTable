package it.unicam.cs.gp.inmytable.exception;

public class ExpirationTimeException extends Exception{
    private static final long serialVersionUID = -4238970147140958242L;
    public static final String MESSAGE = "ExpirationTime should be before meal date: ";
    private String msg;

    public ExpirationTimeException(String msg){
        super(MESSAGE + msg);
        this.msg = msg;
    }

    @Override
    public String getMessage(){
            return MESSAGE + msg;
    }
}
