package models.exception;

public class TakeOffPlaneException extends Exception {
    private String message;

    public TakeOffPlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
