package models;

public class RequiredServiceState {
    private RequiredService service;
    private int remainingSeconds;

    public RequiredServiceState(RequiredService service, int remainingSeconds) {
        this.service = service;
        this.remainingSeconds = remainingSeconds;
    }

    public RequiredService getService() {
        return service;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public void countdownRemainingSeconds() {
        remainingSeconds -= 1;
    }

    @Override
    public String toString() {
        return service.toString() + ": " + remainingSeconds + " remaining seconds.";
    }
}
