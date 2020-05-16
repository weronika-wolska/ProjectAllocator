package exceptions;

public class InvalidArgumentException extends Exception{
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        if( errorMessage == null) errorMessage = "";
        this.errorMessage = errorMessage;
    }

    public InvalidArgumentException() {
        this(null);
    }

    public InvalidArgumentException(String errorMessage) {
        setErrorMessage(errorMessage);
    }
}
