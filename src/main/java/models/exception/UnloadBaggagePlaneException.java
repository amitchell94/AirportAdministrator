package models.exception;

public class UnloadBaggagePlaneException extends Exception {
    private String message;

    public UnloadBaggagePlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
