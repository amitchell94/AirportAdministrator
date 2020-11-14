package models.exception;

public class PlaneStateException extends Exception {
    private String message;

    public PlaneStateException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
