package models;

import models.exception.*;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Runway {
    private static final int RUNWAY_SIZE = 10;
    Map<Integer,Plane> availablePlanes;
    Plane[] runwayArray = new Plane[RUNWAY_SIZE];
    Timer timer  = new Timer();

    public static Runway createRunway(Map<Integer,Plane> availablePlanes) {
        if (availablePlanes == null) return null;
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

    public void showFreeRunways() {
        for (int i = 0; i < RUNWAY_SIZE; i++) {
            Plane plane = runwayArray[i];
            if (plane != null) {
                continue;
            }
            System.out.println("Runway number " + (i + 1) + " is free.");
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

    public void takeOffPlane(String argument) throws TakeOffPlaneException {
        if (argument == null) throw new TakeOffPlaneException("Plane unable to take off, invalid input");

        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new TakeOffPlaneException("Plane unable to take off. No plane with that id found.");

            Plane selectedPlane = availablePlanes.get(planeId);

            if (selectedPlane.getDestination().isEmpty() || selectedPlane.getDestination() == null)
                throw new TakeOffPlaneException("Plane unable to take off, plane does not have a destination");

            if (selectedPlane.getState() != PlaneState.LANDED)
                throw new TakeOffPlaneException("Plane unable to take off. Plane id: " + " is not landed.");

            for (int i = 0; i < RUNWAY_SIZE; i++) {
                if (runwayArray[i] != null && runwayArray[i].getId() == planeId) {
                    // Once a plane takes off, we delete it
                    availablePlanes.remove(planeId);
                    runwayArray[i] = null;
                    return;
                }
            }
            throw new TakeOffPlaneException("Plane unable to take off. Plane not found on any runway");

        } catch (NumberFormatException e) {
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new TakeOffPlaneException("Plane unable to take off, invalid input");
        }
    }
    public void parkPlane(String argument) throws ParkPlaneException {
        if (argument == null) throw new ParkPlaneException("Unable to park plane, invalid input");

        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new ParkPlaneException("Unable to park plane. No plane with that id found.");

            Plane selectedPlane = availablePlanes.get(planeId);

            if (selectedPlane.getState() != PlaneState.LANDED)
                throw new ParkPlaneException("Unable to park plane. Plane id: " + " is not landed.");

            for (int i = 0; i < RUNWAY_SIZE; i++) {
                if (runwayArray[i].getId() == planeId) {
                    // Once a plane parks, we remove it from the runway
                    selectedPlane.setState(PlaneState.PARKED);
                    runwayArray[i] = null;
                    return;
                }
            }
            throw new ParkPlaneException("Unable to park plane. Plane not found on any runway");

        } catch (NumberFormatException e) {
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new ParkPlaneException("Unable to park plane, invalid input");
        }
    }

    public void showPlaneState(String argument) throws PlaneStateException {
        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new PlaneStateException("Unable to get plane state. Plane with id: " + planeId + " not found.");

            Plane plane = availablePlanes.get(planeId);

            System.out.println("Plane id: " + plane.getId());
            System.out.println("State: " + plane.getState());
            System.out.println("Services to perform:");
            for (RequiredServiceState service : plane.getRequiredServices()) {
                System.out.println(service);
            }
            System.out.println();

        } catch (NumberFormatException e) {
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new PlaneStateException("Unable to get plane state. Invalid input");
        }

    }

    public void cleanPlane(String argument) throws CleanPlaneException {
        if (argument == null) throw new CleanPlaneException("Unable to clean plane, invalid input");

        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new CleanPlaneException("Unable to clean plane . No plane with that id found.");

            Plane selectedPlane = availablePlanes.get(planeId);

            if (selectedPlane.getState() != PlaneState.LANDED)
                throw new CleanPlaneException("Unable to clean plane. Plane id: " + planeId + " is not landed.");

            for (RequiredServiceState service: selectedPlane.getRequiredServices()) {
                if (service.getService() == RequiredService.CLEANING) {
                    timer.schedule(new SecondCounter(service,
                            selectedPlane.getRequiredServices()),0,1000);
                    return;
                }
            }
            throw new CleanPlaneException("Unable to clean plane. The plane doesn't need cleaning");


        } catch (NumberFormatException e){
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new CleanPlaneException("Unable to clean plane, invalid input");
        }
    }

    public void refuelPlane(String argument) throws RefuelPlaneException {

        if (argument == null) throw new RefuelPlaneException("Unable to refuel plane, invalid input");

        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new RefuelPlaneException("Unable to refuel plane . No plane with that id found.");

            Plane selectedPlane = availablePlanes.get(planeId);

            if (selectedPlane.getState() != PlaneState.LANDED)
                throw new RefuelPlaneException("Unable to refuel plane. Plane id: " + planeId + " is not landed.");

            for (RequiredServiceState service: selectedPlane.getRequiredServices()) {
                if (service.getService() == RequiredService.REFUEL) {
                    timer.schedule(new SecondCounter(service,
                            selectedPlane.getRequiredServices()),0,1000);
                    return;
                }
            }
            throw new RefuelPlaneException("Unable to refuel plane. The plane doesn't need refueling");


        } catch (NumberFormatException e){
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new RefuelPlaneException("Unable to refuel plane, invalid input");
        }
    }

    public void unloadBaggagePlane(String argument) throws UnloadBaggagePlaneException {

        if (argument == null) throw new UnloadBaggagePlaneException("Unable to unload the baggage, invalid input");

        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new UnloadBaggagePlaneException("Unable to unload the baggage. No plane with that id found.");

            Plane selectedPlane = availablePlanes.get(planeId);

            if (selectedPlane.getState() != PlaneState.LANDED)
                throw new UnloadBaggagePlaneException("Unable to unload the baggage. Plane id: " + planeId + " is not landed.");

            for (RequiredServiceState service: selectedPlane.getRequiredServices()) {
                if (service.getService() == RequiredService.BAGGAGE_UNLOAD) {
                    timer.schedule(new SecondCounter(service,
                            selectedPlane.getRequiredServices()),0,1000);
                    return;
                }
            }
            throw new UnloadBaggagePlaneException("Unable to unload the baggage. The plane doesn't need to be unloaded (Baggage)");


        } catch (NumberFormatException e){
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new UnloadBaggagePlaneException("Unable to unload the baggage, invalid input");
        }
    }

    public void unloadCargoPlane(String argument) throws UnloadCargoPlaneException {

        if (argument == null) throw new UnloadCargoPlaneException("Unable to unload the cargo, invalid input");

        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new UnloadCargoPlaneException("Unable to unload the cargo. No plane with that id found.");

            Plane selectedPlane = availablePlanes.get(planeId);

            if (selectedPlane.getState() != PlaneState.LANDED)
                throw new UnloadCargoPlaneException("Unable to unload the cargo. Plane id: " + planeId + " is not landed.");

            for (RequiredServiceState service: selectedPlane.getRequiredServices()) {
                if (service.getService() == RequiredService.BAGGAGE_UNLOAD) {
                    timer.schedule(new SecondCounter(service,
                            selectedPlane.getRequiredServices()),0,1000);
                    return;
                }
            }
            throw new UnloadCargoPlaneException("Unable to unload the cargo. The plane doesn't need to be unloaded (Cargo)");


        } catch (NumberFormatException e){
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new UnloadCargoPlaneException("Unable to unload the cargo, invalid input");
        }
    }

    public void maintainPlane(String argument) throws MaintenancePlaneException {

        if (argument == null) throw new MaintenancePlaneException("Unable to maintain plane, invalid input");

        try {
            int planeId = Integer.parseInt(argument);

            if (!availablePlanes.containsKey(planeId))
                throw new MaintenancePlaneException("Unable to maintain plane . No plane with that id found.");

            Plane selectedPlane = availablePlanes.get(planeId);

            if (selectedPlane.getState() != PlaneState.LANDED)
                throw new MaintenancePlaneException("Unable to maintain plane. Plane id: " + " is not landed.");

            for (RequiredServiceState service: selectedPlane.getRequiredServices()) {
                if (service.getService() == RequiredService.MAINTENANCE) {
                    timer.schedule(new SecondCounter(service,
                            selectedPlane.getRequiredServices()),0,1000);
                    return;
                }
            }
            throw new MaintenancePlaneException("Unable to maintain plane. The plane doesn't need Maintenance");


        } catch (NumberFormatException e){
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            throw new MaintenancePlaneException("Unable to maintain plane, invalid input");
        }
    }

    private static class SecondCounter extends TimerTask {
        private RequiredServiceState requiredServiceState;
        private Set<RequiredServiceState> requiredServiceStateSet;

        public SecondCounter(RequiredServiceState requiredServiceState, Set<RequiredServiceState> requiredServiceStateSet) {
            this.requiredServiceState = requiredServiceState;
            this.requiredServiceStateSet = requiredServiceStateSet;
        }

        @Override
        public void run() {
            if (requiredServiceState.getRemainingSeconds() > 0) {
                requiredServiceState.countdownRemainingSeconds();
            } else {
                requiredServiceStateSet.remove(requiredServiceState);
                cancel();
            }
        }
    }
}
