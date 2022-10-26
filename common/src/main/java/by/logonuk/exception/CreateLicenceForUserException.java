package by.logonuk.exception;

public class CreateLicenceForUserException extends RuntimeException{
    private String customMessage;

    public CreateLicenceForUserException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return customMessage;
    }
}
