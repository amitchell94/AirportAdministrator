package models.exception;

public class MaintenancePlaneException extends Exception {
    private String message;

    public MaintenancePlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
