package models;

import helper.ScannerHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AirportManagerCommand {
    LAND,
    UNKNOWN,
    EXIT;

    public static List<AirportManagerCommand> getAvailableCommands() {
        return Stream.of(
                AirportManagerCommand.LAND,
                AirportManagerCommand.EXIT
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    public static AirportManagerCommand parseCommand(String stringCommand) {
        if (stringCommand == null) return AirportManagerCommand.UNKNOWN;
        String uppercasedCommand = stringCommand.toUpperCase();
        try {
            return AirportManagerCommand.valueOf(uppercasedCommand);
        } catch (IllegalArgumentException e) {
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            return null;
        }
    }
}
