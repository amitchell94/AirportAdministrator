import java.util.List;

public class Plane {
    private int id;
    private List<String> requiredServices;
    private String origin;
    private String destination;

    public Plane(int id, List<String> requiredServices, String origin, String destination) {
        this.id = id;
        this.requiredServices = requiredServices;
        this.origin = origin;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public List<String> getRequiredServices() {
        return requiredServices;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }
}
