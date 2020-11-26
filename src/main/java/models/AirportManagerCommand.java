package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AirportManagerCommand {
    RUNWAYS,
    LAND,
    GLOBAL_STATE,
    STATE,
    TAKEOFF,
    FREERUNWAYS,
    CLEANING,
    REFUEL,
    UNLOAD_BAGGAGE,
    UNLOAD_CARGO,
    MAINTENANCE,
    PARK,
    UNKNOWN,
    EXIT;

    public static List<AirportManagerCommand> getAvailableCommands() {
        return Stream.of(
                AirportManagerCommand.LAND,
                AirportManagerCommand.GLOBAL_STATE,
                AirportManagerCommand.STATE,
                AirportManagerCommand.RUNWAYS,
                AirportManagerCommand.TAKEOFF,
                AirportManagerCommand.CLEANING,
                AirportManagerCommand.REFUEL,
                AirportManagerCommand.UNLOAD_BAGGAGE,
                AirportManagerCommand.UNLOAD_CARGO,
                AirportManagerCommand.MAINTENANCE,
                AirportManagerCommand.PARK,
                AirportManagerCommand.FREERUNWAYS,
                AirportManagerCommand.EXIT
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        switch (this) {
            case LAND:
                return "Land";
            case TAKEOFF:
                return "Takeoff";
            case PARK:
                return "Park";
            case CLEANING:
                return "Cleaning";
            case REFUEL:
                return "Refuel";
            case UNLOAD_BAGGAGE:
                return "Baggage-unload";
            case UNLOAD_CARGO:
                return "Cargo-unload";
            case MAINTENANCE:
                return "Maintenance";
            case EXIT:
                return "Exit";
            case GLOBAL_STATE:
                return "Global-state";
            case STATE:
                return "State";
            case FREERUNWAYS:
                return "Free-runways";
            case RUNWAYS:
                return "Runways";
            case UNKNOWN:
                return "";
        }
        return "";
    }

    public static AirportManagerCommand parseCommand(String stringCommand) {
        if (stringCommand == null) return AirportManagerCommand.UNKNOWN;
        String lowercasedCommand = stringCommand.toLowerCase();
        switch (lowercasedCommand) {
            case "runways":
                return AirportManagerCommand.RUNWAYS;
            case "land":
                return AirportManagerCommand.LAND;
            case "park":
                return AirportManagerCommand.PARK;
            case "global-state":
                return AirportManagerCommand.GLOBAL_STATE;
            case "cleaning":
                return AirportManagerCommand.CLEANING;
            case "refuel":
                return AirportManagerCommand.REFUEL;
            case "baggage-unload":
                return AirportManagerCommand.UNLOAD_BAGGAGE;
            case "cargo-unload":
                return AirportManagerCommand.UNLOAD_CARGO;
            case "maintenance":
                return AirportManagerCommand.MAINTENANCE;
            case "state":
                return AirportManagerCommand.STATE;
            case "takeoff":
                return AirportManagerCommand.TAKEOFF;
            case "free-runways":
                return AirportManagerCommand.FREERUNWAYS;
            case "exit":
                return AirportManagerCommand.EXIT;
            default:
                return AirportManagerCommand.UNKNOWN;
        }
    }
}
