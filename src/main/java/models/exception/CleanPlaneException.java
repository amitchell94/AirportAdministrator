package models.exception;

public class CleanPlaneException extends Exception {
    private String message;

    public CleanPlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
