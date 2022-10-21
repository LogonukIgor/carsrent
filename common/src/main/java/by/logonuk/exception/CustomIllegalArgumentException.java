package by.logonuk.exception;

public class CustomIllegalArgumentException extends RuntimeException{

    private String customMessage;

    public CustomIllegalArgumentException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }
}
