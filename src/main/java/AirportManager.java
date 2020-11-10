
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class AirportManager {

    public static void main(String ... argv) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        Map<Integer,Plane> planes = new HashMap<>();
        Plane[] runwayArray = new Plane[10];

        boolean validJson = false;

        while (!validJson) {
            System.out.println("Please enter the file path of the JSON file");
            String jsonFilepath = myObj.nextLine();

            try {
                planes = readJson(jsonFilepath);

                if (planes != null) {
                    validJson = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Unable to read JSON file");
            }
        }

//        for (Plane plane :
//                planes) {
//            System.out.println("Plane id: " + plane.getId());
//            System.out.println("Destination: " + plane.getDestination());
//            System.out.println("Origin: " + plane.getOrigin());
//            System.out.println("Required services: " + plane.getRequiredServices());
//            System.out.println();
//        }

        while (true) {

            String command = myObj.nextLine();  // Read user input

            String[] stringSplit = command.split(" ");

            switch (stringSplit[0]) {
                case "land":
                    landPlane(stringSplit[1],planes,runwayArray);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Command not recognised");
            }

        }



    }

    private static void landPlane(String s, Map<Integer, Plane> planes, Plane[] runwayArray) {
        String[] stringSplit = s.split(",");

        try {
            int planeId = Integer.parseInt(stringSplit[0]);
            int runwayId = Integer.parseInt(stringSplit[1]);

            if (runwayArray[runwayId - 1] == null){
                runwayArray[runwayId - 1] = planes.get(planeId);

                planes.get(planeId).setState(PlaneState.LANDED);
            }

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Unable to land plane, invalid input");
        }

    }

    private static Map<Integer, Plane> readJson (String filepath) throws IOException {

        Map<Integer,Plane> planeList = new HashMap();

        JSONParser parser = new JSONParser();
        try {

            //Object obj = parser.parse(new InputStreamReader(new FileInputStream("/Users/user/Documents/OneDrive/Documents/Masters work/Plane.json")));
            Object obj = parser.parse(new InputStreamReader(new FileInputStream(filepath)));

            JSONArray planesJsonList = (JSONArray) obj;

            Iterator<JSONObject> iterator = planesJsonList.iterator();
            while (iterator.hasNext()) {

                Plane plane = jsonToPlane(iterator.next());

                if (plane != null) {
                    planeList.put(plane.getId(), plane);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return planeList;
    }

    private static Plane jsonToPlane(JSONObject jsonObject)  {
        try {
            int id = Integer.parseInt((String) jsonObject.get("Id"));

            JSONArray reqArray = (JSONArray) jsonObject.get("RequiredServices");

            List<RequiredServices> reqList = new ArrayList<>();

            Iterator<String> it = reqArray.iterator();

            while (it.hasNext()) {

                switch (it.next()) {
                    case "Cleaning":
                        if (!reqList.contains(RequiredServices.CLEANING))
                            reqList.add(RequiredServices.CLEANING);
                        break;
                    case "Refuel":
                        if (!reqList.contains(RequiredServices.REFUEL))
                            reqList.add(RequiredServices.REFUEL);
                        break;
                    case "Baggage Unload":
                        if (!reqList.contains(RequiredServices.BAGGAGE_UNLOAD))
                            reqList.add(RequiredServices.BAGGAGE_UNLOAD);
                        break;
                    case "Cargo Unload":
                        if (!reqList.contains(RequiredServices.CARGO_UNLOAD))
                            reqList.add(RequiredServices.CARGO_UNLOAD);
                        break;
                    case "Maintenance":
                        if (!reqList.contains(RequiredServices.MAINTENANCE))
                            reqList.add(RequiredServices.MAINTENANCE);
                        break;
                }

            }

            String origin = (String) jsonObject.get("Origin");
            String dest = (String) jsonObject.get("Destination");

            Plane plane = new Plane(id, reqList, origin, dest, PlaneState.FLYING);

            return plane;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
