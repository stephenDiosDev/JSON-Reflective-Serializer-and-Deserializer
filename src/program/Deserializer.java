package program;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;

public class Deserializer {
    /**
     * takes json string object and creates the object instances and returns it
     * @param source The json string object
     * @return
     */
    public static ArrayList<Object> deserializeObject(String source) {
        ArrayList<Object> objects = new ArrayList<>();

        JsonReader jsonReader = Json.createReader(new StringReader(source));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        //this now contains the cells in the master object array for easier parsing
        JsonArray objectArray = jsonObject.getJsonArray("objects");

        //debug prints
        //System.out.println(objectArray.toString().replace("\\", ""));
        //System.out.println("Size of object array: " + objectArray.size());

        //the object ID is the index in this array for that object
        //Ex: object with ID = 0 is at index 0
        Object[] createdObjects = new Object[objectArray.size()];

        for(int i = 0; i < objectArray.size(); i++) {
            jsonReader = Json.createReader(new StringReader(objectArray.getString(i)));
            jsonObject = jsonReader.readObject();
            System.out.println(jsonObject.toString().replace("\\", ""));

            int id = jsonObject.getInt("id");
            System.out.println("Object ID: " + id);

        }

        return objects;
    }

    private static Object deserializeJson(JsonObject jsonObject) {

        return new Object();
    }

}