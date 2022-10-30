package by.logonuk.exception;

public class UniqueConstraintException extends RuntimeException {

    private String customMessage;

    public UniqueConstraintException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }
}
