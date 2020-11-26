import helper.ScannerHelper;
import helper.ParserHelper;
import models.AirportManagerCommand;
import models.Runway;
import models.exception.*;

import java.io.IOException;

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
                case RUNWAYS:
                    runway.showRunways();
                    break;
                case GLOBAL_STATE:
                    runway.showGlobalState();
                    break;
                case STATE:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.showPlaneState(splittedString[1]);
                    } catch (PlaneStateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case FREERUNWAYS:
                    runway.showFreeRunways();
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
                case TAKEOFF:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.takeOffPlane(splittedString[1]);
                    } catch (TakeOffPlaneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case PARK:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.parkPlane(splittedString[1]);
                    } catch (ParkPlaneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case CLEANING:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.cleanPlane(splittedString[1]);
                    } catch (CleanPlaneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case REFUEL:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.refuelPlane(splittedString[1]);
                    } catch (RefuelPlaneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case UNLOAD_BAGGAGE:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.unloadBaggagePlane(splittedString[1]);
                    } catch (UnloadBaggagePlaneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case UNLOAD_CARGO:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.unloadCargoPlane(splittedString[1]);
                    } catch (UnloadCargoPlaneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case MAINTENANCE:
                    if (splittedString.length < 2) {
                        showWrongParametersNumberMessage();
                        continue;
                    }
                    try {
                        runway.maintainPlane(splittedString[1]);
                    } catch (MaintenancePlaneException e) {
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
        System.out.println();
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
