package models.exception;

public class ParkPlaneException extends Exception {
    private String message;

    public ParkPlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
