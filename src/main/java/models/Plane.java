package models;

import models.PlaneState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Plane {
    private int id;
    private Set<RequiredServiceState> requiredServices;
    private String origin;
    private String destination;
    private PlaneState state;

    public Plane(int id, Set<RequiredServiceState> requiredServices, String origin, String destination, PlaneState state) {
        this.id = id;
        this.requiredServices = requiredServices;
        this.origin = origin;
        this.destination = destination;
        this.state = state;
    }

    public Plane(JSONObject jsonObject) throws NumberFormatException {
        JSONArray reqArray = (JSONArray) jsonObject.get("RequiredServices");
        Set<RequiredServiceState> reqList = new HashSet<>();
        Random random = new Random();
        Iterator<String> it = reqArray.iterator();
        while (it.hasNext()) {
            switch (it.next()) {
                case "Cleaning":
                    reqList.add(
                            new RequiredServiceState(
                                    RequiredService.CLEANING,
                                    random.nextInt(9) + 1
                            )
                    );
                    break;
                case "Refuel":
                    reqList.add(
                            new RequiredServiceState(
                                    RequiredService.REFUEL,
                                    random.nextInt(9) + 1
                            )
                    );
                    break;
                case "Baggage Unload":
                    reqList.add(
                            new RequiredServiceState(
                                    RequiredService.BAGGAGE_UNLOAD,
                                    random.nextInt(9) + 1
                            )
                    );
                    break;
                case "Cargo Unload":
                    reqList.add(
                            new RequiredServiceState(
                                    RequiredService.CARGO_UNLOAD,
                                    random.nextInt(9) + 1
                            )
                    );
                    break;
                case "Maintenance":
                    reqList.add(
                            new RequiredServiceState(
                                    RequiredService.MAINTENANCE,
                                    random.nextInt(9) + 1
                            )
                    );
                    break;
            }
        }
        this.requiredServices = reqList;
        this.id = Integer.parseInt((String) jsonObject.get("Id"));
        this.origin = (String) jsonObject.get("Origin");
        this.destination = (String) jsonObject.get("Destination");
        this.state = PlaneState.FLYING;
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

    public Set<RequiredServiceState> getRequiredServices() {
        return requiredServices;
    }

    public void setState(PlaneState state) {
        this.state = state;
    }
}
