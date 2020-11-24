package program;

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

        //figure out how this will translate into the hashmap thing
        //maybe move hashmap out here and pass it in? Probably the best solution
        for(Object ob : objects) {
            jsonStrings.add(Serializer.serializeObject(ob, hashMap, jsonStrings));
        }

        //remove any empty strings
        for(String s : jsonStrings) {
            if(!s.equals(""))
                outputStrings.add(s);
        }

        System.out.println("About to print:");
        for(String st : outputStrings)
            System.out.println(st);

        System.out.println("Printed");

        //all output strings are now in outputString. We can send these out
    }

    public String toString() {
        return visualizer.toString();
    }
}