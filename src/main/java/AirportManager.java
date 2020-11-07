
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class AirportManager {


    public static void main(String ... argv) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object



        List<Plane> planes = new ArrayList<>();

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

        for (Plane plane :
                planes) {
            System.out.println("Plane id: " + plane.getId());
            System.out.println("Destination: " + plane.getDestination());
            System.out.println("Origin: " + plane.getOrigin());
            System.out.println("Required services: " + plane.getRequiredServices());
            System.out.println();
        }

        while (true) {

            String command = myObj.nextLine();  // Read user input

            String[] stringSplit = command.split(" ");

            switch (stringSplit[0]) {
                case "gay":
                    System.out.println("you're gay");
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("wtf?");
            }

        }



    }

    private static List<Plane> readJson (String filepath) throws IOException {

        List<Plane> planeList = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {

            //JSONObject jsonObject = (JSONObject) parser.parse("/Users/user/Documents/OneDrive/Documents/Masters work/Plane.json");
            Object obj = parser.parse(new InputStreamReader(new FileInputStream("/Users/user/Documents/OneDrive/Documents/Masters work/Plane.json")));

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray planesJsonList = (JSONArray) obj;

            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = planesJsonList.iterator();
            while (iterator.hasNext()) {
                //System.out.println(iterator.next());
                Plane plane = jsonToPlane(iterator.next());
                if (plane != null) {
                    planeList.add(plane);
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

            List<String> reqList = new ArrayList<String>();

            Iterator<String> it = reqArray.iterator();

            while (it.hasNext()) {
                reqList.add(it.next());
            }

            String origin = (String) jsonObject.get("Origin");
            String dest = (String) jsonObject.get("Destination");

            Plane plane = new Plane(id, reqList, origin, dest);

            return plane;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
