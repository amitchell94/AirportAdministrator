package models;

import models.exception.LandingPlaneException;

import java.util.HashMap;
import java.util.Map;

public class Runway {
    private static final int RUNWAY_SIZE = 10;
    Map<Integer,Plane> availablePlanes = new HashMap<>();
    Plane[] runwayArray = new Plane[RUNWAY_SIZE];

    public static Runway createRunway(Map<Integer,Plane> availablePlanes) {
        if (availablePlanes == null ) return null;
        return new Runway(availablePlanes);
    }

    public Runway(Map<Integer,Plane> availablePlanes) {
        this.availablePlanes = availablePlanes;
    }

    public void showGlobalState() {
        for (Plane plane: availablePlanes.values()) {
            System.out.println("Plane id: " + plane.getId());
            System.out.println("Services to perform:");
            for (RequiredServiceState service : plane.getRequiredServices()) {
                System.out.println(service);
            }
            System.out.println();
        }
    }

    public void showRunways() {
        for (int i = 0; i < RUNWAY_SIZE; i++) {
            Plane plane = runwayArray[i];
            System.out.print("Runway number " + (i + 1) + ": ");
            if (plane == null) {
                System.out.println("Free");
            } else {
                System.out.println("Occupied by plane " + plane.getId());
            }
        }
    }

    public void landPlane(String argument) throws LandingPlaneException {
        if (argument == null) throw new LandingPlaneException("Unable to land plane, invalid input");
        String[] stringSplit = argument.split(",");
        if (stringSplit.length != 2) throw new LandingPlaneException("Unable to land plane, invalid input");
        try {
            int planeId = Integer.parseInt(stringSplit[0]);
            int runwayId = Integer.parseInt(stringSplit[1]);
            if (runwayId <= 0 || runwayId > RUNWAY_SIZE) throw new LandingPlaneException("Unable to land plane, invalid input");
            int runwayPointer = runwayId - 1;
            if (runwayArray[runwayPointer] == null) {
                Plane selectedPlane = availablePlanes.get(planeId);
                runwayArray[runwayPointer] = selectedPlane;
                selectedPlane.setState(PlaneState.LANDED);
            }
        } catch (NumberFormatException e){
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new LandingPlaneException("Unable to land plane, invalid input");
        }
    }
}
