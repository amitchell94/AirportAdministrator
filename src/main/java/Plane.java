import java.util.List;

public class Plane {
    private int id;
    private List<RequiredServices> requiredServices;
    private String origin;
    private String destination;
    private PlaneState state;

    public Plane(int id, List<RequiredServices> requiredServices, String origin, String destination, PlaneState state) {
        this.id = id;
        this.requiredServices = requiredServices;
        this.origin = origin;
        this.destination = destination;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public PlaneState getState() {
        return state;
    }

    public void setState(PlaneState state) {
        this.state = state;
    }
}
