package by.logonuk.exception;

public class DateValidationException extends RuntimeException{

    private String customMessage;

    public DateValidationException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }
}
