package ExceptionHandle;

public class IncorrectFileTypeException extends Exception{
    public IncorrectFileTypeException(String message) {
        super(message);
    }
}
