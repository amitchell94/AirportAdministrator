package helper;

import models.Plane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ParserHelper {
    public static Map<Integer, Plane> loadPlanesInformation(String filepath) throws IOException {
        JSONParser parser = new JSONParser();
        JSONArray planesJson = null;
        try {
            // planeJson = parser.parse(new InputStreamReader(new FileInputStream("/Users/user/Documents/OneDrive/Documents/Masters work/Plane.json")));
            planesJson = (JSONArray) parser.parse(new InputStreamReader(new FileInputStream(filepath)));
        } catch (ParseException e) {
            //TODO: Should add proper error handling here other than printStackTrace.
            e.printStackTrace();
            return null;
        }
        Map<Integer, Plane> planeList = new HashMap();
        Iterator<JSONObject> iterator = planesJson.iterator();
        while (iterator.hasNext()) {
            try {
                Plane plane = new Plane(iterator.next());
                planeList.put(plane.getId(), plane);
            } catch (NumberFormatException e) {
                //TODO: Should add proper error handling here other than printStackTrace.
                e.printStackTrace();
            }
        }
        return planeList;
    }
}
