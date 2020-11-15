package models.exception;

public class RefuelPlaneException extends Exception {
    private String message;

    public RefuelPlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
