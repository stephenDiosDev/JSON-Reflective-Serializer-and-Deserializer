package program;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.IdentityHashMap;

public class Sender {
    private Visualizer visualizer;

    private ArrayList<Object> objects;  //holds the users declared objects

    public Sender(boolean run) {
        visualizer = new Visualizer();

        if(run)
            this.driver();
    }

    public void driver() {
        objects = visualizer.userSelectionMenu();
        IdentityHashMap hashMap = new IdentityHashMap();
        ArrayList<String> jsonStrings = new ArrayList<>();

        ArrayList<String> outputStrings = new ArrayList<>();

        JsonObject jsonObject;
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonFieldArray = Json.createArrayBuilder();

        for(Object ob : objects) {
            //jsonFieldArray.add(Serializer.serializeObject(ob, hashMap, jsonStrings));
            jsonStrings.add(Serializer.serializeObject(ob, hashMap, jsonStrings));
        }

        //jsonObjectBuilder.add("objects", jsonFieldArray.build());
        //System.out.println(jsonObjectBuilder.build().toString());

        //remove any empty strings
        for(String s : jsonStrings) {
            if(!s.equals(""))
                outputStrings.add(s);
        }

        //add the actual filled json strings to the objects array
        for(String st : outputStrings) {
            jsonFieldArray.add(st);
        }
        jsonObjectBuilder.add("objects", jsonFieldArray.build());
        jsonObject = jsonObjectBuilder.build();
        //System.out.println(jsonObject.toString());    //debug, but not pretty to look at

        //network code
    }

    public String toString() {
        return visualizer.toString();
    }
}