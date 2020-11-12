import helper.ScannerHelper;
import helper.ParserHelper;
import models.AirportManagerCommand;
import models.Plane;
import models.PlaneState;
import models.Runway;
import models.exception.LandingPlaneException;

import java.io.IOException;
import java.util.*;

public class AirportManager {
    public static void main(String ... argv) {
        Runway runway = null;

        showWelcomeMessage();
        while (runway == null) {
            String jsonFilepath = ScannerHelper.readLine();
            try {
                runway = Runway.createRunway(ParserHelper.loadPlanesInformation(jsonFilepath));
            } catch (IOException e) {
                //TODO: Should add proper error handling here other than printStackTrace.
                e.printStackTrace();
                System.out.println("Invalid JSON file. Please, try again.");
            }
        }

        // runway.showAvailablePlanesDescription();

        while (true) {
            askForInputMessage();
            String stringCommand = ScannerHelper.readLine();
            if (stringCommand == null) {
                showUnrecognizedCommandMessage();
                continue;
            }

            String[] splittedString = stringCommand.split(" ");
            if (splittedString.length <= 0) {
                showWrongParametersNumberMessage();
                continue;
            }
            AirportManagerCommand command = AirportManagerCommand.parseCommand(splittedString[0]);
            if (command == null) {
                showUnrecognizedCommandMessage();
                continue;
            }
            switch (command) {
                case GLOBAL_STATE:
                    runway.showGlobalState();
                    break;
                case LAND:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.landPlane(splittedString[1]);
                    } catch (LandingPlaneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case EXIT:
                    return;
                case UNKNOWN:
                    showUnrecognizedCommandMessage();
                    break;
            }
        }
    }

    private static void showWelcomeMessage() {
        System.out.println("Welcome to Plane Runway Manager");
        System.out.println("Please enter the path to the airport manager file.");
        System.out.println("The program won't start until a valid JSON file is provided.");
    }

    private static void askForInputMessage() {
        System.out.println("Enter an action to perform");
        System.out.println("The available actions are:");
        for (AirportManagerCommand action: AirportManagerCommand.getAvailableCommands()) {
            System.out.println(action);
        }
    }

    private static void showUnrecognizedCommandMessage() {
        System.out.println("Command not recognised. Please, try again.");
    }

    private static void showWrongParametersNumberMessage() {
        System.out.println("Command not recognised. Please, add the necessary arguments.");
    }
}
