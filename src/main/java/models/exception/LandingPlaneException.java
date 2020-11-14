package models.exception;

public class LandingPlaneException extends Exception {
    private String message;

    public LandingPlaneException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
