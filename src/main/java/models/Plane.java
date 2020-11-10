package models;

import models.PlaneState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Plane {
    private int id;
    private Set<RequiredServices> requiredServices;
    private String origin;
    private String destination;
    private PlaneState state;

    public Plane(int id, Set<RequiredServices> requiredServices, String origin, String destination, PlaneState state) {
        this.id = id;
        this.requiredServices = requiredServices;
        this.origin = origin;
        this.destination = destination;
        this.state = state;
    }

    public Plane(JSONObject jsonObject) throws NumberFormatException {
        JSONArray reqArray = (JSONArray) jsonObject.get("RequiredServices");
        Set<RequiredServices> reqList = new HashSet<>();
        Iterator<String> it = reqArray.iterator();
        while (it.hasNext()) {
            switch (it.next()) {
                case "Cleaning":
                    reqList.add(RequiredServices.CLEANING);
                    break;
                case "Refuel":
                    reqList.add(RequiredServices.REFUEL);
                    break;
                case "Baggage Unload":
                    reqList.add(RequiredServices.BAGGAGE_UNLOAD);
                    break;
                case "Cargo Unload":
                    reqList.add(RequiredServices.CARGO_UNLOAD);
                    break;
                case "Maintenance":
                    reqList.add(RequiredServices.MAINTENANCE);
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

    public Set<RequiredServices> getRequiredServices() {
        return requiredServices;
    }

    public void setState(PlaneState state) {
        this.state = state;
    }
}
