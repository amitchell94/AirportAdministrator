package models.exception;

public class UnloadCargoPlaneException extends Exception {
    private String message;

    public UnloadCargoPlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
