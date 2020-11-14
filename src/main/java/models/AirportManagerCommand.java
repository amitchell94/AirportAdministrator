package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AirportManagerCommand {
    LAND,
    GLOBAL_STATE,
    TAKEOFF,
    FREERUNWAYS,
    UNKNOWN,
    EXIT;

    public static List<AirportManagerCommand> getAvailableCommands() {
        return Stream.of(
                AirportManagerCommand.LAND,
                AirportManagerCommand.GLOBAL_STATE,
                AirportManagerCommand.TAKEOFF,
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
            case EXIT:
                return "Exit";
            case GLOBAL_STATE:
                return "Global-state";
            case FREERUNWAYS:
                return "Free-runways";
            case UNKNOWN:
                return "";
        }
        return "";
    }

    public static AirportManagerCommand parseCommand(String stringCommand) {
        if (stringCommand == null) return AirportManagerCommand.UNKNOWN;
        String lowercasedCommand = stringCommand.toLowerCase();
        switch (lowercasedCommand) {
            case "land":
                return AirportManagerCommand.LAND;
            case "global-state":
                return AirportManagerCommand.GLOBAL_STATE;
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
