package by.logonuk.exception;

public class NoSuchEntityException extends RuntimeException {
    private String customMessage;

    public NoSuchEntityException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }
}
